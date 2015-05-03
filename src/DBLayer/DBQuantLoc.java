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

public class DBQuantLoc implements IFDBQuantLoc{

	private  Connection con;
	/** Creates a new instance of DBQuantLoc */
	public DBQuantLoc() {
		con = DbConnection.getInstance().getDBcon();
	}
	
	// Get all QLs of Product
	@Override
	public ArrayList<QuantLoc> getProductQLs(Product product,boolean retriveAssociation) throws Exception {
		return muchWhere("productID = '" + product.getId() + "'", false);
	}
	// Get all QLs of ProductState
	@Override
	public ArrayList<QuantLoc> getStateQLs(ProductState state,boolean retriveAssociation) throws Exception {
		return muchWhere("productID = '" + state.getId() + "'", false);
	}
	// FIND - ID
	@Override
	public QuantLoc findQuantLoc(int id, boolean retriveAssociation) throws Exception {
		return singleWhere("id = '" + id + "'", false);
	} 
	// FIND - PRODUCT
	@Override
	public QuantLoc findQuantLoc(Product product, boolean retriveAssociation) throws Exception {
		return singleWhere("productID = " + product.getId(), false);
	}
	// FIND - PRODUCT STATE
	@Override
	public QuantLoc findQuantLoc(ProductState state, boolean retriveAssociation) throws Exception {
		return singleWhere("stateID = " + state.getId(), false);
	}

