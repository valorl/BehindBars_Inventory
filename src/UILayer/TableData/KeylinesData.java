package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.Alcohol;
import ModelLayer.Product;
import ModelLayer.Week;

public class KeylinesData {
	
	// From Product
	private Product productObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	// Weeks
	private DoubleProperty week1;
	private DoubleProperty week2;
	private DoubleProperty week3;
	private DoubleProperty week4;
	private DoubleProperty week5;

	private DoubleProperty variance;
	private DoubleProperty retail;
	
	
	// Constructor - converts Product obj into InventoryData
	public KeylinesData(Product product) 
	{
		this.productObj = product;
		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		
	
		
	}

	// GET & SET
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	
	
	// END GET & SET
}
