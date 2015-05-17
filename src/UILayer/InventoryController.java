package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import ControlLayer.ProductCtr;
import ModelLayer.Alcohol;
import ModelLayer.Product;
import UILayer.TableData.InventoryData;

public class InventoryController implements Initializable, ChangeablePane{

	private static final String DEFAULT_SELECTION = "spirits";

	private PaneChanger changer;
	private ProductCtr productCtr;


	private StringProperty containerProperty = new SimpleStringProperty("");
	private SimpleStringProperty unitProperty = new SimpleStringProperty("");
	private BooleanProperty weightVisible = new SimpleBooleanProperty(true);

	//private String container = "";
	//private String unit = "";

	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private Button btn_new;

	@FXML
	private Button btn_delete;

	@FXML
	private ComboBox cbox_category = new ComboBox();

	@FXML
	private TableView<InventoryData> table_inventory = new TableView<InventoryData>();

	private ObservableList<InventoryData> data;

	private ObservableList<String> categories;

	//private TableColumn<InventoryData, Double> costContainerCol;

	//private TableColumn<InventoryData, Double> costUnitCol;

	//private TableColumn<InventoryData, Double> retailContainerCol;

	//private TableColumn<InventoryData, Double> retailUnitCol;

	public InventoryController() 
	{
		productCtr = new ProductCtr();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainHbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());
		initButtons();
		initComboBox();
		updateData(true);
		createTable((String)cbox_category.getValue().toString().toLowerCase());

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


		table_inventory.setEditable(true);



		// NAME
		TableColumn<InventoryData, String> nameCol = new TableColumn<InventoryData, String>("Name");
		nameCol.setMinWidth(180);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, String>("name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());    // Making the cell editable with a TextField

		table_inventory.getColumns().add(nameCol);

		// UNIT VOLUME
		TableColumn<InventoryData, Double> unitCol = new TableColumn<InventoryData, Double>("Unit Volume\n     (cl)");
		unitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));  // Making the cell editable with a TextField
		unitCol.setMinWidth(100);
		unitCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("unitVolume"));
		table_inventory.getColumns().add(unitCol);


		// WEIGHT -> Full bottle , Empty Bottle
		TableColumn weightCol = new TableColumn("Weight (gr)");
		weightCol.visibleProperty().bind(weightVisible);
		TableColumn<InventoryData, Double> fullBottleCol = new TableColumn<InventoryData, Double>("Full bottle");
		fullBottleCol.setMinWidth(100);
		fullBottleCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // Making the cell editable with a TextField
		fullBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("fullBottle"));
		fullBottleCol.visibleProperty().bind(weightVisible);
		TableColumn<InventoryData, Double> emptyBottleCol = new TableColumn<InventoryData, Double>("Empty bottle");
		emptyBottleCol.setMinWidth(100);
		emptyBottleCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // Making the cell editable with a TextField
		emptyBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("emptyBottle"));
		emptyBottleCol.visibleProperty().bind(weightVisible);
		weightCol.getColumns().addAll(fullBottleCol, emptyBottleCol);
		table_inventory.getColumns().add(weightCol);

		// COST -> Per bottle , Per cl
		TableColumn costCol = new TableColumn("Cost");
		TableColumn costContainerCol = new TableColumn<InventoryData, Double>();
		costContainerCol.setMinWidth(80);
		costContainerCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // Making the cell editable with a TextField
		costContainerCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costContainer"));


		TableColumn costUnitCol = new TableColumn<InventoryData, Double>();
		costUnitCol.setMinWidth(80);
		//costUnitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		costUnitCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costUnit"));
		costCol.getColumns().addAll(costContainerCol,costUnitCol);

		costContainerCol.textProperty().bind(containerProperty); // Bind label to container property
		costUnitCol.textProperty().bind(unitProperty); // Bind label to unit property

		// RETAIL PRICE -> Per cl, Per bottle
		TableColumn retailCol = new TableColumn("Retail");

		TableColumn retailUnitCol = new TableColumn<InventoryData, Double>();
		retailUnitCol.setMinWidth(80);
		retailUnitCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));  // Making the cell editable with a TextField
		retailUnitCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceUnit"));
		TableColumn retailContainerCol = new TableColumn<InventoryData, Double>();
		retailContainerCol.setMinWidth(80);
		//retailContainerCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		retailContainerCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceContainer"));
		retailCol.getColumns().addAll(retailUnitCol,retailContainerCol);

		retailContainerCol.textProperty().bind(containerProperty); // Bind label to container property
		retailUnitCol.textProperty().bind(unitProperty); // Bind label to unit property

		table_inventory.getColumns().addAll(costCol,retailCol);

		
		// Make the columns non-resizable
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
		
		
		// Adjust table width according to columns, when changing categories
		this.weightVisible.addListener((obs, oldVisible, newVisible) -> {
			
			
			if(!newVisible) {
				table_inventory.setMaxWidth(table_inventory.getMaxWidth() - fullBottleCol.getMinWidth() - emptyBottleCol.getMinWidth());
				table_inventory.setPrefWidth(table_inventory.getPrefWidth() - fullBottleCol.getMinWidth() - emptyBottleCol.getMinWidth());
			}
			else if(newVisible) {

				table_inventory.setMaxWidth(table_inventory.getMaxWidth() + fullBottleCol.getMinWidth() + emptyBottleCol.getMinWidth());
				table_inventory.setPrefWidth(table_inventory.getPrefWidth() + fullBottleCol.getMinWidth() + emptyBottleCol.getMinWidth());
			}
		});
		
