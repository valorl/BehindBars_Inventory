package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBProduct
 * @author Group 3/C
 */
public interface IFDBProduct {
    // get all Products
    public ArrayList<Product> getAllProducts(boolean retriveAssociation) throws Exception;
    //get one Product by its id
    public Product findProduct(int id, boolean retriveAssociation) throws Exception;
    //insert a new Product
    public int insertProduct(Product product) throws Exception;
    //update information about an Product
    public int updateProduct(Product product);
    
}