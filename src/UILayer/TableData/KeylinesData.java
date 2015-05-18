package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ModelLayer.Product;

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
	
	// GETTERS - PROPERTIES

	
	public StringProperty nameProperty() {
		return name;
	}

	public DoubleProperty week1Property()
	{
		return week1;
	}
	
	public DoubleProperty week2Property()
	{
		return week2;
	}
	
	public DoubleProperty week3Property()
	{
		return week3;
	}
	
	public DoubleProperty week4Property()
	{
		return week4;
	}
	
	public DoubleProperty week5Property()
	{
		return week5;
	}
	
	public DoubleProperty varianceProperty()
	{
		return variance;
	}
	
	public DoubleProperty retailProperty()
	{
		return retail;
	}

	// END
	
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

	public double getWeek1() {
		return week1.get();
	}

	public void setWeek1(double week1) {
		this.week1.set(week1);
	}

	public double getWeek2() {
		return week2.get();
	}

	public void setWeek2(double week2) {
		this.week2.set(week2);
	}

	public double getWeek3() {
		return week3.get();
	}

	public void setWeek3(double week3) {
		this.week3.set(week3);
	}

	public double getWeek4() {
		return week4.get();
	}

	public void setWeek4(double week4) {
		this.week4.set(week4);
	}

	public double getWeek5() {
		return week5.get();
	}

	public void setWeek5(double week5) {
		this.week5.set(week5);
	}

	public double getVariance() {
		return variance.get();
	}

	public void setVariance(double variance) {
		this.variance.set(variance);
	}

	public double getRetail() {
		return retail.get();
	}

	public void setRetail(double retail) {
		this.retail.set(retail);
	}
	
	
	// END GET & SET
}
