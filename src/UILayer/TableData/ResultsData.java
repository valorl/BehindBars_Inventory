package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.Product;

public class ResultsData {
	
	// From Product
	private Product productObj;
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	//Week 1
	private DoubleProperty bar1;
	private DoubleProperty bar2;
	private DoubleProperty bar3;
	private DoubleProperty total;
	private DoubleProperty sales;
	
	//Week 2
	private DoubleProperty bar1Week2;
	private DoubleProperty bar2Week2;
	private DoubleProperty bar3Week2;
	private DoubleProperty totalWeek2;
	private DoubleProperty salesWeek2;
	
	private DoubleProperty variance;
	private DoubleProperty revenue;
	private DoubleProperty difference;
	private DoubleProperty lossGain;
	
	
	// Constructor - converts Product obj into ResultsData
	public ResultsData(Product product) 
	{
		this.productObj = product;
		
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		
	}
	
	// GETTERS - PROP
	public DoubleProperty lossGainProperty() {
		return lossGain;
	}

	public DoubleProperty differenceProperty() {
		return difference;
	}

	public DoubleProperty revenueProperty() {
		return revenue;
	}

	public DoubleProperty varianceProperty() {
		return variance;
	}

	public DoubleProperty salesWeek2Property() {
		return salesWeek2;
	}

	public DoubleProperty totalWeek2Property() {
		return totalWeek2;
	}

	public DoubleProperty bar3Week2Property() {
		return bar3Week2;
	}

	public DoubleProperty bar2Week2Property() {
		return bar2Week2;
	}

	public DoubleProperty bar1Week2Property() {
		return bar1Week2;
	}

	public DoubleProperty salesProperty() {
		return sales;
	}

	public DoubleProperty totalProperty() {
		return total;
	}


	public DoubleProperty bar3Property() {
		return bar3;
	}

	public DoubleProperty bar2Property() {
		return bar2;
	}


	public DoubleProperty bar1Property() {
		return bar1;
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
	

	public double getBar1() {
		return bar1.get();
	}

	public void setBar1(double bar1) {
		this.bar1.set(bar1);
	}

	public double getBar2() {
		return bar2.get();
	}

	public void setBar2(double bar2) {
		this.bar2.set(bar2);
	}

	public double getBar3() {
		return bar3.get();
	}

	public void setBar3(double bar3) {
		this.bar3.set(bar3);
	}

	public double getTotal() {
		return total.get();
	}

	public void setTotal(double total) {
		this.total.set(total);
	}

	public double getSales() {
		return sales.get();
	}

	public void setSales(double sales) {
		this.sales.set(sales);
	}

	public double getBar1Week2() {
		return bar1Week2.get();
	}

	public void setBar1Week2(double bar1Week2) {
		this.bar1Week2.set(bar1Week2);
	}

	public double getBar2Week2() {
		return bar2Week2.get();
	}

	public void setBar2Week2(double bar2Week2) {
		this.bar2Week2.set(bar2Week2);
	}

	public double getBar3Week2() {
		return bar3Week2.get();
	}

	public void setBar3Week2(double bar3Week2) {
		this.bar3Week2.set(bar3Week2);
	}

	public double getTotalWeek2() {
		return totalWeek2.get();
	}

	public void setTotalWeek2(double totalWeek2) {
		this.totalWeek2.set(totalWeek2);
	}

	public double getSalesWeek2() {
		return salesWeek2.get();
	}

	public void setSalesWeek2(double salesWeek2) {
		this.salesWeek2.set(salesWeek2);
	}

	public double getVariance() {
		return variance.get();
	}

	public void setVariance(double variance) {
		this.variance.set(variance);
	}

	public double getRevenue() {
		return revenue.get();
	}

	public void setRevenue(double revenue) {
		this.revenue.set(revenue);
	}

	public double getDifference() {
		return difference.get();
	}	

	public void setDifference(double difference) {
		this.difference.set(difference);
	}

	public double getLossGain() {
		return lossGain.get();
	}

	public void setLossGain(double lossGain) {
		this.lossGain.set(lossGain);
	}
	
	
	
	
	// END GET & SET
}
