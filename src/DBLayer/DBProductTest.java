package DBLayer;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import ModelLayer.Measurable;
import ModelLayer.Fruit;
import ModelLayer.Product;
import ModelLayer.QuantLoc;

/**
 * @author mauromonteiro
 *
 */
public class DBProductTest {

	@Test
	public void insertProductTest() 
	{
		Product testProduct = new Product();
		testProduct.setId(20);
		testProduct.setName("Ringnes draft");
		testProduct.setCost(5000.00);
		testProduct.setPrice(110.00);
		testProduct.setUnitVolume(2000);
		testProduct.setType("draft beer");
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

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("Product Insert not OK");
			fail();

		}
	}
	
//	@Test
//	public void insertProductTest2() 
//	{
//		Product testProduct = new Product();
//		testProduct.setId(20);
//		testProduct.setName("Pampero");
//		testProduct.setCost(500.00);
//		testProduct.setPrice(90.00);
//		testProduct.setUnitVolume(70);
//		testProduct.setType("rum");
//		testProduct.setPurchased(10);
//
//
//		QuantLoc testQuantLoc1 = new QuantLoc();
//		testQuantLoc1.setLocation("bar1");
//		testQuantLoc1.setQuantity(2);
//		testProduct.addQuantLoc(testQuantLoc1);
//
//		QuantLoc testQuantLoc2 = new QuantLoc();
//		testQuantLoc2.setLocation("bar2");
//		testQuantLoc2.setQuantity(2);
//		testProduct.addQuantLoc(testQuantLoc2);
//
//		QuantLoc testQuantLoc3 = new QuantLoc();
//		testQuantLoc3.setLocation("bar3");
//		testQuantLoc3.setQuantity(6);
//		testProduct.addQuantLoc(testQuantLoc3);
//
//
//		DBProduct dbProduct = new DBProduct();
//		try {
//			dbProduct.insertProduct(testProduct);
//			System.out.print("Product Insert OK");
//
//		}
//		catch(Exception ex) 
//		{
//			ex.printStackTrace();
//			System.out.print("Product Insert not OK");
//			fail();
//
//		}
//	}
//
//	
//	@Test
//	public void insertProductTest3() 
//	{
//		Product testProduct = new Product();
//		testProduct.setId(20);
//		testProduct.setName("Gordons");
//		testProduct.setCost(500.00);
//		testProduct.setPrice(90.00);
//		testProduct.setUnitVolume(70);
//		testProduct.setType("gin");
//		testProduct.setPurchased(10);
//
//
//		QuantLoc testQuantLoc1 = new QuantLoc();
//		testQuantLoc1.setLocation("bar1");
//		testQuantLoc1.setQuantity(2);
//		testProduct.addQuantLoc(testQuantLoc1);
//
//		QuantLoc testQuantLoc2 = new QuantLoc();
//		testQuantLoc2.setLocation("bar2");
//		testQuantLoc2.setQuantity(2);
//		testProduct.addQuantLoc(testQuantLoc2);
//
//		QuantLoc testQuantLoc3 = new QuantLoc();
//		testQuantLoc3.setLocation("bar3");
//		testQuantLoc3.setQuantity(6);
//		testProduct.addQuantLoc(testQuantLoc3);
//
//
//		DBProduct dbProduct = new DBProduct();
//		try {
//			dbProduct.insertProduct(testProduct);
//			System.out.print("Product Insert OK");
//
//		}
//		catch(Exception ex) 
//		{
//			ex.printStackTrace();
//			System.out.print("Product Insert not OK");
//			fail();
//
//		}
//	}

	@Test

	public void insertMeasurableTest()
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
		testMeasurable.setTotalVolume(70);
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

		DBMeasurable dbMeasurable = new DBMeasurable();
		try {
			dbMeasurable.insertMeasurable(testMeasurable);
			System.out.print("Measurable Insert OK");

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("Measurable Insert not OK");
			fail();

		}
	}



	@Test

	public void insertFruitTest()
	{

		Fruit testFruit = new Fruit();
		//		testFruit.setId(1);
		testFruit.setName("Oranges");
		testFruit.setCost(20.00);
		testFruit.setPrice(2.00);
		testFruit.setUnitVolume(10);
		testFruit.setType("Fruit");
		testFruit.setPurchased(20);
		testFruit.setTotalWeight(20);


		QuantLoc testQuantLoc1 = new QuantLoc();
		testQuantLoc1.setLocation("bar1");
		testQuantLoc1.setQuantity(5);
		testFruit.addQuantLoc(testQuantLoc1);

		QuantLoc testQuantLoc2 = new QuantLoc();
		testQuantLoc2.setLocation("bar2");
		testQuantLoc2.setQuantity(2);
		testFruit.addQuantLoc(testQuantLoc2);

		QuantLoc testQuantLoc3 = new QuantLoc();
		testQuantLoc3.setLocation("bar3");
		testQuantLoc3.setQuantity(13);
		testFruit.addQuantLoc(testQuantLoc3);

		DBFruit dbFruit = new DBFruit();
		try {
			dbFruit.insertFruit(testFruit);
			System.out.print("Fruit Insert OK");

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("Fruit Insert not OK");
			fail();
		}
	}


	@Test
	public void findProductTest() throws Exception
	{

		DBProduct dbProduct = new DBProduct();
		Product testProduct = dbProduct.findProduct(1,false);

		try{
			dbProduct.findProduct(1,false); 
			System.out.print("Test Product name:" + testProduct.getName());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();	
			System.out.print("Could not find Product");

		}
	}

	@Test
	public void updateProductTest() throws Exception
	{

		DBProduct dbProduct = new DBProduct();
		Product testProduct = dbProduct.findProduct(1, false);

		try{
			testProduct.setCost(400.00);
			System.out.print("Test Product new cost is:" + testProduct.getCost());
			dbProduct.updateProduct(testProduct);
		}
		catch (Exception ex)

		{
			ex.printStackTrace();
			System.out.print("could not update product");
		}
	}

	@Test
	public void deleteProductTest()
	{

		DBProduct dbProduct = new DBProduct();

		try{
			Product testProduct = dbProduct.findProduct(1, true);
			dbProduct.delete(testProduct);
			System.out.print("Product deleted");

		}
		catch (Exception ex)
		{
			System.out.print("Product not deleted");
		}

	}


}

