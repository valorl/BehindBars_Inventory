package DBLayer;

import static org.junit.Assert.fail;

import org.junit.Test;

import ModelLayer.Alcohol;
import ModelLayer.Product;
import ModelLayer.QuantLoc;

public class ProductTest {


	@Test
	public void insertProduct() 
	{
		Product product = new Product();
		product.setId(1);

		Alcohol alc = new Alcohol();
		
		QuantLoc  ql  = new QuantLoc();
		ql.setLocation("bar1");
		ql.setQuantity(70);

		product.addQuantLoc(ql);

		DBProduct dbProduct = new DBProduct();
		try {
			dbProduct.insertProduct(product);
		}
		catch(Exception ex) 
		{
			fail();
			ex.printStackTrace();
		}
	}
	
	
}
