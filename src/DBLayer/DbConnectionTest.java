package DBLayer;

import static org.junit.Assert.*;

import org.junit.Test;

public class DbConnectionTest {

	@Test
	public void test() {
		DbConnection dbCon = DbConnection.getInstance();
		if(dbCon != null)
		{
			System.out.println("Connection to database SUCCESSFUL");
		}
		else{
		    fail("Cannot connect to the DB");
		}
	}

}