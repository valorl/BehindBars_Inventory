package ModelLayer;

import java.util.ArrayList;

public class Week {
	
	private int number; 
	private int month; 
	private int year; 
	private ArrayList<ProductState> stateList;
	
	public Week() {
		
	}

	public Week(int number, int month, int year,
			ArrayList<ProductState> stateList) {
		super();
		this.number = number;
		this.month = month;
		this.year = year;
		this.stateList = stateList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public ArrayList<ProductState> getStateList() {
		return stateList;
	}

	public void setStateList(ArrayList<ProductState> stateList) {
		this.stateList = stateList;
	}
	

}
