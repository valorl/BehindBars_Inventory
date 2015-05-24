package ControlLayer;

import DBLayer.DBMeasurable;
import ModelLayer.Measurable;

public class MeasurableCtr {
	
	private DBMeasurable dbMeasurable;
	
	public MeasurableCtr()
	{
		dbMeasurable = new DBMeasurable();
	}
	
	public void createMeasurable(Measurable measurable) throws Exception
	{
		dbMeasurable.insertMeasurable(measurable);
	}
	
	public Measurable findMeasurable(int productID, boolean retriveAssociation) throws Exception
	{
		return dbMeasurable.findMeasurable(productID, retriveAssociation);
		
	}
	
	public void updateMeasurable(double density, double fullWeight, double emptyWeight,
			double totalMeasured) throws Exception
	{
		Measurable measurable = new Measurable();
		measurable.setDensity(density);
		measurable.setFullWeight(fullWeight);
		measurable.setEmptyWeight(emptyWeight);
		measurable.setTotalMeasured(totalMeasured);
		
		dbMeasurable.updateMeasurable(measurable);
		
	}
	
	public void deleteMeasurable(Measurable measurable) throws Exception
	{
		dbMeasurable.delete(measurable);
	}

}
