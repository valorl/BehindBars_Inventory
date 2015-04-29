package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Employee;

public class DBEmployee implements IFDBEmployee {

	private  Connection con;
	/** Creates a new instance of DBEmployee */
	public DBEmployee() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Employee> getAllEmployees(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}

	//get one employee based on its id
	public Employee findEmployee(int id, boolean retriveAssociation) throws Exception
	{
		String wClause = "  id = '" + id + "'";

		Employee employee = null;
		try{
			employee = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Employee not found.");
		}

		return employee;

	}

	public int insertEmployee(Employee employee) throws Exception
	{  //call to get the next id number
		int nextid = GetMax.getMaxId("Select max(id) from employee");
		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		employee.setId(nextid);


		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new employee
			String query="INSERT INTO employee(id, name, phoneNo)  VALUES('?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, employee.getId());
			prepInsert.setString(2, employee.getName());
			prepInsert.setString(3, employee.getPhoneNo());

			prepInsert.setQueryTimeout(5);
			prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("Employee not inserted");
			throw new Exception ("Employee is not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}


	@Override
	public int updateEmployee(Employee employee) throws Exception
	{
		//Employee employeeObj  = employee;
		int rc=-1;

		PreparedStatement prepUpdate = null;

		try{ // update employee

			String query="UPDATE employee SET id = '?', name = '?', phoneNo = '?' WHERE id = '?' ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, employee.getId());
			prepUpdate.setString(2, employee.getName());
			prepUpdate.setString(3, employee.getPhoneNo());
			prepUpdate.setInt(4, employee.getId());

			prepUpdate.setQueryTimeout(5);
			prepUpdate.executeUpdate();


		}//try to close
		catch(Exception ex){

			System.out.println("Update exception in employee db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);
	}

	public int delete(int id) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ // delete from employee


			String query="DELETE FROM employee WHERE id = '?'";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, id);

			prepDelete.setQueryTimeout(5);
			prepDelete.executeUpdate();


		}//try to close	
		catch(Exception ex){
			System.out.println("Delete exception in employee db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);

	}

	public Employee singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Employee employeeObj = new Employee();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the employee from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				employeeObj = buildEmployee(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){}
			}
			else{ //no employee was found
				employeeObj = null;
				throw new Exception("Employee not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return employeeObj;
	}

	private ArrayList<Employee> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Employee> list = new ArrayList<Employee>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the employee from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Employee employeeObj = new Employee();
				employeeObj = buildEmployee(results);	
				list.add(employeeObj);
			}//end while
			stmt.close();     
			if(retrieveAssociation) {}

			if(list.size() == 0) 
			{
				throw new Exception("No employees found.");
			}

		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Employee buildEmployee(ResultSet results)
	{   
		Employee employeeObj = new Employee();
		try{ // the columns from the table employee are used


			employeeObj.setId(results.getInt("id"));
			employeeObj.setName(results.getString("name"));
			employeeObj.setPhoneNo(results.getString("phoneNo"));

		}
		catch(Exception e)
		{
			System.out.println("Error in building the employee object");
		}


		return employeeObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT id, name, phoneNo  FROM employee";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}


}
