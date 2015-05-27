package ControlLayer;

import java.text.SimpleDateFormat;
import java.util.Date;

import DBLayer.DBWeek;
import ModelLayer.Week;

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
}
