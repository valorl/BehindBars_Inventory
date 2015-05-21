package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBMeasurable
 * @author Group 3/C
 */
public interface IFDBMeasurable {
    // get all Measurables
    public ArrayList<Measurable> getAllMeasurables(boolean retriveAssociation) throws Exception;
    //get one Measurable by its id
    public Measurable findMeasurable(int id, boolean retriveAssociation) throws Exception;
    //insert a new Measurable
    public int insertMeasurable(Measurable product) throws Exception;
    //update information about an Measurable
    public int updateMeasurable(Measurable product) throws Exception;
    
}