//		table_inventory.minWidthProperty().bind(nameCol.minWidthProperty()
//				.add(unitCol.widthProperty())
//				.add(weightCol.widthProperty())
//				.add(costCol.widthProperty())
//				.add(retailCol.widthProperty())
//				.add(2.0));
		//table_inventory.maxWidthProperty().bind(table_inventory.minWidthProperty());

	}

	// Update table data 
	private void updateData(boolean firstLoad) 
	{

		String category = (String)cbox_category.getValue().toString().toLowerCase();
		data = FXCollections.observableArrayList(getData(category));

		table_inventory.setItems(data);

		updateColumns((String)cbox_category.getValue().toString().toLowerCase());
	}

	private void updateColumns(String category) {
		if(Product.checkTypeForAlcoholic(category) || category.equals("spirits")) {
			containerProperty.set("Per bottle");
			unitProperty.set("Per cl");
			weightVisible.set(true);
		}
		if(category.toLowerCase().equals("draft beer")) {

			containerProperty.set("Per keg");
			unitProperty.set("Per cl");
			weightVisible.set(false);
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
			updateColumns(cbox_category.getValue().toString().toLowerCase());
			updateData(false);
		});

		updateCategories();

	}
	

	public void updateCategories() 
	{

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
		cbox_category.setValue(cbox_category.getItems().get(0));


	}

	private void initButtons() {
		btn_new.setOnAction((e) -> {
			newProduct();
		});

		btn_save.setOnAction((e) -> {
			commitChanges();
		});
	}

	private void commitChanges() 
	{
		ObservableList<InventoryData> data = table_inventory.getItems();

		new Thread(new Runnable()
		{
			@Override
			public void run() {
				for(InventoryData item : data) {
					Product product = item.getProduct();


					try{
						productCtr.updateProduct(product);
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}

				}
			}
		}).start();

		updateData(false);



	}

	private void discardChanges() {
		updateData(false);
	}

	private void newProduct() {
		Stage newProductStage = new Stage();
		newProductStage.initModality(Modality.APPLICATION_MODAL);
		Parent parent = null;
		NewProductController controller;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("newproduct.fxml"));
			parent = loader.load();
			controller = loader.<NewProductController>getController();
			controller.setCaller(this);
			controller.init(newProductStage);


		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		Scene scene = new Scene(parent);
		//scene.getStylesheets().add(getClass().getResource(Main.getMainCss()).toExternalForm());

		newProductStage.setScene(scene);
		newProductStage.show();
	}

	public void addProduct(Product product) {
		try{
			productCtr.createProduct(product);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		cbox_category.setValue(product.getType());
		updateData(false);
	}

	public ObservableList<String> getCategories() {
		return categories;
	}
}
















