package ControlLayer;


import DBLayer.DBAlcohol;
import ModelLayer.Alcohol;

public class AlcoholCtr {
	
	private DBAlcohol dbAlcohol;
	
	public AlcoholCtr()
	{
		dbAlcohol = new DBAlcohol();
	}
	
	public void createAlcohol(Alcohol alcohol) throws Exception
	{
		dbAlcohol.insertAlcohol(alcohol);
	}
	
	public Alcohol findAlcohol(int productID, boolean retriveAssociation) throws Exception
	{
		return dbAlcohol.findAlcohol(productID, retriveAssociation);
		
	}
	
	public void updateAlcohol(double density, double fullWeight, double emptyWeight,
			double totalVolume) throws Exception
	{
		Alcohol alcohol = new Alcohol();
		alcohol.setDensity(density);
		alcohol.setFullWeight(fullWeight);
		alcohol.setEmptyWeight(emptyWeight);
		alcohol.setTotalVolume(totalVolume);
		
		dbAlcohol.updateAlcohol(alcohol);
		
	}
	
	public void deleteAlcohol(Alcohol alcohol) throws Exception
	{
		dbAlcohol.delete(alcohol);
	}

}
