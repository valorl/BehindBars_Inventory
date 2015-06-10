package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ControlLayer.EmployeeCtr;
import ModelLayer.Employee;
import ModelLayer.Measurable;

public class EmpController implements Initializable{

	private EmployeeCtr empCtr;
	private static Employee loggedEmp;

	Stage window;

	@FXML
	private TextField txt_emp_login, txt_id, txt_name, txt_phone;

	@FXML
	private Button btn_login, btn_addupdate, btn_remove, btn_logout;

	@FXML
	private ListView<Employee> list_employees;

	@FXML
	private Label lbl_output, lbl_logged_emp;
	
	@FXML
	private CheckBox chbox_new;
	
	private ObservableList<Employee> employees = FXCollections.observableArrayList();

	public EmpController() 
	{
		empCtr = new EmployeeCtr();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void init(Stage stage) {
		window = stage;

		initList();
		initButtons();
		initCheckBox();
		clearOutput();
		updateLoggedLbl();
		showHideLogout();
	}

	private void initButtons() {

		btn_login.setOnAction(e -> {
			login();
			updateLoggedLbl();
			showHideLogout();
		});

		btn_addupdate.setOnAction(e -> {
			addUpdate();
		});
		
		btn_remove.setOnAction(e -> {
			remove();
		});
		
		btn_logout.setOnAction(e -> {
			if(this.loggedEmp != null) {
				this.loggedEmp = null;
				updateLoggedLbl();
				showHideLogout();
			}
		});
	}
	
	private void initCheckBox() {
		chbox_new.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
			if(isSelected) {
				txt_id.setDisable(true);
				btn_addupdate.setText("Add");
			}
			else {
				txt_id.setDisable(false);
				btn_addupdate.setText("Update");
			}
		});
	}

	private void remove() {
		if(list_employees.getSelectionModel().getSelectedItem() != null) {
			try {
				empCtr.deleteEmployee(list_employees.getSelectionModel().getSelectedItem().getId());
				output("Employee removed successfully.", false);
				updateData();
			} catch (Exception e1) {
				e1.printStackTrace();
				output("An error occured while removing the employee.",true);
			}
		}
	}

	private void addUpdate() {
		if(txt_name.getText().length() > 0
				&& txt_phone.getText().length() > 0) {

			int id;
			String name = txt_name.getText();
			String phoneNo = txt_phone.getText();

			try {
				
				if(!chbox_new.isSelected() && txt_id.getText().length() > 0) {
					id = Integer.parseInt(txt_id.getText());
					try {
						empCtr.updateEmployee(id, name, phoneNo);
						output("Employee updated successfully.",false);
						updateData();
					} catch (Exception e1) {
						e1.printStackTrace();
						output("An error occured while updating employee (ID:" + id + ").",true);
					}
				}
				else {
					try {
						empCtr.createEmployee(name, phoneNo);
						output("Employee added successfully.",false);
					} catch (Exception e1) {
						e1.printStackTrace();
						output("An error occured while adding a new employee.",true);
					}
				}
				
				updateData();
			}
			catch(NumberFormatException nfe) {
				nfe.printStackTrace();
				output("Error: Invalid ID.", true);
			}

		}
	}

	private void login() {
		if(txt_emp_login.getText().length() > 0) {
			try {
				int empID = Integer.parseInt(txt_emp_login.getText());
				try {
					Employee foundEmployee = empCtr.findEmployee(empID, false);
					loggedEmp = foundEmployee;
					output("Login successful.", false);
				} catch (Exception e1) {
					e1.printStackTrace();
					output("Error: Employee not found.", true);
				}
			}
			catch(NumberFormatException nfe) {
				nfe.printStackTrace();
				output("Error: The employee ID is invalid.", true);
			}

		}
		else {
			output("Error: The 'Employee ID' textfield is empty.", true);
		}
	}

	private void initList() {
		updateData();
		
		list_employees.setOnMouseClicked(e -> {
			if(list_employees.getSelectionModel().getSelectedItem() != null) {
				Employee selEmp = list_employees.getSelectionModel().getSelectedItem();
				txt_id.setText("" +selEmp.getId());
				txt_name.setText(selEmp.getName());
				txt_phone.setText(selEmp.getPhoneNo());
			}
		});
	}

	private void updateData() {
		employees.clear();
		try {
			employees.addAll(empCtr.getAllEmployees());
			list_employees.setItems(employees);
		} catch (Exception e) {
			output("Error while fetching employees from the database.", true);
			e.printStackTrace();
		}
	}

	private void output(String message, boolean isError) {
		if(isError) {
			lbl_output.setStyle("-fx-text-fill: #ff0000");
		}
		else {
			lbl_output.setStyle("-fx-text-fill: #008000");
		}
		lbl_output.setText(message);
	}

	private void clearOutput() {
		lbl_output.setText("");
	}
	
	private void showHideLogout() {
		if(this.loggedEmp != null) {
			btn_logout.setVisible(true);
		}
		else {
			btn_logout.setVisible(false);
		}
	}
	
	private void updateLoggedLbl() {
		if(this.loggedEmp != null) 	{
			lbl_logged_emp.setText(loggedEmp.toString());
		}
	}
}
