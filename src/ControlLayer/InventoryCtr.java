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
		Week weekB = weekCtr.findWeek(date);
		Week weekA = weekCtr.findPreviousWeek(weekB);
		WeekResult weekResult = new WeekResult();
		int maxSize = 0; 
		if(weekA != null && weekB != null) {
			weekResult.setWeekA(weekA);
			weekResult.setWeekB(weekB);
			if(weekA.getStateList().size() > weekB.getStateList().size()) 
			{
				maxSize = weekA.getStateList().size();
			}
			else 
			{
				maxSize = weekB.getStateList().size();
			}

			for(int i = 0; i < maxSize; i++) 
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
					itemResult.setStates(productStateA, productStateB);
					itemResult.calculateVariance();
					itemResult.calculateRevenue();
				}
				weekResult.addResult(itemResult);
			}
		}
		else {
			weekResult = null;
		}
		return weekResult;
	}



}
