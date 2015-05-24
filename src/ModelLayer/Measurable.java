package ModelLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Measurable extends Product {

	private double fullWeight;
	private double emptyWeight;
	private double density;
	private double totalMeasured;
	private HashMap<Measurable,Double> cocktailContents;
	
	
	// CONSTRUCTORS
	
	public Measurable() {
		cocktailContents = new HashMap<Measurable,Double>();
	}
	
	public Measurable(boolean isCocktail) 
	{
		if(isCocktail) 
		{
			cocktailContents = new HashMap<Measurable,Double>();
		}
		else 
		{
			cocktailContents = null;
		}
	}
	
	public Measurable(Product product) {
		this.setId(product.getId());
		this.setName(product.getName());
		this.setCost(product.getCost());
		this.setPrice(product.getPrice());
		this.setType(product.getType());
		this.setUnitVolume(product.getUnitVolume());
		
		cocktailContents = new HashMap<Measurable,Double>();
	}

	public Measurable(String name, double cost, double price,
			ArrayList<QuantLoc> quantLocs, double unitVolume, String type,
			int purchased, double fullWeight, double emptyWeight,
			double totalVolume) {
		super(name, cost, price, quantLocs, unitVolume, type, purchased);
		this.fullWeight = fullWeight;
		this.emptyWeight = emptyWeight;
		this.totalMeasured = totalVolume;
		
		cocktailContents = new HashMap<Measurable,Double>();
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

	public double getTotalMeasured() {
		return totalMeasured;
	}
	public void setTotalMeasured(double totalMeasured) {
		this.totalMeasured = totalMeasured;
	}
	
	// Cocktail IS, GET, SET, ADD, REMOVE, FIND

	public boolean isCocktail() 
	{
		if(cocktailContents != null && cocktailContents.size() > 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public HashMap<Measurable,Double> getCocktailContents() {
		return cocktailContents;
	}
	public void setCocktailContents(HashMap<Measurable,Double> cocktailContents) {
		this.cocktailContents = cocktailContents;
	}
	public void addIngredient(Measurable ingredient, double volume) {
		if(!cocktailContents.containsKey(ingredient)) 
		{
			cocktailContents.put(ingredient, volume);
		}
	}
	public void removeIngredient(Measurable ingredient) {
		cocktailContents.remove(ingredient);
	}
	public Measurable findIngredient(String name) 
	{
		Measurable foundIngredient = null;
		boolean found = false;
		for(Measurable ingredient : cocktailContents.keySet()) 
		{
			if(!found && ingredient.getName().toLowerCase().equals(name.toLowerCase())) 
			{
				foundIngredient = ingredient;
				found = true;
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
				tempDensity = (fullWeight-emptyWeight)/(super.getUnitVolume()*10);
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
