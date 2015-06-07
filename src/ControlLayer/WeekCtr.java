package ControlLayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import DBLayer.DBWeek;
import ModelLayer.ItemResult;
import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.Week;
import ModelLayer.WeekResult;

public class WeekCtr {

	private DBWeek dbWeek;
	
	public WeekCtr() 
	{
		dbWeek = new DBWeek();
	}
	
	public Week createWeek(Date date) {
		Week newWeek =  new Week();
		int[] numbers = dateToNumbers(date);
		
		newWeek.setNumber(numbers[0]);
		newWeek.setMonth(numbers[1]);
		newWeek.setYear(numbers[2]);
		
		return newWeek;
	}
	
	public void insertWeek(Week week) throws Exception {
		dbWeek.insertWeek(week);
	}
	public void updateWeek(Week week) throws Exception {
		dbWeek.updateWeek(week);
	}
	
	public Week findWeek(Date date) throws Exception 
	{		
		int[] numbers = dateToNumbers(date);
		return dbWeek.findWeek(numbers[0],numbers[2], true);		
	}
	
	public Week findPreviousWeek(Week week) throws Exception 
	{
		int previousID = week.getId() - 1;
		return dbWeek.findWeekId(previousID, true);
	}
	
	public Week findWeekById(int id) throws Exception {
		return dbWeek.findWeekId(id, true);
	}
	
	public ArrayList<Week> findWeeksMonth(int month, int year) throws Exception {
		return dbWeek.findWeeksMonth(month, year, true);
	}
	
	// Convert Date obj to weekNr, month, year
	private int[] dateToNumbers(Date date) 
	{
		int[] numbers = new int[3];
		
		try{
		numbers[0] = Integer.parseInt(new SimpleDateFormat("w").format(date));
		numbers[1] = Integer.parseInt(new SimpleDateFormat("M").format(date));
		numbers[2] = Integer.parseInt(new SimpleDateFormat("y").format(date));
		}
		catch(NumberFormatException ex) 
		{
			ex.printStackTrace();
		}
		
		return numbers;
	}
	
	public ArrayList<Integer> getYears() throws Exception
	{
		return dbWeek.getYears();
	}
	
	public ArrayList<Integer> getMonths() throws Exception
	{
		return dbWeek.getMonths();
	}
	
	class WeekComparator implements Comparator<Week> {
	    public int compare(Week week1, Week week2) {
	        return week1.getNumber() - week2.getNumber();
	    }
	}
	
	public ArrayList<Week> getWeeksMonthYear(int month, int year) throws Exception
	{
		ArrayList<Week> weekList = new ArrayList<Week>();
		weekList = dbWeek.getWeeksMonthYear(month, year);
		
		Collections.sort(weekList, new WeekComparator());
		return weekList;
	}
	
	public WeekResult getResults(int month, int year) throws Exception 
	{
		ArrayList<Week> weekList = new ArrayList<Week>();
		weekList = dbWeek.getWeeksMonthYear(month, year);
		
		Collections.sort(weekList, new WeekComparator());
		
		Week weekA = new Week();
		Week weekB = new Week();
		Week weekC = new Week();
		Week weekD = new Week();
		Week weekE = new Week();
		if(weekList.size() >= 0) { 
			weekA = weekList.get(0);
		}
		else {
			weekA = null;
		}
		if(weekList.size() >= 1) { 
			weekB = weekList.get(1);
		}
		else {
			weekB = null;
		}

		if(weekList.size() >= 2) { 
			weekC = weekList.get(0);
		}
		else {
			weekC = null;
		}

		if(weekList.size() >= 3) { 
			weekD = weekList.get(0);
		}
		else {
			weekD = null;
		}

		if(weekList.size() >= 4) { 
			weekE = weekList.get(0);
		}
		else {
			weekE = null;
		}

		WeekResult weekResult = new WeekResult();

		int maxSize = 0; 
		if(weekA != null) {
			
			for(Week week : weekList)
			{
				if(week.getStateList().size() > maxSize) {
					maxSize = week.getStateList().size();
				}
			}

			for(int i = 0; i < maxSize; i++) 
			{
				ProductState productStateA;
				ProductState productStateB;
				ProductState productStateC;
				ProductState productStateD;
				ProductState productStateE;
				
				ItemResult itemResult = new ItemResult();
				productStateA = weekA.getState(i);
				//Product product = productStateA.getProduct();
				//itemResult.setProduct(product);
				if(weekB != null)
				{
					productStateB = weekB.getState(i);
				}
				else{
					productStateB = null;
				}
				Product product = productStateA.getProduct();
				
				if(weekC != null) {

					productStateC = weekC.getState(i);
				}
				else {
					productStateC = null;
				}
				
				if(weekD != null) {
					productStateD = weekD.getState(i);
				}
				else {
					productStateD = null;
				}
				
				if(weekE != null) {
					productStateE = weekE.getState(i);
				}
				else {
					productStateE = null;
				}

				
				if(productStateA == null || productStateB == null || productStateC == null || productStateD == null || productStateE == null) 
				{
					itemResult.setRevenue(-1);
					itemResult.setVariance(-1);
				}
				else 
				{
					itemResult.setStatesKeylines(productStateA, productStateB, productStateC, productStateD, productStateE);
					itemResult.calculateVariance();
					itemResult.calculateRevenue();
				}
				weekResult.addResult(itemResult);
			}
		}
		else {
			weekResult = null;
		}
		
		System.out.println("" + weekResult.toString());

		return weekResult;
	}
}
