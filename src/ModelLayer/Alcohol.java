package ModelLayer;

import java.util.ArrayList;

public class Alcohol extends Product {

	private double fullWeight;
	private double emptyWeight;
	private double density;
	private double totalVolume;
	private ArrayList<Alcohol> cocktailContents;
	
	
	// CONSTRUCTORS
	
	public Alcohol() {}
	
	public Alcohol(boolean isCocktail) 
	{
		if(isCocktail) 
		{
			cocktailContents = new ArrayList<Alcohol>();
		}
		else 
		{
			cocktailContents = null;
		}
	}

	public Alcohol(String name, double cost, double price,
			ArrayList<QuantLoc> quantLocs, double unitVolume, String type,
			int purchased, double fullWeight, double emptyWeight,
			double totalVolume) {
		super(name, cost, price, quantLocs, unitVolume, type, purchased);
		this.fullWeight = fullWeight;
		this.emptyWeight = emptyWeight;
		this.totalVolume = totalVolume;
	};
	

	// General GET & SET 
	
	public double getFullWeight() {
		return fullWeight;
	}
	public void setFullWeight(double fullWeight) {
		this.fullWeight = fullWeight;
	}

	public double getEmptyWeight() {
		return emptyWeight;
	}
	public void setEmptyWeight(double emptyWeight) {
		this.emptyWeight = emptyWeight;
	}

	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}

	public double getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}
	
	// Cocktail IS, GET, SET, ADD, REMOVE, FIND

	public boolean isCocktail() 
	{
		if(this.cocktailContents.size() > 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public ArrayList<Alcohol> getCocktailContents() {
		return cocktailContents;
	}
	public void setCocktailContents(ArrayList<Alcohol> cocktailContents) {
		this.cocktailContents = cocktailContents;
	}
	public void addIngredient(Alcohol ingredient) {
		if(!cocktailContents.contains(ingredient)) {
			cocktailContents.add(ingredient);
		}
	}
	public void removeIngredient(Alcohol ingredient) {
		cocktailContents.remove(ingredient);
	}
	public Alcohol findIngredient(String name) 
	{
		Alcohol foundIngredient = null;
		boolean found = false;
		int i = 0;
		while(!found && i < cocktailContents.size())
		{
			if(cocktailContents.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				foundIngredient = cocktailContents.get(i);
				found = true;
			}
			else 
			{
				i++;
			}
		}
		return foundIngredient;
	}

	
	// Density formula
	public double calculateDensity() throws Exception
	{
		double tempDensity = 0;
		
		if(fullWeight > 0 && emptyWeight > 0 && super.getUnitVolume() > 0) 
		{
			if(fullWeight > emptyWeight) 
			{
				tempDensity = (fullWeight-emptyWeight)/super.getUnitVolume();
			}
			else 
			{
				throw new Exception("Incorrect weight values.");
			}
		}
		else 
		{
			throw new Exception("Not enough information.");
		}
		
		return tempDensity;
	}
}
