package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ModelLayer.Alcohol;
import ModelLayer.Product;

public class InventoryData {
	
	// From Product
	private Product productObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private DoubleProperty costBottle;
	private DoubleProperty priceCl;
	private DoubleProperty unitVolume;
	
	// To be calculated
	private DoubleProperty costCl;
	private DoubleProperty priceBottle;

	// If alcohol
	private DoubleProperty fullBottle;
	private DoubleProperty emptyBottle;
	
	
	// Constructor - converts Product obj into InventoryData
	public InventoryData(Product product) 
	{
		this.productObj = product;
		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		this.costBottle = new SimpleDoubleProperty(product.getCost());
		this.priceCl = new SimpleDoubleProperty(product.getPrice());
		this.unitVolume = new SimpleDoubleProperty(product.getUnitVolume());
		
		if(Product.checkType(product.getType())) {
			Alcohol alc = (Alcohol) product;
			this.fullBottle = new SimpleDoubleProperty(alc.getFullWeight());
			this.emptyBottle = new SimpleDoubleProperty(alc.getEmptyWeight());
		}
		else 
		{
			this.fullBottle = null;
			this.emptyBottle = null;
		}
		
		this.costCl = new SimpleDoubleProperty(costBottle.get()/unitVolume.get());
		this.priceBottle = new SimpleDoubleProperty(priceCl.get()*unitVolume.get());	
		
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
