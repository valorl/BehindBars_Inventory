package DBLayer;
import ModelLayer.*;

import java.util.ArrayList;
/**
 * Interface of the DBProduct
 * @author Group 3/C
 */
public interface IFDBEmployee {
    // get all Employees
    public ArrayList<Employee> getAllEmployees(boolean retriveAssociation) throws Exception;
    //get one Employee by its id
    public Employee findEmployee(int id, boolean retriveAssociation) throws Exception;
    //insert a new Employee
    public int insertEmployee(Employee employee) throws Exception;
    //update information about an Employee
    public int updateEmployee(Employee employee) throws Exception;
    
}