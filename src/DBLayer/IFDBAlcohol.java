package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBAlcohol
 * @author Group 3/C
 */
public interface IFDBAlcohol {
    // get all Alcohols
    public ArrayList<Alcohol> getAllAlcohols(boolean retriveAssociation) throws Exception;
    //get one Alcohol by its id
    public Alcohol findAlcohol(int id, boolean retriveAssociation) throws Exception;
    //insert a new Alcohol
    public int insertAlcohol(Alcohol product) throws Exception;
    //update information about an Alcohol
    public int updateAlcohol(Alcohol product) throws Exception;
    
}