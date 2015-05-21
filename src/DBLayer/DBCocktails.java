package DBLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ModelLayer.Measurable;

public class DBCocktails {

	private Connection con;

	public DBCocktails() 
	{
		con = DbConnection.getInstance().getDBcon();
	}

	public boolean isCocktail(Measurable measurable) throws Exception
	{
		ResultSet results = null;
		boolean isCocktail = false;
		String query = buildQuery("cocktailID = " + measurable.getId());

		try{
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if( results.next() ){
				isCocktail = true;
			}
			else 
			{
				isCocktail = false;
			}
		}
		catch(Exception ex) 
		{
			throw new Exception("isCocktail check unsuccessful");
		}

		return isCocktail;
	}

	public Measurable retrieveIngredients(Measurable measurable) throws Exception
	{
		Measurable measurableObj = measurable;
		ResultSet results = null;
		String query = buildQuery("cocktailID = " + measurable.getId());
		ArrayList<Measurable> ingredients = new ArrayList<Measurable>();

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			DBMeasurable dbMeasurable = new DBMeasurable();

			while(results.next()) {

				Measurable ingredient = dbMeasurable.findMeasurable(results.getInt("cocktailID"), false);
				double volume = results.getDouble("volume");
				measurableObj.addIngredient(ingredient, volume);

			}
		}
		catch(Exception ex) {
			throw new Exception("Get ingredients failed.");
		}

		return measurableObj;
	}

	public void insertContents(Measurable measurable) throws Exception
	{
		Measurable measurableObj = measurable;
		HashMap<Measurable,Double> ingredientList = measurable.getCocktailContents();

		for(Map.Entry<Measurable, Double> entry : ingredientList.entrySet()) 
		{
			try{
				insertIngredient(measurable, entry.getKey(),entry.getValue());
			}
			catch(Exception ex) {
				throw new Exception(ex.toString());
			}
		}
	}
	
	
	
	public void updateContents(Measurable measurable) throws Exception 
	{
		Measurable measurableObj = measurable;
		deleteContents(measurableObj);		
		insertContents(measurableObj);
	}
	
	public void deleteContents(Measurable measurable) throws Exception 
	{
		Measurable measurableObj = measurable;
		Set<Measurable> ingredients = measurableObj.getCocktailContents().keySet();
		for(Measurable ingredient : ingredients) 
		{
			deleteIngredient(measurableObj, ingredient);
		}
	}
	
	private int insertIngredient(Measurable measurable, Measurable ingredient, double volume) throws Exception
	{
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new ingredient
			String query="INSERT INTO cocktails(cocktailID, ingredientID, volume)  VALUES(?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, measurable.getId());
			prepInsert.setInt(2, ingredient.getId());
			prepInsert.setDouble(3,volume);

			prepInsert.setQueryTimeout(5);
			rc = prepInsert.executeUpdate();


		}//end try
		catch(SQLException ex){
			System.out.println("Ingredient not inserted");
			throw new Exception ("Ingredient not inserted correctly");
		}
		finally 
		{
			prepInsert.close();
		}
		return(rc);
	}
	
	private int deleteIngredient(Measurable measurable, Measurable ingredient) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ 


			String query="DELETE FROM cocktails WHERE cocktailID = ? AND ingredientID = ?";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, measurable.getId());
			prepDelete.setInt(2, ingredient.getId());

			prepDelete.setQueryTimeout(5);
			rc = prepDelete.executeUpdate();

		}
		catch(Exception ex){
			System.out.println("Delete exception in ingredient db: "+ex);
		}
		finally
		{
			prepDelete.close();
		}
		return(rc);
	}
	
	private String buildQuery(String wClause)
	{
		String query="SELECT cocktailID, ingredientID, volume FROM cocktails";

		if (wClause.length()>0)
			query=query+" WHERE "+ wClause;

		return query;
	}

}
