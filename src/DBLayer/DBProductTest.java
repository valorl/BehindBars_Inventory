package DBLayer;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import ModelLayer.Alcohol;
import ModelLayer.Product;
import ModelLayer.QuantLoc;

public class DBProductTest {

	@Test
	public void insertProductTest() 
	{
		Product testProduct = new Product();
		testProduct.setId(1);
		testProduct.setName("Absolut Vodka");
		testProduct.setCost(100.00);
		testProduct.setPrice(300.00);
		testProduct.setUnitVolume(70);
		testProduct.setType("Alcohol");
		testProduct.setPurchased(20);
		
		Alcohol testAlcohol = new Alcohol();
		testAlcohol.getId();
		testAlcohol.getFullWeight();
		testAlcohol.getEmptyWeight();
		testAlcohol.getDensity();
		testAlcohol.getTotalVolume();
		testAlcohol.getType();
		
				
				
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
			System.out.print("Insert OK");

		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.print("Insert not OK");
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

