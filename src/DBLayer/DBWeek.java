package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Employee;
import ModelLayer.Week;

public class DBWeek implements IFDBWeek{

	private  Connection con;
	/** Creates a new instance of DBWeek */
	public DBWeek() {
		con = DbConnection.getInstance().getDBcon();
	}

	public ArrayList<Week> getAllWeeks(boolean retriveAssociation) throws Exception
	{
		return muchWhere("", retriveAssociation);
	}

	//get one week based on its number, year, month
	public Week findWeek(int number,int month,int year, boolean retriveAssociation) throws Exception
	{   
		String wClause = "  number = '" + number + "'"+"', "+
				"year ='"+ year+"', "+"', "+
				"month ='"+ month+"'";

		Week week = null;
		try{
			week = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Week not found.");
		}

		return week;

	}
	
	//get one week based on its id
	public Week findWeekId(int id, boolean retriveAssociation) throws Exception
	{  
		String wClause = "  id = '" + id + "'";

		Week week = null;
		try{
			week = singleWhere(wClause, retriveAssociation);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Week not found.");
		}

		return week;

	}


	//insert a new week

	public int insertWeek(Week week) throws Exception
	{  //call to get the next id number
		int nextid = GetMax.getMaxId("Select max(id) from week");
		nextid = nextid + 1;
		System.out.println("next id = " +  nextid);
		week.setId(nextid);


		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new week
			String query="INSERT INTO week(id, number, month, year, employeeId)  VALUES(?,?,?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, week.getId());
			prepInsert.setInt(2, week.getNumber());
			prepInsert.setInt(3, week.getMonth());
			prepInsert.setInt(4, week.getYear());
			prepInsert.setDouble(5, week.getEmployee().getId());

			prepInsert.setQueryTimeout(5);
			prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("Week not inserted");
			throw new Exception ("Week is not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}

	@Override
	public int updateWeek(Week week) throws Exception
	{
		//Week weekObj  = week;
		int rc=-1;

		PreparedStatement prepUpdate = null;

		try{ // update week

			String query="UPDATE week SET id = ?, number = ?, month = ?, year = ?, employeeId = ? WHERE id = ? ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setInt(1, week.getId());
			prepUpdate.setInt(2, week.getNumber());
			prepUpdate.setInt(3, week.getMonth());
			prepUpdate.setInt(4, week.getYear());
			prepUpdate.setInt(5, week.getEmployee().getId());
			prepUpdate.setInt(6, week.getId());

			prepUpdate.setQueryTimeout(5);
			prepUpdate.executeUpdate();

		}//try to close
		catch(Exception ex){

			System.out.println("Update exception in week db: "+ex);

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

		try{ // delete from week


			String query="DELETE FROM week WHERE id = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, id);

			prepDelete.setQueryTimeout(5);
			prepDelete.executeUpdate();

		}//try to close	
		catch(Exception ex){
			System.out.println("Delete exception in week db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}

	public Week singleWhere(String wClause, boolean retrieveAssociation)  throws Exception
	{
		ResultSet results;
		Week weekObj = new Week();

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the week from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				weekObj = buildWeek(results);
				//assocaition is to be build
				stmt.close();
				if(retrieveAssociation){

					DBEmployee dbEmployee = new DBEmployee();
					Employee employee = dbEmployee.findEmployee(weekObj.getEmployee().getId(), false);
					weekObj.setEmployee(employee);
				}
			}
			else{ //no week was found
				weekObj = null;
				throw new Exception("Week not found.");
			}
		}//end try	
		catch(Exception e){
			System.out.println("Query exception: "+e);
		}
		return weekObj;
	}

	private ArrayList<Week> muchWhere(String wClause, boolean retrieveAssociation) throws Exception
	{
		ResultSet results;
		ArrayList<Week> list = new ArrayList<Week>();	

		String query =  buildQuery(wClause);
		System.out.println(query);
		try{ // read the week from the database
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);


			while( results.next() ){
				Week weekObj = new Week();
				weekObj = buildWeek(results);	
				list.add(weekObj);
			}//end while
			stmt.close();     
			if(retrieveAssociation) {}

			if(list.size() == 0) 
			{
				throw new Exception("No weeks found.");
			}



		}//try to close	
		catch(Exception e){
			System.out.println("Query exception - select: "+e);
			e.printStackTrace();
		}
		return list;
	}

	private Week buildWeek(ResultSet results)
	{   
		Week weekObj = new Week();
		try{ // the columns from the table week are used

			DBEmployee dbEmployee = new DBEmployee();
			Employee employee = dbEmployee.findEmployee(results.getInt("employeeId"), false);

			weekObj.setId(results.getInt("id"));
			weekObj.setNumber(results.getInt("number"));
			weekObj.setMonth(results.getInt("month"));
			weekObj.setYear(results.getInt("year"));			
			weekObj.setEmployee(employee);


		}
		catch(Exception e)
		{
			System.out.println("Error in building the week object");
		}


		return weekObj;
	}

	private String buildQuery(String wClause)
	{
		String query="SELECT id, number, month, year, employeeId FROM week";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}


}
