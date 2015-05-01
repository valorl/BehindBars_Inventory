package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBProductState
 * @author Group 3/C
 */
public interface IFDBProductState {
    // get all Product states
    public ArrayList<ProductState> getAllProductStates(boolean retriveAssociation) throws Exception;
    //get one Product by its id
    public ProductState findProductState(int id, boolean retriveAssociation) throws Exception;
    //insert a new Product
    public int insertProductState(ProductState productState, int weekID) throws Exception;
    //update information about an ProductState
    public int updateProductState(ProductState productState, int weekID) throws Exception;
    
}