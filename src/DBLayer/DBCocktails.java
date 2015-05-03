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

import ModelLayer.Alcohol;

public class DBCocktails {

	private Connection con;

	public DBCocktails() 
	{
		con = DbConnection.getInstance().getDBcon();
	}

	public boolean isCocktail(Alcohol alcohol) throws Exception
	{
		ResultSet results = null;
		boolean isCocktail = false;
		String query = buildQuery("cocktailID = " + alcohol.getId());

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

	public Alcohol retrieveIngredients(Alcohol alcohol) throws Exception
	{
		Alcohol alcoholObj = alcohol;
		ResultSet results = null;
		String query = buildQuery("cocktailID = " + alcohol.getId());
		ArrayList<Alcohol> ingredients = new ArrayList<Alcohol>();

		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			DBAlcohol dbAlcohol = new DBAlcohol();

			while(results.next()) {

				Alcohol ingredient = dbAlcohol.findAlcohol(results.getInt("cocktailID"), false);
				double volume = results.getDouble("volume");
				alcoholObj.addIngredient(ingredient, volume);

			}
		}
		catch(Exception ex) {
			throw new Exception("Get ingredients failed.");
		}

		return alcoholObj;
	}

	public void insertContents(Alcohol alcohol) throws Exception
	{
		Alcohol alcoholObj = alcohol;
		HashMap<Alcohol,Double> ingredientList = alcohol.getCocktailContents();

		for(Map.Entry<Alcohol, Double> entry : ingredientList.entrySet()) 
		{
			try{
				insertIngredient(alcohol, entry.getKey(),entry.getValue());
			}
			catch(Exception ex) {
				throw new Exception(ex.toString());
			}
		}
	}
	
	
	
	public void updateContents(Alcohol alcohol) throws Exception 
	{
		Alcohol alcoholObj = alcohol;
		deleteContents(alcoholObj);		
		insertContents(alcoholObj);
	}
	
	public void deleteContents(Alcohol alcohol) throws Exception 
	{
		Alcohol alcoholObj = alcohol;
		Set<Alcohol> ingredients = alcoholObj.getCocktailContents().keySet();
		for(Alcohol ingredient : ingredients) 
		{
			deleteIngredient(alcoholObj, ingredient);
		}
	}
	
	private int insertIngredient(Alcohol alcohol, Alcohol ingredient, double volume) throws Exception
	{
		int rc = -1;

		PreparedStatement prepInsert = null;

		try{ // insert new ingredient
			String query="INSERT INTO cocktails(cocktailID, ingredientID, volume)  VALUES('?,?,?)";

			prepInsert = con.prepareStatement(query);
			prepInsert.setInt(1, alcohol.getId());
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
	
	private int deleteIngredient(Alcohol alcohol, Alcohol ingredient) throws Exception
	{
		int rc=-1;


		PreparedStatement prepDelete = null;

		try{ 


			String query="DELETE FROM cocktails WHERE cocktailID = '?' AND ingredientID = '?'";
			prepDelete = con.prepareStatement(query);
			prepDelete.setInt(1, alcohol.getId());
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
