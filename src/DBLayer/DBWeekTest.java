package DBLayer;

import static org.junit.Assert.*;

import org.junit.Test;

import ModelLayer.Week;
import ModelLayer.Employee;

public class DBWeekTest {
	
	@Test
	public void insertWeekTest()
	{			
		Week testWeek = new Week();
		testWeek.setId(2.3);
		testWeek.setNumber(2);
		testWeek.setMonth(3);
		testWeek.setYear(2015);	

		Employee testEmp = new Employee();
		testEmp.setId(01);
		
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

