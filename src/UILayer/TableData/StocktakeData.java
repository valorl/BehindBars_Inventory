package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ModelLayer.Alcohol;
import ModelLayer.Product;

public class StocktakeData {
	
	// From Product
	private Product productObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	private DoubleProperty storageBottle;
	private DoubleProperty bar1Bottle;
	private DoubleProperty bar1Open;
	private DoubleProperty bar2Bottle;
	private DoubleProperty bar2Open;
	private DoubleProperty bar3Bottle;
	private DoubleProperty bar3Open;
	
	private DoubleProperty sales;
		
	
	// Constructor - converts Product obj into InventoryData
	public StocktakeData(Product product) 
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
