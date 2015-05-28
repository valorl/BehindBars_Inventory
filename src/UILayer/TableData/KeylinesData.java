package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ModelLayer.ItemResult;
import ModelLayer.Product;
import ModelLayer.ProductState;

public class KeylinesData {
	
	// From Product
	private Product product;
	
	private ProductState state1;
	private ProductState state2;
	private ProductState state3;
	private ProductState state4;
	private ProductState state5;
	
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	// Weeks
	private DoubleProperty week1Sales;
	private DoubleProperty week2Sales;
	private DoubleProperty week3Sales;
	private DoubleProperty week4Sales;
	private DoubleProperty week5Sales;
	
	//Weeks - difference
	private DoubleProperty week1Dif;
	private DoubleProperty week2Dif;
	private DoubleProperty week3Dif;
	private DoubleProperty week4Dif;
	private DoubleProperty week5Dif;
	
	private DoubleProperty totalSales;
	private DoubleProperty totalDif;
	private DoubleProperty revenue;
	
	
	// Constructor - converts Product obj into InventoryData
	public KeylinesData(ItemResult result) 
	{
	
		this.product = result.getProduct();
		
		if(result.getStateE() == null)
		{
			this.state1 = result.getStateA();
			this.week1Sales = new SimpleDoubleProperty(state1.getSold());
			this.week1Dif = new SimpleDoubleProperty(state1.getTotalQuantity() - state1.getSold());
			
			this.state2 = result.getStateB();
			this.week2Sales = new SimpleDoubleProperty(state2.getSold());
			this.week2Dif = new SimpleDoubleProperty(state2.getTotalQuantity() - state2.getSold());
			
			this.state3 = result.getStateC();
			this.week3Sales = new SimpleDoubleProperty(state3.getSold());
			this.week3Dif = new SimpleDoubleProperty(state3.getTotalQuantity() - state3.getSold());
			
			this.state4 = result.getStateD();
			this.week4Sales = new SimpleDoubleProperty(state4.getSold());
			this.week4Dif = new SimpleDoubleProperty(state4.getTotalQuantity() - state4.getSold());	
		
			this.week5Sales = new SimpleDoubleProperty();
			this.week5Dif = new SimpleDoubleProperty();
			
			this.totalSales = new SimpleDoubleProperty(week1Sales.get() + week2Sales.get() + week3Sales.get() + week4Sales.get());
			this.totalDif = new SimpleDoubleProperty(week1Dif.get() + week2Dif.get() + week3Dif.get() + week4Dif.get());

		}
		else if(result.getStateB() == null)
		{
			this.state1 = result.getStateA();
			this.week1Sales = new SimpleDoubleProperty(state1.getSold());
			this.week1Dif = new SimpleDoubleProperty(state1.getTotalQuantity() - state1.getSold());
			
			this.state2 = result.getStateB();
			this.week2Sales = new SimpleDoubleProperty();
			this.week2Dif = new SimpleDoubleProperty();
			
			this.state3 = result.getStateC();
			this.week3Sales = new SimpleDoubleProperty();
			this.week3Dif = new SimpleDoubleProperty();
			
			this.state4 = result.getStateD();
			this.week4Sales = new SimpleDoubleProperty();
			this.week4Dif = new SimpleDoubleProperty();	
		
			this.week5Sales = new SimpleDoubleProperty();
			this.week5Dif = new SimpleDoubleProperty();
			
			this.totalSales = new SimpleDoubleProperty(week1Sales.get());
			this.totalDif = new SimpleDoubleProperty(week1Dif.get());
		}
		else
		{
			this.state1 = result.getStateA();
			this.week1Sales = new SimpleDoubleProperty(state1.getSold());
			this.week1Dif = new SimpleDoubleProperty(state1.getTotalQuantity() - state1.getSold());
			
			this.state2 = result.getStateB();
			this.week2Sales = new SimpleDoubleProperty(state2.getSold());
			this.week2Dif = new SimpleDoubleProperty(state2.getTotalQuantity() - state2.getSold());
			
			this.state3 = result.getStateC();
			this.week3Sales = new SimpleDoubleProperty(state3.getSold());
			this.week3Dif = new SimpleDoubleProperty(state3.getTotalQuantity() - state3.getSold());
			
			this.state4 = result.getStateD();
			this.week4Sales = new SimpleDoubleProperty(state4.getSold());
			this.week4Dif = new SimpleDoubleProperty(state4.getTotalQuantity() - state4.getSold());	
			
			this.state5 = result.getStateE();
			this.week5Sales = new SimpleDoubleProperty(state5.getSold());
			this.week5Dif = new SimpleDoubleProperty(state5.getTotalQuantity() - state5.getSold());
			
			this.totalSales = new SimpleDoubleProperty(week1Sales.get() + week2Sales.get() + week3Sales.get() + week4Sales.get() + week5Sales.get());
			this.totalDif = new SimpleDoubleProperty(week1Dif.get() + week2Dif.get() + week3Dif.get() + week4Dif.get() + week5Dif.get());

		}

		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
				
		this.revenue = new SimpleDoubleProperty(totalDif.get() * product.getPrice());
		
		
	}
	
