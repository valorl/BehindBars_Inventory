package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ModelLayer.Measurable;
import ModelLayer.Product;


public class NewProductController implements Initializable{

	private InventoryController caller;

	private Product product;

	private Stage stage;

	@FXML
	private TextField txt_name,txt_unit,txt_cost,txt_retail,txt_full,txt_empty;
	
	@FXML
	private Label txt_output;

	@FXML
	private ComboBox<String> cbox_category;

	@FXML
	private AnchorPane anc_weights;

	@FXML
	private AnchorPane anc_ingredients;

	@FXML
	private Button btn_confirm;

	@FXML 
	private Button btn_cancel;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void init(Stage stage) 
	{
		this.stage = stage;
		txt_output.setStyle("-fx-text-fill: #FF0000;");
		initComboBox();
		initButtons();
	}

	private void initButtons() {
		btn_confirm.setOnAction((e) -> {

			if(createProduct()) {
				caller.addProduct(product);
				stage.close();
			}
			

		});

		btn_cancel.setOnAction((e) -> {
			stage.close();
		});
	}

	private boolean createProduct() {

		product = new Product();
		product.setType(cbox_category.getValue().toLowerCase());

		String name = txt_name.getText();
		String unit = txt_unit.getText();
		String cost = txt_cost.getText();
		String retail = txt_retail.getText();

		String full = txt_full.getText();
		String empty = txt_empty.getText();


		if(name.length() > 0
				&& unit.length() > 0
				&& cost.length() > 0
				&& retail.length() > 0) {

			product.setName(name);

			try {
				product.setUnitVolume(Double.parseDouble(unit));
				product.setCost(Double.parseDouble(cost));
				product.setPrice(Double.parseDouble(retail));

				if(TypeManager.isMeasurableDrinkType(cbox_category.getValue().toLowerCase())) {
					Measurable alc = (Measurable) product;					
					alc.setFullWeight(Double.parseDouble(full));
					alc.setEmptyWeight(Double.parseDouble(empty));
					product = alc; 
				}

			}
			catch(NumberFormatException ex) {
				output("Error: Wrong input");
				return false;
			}


		}
		else {
			output("Some fields are empty.");
			return false;
		}

		return true;


	}

	private void initComboBox() 
	{
		caller.updateCategories();
		System.out.println("CATEGORIES: " + caller.getCategories().size() + ".");
		cbox_category.setItems(caller.getCategories());
		
		//cbox_category.getItems().remove(0);
		cbox_category.setValue(cbox_category.getItems().get(0));
		cbox_category.setOnAction((e) -> {
			updateLayout();
		});

		updateLayout();


	}

	private void updateLayout() {
		if(TypeManager.isMeasurableDrinkType(cbox_category.getValue().toLowerCase())) {
			anc_weights.setDisable(false);
			anc_ingredients.setDisable(true);
		}
		else {
			anc_weights.setDisable(true);
			anc_ingredients.setDisable(true);
		}
	}

	private void output(String string) {
		txt_output.setText(string);
	}

	public void setCaller(InventoryController caller) {
		this.caller = caller;
	}
}
