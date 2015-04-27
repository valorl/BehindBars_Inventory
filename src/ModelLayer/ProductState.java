package ModelLayer;

public class ProductState {
	
	private double currentCost;
	private double currentPrice; 
	private int purchased;
	private Product product; 
	
	public ProductState() {
		
	}

	public ProductState(double currentCost,
			double currentPrice, int purchased, Product product) {
		super();
		this.currentCost = currentCost;
		this.currentPrice = currentPrice;
		this.purchased = purchased;
		this.product = product;
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
	

}
