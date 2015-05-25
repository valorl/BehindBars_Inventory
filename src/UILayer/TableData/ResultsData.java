package UILayer.TableData;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import ModelLayer.ItemResult;
import ModelLayer.Product;
import ModelLayer.ProductState;

public class ResultsData {
	
	// From ItemResult
	private Product product;
	private ProductState stateA;
	private ProductState stateB;
	
	private SimpleStringProperty name;
	
	//Week 1
	private DoubleProperty bar1;
	private DoubleProperty bar2;
	private DoubleProperty bar3;
	private DoubleProperty total;
	
	//Week 2
	private DoubleProperty bar1Week2;
	private DoubleProperty bar2Week2;
	private DoubleProperty bar3Week2;
	private DoubleProperty totalWeek2;
	
	// Sales
	private DoubleProperty thSales;
	private DoubleProperty actSales;
	
	// Revenue
	private DoubleProperty thRevenue;
	private DoubleProperty actRevenue;
	
	// Loss/Gain
	private DoubleProperty lossGain;
	
	
	// Constructor - converts Product obj into ResultsData
	public ResultsData(ItemResult result) 
	{
		this.product = result.getProduct();
		this.stateA = result.getStateA();
		this.stateB = result.getStateB();
		
		// Name
		this.name = new SimpleStringProperty(product.getName());
		
		// Week 1
		this.bar1 = new SimpleDoubleProperty(stateA.getQuantLocs().get(0).getQuantity());
		this.bar2 = new SimpleDoubleProperty(stateA.getQuantLocs().get(1).getQuantity());
		this.bar3 = new SimpleDoubleProperty(stateA.getQuantLocs().get(2).getQuantity());
		this.total = new SimpleDoubleProperty(stateA.getTotalQuantity());
		
		// Week 2
		this.bar1Week2 = new SimpleDoubleProperty(stateB.getQuantLocs().get(0).getQuantity());
		this.bar2Week2 = new SimpleDoubleProperty(stateB.getQuantLocs().get(1).getQuantity());
		this.bar3Week2 = new SimpleDoubleProperty(stateB.getQuantLocs().get(2).getQuantity()); 
		this.totalWeek2 = new SimpleDoubleProperty(stateB.getTotalQuantity());
		
		// Sales
		this.thSales = new SimpleDoubleProperty(result.getVariance());
		this.actSales = new SimpleDoubleProperty(stateB.getSold());
		
		// Revenue
		this.thRevenue = new SimpleDoubleProperty(result.getRevenue());
		this.actRevenue = new SimpleDoubleProperty(actSales.get()*product.getPrice());
		
		// Loss Gain
		this.lossGain = new SimpleDoubleProperty();
		lossGain.bind(actRevenue.subtract(thRevenue));
	}
	
	// GETTERS - PROP
	public DoubleProperty bar1Property() {
		return bar1;
	}
	public DoubleProperty bar2Property() {
		return bar2;
	}
	public DoubleProperty bar3Property() {
		return bar3;
	}
	public DoubleProperty totalProperty() {
		return total;
	}
	
	public DoubleProperty bar1Week2Property() {
		return bar1Week2;
	}
	public DoubleProperty bar2Week2Property() {
		return bar2Week2;
	}
	public DoubleProperty bar3Week2Property() {
		return bar3Week2;
	}
	public DoubleProperty totalWeek2Property() {
		return totalWeek2;
	}
	
	public DoubleProperty thSalesProperty() {
		return thSales;
	}
	public DoubleProperty actSalesProperty() {
		return actSales;
	}
	
	public DoubleProperty thRevenueProperty() {
		return thRevenue;
	}
	public DoubleProperty actRevenueProperty() {
		return actRevenue;
	}
	
	public DoubleProperty lossGainProperty() {
		return lossGain;
	}

	
	// GET & SET
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

	public double getActSalesWeek2() {
		return actSales.get();
	}

	public void setActSalesWeek2(double salesWeek2) {
		this.actSales.set(salesWeek2);
	}

	public double getThSales() {
		return thSales.get();
	}

	public void setThSales(double variance) {
		this.thSales.set(variance);
	}

	public double getThRevenue() {
		return thRevenue.get();
	}

	public void setThRevenue(double revenue) {
		this.thRevenue.set(revenue);
	}

	public double getActRevenue() {
		return actRevenue.get();
	}	

	public void setActRevenue(double difference) {
		this.actRevenue.set(difference);
	}

	public double getLossGain() {
		return lossGain.get();
	}

	public void setLossGain(double lossGain) {
		this.lossGain.set(lossGain);
	}
	
	
	public Product getProduct() {
		return product;
	}
	
	
	
	// END GET & SET
}
