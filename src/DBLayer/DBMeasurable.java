package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Measurable;
import ModelLayer.Product;


public class DBMeasurable implements IFDBMeasurable{

	private  Connection con;
	/** Creates a new instance of DBMeasurable */
	public DBMeasurable() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Measurable> getAllMeasurables(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}
	//get one measurable based on its id
	public Measurable findMeasurable(int productID, boolean retriveAssociation) throws Exception
	{   String wClause = "  productID = '" + productID + "'";

	Measurable measurable = null;
	try{
		measurable = singleWhere(wClause, retriveAssociation);
	}
	catch(Exception ex) 
	{
		ex.printStackTrace();
		throw new Exception("Measurable not found.");
	}

	return measurable;

	}


	//insert a new measurable

	public int insertMeasurable(Measurable measurable) throws Exception
	{ 
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new measurable
			String query="INSERT INTO measurable(productID, fullWeight, emptyWeight, totalMeasured)  VALUES(?,?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, measurable.getId()); 
			prepInsert.setDouble(2, measurable.getFullWeight());
			prepInsert.setDouble(3, measurable.getEmptyWeight());
			prepInsert.setDouble(4, measurable.getTotalMeasured());

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();
			
			DBCocktails dbCocktails = new DBCocktails();
			dbCocktails.insertContents(measurable);


		}//end try
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("Measurable not inserted");
			throw new Exception ("Measurable not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateMeasurable(Measurable measurable) throws Exception
	{
		Measurable measurableObj  = measurable;
		int rc=-1;

		//			String query="UPDATE measurable SET "+
		//					"id ='"+ measurableObj.getId()+"', "+
		//					"name ='"+ measurableObj.getName()+"', "+
		//					"purchasePrice ='"+ 	measurableObj.getPurchasePrice()  + "',"  +
		//					"salesPrice ='"+   measurableObj.getSalesPrice()  + "',"  +
		//					"rentPrice ='"+   measurableObj.getRentPrice() + "',"  +
		//					"countryOfOrigin ='"+   measurableObj.getCountryOfOrigin() + "',"  +
		//					"minStock ='"+   measurableObj.getMinStock() + "',"  +
		//					"currStock ='"+    measurableObj.getCurrStock() + "',"  +
		//					"type ='"+  measurableObj.getType() + "',"  +
		//					"supplierId ='"+  measurableObj.getSupplierId() + "'" +
		//					" WHERE id = '"+ measurableObj.getId() + "'";
		//			System.out.println("Update query:" + query);

		PreparedStatement prepUpdate = null;

		try{ // update measurable

			String query="UPDATE measurable SET productID = ?, fullWeight = ?, emptyWeight = ?, totalMeasured = ? WHERE productID = ? ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, measurable.getId());
			prepUpdate.setDouble(2, measurable.getFullWeight());
			prepUpdate.setDouble(3, measurable.getEmptyWeight());
			prepUpdate.setDouble(5, measurable.getTotalMeasured());
			prepUpdate.setInt(6, measurable.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();


		}//try to close
		catch(Exception ex){

			System.out.println("Update exception in measurable db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);
	}

	public int delete(Measurable measurable) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ 


			String query="DELETE FROM measurable WHERE id = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, measurable.getId());

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();
			
			DBCocktails dbCocktails = new DBCocktails();
			dbCocktails.deleteContents(measurable);

		}
		catch(Exception ex){
			System.out.println("Delete exception in measurable db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public Measurable singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Measurable measurableObj = new Measurable();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the measurable from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				measurableObj = buildMeasurable(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){
					DBCocktails dbCocktails = new DBCocktails();
					measurableObj = dbCocktails.retrieveIngredients(measurableObj);
				}
			}
			else{ //no measurable was found
				measurableObj = null;
				throw new Exception("Measurable not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return measurableObj;
	}

	private ArrayList<Measurable> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Measurable> list = new ArrayList<Measurable>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the measurable from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Measurable measurableObj = new Measurable();
				measurableObj = buildMeasurable(results);	
				
				if(retrieveAssociation) {
					DBCocktails dbCocktails = new DBCocktails();
					measurableObj = dbCocktails.retrieveIngredients(measurableObj);
				}				
				list.add(measurableObj);
			} //end while
			stmt.close();     
			

			if(list.size() == 0) 
			{
				throw new Exception("No measurables found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Measurable buildMeasurable(ResultSet results)
	{   
		Measurable measurableObj = new Measurable();
		try{ 


			measurableObj.setId(results.getInt("productId"));
			measurableObj.setFullWeight(results.getDouble("fullWeight"));
			measurableObj.setEmptyWeight(results.getDouble("emptyWeight"));
			measurableObj.setTotalMeasured(results.getDouble("totalMeasured"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error in building the measurable object");
		}
		return measurableObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT productID, fullWeight, emptyWeight, totalMeasured FROM measurable";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}


}
