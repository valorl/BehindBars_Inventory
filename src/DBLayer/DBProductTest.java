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
		//testProduct.addQuantLoc(quantLocs);
		testProduct.setUnitVolume(70);
		testProduct.setType("Alcohol");
		testProduct.setPurchased(20);
		
		QuantLoc  quantLocs = new QuantLoc();
		quantLocs.setLocation("bar1");
		quantLocs.setQuantity(70);
		testProduct.addQuantLoc(quantLocs);
		

		//Alcohol testAlcohol = new Alcohol();
		



		DBProduct dbProduct = new DBProduct();
		try {
			dbProduct.insertProduct(testProduct);
			System.out.print("Insert OK");
			
		}
		catch(Exception ex) 
		{
			fail();
			ex.printStackTrace();
			System.out.print("Insert not OK");
		}
	}	
	
}
