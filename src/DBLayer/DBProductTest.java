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
		
		QuantLoc testQuantLoc1 = new QuantLoc();
		testQuantLoc1.setLocation("bar1");
		testQuantLoc1.setQuantity(70);
		testProduct.addQuantLoc(testQuantLoc1);
		
		QuantLoc testQuantLoc2 = new QuantLoc();
		testQuantLoc2.setLocation("bar2");
		testQuantLoc2.setQuantity(70);
		testProduct.addQuantLoc(testQuantLoc2);
		
		QuantLoc testQuantLoc3 = new QuantLoc();
		testQuantLoc3.setLocation("bar3");
		testQuantLoc3.setQuantity(70);
		testProduct.addQuantLoc(testQuantLoc3);
		

		//Alcohol testAlcohol = new Alcohol();
		
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
	
}
