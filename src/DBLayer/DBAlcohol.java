package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Alcohol;
import ModelLayer.Product;


public class DBAlcohol implements IFDBAlcohol{

	private  Connection con;
	/** Creates a new instance of DBAlcohol */
	public DBAlcohol() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Alcohol> getAllAlcohols(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}
	//get one alcohol based on its id
	public Alcohol findAlcohol(int productID, boolean retriveAssociation) throws Exception
	{   String wClause = "  productID = '" + productID + "'";

	Alcohol alcohol = null;
	try{
		alcohol = singleWhere(wClause, retriveAssociation);
	}
	catch(Exception ex) 
	{
		ex.printStackTrace();
		throw new Exception("Alcohol not found.");
	}

	return alcohol;

	}


	//insert a new alcohol

	public int insertAlcohol(Alcohol alcohol) throws Exception
	{ 
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new alcohol
			String query="INSERT INTO alcohol(productID, fullWeight, emptyWeight, density, totalVolume)  VALUES('?,?,?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, alcohol.getId());
			prepInsert.setDouble(2, alcohol.getFullWeight());
			prepInsert.setDouble(3, alcohol.getEmptyWeight());
			prepInsert.setDouble(4, alcohol.getDensity());
			prepInsert.setDouble(5, alcohol.getTotalVolume());

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();
			
			DBCocktails dbCocktails = new DBCocktails();
			dbCocktails.insertContents(alcohol);


		}//end try
		catch(SQLException ex){
			System.out.println("Alcohol not inserted");
			throw new Exception ("Alcohol not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateAlcohol(Alcohol alcohol) throws Exception
	{
		Alcohol alcoholObj  = alcohol;
		int rc=-1;

		//			String query="UPDATE alcohol SET "+
		//					"id ='"+ alcoholObj.getId()+"', "+
		//					"name ='"+ alcoholObj.getName()+"', "+
		//					"purchasePrice ='"+ 	alcoholObj.getPurchasePrice()  + "',"  +
		//					"salesPrice ='"+   alcoholObj.getSalesPrice()  + "',"  +
		//					"rentPrice ='"+   alcoholObj.getRentPrice() + "',"  +
		//					"countryOfOrigin ='"+   alcoholObj.getCountryOfOrigin() + "',"  +
		//					"minStock ='"+   alcoholObj.getMinStock() + "',"  +
		//					"currStock ='"+    alcoholObj.getCurrStock() + "',"  +
		//					"type ='"+  alcoholObj.getType() + "',"  +
		//					"supplierId ='"+  alcoholObj.getSupplierId() + "'" +
		//					" WHERE id = '"+ alcoholObj.getId() + "'";
		//			System.out.println("Update query:" + query);

		PreparedStatement prepUpdate = null;

		try{ // update alcohol

			String query="UPDATE alcohol SET productID = '?', fullWeight = '?', emptyWeight = '?', density = '?', totalVolume = '?' WHERE productID = '?' ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, alcohol.getId());
			prepUpdate.setDouble(2, alcohol.getFullWeight());
			prepUpdate.setDouble(3, alcohol.getEmptyWeight());
			prepUpdate.setDouble(4, alcohol.getDensity());
			prepUpdate.setDouble(5, alcohol.getTotalVolume());
			prepUpdate.setInt(6, alcohol.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();


		}//try to close
		catch(Exception ex){

			System.out.println("Update exception in alcohol db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);
	}

	public int delete(Alcohol alcohol) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ 


			String query="DELETE FROM alcohol WHERE id = '?'";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, alcohol.getId());

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();
			
			DBCocktails dbCocktails = new DBCocktails();
			dbCocktails.deleteContents(alcohol);

		}
		catch(Exception ex){
			System.out.println("Delete exception in alcohol db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public Alcohol singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Alcohol alcoholObj = new Alcohol();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the alcohol from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				alcoholObj = buildAlcohol(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){
					DBCocktails dbCocktails = new DBCocktails();
					alcoholObj = dbCocktails.retrieveIngredients(alcoholObj);
				}
			}
			else{ //no alcohol was found
				alcoholObj = null;
				throw new Exception("Alcohol not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return alcoholObj;
	}

	private ArrayList<Alcohol> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Alcohol> list = new ArrayList<Alcohol>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the alcohol from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Alcohol alcoholObj = new Alcohol();
				alcoholObj = buildAlcohol(results);	
				
				if(retrieveAssociation) {
					DBCocktails dbCocktails = new DBCocktails();
					alcoholObj = dbCocktails.retrieveIngredients(alcoholObj);
				}				
				list.add(alcoholObj);
			} //end while
			stmt.close();     
			

			if(list.size() == 0) 
			{
				throw new Exception("No alcohols found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Alcohol buildAlcohol(ResultSet results)
	{   
		Alcohol alcoholObj = null;
		try{ 


			alcoholObj.setId(results.getInt("id"));
			alcoholObj.setFullWeight(results.getDouble("fullWeight"));
			alcoholObj.setEmptyWeight(results.getDouble("emptyWeight"));
			alcoholObj.setDensity(results.getDouble("density"));
			alcoholObj.setTotalVolume(results.getDouble("totalVolume"));

		}
		catch(Exception e)
		{
			System.out.println("Error in building the alcohol object");
		}
		return alcoholObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT productID, fullWeight, emptyWeight, density, totalVolume FROM alcohol";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}


}
