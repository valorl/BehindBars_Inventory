package ModelLayer;

import java.util.ArrayList;

public class ProductState {
	
	private int id;
	private double currentCost;
	private double currentPrice;
	private double sold;
	private int purchased;
	private Product product;
	private ArrayList<QuantLoc> quantLocs;
	
	public ProductState() {
		
	}

	public ProductState(Product product) {
		super();
		this.product = product;
		this.purchased = product.getPurchased();
		this.currentCost = product.getCost();
		this.currentPrice = product.getPrice();
		
		quantLocs = new ArrayList<QuantLoc>();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(double currentCost) {
		this.currentCost = currentCost;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getSold() {
		return sold;
	}

	public void setSold(double sold) {
		this.sold = sold;
	}

	public int getPurchased() {
		return purchased;
	}

	public void setPurchased(int purchased) {
		this.purchased = purchased;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		
	// Sum up quantities from all QuantLocs
	public double getTotalQuantity() 
	{
		return quantLocs.stream().mapToDouble(QuantLoc::getQuantity).sum();
	}
	
	
	
	
	
}
