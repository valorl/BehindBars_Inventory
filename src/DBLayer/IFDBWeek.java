package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBProduct
 * @author Group 3/C
 */
public interface IFDBWeek {
    // get all Week
    public ArrayList<Week> getAllWeeks(boolean retriveAssociation) throws Exception;
    //get one Week by its id
    public Week findWeekId(int id, boolean retriveAssociation) throws Exception;
    //get one Week by its number, year, month
    public Week findWeek(int number, int year, int month, boolean retriveAssociation) throws Exception;
    //insert a new Product
    public int insertWeek(Week week) throws Exception;
    //update information about an Product
    public int updateWeek(Week week) throws Exception;
    
}