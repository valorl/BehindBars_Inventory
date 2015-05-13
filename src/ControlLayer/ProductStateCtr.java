package ControlLayer;

import java.util.ArrayList;

import DBLayer.DBProductState;
import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.QuantLoc;

public class ProductStateCtr {
	
	private DBProductState dbProductState;
	
	public ProductStateCtr()
	{
		dbProductState = new DBProductState();
	}
	
	public void createProductState(ProductState productState, int weekId) throws Exception
	{
		dbProductState.insertProductState(productState, weekId);
	}
	
	public ProductState findProductState(int id, boolean retriveAssociation) throws Exception
	{
		return dbProductState.findProductState(id, retriveAssociation);
	}

	public void updateProductState(double currentCost, double currentPrice, int purchased, Product product,
			double sold, ArrayList<QuantLoc> quantLocs, int weekId) throws Exception
	{
		ProductState productState = new ProductState();
		productState.setCurrentCost(currentCost);
		productState.setCurrentPrice(currentPrice);
		productState.setPurchased(purchased);
		productState.setProduct(product);
		productState.setSold(sold);
		productState.setQuantLocs(quantLocs);
		
		dbProductState.updateProductState(productState, weekId);
		
	}
	
	public void deleteProductState(int id) throws Exception
	{
		dbProductState.delete(id);
	}
}