	// INSERT - QL in Product	
	@Override
	public int insertQuantLoc(QuantLoc quantLoc,Product product) throws Exception {
		
		int nextid = GetMax.getMaxId("Select max(id) from quantLoc");
		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		quantLoc.setId(nextid);
		
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new alcohol
			String query="INSERT INTO quantloc(id, quantity, location, productID, stateID)  VALUES('?,?,?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, quantLoc.getId());
			prepInsert.setDouble(2, quantLoc.getQuantity());
			prepInsert.setString(3, quantLoc.getLocation());
			prepInsert.setInt(4, product.getId());
			prepInsert.setNull(5, java.sql.Types.INTEGER);

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("QuantLoc not inserted");
			throw new Exception ("QuantLoc not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}
	
	// INSERT - QL in ProductState
	@Override
	public int insertQuantLoc(QuantLoc quantLoc,ProductState state) throws Exception {
		
		int nextid = GetMax.getMaxId("Select max(id) from quantLoc");
		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		quantLoc.setId(nextid);
		
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new alcohol
			String query="INSERT INTO quantloc(id, quantity, location, productID, stateID)  VALUES('?,?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, quantLoc.getId());
			prepInsert.setDouble(2, quantLoc.getQuantity());
			prepInsert.setString(3, quantLoc.getLocation());
			prepInsert.setNull(4, java.sql.Types.INTEGER);
			prepInsert.setInt(5, state.getId());

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("QuantLoc not inserted");
			throw new Exception ("QuantLoc not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	// UPDATE - PRODUCT
	@Override
	public int updateQuantLoc(QuantLoc quantLoc, Product product) throws Exception {
		QuantLoc QLObj  = quantLoc;
		int rc=-1;

		//			String query="UPDATE product SET "+
		//					"id ='"+ productObj.getId()+"', "+
		//					"name ='"+ productObj.getName()+"', "+
		//					"purchasePrice ='"+ 	productObj.getPurchasePrice()  + "',"  +
		//					"salesPrice ='"+   productObj.getSalesPrice()  + "',"  +
		//					"rentPrice ='"+   productObj.getRentPrice() + "',"  +
		//					"countryOfOrigin ='"+   productObj.getCountryOfOrigin() + "',"  +
		//					"minStock ='"+   productObj.getMinStock() + "',"  +
		//					"currStock ='"+    productObj.getCurrStock() + "',"  +
		//					"type ='"+  productObj.getType() + "',"  +
		//					"supplierId ='"+  productObj.getSupplierId() + "'" +
		//					" WHERE id = '"+ productObj.getId() + "'";
		//			System.out.println("Update query:" + query);

		PreparedStatement prepUpdate = null;

		try{ // update product

			String query="UPDATE quantLoc SET id = '?', quantity = '?', location = '?', productID = '?', stateID = '?' WHERE id = '?' ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, QLObj.getId());
			prepUpdate.setDouble(2, QLObj.getQuantity());
			prepUpdate.setString(3, QLObj.getLocation());
			prepUpdate.setInt(4, product.getId());
			prepUpdate.setNull(5, java.sql.Types.INTEGER);
			prepUpdate.setInt(6, QLObj.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();

		}//try to close
		catch(Exception ex){
			System.out.println("Update exception in product db: "+ex);
		}
		finally 
		{
			prepUpdate.close();
		}
		return(rc);
	}
	// UPDATE - PRODUCT STATE
	@Override
	public int updateQuantLoc(QuantLoc quantLoc, ProductState state) throws Exception {
		QuantLoc QLObj  = quantLoc;
		int rc=-1;

		//			String query="UPDATE product SET "+
		//					"id ='"+ productObj.getId()+"', "+
		//					"name ='"+ productObj.getName()+"', "+
		//					"purchasePrice ='"+ 	productObj.getPurchasePrice()  + "',"  +
		//					"salesPrice ='"+   productObj.getSalesPrice()  + "',"  +
		//					"rentPrice ='"+   productObj.getRentPrice() + "',"  +
		//					"countryOfOrigin ='"+   productObj.getCountryOfOrigin() + "',"  +
		//					"minStock ='"+   productObj.getMinStock() + "',"  +
		//					"currStock ='"+    productObj.getCurrStock() + "',"  +
		//					"type ='"+  productObj.getType() + "',"  +
		//					"supplierId ='"+  productObj.getSupplierId() + "'" +
		//					" WHERE id = '"+ productObj.getId() + "'";
		//			System.out.println("Update query:" + query);

		PreparedStatement prepUpdate = null;

		try{ // update product

			String query="UPDATE quantLoc SET id = '?', quantity = '?', location = '?', productID = '?', stateID = '?' WHERE id = '?' ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, QLObj.getId());
			prepUpdate.setDouble(2, QLObj.getQuantity());
			prepUpdate.setString(3, QLObj.getLocation());
			prepUpdate.setNull(4, java.sql.Types.INTEGER);
			prepUpdate.setInt(5, state.getId());
			prepUpdate.setInt(6, QLObj.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();

		}//try to close
		catch(Exception ex){
			System.out.println("Update exception in product db: "+ex);
		}
		finally 
		{
			prepUpdate.close();
		}
		return(rc);
	}

	public int delete(QuantLoc quantLoc) throws Exception
	{
		int rc=-1;
		PreparedStatement prepDelete = null;

		try{ // delete from product
			String query="DELETE FROM quantLoc WHERE id = '?'";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, quantLoc.getId());

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();
		}//try to close	
		catch(Exception ex){
			System.out.println("Delete exception in product db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public QuantLoc singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		QuantLoc QLObj = new QuantLoc();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the QL from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				QLObj = buildQuantLoc(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){}
			}
			else{ //no QL was found
				QLObj = null;
				throw new Exception("QuantLoc not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return QLObj;
	}
	
	private ArrayList<QuantLoc> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<QuantLoc> list = new ArrayList<QuantLoc>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the QL from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				QuantLoc QLObj = new QuantLoc();
				QLObj = buildQuantLoc(results);	
				list.add(QLObj);
			}//end while
			stmt.close();     
			if(retrieveAssociation) {}

			if(list.size() == 0) 
			{
				throw new Exception("No QLs found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	private QuantLoc buildQuantLoc(ResultSet results)
	{   
		QuantLoc QLObj = null;
		try{ // the columns from the table product are used
			QLObj.setId(results.getInt("id"));
			QLObj.setQuantity(results.getDouble("quantity"));
			QLObj.setLocation(results.getString("location"));
		}
		catch(Exception e)
		{
			System.out.println("Error in building the product object");
		}
		return QLObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT id, quantity, location, productID, stateID FROM quantLoc";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}



}
