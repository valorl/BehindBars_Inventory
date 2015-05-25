package ModelLayer;

public class ItemResult {
	
	private ProductState stateA;
	private ProductState stateB;
	
	private Product product;
	private double variance;
	private double revenue;
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	
	
	public ProductState getStateA() {
		return stateA;
	}
	public void setStateA(ProductState stateA) {
		this.stateA = stateA;
	}
	public ProductState getStateB() {
		return stateB;
	}
	public void setStateB(ProductState stateB) {
		this.stateB = stateB;
	}
	public void setStates(ProductState a, ProductState b) {
		this.stateA = a;
		this.stateB = b;
	}
	
	public double calculateVariance() 
	{
		variance =  (stateA.getTotalQuantity() - stateB.getTotalQuantity());
		return variance;
	}
	public double calculateRevenue() 
	{
		revenue = variance*(stateB.getCurrentPrice());
		return revenue;		
	}
	

}
