package ControlLayer;

import java.util.ArrayList;
import java.util.Date;

import ModelLayer.ItemResult;
import ModelLayer.ProductState;
import ModelLayer.Week;

public class InventoryCtr {
	
	private WeekCtr weekCtr;
	
	public InventoryCtr() 
	{
		weekCtr = new WeekCtr();
	}
	
	/*
	 Use-case: List Inventory Results
 	 Operation: displayResults(date)
	*/
	public ArrayList<ItemResult> getResults(Date date) throws Exception 
	{
		
		Week weekA = weekCtr.findWeek(date);
		Week weekB = weekCtr.findPreviousWeek(weekA);
		
		ArrayList<ItemResult> weekResults = new ArrayList<ItemResult>();
		
		int maxSize = 0; 
		if(weekA.getStateList().size() > weekB.getStateList().size()) {
			maxSize = weekA.getStateList().size();
		}
		else {
			maxSize = weekB.getStateList().size();
		}
		
		for(int i = 1; i <= maxSize; i++) 
		{
			ItemResult itemResult = new ItemResult();
			
			ProductState productStateA = weekA.getState(i);
			ProductState productStateB = weekB.getState(i);
			
			if(productStateA == null || productStateB == null) 
			{
				itemResult.setRevenue(-1);
				itemResult.setVariance(-1);
			}
			else 
			{
				double variance = calculateVariance(productStateA,productStateB);
				itemResult.setVariance(variance);
				double revenue = calculateRevenue(productStateA,productStateB);
				itemResult.setRevenue(revenue);
			}
			weekResults.add(itemResult);
		}
		
		return weekResults;
	}
	
	private double calculateVariance(ProductState stateA, ProductState stateB) 
	{
		return (stateA.getTotalQuantity() - stateB.getTotalQuantity());
	}
	private double calculateRevenue(ProductState stateA, ProductState stateB) 
	{
		double variance = calculateVariance(stateA,stateB);
		return variance*(stateB.getCurrentPrice());
		
	}

}
