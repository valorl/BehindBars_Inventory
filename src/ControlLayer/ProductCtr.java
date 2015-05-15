 package ControlLayer;

import java.util.ArrayList;

import DBLayer.DBProduct;
import ModelLayer.Product;
import ModelLayer.QuantLoc;

public class ProductCtr {
	
	private DBProduct dbProduct;
	
	public ProductCtr()
	{
		dbProduct = new DBProduct();
	}
	
	public void createProduct(Product product) throws Exception
	{
		dbProduct.insertProduct(product);
	}
	
	public Product findProduct(int id, boolean retriveAssociation) throws Exception
	{
		return dbProduct.findProduct(id, retriveAssociation);
	}
	public ArrayList<Product> getAllOf(String category) throws Exception
	{
		return dbProduct.getAllOf(category, true);
	}

	public void updateProduct(String name, double cost, double price, ArrayList<QuantLoc> quantLocs, double unitVolume, String type,
	int purchased) throws Exception
	{
		Product product = new Product();
		product.setCost(cost);
		product.setName(name);
		product.setPrice(price);
		product.setPurchased(purchased);
		product.setQuantLocs(quantLocs);
		product.setType(type);
		product.setUnitVolume(unitVolume);
		
		dbProduct.updateProduct(product);
		
	}
	
	public void deleteProduct(Product product) throws Exception
	{
		dbProduct.delete(product);
	}
	
	public ArrayList<String> getTypes() throws Exception
	{
		return dbProduct.getTypes();
	}
}
