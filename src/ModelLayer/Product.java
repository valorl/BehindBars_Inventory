package ModelLayer;

import java.util.ArrayList;
import java.util.Arrays;


public class Product {
	
	private static ArrayList<String> ALCOHOLIC_TYPES = new ArrayList<>(Arrays.asList("vodka", "gin", "rum", "tequilla", "scotch", "brandy", "bitter", "liqueur"));
	
	

	private int id;
	private String name;
	private double cost;
	private double price;
	private ArrayList<QuantLoc> quantLocs;
	private double unitVolume;
	private String type;
	private int purchased;
	
	// CONSTRUCTORS
	
	public Product() {
		quantLocs = new ArrayList<QuantLoc>();
	};
	
	public Product(String name, double cost, double price,
			ArrayList<QuantLoc> quantLocs, double unitVolume, String type,
			int purchased) {
		
		super();
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.quantLocs = quantLocs;
		this.unitVolume = unitVolume;
		this.type = type;
		this.purchased = purchased;
	}



	// GET & SET
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public double getUnitVolume() {
		return unitVolume;
	}
	public void setUnitVolume(double unitVolume) {
		this.unitVolume = unitVolume;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int getPurchased() {
		return purchased;
	}
	public void setPurchased(int purchased) {
		this.purchased = purchased;
	}
	
	// QuantLoc GET, SET, ADD, REMOVE, FIND
	public ArrayList<QuantLoc> getQuantLocs() {
		return quantLocs;
	}
	public void setQuantLocs(ArrayList<QuantLoc> quantLocs) {
		this.quantLocs = quantLocs;
	}
	public void addQuantLoc(QuantLoc qLock) 
	{
		if(!quantLocs.contains(qLock)) 
		{
			quantLocs.add(qLock);
		}
	}
	public void removeQuantLoc(QuantLoc qLock) 
	{
		quantLocs.remove(qLock);
	}
	public QuantLoc findQuantLoc(String location) 
	{
		QuantLoc foundQL = null;
		boolean found = false;
		int i = 0;
		while(!found && i < quantLocs.size()) 
		{
			if(quantLocs.get(i).getLocation().toLowerCase().equals(location.toLowerCase())) 
			{
				foundQL = quantLocs.get(i);
				found = true;
			}
			else 
			{
				i++;
			}
		}
		return foundQL;
	}
	
	
	
	
	
	
}