	// GETTERS - PROPERTIES

	
	public StringProperty nameProperty() {
		return name;
	}

	public DoubleProperty week1SalesProperty()
	{
		return week1Sales;
	}
	
	public DoubleProperty week2SalesProperty()
	{
		return week2Sales;
	}
	
	public DoubleProperty week3SalesProperty()
	{
		return week3Sales;
	}
	
	public DoubleProperty week4SalesProperty()
	{
		return week4Sales;
	}
	
	public DoubleProperty week5SalesProperty()
	{
		return week5Sales;
	}
	
	public DoubleProperty totalSalesProperty()
	{
		return totalSales;
	}
	
	public DoubleProperty totalDifProperty()
	{
		return totalDif;
	}
	
	public DoubleProperty retailProperty()
	{
		return revenue;
	}
	
	public DoubleProperty week1Dif()
	{
		return week1Dif;
	}
	
	public DoubleProperty week2Dif()
	{
		return week2Dif;
	}
	
	public DoubleProperty week3Dif()
	{
		return week3Dif;
	}
	
	public DoubleProperty week4Dif()
	{
		return week4Dif;
	}
	
	public DoubleProperty week5Dif()
	{
		return week5Dif;
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

	public double getWeek1Sales() {
		return week1Sales.get();
	}

	public void setWeek1Sales(double week1Sales) {
		this.week1Sales.set(week1Sales);
	}

	public double getWeek2Sales() {
		return week2Sales.get();
	}

	public void setWeek2Sales(double week2Sales) {
		this.week2Sales.set(week2Sales);
	}

	public double getWeek3Sales() {
		return week3Sales.get();
	}

	public void setWeek3Sales(double week3Sales) {
		this.week3Sales.set(week3Sales);
	}

	public double getWeek4Sales() {
		return week4Sales.get();
	}

	public void setWeek4Sales(double week4Sales) {
		this.week4Sales.set(week4Sales);
	}

	public double getWeek5Sales() {
		return week5Sales.get();
	}

	public void setWeek5Sales(double week5Sales) {
		this.week5Sales.set(week5Sales);
	}

	public double getTotalSales() {
		return totalSales.get();
	}

	public void setTotal(double totalSales) {
		this.totalSales.set(totalSales);
	}	
	
	public double getTotalDif() {
		return totalDif.get();
	}

	public void setTotalDif(double totalDif) {
		this.totalDif.set(totalDif);
	}


	public double getRevenue() {
		return revenue.get();
	}

	public void setRetail(double revenue) {
		this.revenue.set(revenue);
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getWeek1Dif() {
		return week1Dif.get();
	}

	public void setWeek1Dif(double week1Dif) {
		this.week1Dif.set(week1Dif);
	}

	public double getWeek2Dif() {
		return week2Dif.get();
	}

	public void setWeek2Dif(double week2Dif) {
		this.week2Dif.set(week2Dif);
	}

	public double getWeek3Dif() {
		return week3Dif.get();
	}

	public void setWeek3Dif(double week3Dif) {
		this.week3Dif.set(week3Dif);
	}

	public double getWeek4Dif() {
		return week4Dif.get();
	}

	public void setWeek4Dif(double week4Dif) {
		this.week4Dif.set(week4Dif);
	}

	public double getWeek5Dif() {
		return week5Dif.get();
	}

	public void setWeek5Dif(double week5Dif) {
		this.week5Dif.set(week5Dif);
	}

	// END GET & SET
}
