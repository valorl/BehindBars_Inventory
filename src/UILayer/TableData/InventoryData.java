package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ModelLayer.Measurable;
import ModelLayer.Product;
import UILayer.TypeManager;

public class InventoryData {

	// From Product
	private Product productObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private DoubleProperty costContainer;
	private DoubleProperty priceUnit;
	private DoubleProperty unitVolume;

	// To be calculated
	private DoubleProperty costUnit;
	private DoubleProperty priceContainer;

	// If measurable
	private DoubleProperty fullWeight;
	private DoubleProperty emptyWeight;

	// Constructor - converts Product obj into InventoryData
	public InventoryData(Product product) 
	{
		this.productObj = product;

		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		this.costContainer = new SimpleDoubleProperty(product.getCost());
		this.priceUnit = new SimpleDoubleProperty(product.getPrice());
		this.unitVolume = new SimpleDoubleProperty(product.getUnitVolume());

		if(TypeManager.isMeasurableType(product.getType())) {
			Measurable mes = (Measurable) product;
			this.fullWeight = new SimpleDoubleProperty(mes.getFullWeight());
			this.emptyWeight = new SimpleDoubleProperty(mes.getEmptyWeight());
			productObj = mes;
		}
		else 
		{
			this.fullWeight = new SimpleDoubleProperty();
			this.emptyWeight = new SimpleDoubleProperty();
		}
		this.costUnit = new SimpleDoubleProperty();
		this.priceContainer = new SimpleDoubleProperty();
		updateCostUnit();
		updatePriceContainer();
		System.out.println("InventoryData created: " + id + name + costContainer + priceUnit + unitVolume + fullWeight + emptyWeight);

		
		// PROPERTY LISTENERS - Sync InventoryData with Product , update calculated costs & prices
		
		this.name.addListener((obs, oldName, newName) -> 
		productObj.setName(newName));

		this.costContainer.addListener((obs, oldCostContainer, newCostContainer) -> {
			productObj.setCost((double) newCostContainer);
			updateCostUnit();
		});

		this.priceUnit.addListener((obs, oldPriceUnit, newPriceUnit) -> {
			productObj.setPrice((double) newPriceUnit);
			updatePriceContainer();
		});

		this.unitVolume.addListener((obs, oldUnitVolume, newUnitVolume) -> {
			productObj.setUnitVolume((double) newUnitVolume);
		});

		this.fullWeight.addListener((obs, oldFullWeight, newFullWeight) -> {
			if(productObj instanceof Measurable) {
				Measurable mes = (Measurable) productObj;
				mes.setFullWeight((double) newFullWeight);
			}
		});
		
		this.emptyWeight.addListener((obs, oldEmptyWeight, newEmptyWeight) -> {
			if(productObj instanceof Measurable) {
				Measurable mes = (Measurable) productObj;
				mes.setEmptyWeight((double) newEmptyWeight);
			}
		});


	}
	
	
	// GETTERS -  PROPERTIES
	public StringProperty nameProperty() {
		return name;
	}
	public DoubleProperty costContainerProperty() {
		return costContainer;
	}
	public DoubleProperty priceUnitProperty() {
		return priceUnit;
	}
	public DoubleProperty unitVolumeProperty() {
		return unitVolume;
	}

	public DoubleProperty costUnitProperty() {
		return costUnit;
	}
	public DoubleProperty priceContainerProperty() {
		return priceContainer;
	}

	public DoubleProperty fullWeightProperty() {
		return fullWeight;
	}
	public DoubleProperty emptyWeightProperty() {
		return emptyWeight; 
	}


	// GETTERS & SETTERS - OBJECT ATTRIBUTES / PRIMITIVES
	public Product getProduct() 
	{
		return productObj;
	}

	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}
	public final void setName(String name) {
		this.nameProperty().set(name);
	}

	public double getCostContainer() {
		return costContainer.get();
	}
	public void setCostContainer(double costBottle) {
		this.costContainer.set(costBottle);
		productObj.setCost(costBottle);
		updateCostUnit();
	}
	public double getCostUnit() {
		return costUnit.get();
	}
	public void setCostUnit(double costUnit) {
		this.costUnit.set(costUnit);
	}

	public double getPriceUnit() {
		return priceUnit.get();
	}
	public void setPriceUnit(double priceCl) {
		this.priceUnit.set(priceCl);
		productObj.setPrice(priceCl);
		updatePriceContainer();
	}
	public double getPriceContainer() {
		return priceContainer.get();
	}
	public void setPriceContainer(double priceBottle) {
		this.priceContainer.set(priceBottle);
	}

	public double getUnitVolume() {
		return unitVolume.get();
	}
	public void setUnitVolume(double unitVolume) {
		this.unitVolume.set(unitVolume);
		productObj.setUnitVolume(unitVolume);
	}

	public double getFullWeight() {

		return fullWeight.get();
	}
	public void setFullWeight(double fullBottle) {

		if(productObj instanceof Measurable) {
			this.fullWeight.set(fullBottle);
			Measurable mes = (Measurable) productObj;
			mes.setFullWeight(fullBottle);		
			productObj = mes;
		}
	}

	public double getEmptyWeight() {

		return emptyWeight.get();
	}
	public void setEmptyWeight(double emptyBottle) {
		this.emptyWeight.set(emptyBottle);
		if(productObj instanceof Measurable) {
			Measurable mes = (Measurable) productObj;
			mes.setEmptyWeight(emptyBottle);
			productObj = mes;
		}
	}
	// END GETTERS & SETTERS

	// COST & PRICE CALCULATIONS
	public void updateCostUnit() 
	{
		double cost = this.costContainer.get()/unitVolume.get();

		cost = Math.round(cost * 100);
		cost = cost/100;

		costUnit.set(cost);
	}
	public void updatePriceContainer() {
		priceContainer.set(priceUnit.get()*unitVolume.get());
	}
}
