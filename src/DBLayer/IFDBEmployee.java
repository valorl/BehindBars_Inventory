package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBProduct
 * @author Group 3/C
 */
public interface IFDBEmployee {
    // get all Products
    public ArrayList<Employee> getAllEmployees(boolean retriveAssociation) throws Exception;
    //get one Product by its id
    public Employee findEmployee(int id, boolean retriveAssociation) throws Exception;
    //insert a new Product
    public int insertEmployee(Employee employee) throws Exception;
    //update information about an Product
    public int updateEmployee(Employee employee) throws Exception;
    
}