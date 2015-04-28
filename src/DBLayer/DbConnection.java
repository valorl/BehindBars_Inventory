package DBLayer;

import java.sql.*;

/**
 * @Author Group C/3
 */

public class DbConnection
{   //Constants used to get access to the database
	//SQL Server
   private static final String  driver = "jdbc:sqlserver://kraka.ucn.dk:1433";
   //private static final String  driver = "jdbc:sqlserver://localhost:1433";
   private static final String  databaseName = ";databaseName=dmaj0914_2Sem_3";
    //SQL Server
   private static String  userName = ";user=dmaj0914_2Sem_3";
   // private static String  userName = "; user = ";
    private static String password = ";password=IsAllowed";
   
  
    private DatabaseMetaData dma;
    private static Connection con;
    // an instance of the class is generated
    private static DbConnection  instance = null;

    // the constructor is private to ensure that only one object of this class is created
    private DbConnection()
    {
    	String url = driver + databaseName + userName + password;

        try{
            //load af driver
            //SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Class loading OK");
          
        }
        catch(Exception e){
            System.out.println("Cannot find the driver");
            System.out.println(e.getMessage());
        }//end catch
        try{
            //connection to the database
           
            con = DriverManager.getConnection(url);
            //set autocommit
            con.setAutoCommit(true);
            dma = con.getMetaData(); // get meta data
            System.out.println("Connection to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Database product name " + dma.getDatabaseProductName());
        }//end try
        catch(Exception e){

            System.out.println("Problems with the connection to the database");
            System.out.println(e.getMessage());
            System.out.println(url);
        }//end catch
    }//end  constructor
	   
  //closeDb: closes the connection to the database
    public static void closeConnection()
    {
       	try{
            con.close();
            System.out.println("Connection closed");
        }
         catch (Exception e){
            System.out.println("Error trying to close the connection " +  e.getMessage());
         }
    }//end closeDB
		
    //getDBcon: Get-method, returns the connection to the database
    public  Connection getDBcon()
    {
       return con;
    }
    //this method is used to get the instance of the connection
    public static DbConnection getInstance()
    {
        if (instance == null)
        {
          instance = new DbConnection();
        }
        return instance;
    }
    public static void startTransaction()
    { try{
        con.setAutoCommit(false);
        }
      catch(Exception e){
        System.out.println("Start transaction failed.");
        System.out.println(e.getMessage());
      }
    }
    public static void commitTransaction()
    { try{
        con.setAutoCommit(true);
        }
      catch(Exception e){
        System.out.println("Commit transaction failed.");
        System.out.println(e.getMessage());
      }
    }
    public static void rollbackTransaction()
    { try{
        con.rollback();
        con.setAutoCommit(true);
        }
      catch(Exception e){
        System.out.println("Rollback transaction failed.");
        System.out.println(e.getMessage());
      }
    }
}//end DbConnection
