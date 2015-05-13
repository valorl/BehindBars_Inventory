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
		testWeek.setNumber(2);
		testWeek.setMonth(3);
		testWeek.setYear(2015);	
		

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
		
		@Test
		public void findWeekTest()
		{
			Week findWeek = new Week();
			findWeek.getNumber();
			findWeek.getMonth();
			findWeek.getYear();
			
			DBWeek dbWeek = new DBWeek();
			try {
				dbWeek.findWeek(2, 3, 2015, retriveAssociation);
				System.out.print("Week found");
			}
			catch(Exception ex)
			{
				fail();
				ex.printStackTrace();
				System.out.print("Could not find week");
			}
		}
		
		/*@Test
		public void updateWeekTest()
		{
			Week updateWeek = new Week();
			
		}
		*/
		
		/*@Test
		public void deleteWeekTest()
		{
			Week deleteWeek = new Week();
			deleteWeek.
		}
		*/
	}



