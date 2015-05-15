package UILayer.TableData;

import java.text.DecimalFormat;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.Alcohol;
import ModelLayer.Product;

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

	// If alcohol
	private DoubleProperty fullBottle;
	private DoubleProperty emptyBottle;

	// Constructor - converts Product obj into InventoryData
	public InventoryData(Product product) 
	{
		this.productObj = product;

		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		this.costContainer = new SimpleDoubleProperty(product.getCost());
		this.priceUnit = new SimpleDoubleProperty(product.getPrice());
		this.unitVolume = new SimpleDoubleProperty(product.getUnitVolume());

		if(Product.checkTypeForAlcoholic(product.getType())) {
			Alcohol alc = (Alcohol) product;
			this.fullBottle = new SimpleDoubleProperty(alc.getFullWeight());
			this.emptyBottle = new SimpleDoubleProperty(alc.getEmptyWeight());
		}
		else 
		{
			this.fullBottle = null;
			this.emptyBottle = null;
		}
		this.costUnit = new SimpleDoubleProperty();
		this.priceContainer = new SimpleDoubleProperty();
		updateCostUnit();
		updatePriceContainer();
		System.out.println("InventoryData created: " + id + name + costContainer + priceUnit + unitVolume);

	}

	// GET & SET
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
	public void setName(String name) {
		this.name.set(name);
	}

	public double getCostContainer() {
		return costContainer.get();
	}
	public void setCostContainer(double costBottle) {
		this.costContainer.set(costBottle);
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
	}

	public double getFullBottle() {
		if(fullBottle == null) {
			return 0;
		}
		return fullBottle.get();
	}
	public void setFullBottle(double fullBottle) {
		this.fullBottle.set(fullBottle);
	}

	public double getEmptyBottle() {
		if(emptyBottle == null) return 0;
		return emptyBottle.get();
	}
	public void setEmptyBottle(double emptyBottle) {
		this.emptyBottle.set(emptyBottle);
	}
	// END GET & SET

	// PRICE CALCS
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
