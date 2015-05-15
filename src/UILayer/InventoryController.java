package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import ControlLayer.ProductCtr;
import ModelLayer.Product;
import UILayer.TableData.InventoryData;

public class InventoryController implements Initializable, ChangeablePane{

	private static final String DEFAULT_SELECTION = "spirits";

	private PaneChanger changer;
	private ProductCtr productCtr;

	private SimpleStringProperty container = new SimpleStringProperty("");
	private SimpleStringProperty unit = new SimpleStringProperty("");
	
	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private ComboBox cbox_category = new ComboBox();

	@FXML
	private TableView<InventoryData> table_inventory = new TableView<InventoryData>();

	private ObservableList<InventoryData> data;

	private ObservableList<String> categories;

	public InventoryController() 
	{
		productCtr = new ProductCtr();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainHbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());

		initComboBox();
		createTable(DEFAULT_SELECTION);
		updateData(true);
	}

	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}

	// Initialize Table for Weightable liquids
	private void createTable(String category)
	{
		
		boolean isAlcohol = Product.checkTypeForAlcoholic(category);
		if(category.toLowerCase().equals("spirits")) isAlcohol = true;
		
		
		table_inventory.setMaxWidth(802);
		table_inventory.setPrefWidth(802);
		container.set("bottle");
		unit.set("cl");
		

		table_inventory.setEditable(true);



		// NAME
		TableColumn<InventoryData, String> nameCol = new TableColumn<InventoryData, String>("Name");
		nameCol.setMinWidth(180);
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, String>("name"));
		table_inventory.getColumns().add(nameCol);

		// UNIT VOLUME
		TableColumn<InventoryData, Double> unitCol = new TableColumn<InventoryData, Double>("Unit Volume\n     (cl)");
		unitCol.setMinWidth(100);
		unitCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("unitVolume"));
		table_inventory.getColumns().add(unitCol);


		// WEIGHT -> Full bottle , Empty Bottle
		TableColumn weightCol = new TableColumn("Weight (gr)");
		TableColumn<InventoryData, Double> fullBottleCol = new TableColumn<InventoryData, Double>("Full bottle");
		fullBottleCol.setMinWidth(100);
		fullBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("fullBottle"));
		TableColumn<InventoryData, Double> emptyBottleCol = new TableColumn<InventoryData, Double>("Empty bottle");
		emptyBottleCol.setMinWidth(100);
		emptyBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("emptyBottle"));
		weightCol.getColumns().addAll(fullBottleCol, emptyBottleCol);
		table_inventory.getColumns().add(weightCol);

		// COST -> Per bottle , Per cl
		TableColumn costCol = new TableColumn("Cost");
		TableColumn<InventoryData, Double> costBottleCol = new TableColumn<InventoryData, Double>("Per " + container.get());
		costBottleCol.setMinWidth(80);
		costBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costContainer"));
		TableColumn<InventoryData, Double> costClCol = new TableColumn<InventoryData, Double>("Per " + unit.get());
		costClCol.setMinWidth(80);
		costClCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costUnit"));
		costCol.getColumns().addAll(costBottleCol,costClCol);

		// RETAIL PRICE -> Per cl, Per bottle
		TableColumn retailCol = new TableColumn("Retail");

		TableColumn<InventoryData, Double> retailClCol = new TableColumn<InventoryData, Double>("Per " + unit.get());
		retailClCol.setMinWidth(80);
		retailClCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceUnit"));
		TableColumn<InventoryData, Double> retailBottleCol = new TableColumn<InventoryData, Double>("Per " + container.get());
		retailBottleCol.setMinWidth(80);
		retailBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceContainer"));
		retailCol.getColumns().addAll(retailClCol,retailBottleCol);

		table_inventory.getColumns().addAll(costCol,retailCol);

		for(Object column : table_inventory.getColumns().toArray()) 
		{
			TableColumn col = (TableColumn) column;
			col.setResizable(false);
			if (col.getColumns().size() > 0) {
				for(Object subColumn : col.getColumns()) 
				{ 
					TableColumn subCol = (TableColumn) subColumn;
					subCol.setResizable(false);
				}
			}
		}

	}

	// Update table data 
	private void updateData(boolean firstLoad) 
	{

		new Thread(new Runnable()
		{
			@Override
			public void run() {
				if(firstLoad) {

					data = FXCollections.observableArrayList(getData(DEFAULT_SELECTION));
				}
				else {
					String category = (String)cbox_category.getValue().toString().toLowerCase();
					setUnits(category);
					data = FXCollections.observableArrayList(getData(category));

				}


				table_inventory.setItems(data);
			}
		}).start();

	}
	
	private void setUnits(String category) {
		if(category.toLowerCase().equals("draft beer")) {
			container.set("keg");
			unit.set("pint");
		}
	}
	// Fetch data from the DB depending on the category
	
	private ArrayList<InventoryData> getData(String category) 
	{
		ArrayList<InventoryData> invData = new ArrayList<InventoryData>();
		ArrayList<Product> products = new ArrayList<Product>();
		try{
			if(category.toLowerCase().equals("spirits")) 
			{
				for(String type : Product.getAlcoholicTypes()) 
				{
					products.addAll(productCtr.getAllOf(type));
				}
			}
			else {
				products = productCtr.getAllOf(category);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		if(products != null) {
			for(Product product : products) {
				InventoryData newData = new InventoryData(product);
				invData.add(newData);
			}
		}

		return invData;

	}


	private ArrayList<String> getTypes() {
		try {
			return productCtr.getTypes();
		}
		catch(Exception ex) {

			ex.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void initComboBox() {
		cbox_category.setButtonCell(new ListCell(){

			@Override
			protected void updateItem(Object item, boolean empty) {
				super.updateItem(item, empty); 
				if(empty || item==null){
					// styled like -fx-prompt-text-fill:
					setStyle("-fx-text-fill: #fff; "
							+ "-fx-font-family: Verdana; -fx-font-size: 14px;"
							+ "-fx-effect: dropshadow( one-pass-box , rgba(100, 100, 100, 0.9) , 0, 0.0 , 0 , 1);"
							+ "-fx-font-weight: 600;");
				} else {
					setStyle("-fx-text-fill: #fff");
					setText(item.toString());
				}
			}
		});

		cbox_category.setOnAction((e) -> {
			updateData(false);
		});

		updateCategories();
	}

	private void updateCategories() 
	{
		new Thread(new Runnable()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				categories = FXCollections.observableArrayList();
				String spirits = "spirits";

				ArrayList<String> allTypes = new ArrayList<String>();
				allTypes.add(spirits);
				allTypes.addAll(getTypes());

				for(String item : allTypes) 
				{
					String capitalizedItem = item.substring(0, 1).toUpperCase() + item.substring(1);
					categories.add(capitalizedItem);
				}
				cbox_category.setItems(categories);
			}
		}).start();
	}



}
















