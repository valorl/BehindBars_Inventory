package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Alcohol;
import ModelLayer.Fruit;
import ModelLayer.Product;


public class DBFruit implements IFDBFruit{

	private  Connection con;
	/** Creates a new instance of DBFruit */
	public DBFruit() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Fruit> getAllFruits(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}
	//get one fruit based on its id
	public Fruit findFruit(int productID, boolean retriveAssociation) throws Exception
	{   
		String wClause = "  productID = '" + productID + "'";


		Fruit fruit = null;
		try{
			fruit = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Fruit not found.");
		}

		return fruit;

	}
	
	public int insertFruit(Fruit fruit) throws Exception
	{ 
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new fruit
			String query="INSERT INTO fruit(productID, totalWeight)  VALUES(?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, fruit.getId());
			prepInsert.setDouble(2, fruit.getTotalWeight());

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();

		}//end try
		catch(SQLException ex){
			System.out.println("Fruit not inserted");
			throw new Exception ("Fruit not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateFruit(Fruit fruit) throws Exception
	{
		Fruit fruitObj  = fruit;
		int rc=-1;

		//			String query="UPDATE fruit SET "+
		//					"id ='"+ fruitObj.getId()+"', "+
		//					"name ='"+ fruitObj.getName()+"', "+
		//					"purchasePrice ='"+ 	fruitObj.getPurchasePrice()  + "',"  +
		//					"salesPrice ='"+   fruitObj.getSalesPrice()  + "',"  +
		//					"rentPrice ='"+   fruitObj.getRentPrice() + "',"  +
		//					"countryOfOrigin ='"+   fruitObj.getCountryOfOrigin() + "',"  +
		//					"minStock ='"+   fruitObj.getMinStock() + "',"  +
		//					"currStock ='"+    fruitObj.getCurrStock() + "',"  +
		//					"type ='"+  fruitObj.getType() + "',"  +
		//					"supplierId ='"+  fruitObj.getSupplierId() + "'" +
		//					" WHERE id = '"+ fruitObj.getId() + "'";
		//			System.out.println("Update query:" + query);

		PreparedStatement prepUpdate = null;

		try{ // update fruit

			String query="UPDATE fruit SET productID = ?, totalWeight = ? WHERE productID = ? ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, fruit.getId());
			prepUpdate.setDouble(2, fruit.getTotalWeight());
			prepUpdate.setInt(3, fruit.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();
		}//try to close
		catch(Exception ex){
			System.out.println("Update exception in fruit db: "+ex);
		}
		finally 
		{
			prepUpdate.close();
		}
		return(rc);
	}

	public int delete(int productID) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ 


			String query="DELETE FROM fruit WHERE productID = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, productID);

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();
		}
		catch(Exception ex){
			System.out.println("Delete exception in fruit db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public Fruit singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Fruit fruitObj = new Fruit();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the fruit from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				fruitObj = buildFruit(results);
				stmt.close();
				if(retrieveAssociation){}
			}
			else{ //no fruit was found
				fruitObj = null;
				throw new Exception("Fruit not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return fruitObj;
	}
	
	private ArrayList<Fruit> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Fruit> list = new ArrayList<Fruit>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the fruit from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Fruit fruitObj = new Fruit();
				fruitObj = buildFruit(results);	
				list.add(fruitObj);
				if(retrieveAssociation) {}				
				
			} //end while
			stmt.close();     			

			if(list.size() == 0) 
			{
				throw new Exception("No fruits found.");
			}

		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Fruit buildFruit(ResultSet results)
	{   
		Fruit fruitObj = null;
		try{ 
			fruitObj.setId(results.getInt("id"));
			fruitObj.setTotalWeight(results.getDouble("totalWeight"));
		}
		catch(Exception e)
		{
			System.out.println("Error in building the fruit object");
		}
		return fruitObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT productID, totalWeight FROM fruit";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}

}