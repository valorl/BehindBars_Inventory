package ControlLayer;

import java.util.ArrayList;
import java.util.Date;

import ModelLayer.ItemResult;
import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.Week;
import ModelLayer.WeekResult;

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
	public WeekResult getResults(Date date) throws Exception 
	{
		
		Week weekA = weekCtr.findWeek(date);
		Week weekB = weekCtr.findPreviousWeek(weekA);
		
		WeekResult weekResult = new WeekResult();
		
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
			Product product = productStateA.getProduct();
			itemResult.setProduct(product);
			ProductState productStateB = weekB.getStateByProduct(product);
			
			if(productStateA == null || productStateB == null) 
			{
				itemResult.setRevenue(-1);
				itemResult.setVariance(-1);
			}
			else 
			{
				itemResult.setStates(productStateA, productStateB);
				itemResult.calculateVariance();
				itemResult.calculateRevenue();
			}
			weekResult.addResult(itemResult);
		}
		
		return weekResult;
	}
	
	

}
