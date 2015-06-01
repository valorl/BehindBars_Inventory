package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Measurable;
import ModelLayer.Product;
import ModelLayer.QuantLoc;
import UILayer.TypeManager;

public class DBProduct implements IFDBProduct{

	private  Connection con;
	/** Creates a new instance of DBProduct */
	public DBProduct() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Product> getAllProducts(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}
	//get every product of given category
	public ArrayList<Product> getAllOf(String category, boolean retrieveAssociation) throws Exception 
	{
		String wClause = "type = '" + category + "'";

		return muchWhere(wClause, retrieveAssociation);
	}
	//get one product based on its id
	public Product findProduct(int id, boolean retriveAssociation) throws Exception
	{   
		String wClause = "  id = '" + id + "'";

		Product product = null;
		try{
			product = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Product not found.");
		}

		return product;

	}
	
	// get one product based on its name
	public Product findProduct(String name, boolean retriveAssociation) throws Exception
	{   
		String wClause = "  name = '" + name + "'";

		Product product = null;
		try{
			product = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Product not found.");
		}

		return product;

	}

	public ArrayList<String> getTypes() throws Exception
	{
		ArrayList<String> types = new ArrayList<String>();
		
		String query="SELECT DISTINCT type FROM product";
		PreparedStatement prepStmt = null;
		try{
			prepStmt = con.prepareStatement(query);
			
			ResultSet results = prepStmt.executeQuery();
			
			while(results.next()) 
			{
				types.add(results.getString("type"));
			}
			
			if(!(types.size() > 0)) 
			{
				throw new Exception("No types returned.");
			}
			
		}
		catch(SQLException ex) 
		{
			ex.printStackTrace();
			throw new Exception("Error while getting product types.");
		}
		
		return types;
	}



	//insert a new product

	public int insertProduct(Product product) throws Exception
	{  //call to get the next id number
		int nextid = GetMax.getMaxId("Select max(id) from product");

		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		product.setId(nextid);


		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new product
			String query="INSERT INTO product(id, name, cost, price, unitVolume, type, purchased)  VALUES(?,?,?,?,?,?,?)";

			DbConnection.startTransaction();
			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, product.getId());
			prepInsert.setString(2, product.getName());
			prepInsert.setDouble(3, product.getCost());
			prepInsert.setDouble(4, product.getPrice());
			prepInsert.setDouble(5, product.getUnitVolume());
			prepInsert.setString(6, product.getType());
			prepInsert.setInt(7, product.getPurchased());


			//						+
			//						product.getId() + "','" +
			//						product.getName()  + "','"  +
			//						product.getPurchasePrice()  + "','"  +
			//						product.getSalesPrice()  + "','"  +
			//						product.getRentPrice() + "','"  +
			//						product.getCountryOfOrigin() + "','"  +
			//						product.getMinStock() + "','"  +
			//						product.getCurrStock() + "','"  +
			//						product.getType() + "','"  +
			//						product.getSupplierId() + "')";
			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();

			if(product.getQuantLocs() != null &&product.getQuantLocs().size() > 0) {
				DBQuantLoc dbQL = new DBQuantLoc();

				for (QuantLoc qLoc : product.getQuantLocs()) 
				{
					dbQL.insertQuantLoc(qLoc, product);
				}
			}

			if(product.getType() != null) {
				if(TypeManager.isMeasurableDrinkType(product.getType())) 
				{
					Measurable measurable = (Measurable) product;
					DBMeasurable dbMeasurable = new DBMeasurable();
					dbMeasurable.insertMeasurable(measurable);
				}
			}
			else 
			{
				throw new Exception("Product type missing.");
			}
			
