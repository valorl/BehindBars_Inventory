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

	
	// State GET, SET, ADD, REMOVE, FIND
	public ArrayList<ProductState> getStateList() {
		return stateList;
	}
	public void setStateList(ArrayList<ProductState> stateList) {
		this.stateList = stateList;
	}
	public void addState(ProductState state) 
	{
		if(!stateList.contains(state)) 
		{
			stateList.add(state);
		}
	}
	public void removeState(ProductState state) 
	{
		stateList.remove(state);
	}
	public ProductState findState(String productName) 
	{
		ProductState foundState = null;
		boolean found = false;
		int i = 0;
		while(!found && i < stateList.size()) 
		{
			if(stateList.get(i).getProduct().getName().toLowerCase().equals(productName.toLowerCase()))
			{
				foundState = stateList.get(i);
				found = true;
			}
			else 
			{
				i++;
			}
		}
		return foundState;
	}
	

}
