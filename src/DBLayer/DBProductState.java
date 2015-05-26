package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.QuantLoc;

public class DBProductState implements IFDBProductState{

	private  Connection con;
	/** Creates a new instance of DBProductState */
	public DBProductState() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<ProductState> getAllProductStates(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}

	public ProductState findProductState(int id, boolean retriveAssociation) throws Exception
	{  
		String wClause = "  id = '" + id + "'";

		ProductState productState = null;
		try{
			productState = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("ProductState not found.");
		}

		return productState;
	}
	public ArrayList<ProductState> findWeekStates(int weekID, boolean retrieveAssociation) throws Exception 
	{
		String wClause = " weekID = '" + weekID + "'";
		return muchWhere(wClause, retrieveAssociation);
	}


	//insert a new productState

	public int insertProductState(ProductState productState, int weekID) throws Exception
	{  //call to get the next id number
		int nextid = GetMax.getMaxId("Select max(id) from productState");
		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		productState.setId(nextid);


		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new productState
			String query="INSERT INTO productState(id, currentCost, currentPrice, purchased, productID, weekID, sold)  VALUES(?,?,?,?,?,?,?)";

//			DbConnection.startTransaction();
			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, productState.getId());
			prepInsert.setDouble(2, productState.getCurrentCost());
			prepInsert.setDouble(3, productState.getCurrentPrice());
			prepInsert.setInt(4, productState.getPurchased());
			prepInsert.setInt(5, productState.getProduct().getId());
			prepInsert.setInt(6, weekID);
			prepInsert.setDouble(7, productState.getSold());


			prepInsert.setQueryTimeout(5);
			prepInsert.executeUpdate();
//			DbConnection.commitTransaction();

			try {
				if(productState.getQuantLocs() != null &&  productState.getQuantLocs().size() > 0) {
					DBQuantLoc dbQL = new DBQuantLoc();

					for (QuantLoc qLoc : productState.getQuantLocs()) 
					{
						dbQL.insertQuantLoc(qLoc, productState);
					}
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}


		}//end try
		catch(SQLException ex){
//			DbConnection.rollbackTransaction();
			System.out.println("ProductState not inserted");
			throw new Exception ("ProductState is not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateProductState(ProductState productState, int weekID) throws Exception
	{
		//ProductState productStateObj  = productState;
		int rc=-1;

		PreparedStatement prepUpdate = null;

		try{ // update productState

			String query="UPDATE productState SET currentCost = ?, currentPrice = ?, purchased = ?, productID = ?, weekID = ?, sold = ? WHERE weekID = ? ";

			DbConnection.startTransaction();
			prepUpdate = con.prepareStatement(query);
			prepUpdate.setDouble(1, productState.getCurrentCost());
			prepUpdate.setDouble(2, productState.getCurrentPrice());
			prepUpdate.setInt(3, productState.getPurchased());
			prepUpdate.setInt(4, productState.getProduct().getId());
			prepUpdate.setInt(5, weekID);
			prepUpdate.setDouble(6, productState.getSold());
			prepUpdate.setInt(7, weekID);

			prepUpdate.setQueryTimeout(5);
			prepUpdate.executeUpdate();
			DbConnection.commitTransaction();

			try {
			if(productState.getQuantLocs() != null && productState.getQuantLocs().size() > 0) {
				DBQuantLoc dbQL = new DBQuantLoc();

				for (QuantLoc qLoc : productState.getQuantLocs()) 
				{
					dbQL.insertQuantLoc(qLoc, productState);
				}
			}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}


		}//try to close
		catch(Exception ex){
			DbConnection.rollbackTransaction();
			ex.printStackTrace();
			System.out.println("Update exception in productState db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);
	}

	public int delete(int id) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ // delete from productState


			String query="DELETE FROM productState WHERE id = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, id);

			prepDelete.setQueryTimeout(5);
			prepDelete.executeUpdate();

		}//try to close	
		catch(Exception ex){
			System.out.println("Delete exception in productState db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public ProductState singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		ProductState productStateObj = new ProductState();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the productState from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				productStateObj = buildProductState(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){

					DBProduct dbProduct = new DBProduct();
					Product product = dbProduct.findProduct(productStateObj.getProduct().getId(), false);
					productStateObj.setProduct(product);

					ArrayList<QuantLoc> quantLocs = new ArrayList<QuantLoc>();
					DBQuantLoc dbQuantLoc = new DBQuantLoc();
					quantLocs = dbQuantLoc.getStateQLs(productStateObj, false);
					if(quantLocs != null && quantLocs.size() > 0) {
						productStateObj.setQuantLocs(quantLocs);
					}


				}
			}
			else{ //no productState was found
				productStateObj = null;
				throw new Exception("ProductState not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return productStateObj;
	}

	private ArrayList<ProductState> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<ProductState> list = new ArrayList<ProductState>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the productState from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				ProductState productStateObj = new ProductState();
				productStateObj = buildProductState(results);	

				if(retrieveAssociation) {
					DBProduct dbProduct = new DBProduct();
					Product product = dbProduct.findProduct(productStateObj.getProduct().getId(), false);
					productStateObj.setProduct(product);

					ArrayList<QuantLoc> quantLocs = new ArrayList<QuantLoc>();
					DBQuantLoc dbQuantLoc = new DBQuantLoc();
					quantLocs = dbQuantLoc.getStateQLs(productStateObj, false);
					if(quantLocs != null && quantLocs.size() > 0) {
						productStateObj.setQuantLocs(quantLocs);
					}

				}
				list.add(productStateObj);
			}//end while
			stmt.close();     
			if(retrieveAssociation) {}

			if(list.size() == 0) 
			{
				throw new Exception("No productStates found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private ProductState buildProductState(ResultSet results)
	{   
		ProductState productStateObj = new ProductState();
		try{ // the columns from the table productState are used

			DBProduct dbProduct = new DBProduct();
			Product product = dbProduct.findProduct(results.getInt("productID"), false);
			productStateObj.setProduct(product);

			productStateObj.setId(results.getInt("id"));
			productStateObj.setCurrentCost(results.getDouble("currentCost"));
			productStateObj.setCurrentPrice(results.getDouble("currentPrice"));
			productStateObj.setPurchased(results.getInt("purchased"));			
			productStateObj.setProduct(product);
			productStateObj.setSold(results.getDouble("sold"));


		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error in building the productState object");
		}


		return productStateObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT id, currentCost, currentPrice, purchased, productID, weekID, sold FROM productState";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}


}
