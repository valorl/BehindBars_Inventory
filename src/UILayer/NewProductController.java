package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import DBLayer.DBProduct;
import ModelLayer.Measurable;
import ModelLayer.Product;
import UILayer.TableData.IngredientListData;


public class NewProductController implements Initializable{

	private InventoryController caller;

	private Product product;

	private Stage stage;

	private ObservableList<IngredientListData> listData = FXCollections.observableArrayList();

	@FXML
	private TextField txt_name,txt_volume,txt_unit,txt_cost,txt_retail,txt_full,txt_empty,txt_ing_volume;

	@FXML
	private Label lbl_output;

	@FXML
	private ComboBox<String> cbox_category, cbox_cocktail_cat;

	@FXML
	private ComboBox<Measurable> cbox_ingredient;
	@FXML
	private ListView<IngredientListData> list_ingredients;

	@FXML
	private AnchorPane anc_weights, anc_cat_new, anc_ingredients;

	@FXML
	private Button btn_save, btn_cancel, btn_add, btn_remove;

	private ObservableList<Measurable> ingredients;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}

	public void init(Stage stage) 
	{
		this.stage = stage;
		
		lbl_output.setStyle("-fx-text-fill: #FF0000;");


		initComboCat();
		initButtons();
		initComboCocktailCat();
		initComboIngredients();
		initIngredientList();
	}

	private void initButtons() {
		btn_save.setOnAction((e) -> {

			if(createProduct()) {
				caller.addProduct(product);
				stage.close();
			}


		});

		btn_cancel.setOnAction((e) -> {
			stage.close();
		});

		btn_add.setOnAction((e) -> {
			if(cbox_ingredient.getValue() != null && txt_ing_volume.getText().length() > 0) {
				try {
					IngredientListData listItem = new IngredientListData(cbox_ingredient.getValue(),Double.parseDouble(txt_ing_volume.getText()));
					listData.add(listItem);

					output("");
				}
				catch(NumberFormatException ex) {
					output("Error: Wrong input");
				}
			}
		});

		btn_remove.setOnAction((e) -> {
			if(list_ingredients.getSelectionModel().getSelectedItems().size() > 0) {
				
				for(IngredientListData item : list_ingredients.getSelectionModel().getSelectedItems()) {
					
					listData.remove(item);
				}
			}
		});
	}

	private boolean createProduct() {
		product = new Product();
		product.setType(cbox_category.getValue().toLowerCase());


		String name = txt_name.getText();
		String unit = txt_volume.getText();
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
					Measurable alc = new Measurable(product);					
					alc.setFullWeight(Double.parseDouble(full));
					alc.setEmptyWeight(Double.parseDouble(empty));
					
					
					if(TypeManager.isMixed(cbox_category.getValue().toLowerCase())) {
						for(IngredientListData item : listData) {
							alc.addIngredient(item.getIngredient(), item.getVolume());
							System.out.println("\n\n CREATE PRODUCT -  ADD ING: Ingredient ID: "  + item.getIngredient().getId() + "  Ingredient Name: " + item.getIngredient().getName() + "\n\n");
						}
					}
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

	private void initComboCat() 
	{
		//caller.updateCategories();
		System.out.println("CATEGORIES: " + caller.getCategories().size() + ".");
		cbox_category.setItems(caller.getCategories());
		
		cbox_category.getItems().remove("Spirits");
		cbox_category.setValue(cbox_category.getItems().get(0));
		cbox_category.setOnAction((e) -> {
			updateLayout();
		});

		updateLayout();


	}


	private void initComboCocktailCat() {
		//caller.updateCategories();

		ObservableList<String> ingCategories = FXCollections.observableArrayList();
		for(String category : caller.getCategories()) {
			if(TypeManager.isMeasurableDrinkType(category.toLowerCase()) && !(TypeManager.isMixed(category.toLowerCase()))) {
				ingCategories.add(category);
			}
		}
		cbox_cocktail_cat.setItems(ingCategories);
		cbox_cocktail_cat.setValue(cbox_cocktail_cat.getItems().get(0));

		cbox_cocktail_cat.setOnAction((e) -> {
			initComboIngredients();
		});
	}

	private void initComboIngredients() {
		ingredients = FXCollections.observableArrayList();

		DBProduct dbProd = new DBProduct();
		try {
			if(cbox_cocktail_cat.getValue() != null) {
				ArrayList<Product> products = dbProd.getAllOf(cbox_cocktail_cat.getValue().toLowerCase(), true);

				if(products != null && products.size() > 0) {
					for(Product product : products) {
						if(product instanceof Measurable) {
							Measurable mes = new Measurable(product);
							ingredients.add(mes);							
						}
					}
				}

			}

			cbox_ingredient.setItems(ingredients);
			cbox_ingredient.setValue(cbox_ingredient.getItems().get(0));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void initIngredientList() {
		list_ingredients.setItems(listData);

		list_ingredients.setCellFactory(new Callback<ListView<IngredientListData>, ListCell<IngredientListData>>(){

			@Override
			public ListCell<IngredientListData> call(ListView<IngredientListData> p) {

				ListCell<IngredientListData> cell = new ListCell<IngredientListData>(){

					@Override
					protected void updateItem(IngredientListData data, boolean bln) {
						super.updateItem(data, bln);
						if (data != null) {
							setText( "" + data.getVolume() + "cl of " + data.getIngredient().getName());
						}
					}

				};

				return cell;
			}
		});

	}

	private void updateLayout() {

		if(TypeManager.isMeasurableType(cbox_category.getValue().toLowerCase())) {
			anc_weights.setDisable(false);

			//			this.product = (Measurable) this.product;

			if(TypeManager.isMixed(cbox_category.getValue().toLowerCase())) {
				anc_ingredients.setDisable(false);
			}
			else {
				anc_ingredients.setDisable(true);
			}

		}
		else {

			//			this.product = (Product) this.product;

			anc_weights.setDisable(true);
			anc_ingredients.setDisable(true);
		}
	}

	private void output(String string) {
		lbl_output.setText(string);
	}

	public void setCaller(InventoryController caller) {
		this.caller = caller;
	}



}


