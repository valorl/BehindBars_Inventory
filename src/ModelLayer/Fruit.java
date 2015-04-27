package ModelLayer;

import java.util.ArrayList;

public class Fruit extends Product{

	private double totalWeight;

	
	// CONSTRUCTORS
	public Fruit() {}
	
	public Fruit(String name, double cost, double price,
			ArrayList<QuantLoc> quantLocs, double unitVolume, String type,
			int purchased, double totalWeight) {
		super(name, cost, price, quantLocs, unitVolume, type, purchased);
		this.totalWeight = totalWeight;
	}

	// GET & SET
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	

}
