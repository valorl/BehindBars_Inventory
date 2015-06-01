package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.Product;

public class StocktakeData {
	
	// From Product
	private Product productObj;
	//private ProductState productStateObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	private IntegerProperty storage;
	private IntegerProperty bar1;
	private DoubleProperty bar1open;
	private IntegerProperty bar2;
	private DoubleProperty bar2open;
	private IntegerProperty bar3;
	private DoubleProperty bar3open;
	
	private DoubleProperty sales;
		
	
	// Constructor - converts Product obj into InventoryData
	public StocktakeData(Product product) 
	{
		this.productObj = product;
		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		
		this.storage = new SimpleIntegerProperty(0);
		this.bar1 = new SimpleIntegerProperty(0);
		this.bar1open = new SimpleDoubleProperty(0);
		this.bar2 = new SimpleIntegerProperty(0);
		this.bar2open = new SimpleDoubleProperty(0);
		this.bar3 = new SimpleIntegerProperty(0);
		this.bar3open = new SimpleDoubleProperty(0);
		
		this.sales = new SimpleDoubleProperty(0);
		// PROPERTY LISTENERS
		
		this.storage.addListener((obs, oldValue, newValue) -> {
			if((int)newValue < 0) {
				storage.set(0);
			}
		});
		this.bar1.addListener((obs, oldValue, newValue) -> {
			if((int)newValue < 0) {
				bar1.set(0);
			}
		});
		this.bar1open.addListener((obs, oldValue, newValue) -> {
			if((double)newValue < 0) {
				bar1open.set(0);
			}
		});
		this.bar2.addListener((obs, oldValue, newValue) -> {
			if((int)newValue < 0) {
				bar2.set(0);
			}
		});
		this.bar2open.addListener((obs, oldValue, newValue) -> {
			if((double)newValue < 0) {
				bar2open.set(0);
			}
		});
		this.bar3.addListener((obs, oldValue, newValue) -> {
			if((int)newValue < 0) {
				bar3.set(0);
			}
		});
		this.bar3open.addListener((obs, oldValue, newValue) -> {
			if((double)newValue < 0) {
				bar3open.set(0);
			}
		});
		this.sales.addListener((obs, oldValue, newValue) -> {
			if((double)newValue < 0) {
				sales.set(0);
			}
		});
		
	}
	
	// GETTERS PROPERTIES
	
	public IntegerProperty storageProperty() {
		return storage;
	}
	
	public IntegerProperty bar1Property() {
		return bar1;
	}
	
	public DoubleProperty bar1openProperty() {
		return bar1open;
	}
	
	public IntegerProperty bar2Property() {
		return bar2;
	}

	public DoubleProperty bar2openProperty() {
		return bar2open;
	}

	public IntegerProperty bar3Property() {
		return bar3;
	}

	public DoubleProperty bar3openProperty() {
		return bar3open;
	}

	public DoubleProperty salesProperty() {
		return sales;
	}

	// GET & SET
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}

	public void setStorage(int storage) {
		this.storage.set(storage);
	}

	public void setBar1(int bar1) {
		this.bar1.set(bar1);
	}

	public void setBar1open(double bar1open) {
		this.bar1open.set(bar1open);
	}

	public void setBar2(int bar2) {
		this.bar2.set(bar2);
	}

	public void setBar2open(double bar2open) {
		this.bar2open.set(bar2open);
	}

	public void setBar3(int bar3) {
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
	
	public int getStorage() {
		if(storage == null) {
			return 0;
		}
		return storage.get();
	}
	
	public int getBar1() {
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
	
	public int getBar2() {
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

	public int getBar3() {
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
