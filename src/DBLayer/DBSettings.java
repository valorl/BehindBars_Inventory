package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBSettings implements IFDBSettings {

	private  Connection con;
	/** Creates a new instance of DBProduct */
	public DBSettings() {
		con = DbConnection.getInstance().getDBcon();
	}

	public String findSetting(String settingCode, boolean retriveAssociation) throws Exception
	{
		String wClause = "  settingCode = '" + settingCode + "'";

		String setting = null;
		try{
			setting = buildQuery(wClause);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Setting not found.");
		}

		return setting;
	}
	//insert a new Setting
	public int insertSetting(String settingCode, String value) throws Exception
	{
		//call to get the next id number
		//int nextid = GetMax.getMaxId("Select max(id) from week");
		//nextid = nextid + 1;
		//System.out.println("next id = " +  nextid);
		//week.setId(nextid);


		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new week
			String query="INSERT INTO settings(settingCode, value)  VALUES(?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setString(1, settingCode);
			prepInsert.setString(2, value);

			prepInsert.setQueryTimeout(5);
			prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("Setting not inserted");
			throw new Exception ("Setting is not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);

	}
	//update information about an SettingState
	public int updateSettingState(String settingCode, String value) throws Exception
	{
		int rc=-1;

		PreparedStatement prepUpdate = null;

		try{ // update week

			String query="UPDATE week SET settingCode = ?, value = ? WHERE settingCode = ? ";

			prepUpdate = con.prepareStatement(query);
			prepUpdate.setString(1, settingCode);
			prepUpdate.setString(2, value);
			prepUpdate.setString(3, settingCode);

			prepUpdate.setQueryTimeout(5);
			prepUpdate.executeUpdate();

		}//try to close
		catch(Exception ex){

			System.out.println("Update exception in settings db: "+ex);

		}
		finally 
		{

			prepUpdate.close();

		}
		return(rc);

	}

	public int delete(String settingCode) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ // delete from week


			String query="DELETE FROM week WHERE settingCode = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setString(1, settingCode);

			prepDelete.setQueryTimeout(5);
			prepDelete.executeUpdate();

		}//try to close	
		catch(Exception ex){
			System.out.println("Delete exception in settings db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}


	private String buildQuery(String wClause)
	{
		String query="SELECT settingCode, value FROM settings";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}

}
