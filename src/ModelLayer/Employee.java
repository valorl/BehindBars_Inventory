package ModelLayer;

public class Employee {
	
	private int id;
	private String name; 
	private String phoneNo;
	
	public Employee() {
		
	}


	public Employee(String name, String phoneNo) {
		super();
		this.name = name;
		this.phoneNo = phoneNo;
	}

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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String toString() {
		String idString = String.format("%02d ", this.id);
		return idString + " " + this.name;
	}
	
}
