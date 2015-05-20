package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.Product;
import ModelLayer.ProductState;

public class StocktakeData {
	
	// From Product
	private Product productObj;
	//private ProductState productStateObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	private DoubleProperty storage;
	private DoubleProperty bar1;
	private DoubleProperty bar1open;
	private DoubleProperty bar2;
	private DoubleProperty bar2open;
	private DoubleProperty bar3;
	private DoubleProperty bar3open;
	
	private DoubleProperty sales;
		
	
	// Constructor - converts Product obj into InventoryData
	public StocktakeData(Product product) 
	{
		this.productObj = product;
		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		
		this.storage = new SimpleDoubleProperty();
		this.bar1 = new SimpleDoubleProperty();
		this.bar1open = new SimpleDoubleProperty();
		this.bar2 = new SimpleDoubleProperty();
		this.bar2open = new SimpleDoubleProperty();
		this.bar3 = new SimpleDoubleProperty();
		this.bar3open = new SimpleDoubleProperty();
		
		this.sales = new SimpleDoubleProperty();
		// PROPERTY LISTENERS
//		
//		this.storage.addListener((obs, oldStorage, newStorage) -> 
//		productStateObj.setSold((double) newStorage));
//		
//		this.bar1.addListener((obs, oldBar1Bottle, newBar1Bottle) ->
//		productStateObj.setSold((double) newBar1Bottle));
//		
//		this.bar1open.addListener((obs, oldBar1open, newBar1open) ->
//		productStateObj.setSold((double) newBar1open));
//		
//		this.bar2.addListener((obs, oldBar2, newBar2) ->
//		productStateObj.setSold((double) newBar2));
//		this.bar2open.addListener((obs, oldBar2open, newBar2open) ->
//		productStateObj.setSold((double) newBar2open));
//		
//		this.bar3.addListener((obs, oldBar3, newBar3) ->
//		productStateObj.setSold((double) newBar3));
//		this.bar3open.addListener((obs, oldBar3open, newBar3open) ->
//		productStateObj.setSold((double) newBar3open));
//		
//		this.sales.addListener((obs, oldSales, newSales) ->
//		productStateObj.setSold((double) newSales));
	}
	
	// GETTERS PROPERTIES
	
	public DoubleProperty getStorageProperty() {
		return storage;
	}
	
	public DoubleProperty getBar1BottleProperty() {
		return bar1;
	}
	
	public DoubleProperty getBar1OpenProperty() {
		return bar1open;
	}
	
	public DoubleProperty getBar2BottleProperty() {
		return bar2;
	}

	public DoubleProperty getBar2OpenProperty() {
		return bar2open;
	}

	public DoubleProperty getBar3BottleProperty() {
		return bar3;
	}

	public DoubleProperty getBar3openProperty() {
		return bar3open;
	}

	public DoubleProperty getSalesProperty() {
		return sales;
	}

	// GET & SET
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}

	public void setStorage(double storage) {
		this.storage.set(storage);
	}

	public void setBar1(double bar1) {
		this.bar1.set(bar1);
	}

	public void setBar1open(double bar1open) {
		this.bar1open.set(bar1open);
	}

	public void setBar2(double bar2) {
		this.bar2.set(bar2);
	}

	public void setBar2open(double bar2open) {
		this.bar2open.set(bar2open);
	}

	public void setBar3(double bar3) {
		this.bar3.set(bar3);
	}

	public void setBar3open(double bar3open) {
		this.bar3open.set(bar3open);
	}

	public void setSales(double sales) {
		this.sales.set(sales);
	}

	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	
	public double getStorage() {
		if(storage == null) {
			return 0;
		}
		return storage.get();
	}
	
	public double getBar1() {
		if(bar1 == null) {
			return 0;
		}
		return bar1.get();
	}
	
	public double getBar1open() {
		if(bar1open == null) {
			return 0;
		}
		return bar1open.get();
	}
	
	public double getBar2() {
		if(bar2 == null) {
			return 0;
		}
		return bar2.get();
	}

	public double getBar2open() {
		if(bar2open == null) {
			return 0;
		}
		return bar2open.get();
	}

	public double getBar3() {
		if(bar3 == null) {
			return 0;
		}
		return bar3.get();
	}

	public double getBar3open() {
		if(bar3open == null) {
			return 0;
		}
		return bar3open.get();
	}

	public double getSales() {
		if(sales == null) {
			return 0;
		}
		return sales.get();
	}
	
	public Product getProduct() 
	{
		return productObj;
	}

	
	// END GET & SET
}
