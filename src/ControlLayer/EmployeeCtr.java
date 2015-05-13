package ControlLayer;

import DBLayer.DBEmployee;
import ModelLayer.Employee;

public class EmployeeCtr {

	private DBEmployee dbEmployee;

	public EmployeeCtr() {
		dbEmployee = new DBEmployee();
	}

	public void createEmployee(String name, String phoneNo) throws Exception
	{
		Employee employee = new Employee(name, phoneNo);
		
		try{
			dbEmployee.insertEmployee(employee);
		}
		catch(Exception e) {
			throw new Exception("Employee not inserted");
		}
	}
	
	public Employee findEmployee(int id, boolean retriveAssociation) throws Exception
	{
		try {
			return dbEmployee.findEmployee(id, retriveAssociation);
		}
		catch(Exception e) {
			throw new Exception("Employee not found");
		}
	}
	
	public void updateEmployee(int id, String name, String phoneNo) throws Exception
	{
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName(name);
		employee.setPhoneNo(phoneNo);
		
		dbEmployee.updateEmployee(employee);
		
	}
	
	public void deleteEmployee(int id) throws Exception
	{
		dbEmployee.delete(id);
	}

}
