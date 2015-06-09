package ModelLayer;

public class ItemResult {

	private ProductState stateA;
	private ProductState stateB;
	
	private ProductState stateC;
	private ProductState stateD;
	private ProductState stateE;
	

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
	
	public ProductState getStateC() {
		return stateC;
	}
	public void setStateC(ProductState stateC) {
		this.stateC = stateC;
	}
	public ProductState getStateD() {
		return stateD;
	}
	public void setStateD(ProductState stateD) {
		this.stateD = stateD;
	}
	public ProductState getStateE() {
		return stateE;
	}
	public void setStateE(ProductState stateE) {
		this.stateE = stateE;
	}
	
	public void setStates(ProductState a, ProductState b) {
		this.stateA = a;
		this.stateB = b;
		if(stateA.getProduct() != null) {
			product = stateA.getProduct();
		}
		else if(stateB != null){
			product = stateB.getProduct();
		}
	}
	
	public void setStatesKeylines(ProductState a, ProductState b, ProductState c, ProductState d, ProductState e) {
		this.stateA = a;
		this.stateB = b;		
		this.stateA = c;
		this.stateB = d;
		this.stateE = e;
	}

	public double calculateVariance() 
	{
		variance =  (-1 * ((stateB.getTotalQuantity()- stateB.getPurchased()*stateB.getProduct().getUnitVolume()) - stateA.getTotalQuantity()));
		return variance;
	}
	public double calculateRevenue() 
	{
		revenue = variance*(stateB.getCurrentPrice());
		return revenue;		
	}


}
