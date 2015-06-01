package DBLayer;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import ModelLayer.Measurable;
import ModelLayer.Product;
import ModelLayer.QuantLoc;

/**
 * @author mauromonteiro
 *
 */
public class DBProductTest {

	@Test
	public void insertFindUpdateDeleteProductTest() 
	{
		Product testProduct = new Product();
		testProduct.setId(20);
		testProduct.setName("Ringnes draft");
		testProduct.setCost(5000.00);
		testProduct.setPrice(110.00);
		testProduct.setUnitVolume(2000);
		testProduct.setType("test typer");
		testProduct.setPurchased(3);


		QuantLoc testQuantLoc1 = new QuantLoc();
		testQuantLoc1.setLocation("bar1");
		testQuantLoc1.setQuantity(3);
		testProduct.addQuantLoc(testQuantLoc1);

		QuantLoc testQuantLoc2 = new QuantLoc();
		testQuantLoc2.setLocation("bar2");
		testQuantLoc2.setQuantity(5);
		testProduct.addQuantLoc(testQuantLoc2);

		QuantLoc testQuantLoc3 = new QuantLoc();
		testQuantLoc3.setLocation("bar3");
		testQuantLoc3.setQuantity(1);
		testProduct.addQuantLoc(testQuantLoc3);


		DBProduct dbProduct = new DBProduct();
		try {
			dbProduct.insertProduct(testProduct);
			System.out.print("Product Insert OK");
			Product foundProd = dbProduct.findProduct(testProduct.getName(), true);
			if(foundProd != null)  {
				System.out.print("Product Find OK");
				foundProd.setPrice(5000);
				System.out.print("Product Update OK");
			}
			
			dbProduct.delete(testProduct);
			System.out.print("Product Delete OK");

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("InsertFindUpdateDelete of Product not OK.");
			fail();

		}
	}


	@Test
	public void insertFindUpdateDeleteMeasurableTest()
	{

		Measurable testMeasurable = new Measurable();
		testMeasurable.setId(1);
		testMeasurable.setName("Zacapa");
		testMeasurable.setCost(300.00);
		testMeasurable.setPrice(80.00);
		testMeasurable.setUnitVolume(70);
		testMeasurable.setPurchased(3);
		testMeasurable.setFullWeight(5000);
		testMeasurable.setEmptyWeight(2000);
		//testMeasurable.setDensity();
		testMeasurable.setTotalMeasured(70);
		testMeasurable.setType("Rum");


		QuantLoc testQuantLoc1 = new QuantLoc();
		testQuantLoc1.setLocation("bar1");
		testQuantLoc1.setQuantity(1);
		testMeasurable.addQuantLoc(testQuantLoc1);

		QuantLoc testQuantLoc2 = new QuantLoc();
		testQuantLoc2.setLocation("bar2");
		testQuantLoc2.setQuantity(1);
		testMeasurable.addQuantLoc(testQuantLoc2);

		QuantLoc testQuantLoc3 = new QuantLoc();
		testQuantLoc3.setLocation("bar3");
		testQuantLoc3.setQuantity(1);
		testMeasurable.addQuantLoc(testQuantLoc3);

		DBProduct dbProduct = new DBProduct();
		try {
			dbProduct.insertProduct(testMeasurable);
			System.out.print("Measurable Insert OK");
			Measurable foundMes = new Measurable(dbProduct.findProduct(testMeasurable.getName(), true));
			if(foundMes != null)  {
				System.out.print("Measurable Find OK");
				foundMes.setPrice(5000);
				System.out.print("Measurable Update OK");
			}
			
			dbProduct.delete(testMeasurable);
			System.out.print("Measurable Delete OK");

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("Measurable Insert not OK");
			fail();

		}
	}


}

