package ModelLayer;

public class QuantLoc {

	private double quantity; 
	private String location; 
	
	public QuantLoc() {
		
	}
	
	public QuantLoc(double quantity,
			String location) {
		super();
		this.quantity = quantity;
		this.location = location;
	}

		public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	
}