			DbConnection.commitTransaction();

		}//end try
		catch(SQLException ex){
			DbConnection.rollbackTransaction();
			ex.printStackTrace();
			System.out.println(ex);
			System.out.println("Product not inserted");
			throw new Exception ("Product is not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateProduct(Product product) throws Exception
	{
		Product productObj  = product;
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

			String query="UPDATE product SET id = ?, name = ?, cost = ?, price = ?, unitVolume = ?, type = ?, purchased = ? WHERE id = ? ";
			
			DbConnection.startTransaction();
			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, product.getId());
			prepUpdate.setString(2, product.getName());
			prepUpdate.setDouble(3, product.getCost());
			prepUpdate.setDouble(4, product.getPrice());
			prepUpdate.setDouble(5, product.getUnitVolume());
			prepUpdate.setString(6, product.getType());
			prepUpdate.setInt(7, product.getPurchased());
			prepUpdate.setInt(8, product.getId());

			prepUpdate.setQueryTimeout(5);
			rc = prepUpdate.executeUpdate();
			

			if(product.getQuantLocs() != null &&product.getQuantLocs().size() > 0) {
				DBQuantLoc dbQL = new DBQuantLoc();

				for (QuantLoc qLoc : product.getQuantLocs()) 
				{
					dbQL.updateQuantLoc(qLoc, product);
				}
			}

			if(product.getType() != null) {
				if(TypeManager.isMeasurableDrinkType(product.getType())) 
				{
					Measurable measurable = (Measurable) product;
					DBMeasurable dbMeasurable = new DBMeasurable();
					//dbMeasurable.updateMeasurable(measurable);
				}
			}
			DbConnection.commitTransaction();
		}//try to close
		catch(Exception ex){
			DbConnection.rollbackTransaction();
			System.out.println("Update exception in product db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);
	}

	public int delete(Product product) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ // delete from product


			String query="DELETE FROM product WHERE id = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, product.getId());

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();

			if(product.getType() != null) {
				if(TypeManager.isMeasurableDrinkType(product.getType())) 
				{
					//Measurable measurable = (Measurable) product;
					DBMeasurable dbMeasurable = new DBMeasurable();
					dbMeasurable.delete(dbMeasurable.findMeasurable(product.getId(), false));
				}

			}
			else 
			{
				throw new Exception("Product type missing.");
			}

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

	public Product singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Product productObj = new Product();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the product from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				productObj = buildProduct(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){

					DBQuantLoc dbQL = new DBQuantLoc();
					ArrayList<QuantLoc> qLList = dbQL.getProductQLs(productObj, false);
					if(qLList != null && qLList.size() > 0) 
					{
						productObj.setQuantLocs(qLList);
					}

				}
			}
			else{ //no product was found
				productObj = null;
				throw new Exception("Product not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return productObj;
	}


	private ArrayList<Product> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Product> list = new ArrayList<Product>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the product from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Product productObj = new Product();
				productObj = buildProduct(results);	

				DBQuantLoc dbQL = new DBQuantLoc();
				if(retrieveAssociation) {

					ArrayList<QuantLoc> qLList = dbQL.getProductQLs(productObj, false);
					if(qLList != null && qLList.size() > 0) 
					{
						productObj.setQuantLocs(qLList);
					}
				}
				list.add(productObj);
			}//end while
			stmt.close();     


			if(list.size() == 0) 
			{
				throw new Exception("No products found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Product buildProduct(ResultSet results)
	{   
		Product productObj = null;
		try{ // the columns from the table product are used

			String type = results.getString("type");
			if(TypeManager.isMeasurableDrinkType(type)) 
			{
				productObj = new Measurable();
			}
			else 
			{
				productObj = new Product();
			}

			productObj.setId(results.getInt("id"));
			productObj.setName(results.getString("name"));
			productObj.setCost(results.getDouble("cost"));
			productObj.setPrice(results.getDouble("price"));
			productObj.setUnitVolume(results.getDouble("unitVolume"));
			productObj.setType(results.getString("type"));
			productObj.setPurchased(results.getInt("purchased"));

			if(TypeManager.isMeasurableType(type)) 
			{
				DBMeasurable dbMeasurable = new DBMeasurable();
				Measurable measurableObj = dbMeasurable.findMeasurable(results.getInt("id"), false);

				Measurable castedProduct = (Measurable) productObj;
				castedProduct.setFullWeight(measurableObj.getFullWeight());
				castedProduct.setEmptyWeight(measurableObj.getEmptyWeight());
				castedProduct.setDensity(measurableObj.getDensity());
				castedProduct.setTotalMeasured(measurableObj.getTotalMeasured());
				productObj = castedProduct;

			}
		}
		catch(Exception e)
		{
			System.out.println("Error in building the product object");
		}


		return productObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT id, name, cost, price, unitVolume, type, purchased  FROM product";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}



}
