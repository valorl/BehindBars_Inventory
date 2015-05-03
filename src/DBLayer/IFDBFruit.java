package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBFruit
 * @author Group 3/C
 */
public interface IFDBFruit {
    // get all Fruits
    public ArrayList<Fruit> getAllFruits(boolean retriveAssociation) throws Exception;
    //get one Fruit by its id
    public Fruit findFruit(int id, boolean retriveAssociation) throws Exception;
    //insert a new Fruit
    public int insertFruit(Fruit product) throws Exception;
    //update information about an Fruit
    public int updateFruit(Fruit product) throws Exception;
    
}