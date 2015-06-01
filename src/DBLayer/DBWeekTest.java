package DBLayer;

import static org.junit.Assert.*;

import org.junit.Test;

import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.Week;
import ModelLayer.Employee;

public class DBWeekTest {
	
	@Test
	public void insertWeekTest()
	{			
		Week testWeek = new Week();
		testWeek.setNumber(2);
		testWeek.setMonth(3);
		testWeek.setYear(2015);	
		
		Product testProduct = new Product();
		testProduct.setId(7);
		testProduct.setName("Bombay Gin");
		testProduct.setCost(100.00);
		testProduct.setPrice(200.00);
		testProduct.setUnitVolume(70);
		testProduct.setType("Alcohol");
		testProduct.setPurchased(20);
				
		ProductState state = new ProductState(testProduct);
		testWeek.addState(state);

		Employee employee = new Employee();
		testWeek.setEmployee(employee);
		
		DBWeek dbWeek = new DBWeek();
		try {
			dbWeek.insertWeek(testWeek);
			System.out.print("Insert complete");
			
			}
		catch(Exception ex)
		{
			fail();
			ex.printStackTrace();
			System.out.print("Insert failed");
		}
	}
	}



