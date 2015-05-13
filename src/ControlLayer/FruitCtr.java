package ControlLayer;

import DBLayer.DBFruit;
import ModelLayer.Fruit;

public class FruitCtr {
	
	private DBFruit dbFruit;
	
	public FruitCtr()
	{
		dbFruit = new DBFruit();
	}
	
	public void createFruit(Fruit fruit) throws Exception
	{
		dbFruit.insertFruit(fruit);
	}
	
	public Fruit findFruit(int productID, boolean retriveAssociation) throws Exception
	{
		return dbFruit.findFruit(productID, retriveAssociation);
		
	}

	public void updateFruit(double totalWeight) throws Exception
	{
		Fruit fruit = new Fruit();
		fruit.setTotalWeight(totalWeight);
		dbFruit.updateFruit(fruit);
		
	}
	
	public void deleteFruit(int productID) throws Exception
	{
		dbFruit.delete(productID);
	}
}
