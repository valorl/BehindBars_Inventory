package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ControlLayer.ProductCtr;
import ModelLayer.Product;
import UILayer.Converters.DoubleStringConverter;
import UILayer.TableData.InventoryData;

public class InventoryController implements Initializable, ChangeablePane{

	//private static final String DEFAULT_SELECTION = "spirits";

	private PaneChanger changer;
	private ProductCtr productCtr;


	private StringProperty containerProperty = new SimpleStringProperty("");
	private SimpleStringProperty unitProperty = new SimpleStringProperty("Per cl");
	private BooleanProperty weightVisible = new SimpleBooleanProperty(true);

	//private String container = "";
	//private String unit = "";

	@FXML
	private AnchorPane mainHbox;

	@FXML
	private Button btn_save, btn_new, btn_delete, btn_search, btn_deliveries;

	@FXML
	private TextField txt_search;

	@FXML
	private Label lbl_output;

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
		clearOutput();
		initMenuItem();
		initButtons();
		initComboBox();
		initSearch();

		createTable();
		updateData();

		mainHbox.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.F && e.isControlDown()) { 
				txt_search.requestFocus();
			}

			//			if(e.getCode() == KeyCode.S) {
			//				int index = cbox_category.getItems().indexOf(cbox_category.getValue());
			//				index++;
			//				index = index % (cbox_category.getItems().size()-1);
			//				cbox_category.setValue(cbox_category.getItems().get(index));
			//				cbox_category.fireEvent(new ActionEvent());
			//			}

			//			if(e.getCode() == KeyCode.W) {
			//				int index = cbox_category.getItems().indexOf(cbox_category.getValue());
			//				index--;
			//				index = index % (cbox_category.getItems().size()-1);
			//				cbox_category.setValue(cbox_category.getItems().get(index));
			//				cbox_category.fireEvent(new ActionEvent());
			//				
			//			}
		});
	}

	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}

	// Initialize Table 
	private void createTable()
	{
		table_inventory.setPlaceholder(new Label("No products found."));
		table_inventory.setMaxWidth(802);
		table_inventory.setPrefWidth(802);
		table_inventory.setEditable(true);

		// NAME
		TableColumn<InventoryData, String> nameCol = new TableColumn<InventoryData, String>("Name");
		nameCol.setSortType(TableColumn.SortType.ASCENDING);
		nameCol.setMinWidth(180);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, String>("name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());    // Making the cell editable with a TextField

		table_inventory.getColumns().add(nameCol);
		table_inventory.getSortOrder().add(nameCol);
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
		TableColumn<InventoryData, Double> fullBottleCol = new TableColumn<InventoryData, Double>("Full");
		fullBottleCol.setMinWidth(100);
		fullBottleCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // Making the cell editable with a TextField
		fullBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("fullWeight"));
		fullBottleCol.visibleProperty().bind(weightVisible);
		TableColumn<InventoryData, Double> emptyBottleCol = new TableColumn<InventoryData, Double>("Empty");
		emptyBottleCol.setMinWidth(100);
		emptyBottleCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // Making the cell editable with a TextField
		emptyBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("emptyWeight"));
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


	private void initMenuItem() {

		//		 showEditItem.setOnAction((e) -> {
		//			 showEdit();
		//		 });
	}



	// Update table data 
	private void updateData() 
	{

		//		if(cbox_category.getValue() != null) {
		//			String category = (String)cbox_category.getValue().toString().toLowerCase();
		//
		//			data = FXCollections.observableArrayList(getData(category));
		//
		//			table_inventory.setItems(data);
		//
		//			updateColumns((String)cbox_category.getValue().toString().toLowerCase());
		//		}

		data = FXCollections.observableArrayList(getData(null));
		filterData();
	}

	private void updateColumns(String category) {

		containerProperty.set("Per " + TypeManager.getUnit(category));

		if(TypeManager.isMeasurableType(category) || category.equals("spirits")) {

			weightVisible.set(true);
		}
		else {
			weightVisible.set(false);
		}
	}

	// Fetch data from the DB depending on the category
	private ArrayList<InventoryData> getData(String category) 
	{
		//		ArrayList<InventoryData> invData = new ArrayList<InventoryData>();
		//		ArrayList<Product> products = new ArrayList<Product>();
		//		try{
		//			if(category.toLowerCase().equals("spirits")) 
		//			{
		//				for(String type : TypeManager.getSpiritTypes()) 
		//				{
		//					products.addAll(productCtr.getAllOf(type));
		//				}
		//			}
		//			else {
		//				products = productCtr.getAllOf(category);
		//			}
		//		}
		//		catch(Exception ex)
		//		{
		//			ex.printStackTrace();
		//		}
		//
		//		if(products != null) {
		//			for(Product product : products) {
		//				InventoryData newData = new InventoryData(product);
		//				invData.add(newData);
		//			}
		//		}
		//
		//		return invData;

		ArrayList<InventoryData> invData = new ArrayList<InventoryData>();
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			products = productCtr.getAllProducts();
		}
		catch(Exception ex) {
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

			filterData();

			updateColumns(cbox_category.getValue().toString().toLowerCase());
			//updateData();
		});

		updateCategories();

	}

	private void filterData() {
		ObservableList<InventoryData> newItems = FXCollections.observableArrayList();

		if(data != null && data.size() > 0) {
			for(InventoryData dataItem : data) {
				if(cbox_category.getValue().toString().toLowerCase().equals("spirits")) {
					if(TypeManager.getSpiritTypes().contains(dataItem.getProduct().getType().toLowerCase())) {
						newItems.add(dataItem);
					}
				}
				else if(dataItem.getProduct().getType().equals(cbox_category.getValue().toString().toLowerCase())) {
					newItems.add(dataItem);
				}
			}

			table_inventory.setItems(newItems);
			table_inventory.sort();
		}
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

		updateColumns(cbox_category.getValue().toString().toLowerCase());


	}

	private void initButtons() {
		btn_new.setOnAction((e) -> {
			newProduct();
		});

		btn_save.setOnAction((e) -> {
			commitChanges();
		});

		btn_delete.setOnAction((e) -> {
			deleteProduct();
		});
		
		btn_deliveries.setOnAction((e) -> {
			delivery();
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

		updateData();



	}

	private void discardChanges() {
		updateData();
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
		updateData();
	}

	public ObservableList<String> getCategories() {
		return categories;
	}

	private void deleteProduct() {

		InventoryData dataObj = table_inventory.getSelectionModel().getSelectedItem();

		if(dataObj != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Product deletion");
			alert.setHeaderText("You are about to remove a product from the database.");
			alert.setContentText("Are you sure?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				Product prodObj = dataObj.getProduct();
				try {
					productCtr.deleteProduct(prodObj);
					output("Product deleted successfully.",false);
				}
				catch(Exception ex) {
					ex.printStackTrace();
					output("An error occured while trying to delete a product.", true);
				}
				//updateData();
				table_inventory.getItems().remove(dataObj);
			} 


		}
		else {
			output("Error: No product selected.", true);
		}

	}

	private void delivery() {
		if(table_inventory.getSelectionModel().getSelectedItem() != null) {
			
			Product theProduct = table_inventory.getSelectionModel().getSelectedItem().getProduct();
			
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Register delivery");
			dialog.setHeaderText("Delivery for " + theProduct.getName() + ":");
			dialog.setContentText("Please enter number of bottles:");
			
			Optional<String> result = dialog.showAndWait();
			
			if(result.isPresent()) {
				int purchased = 0;
				try {
					purchased = Integer.parseInt(result.get());
					theProduct.setPurchased(purchased);
					ProductCtr prodCtr = new ProductCtr();
					
					prodCtr.updateProduct(theProduct);
					output("Delivery registered successfully.", false);
				}
				catch(NumberFormatException ex) {
					ex.printStackTrace();
					output("Error: Incorrect delivery input", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			result.ifPresent(noOfBottles -> {
//				int purchased = 0;
//				try {
//					purchased = Integer.parseInt(noOfBottles);
//					theProduct.setPurchased(purchased);
//					output("Delivery registered successfully.", false);
//				}
//				catch(NumberFormatException ex) {
//					ex.printStackTrace();
//					output("Error: Incorrect delivery input", true);
//				}
//			});
		}
		else {
			output("No product selected.", true);
		}
	}
	
	private void initSearch() {

		txt_search.setPromptText("Search...");

		//		txt_search.textProperty().addListener(new ChangeListener<String>() {
		//			@Override
		//			public void changed(ObservableValue<? extends String> observable,
		//					String oldValue, String newValue) {
		//
		//				searchResults();
		//			}
		//		});

		txt_search.textProperty().addListener(new InvalidationListener() {


			@Override

			public void invalidated(Observable o) {

				if(txt_search.textProperty().get().isEmpty()) {

					table_inventory.setItems(data);
					filterData();

					return;

				}

				ObservableList<InventoryData> newItems = FXCollections.observableArrayList();

				if(data != null && data.size() > 0) {
					for(InventoryData dataItem : data) {
						if(dataItem.getProduct().getName().toLowerCase().contains(txt_search.getText().toLowerCase())) {
							newItems.add(dataItem);
						}
					}

					table_inventory.setItems(newItems);
				}
			}


		});

	}

	private void searchResults() {

		ObservableList<InventoryData> oldData = FXCollections.observableArrayList(data);

		if(txt_search.getText().length() > 0 ) {
			data.clear();

			for(InventoryData dataItem : oldData) {
				if(dataItem.getProduct().getName().toLowerCase().contains(txt_search.getText().toLowerCase())) {
					data.add(dataItem);
				}
			}

		}
		else {
			updateData();
		}
	}

	private void output(String message, boolean isError) {
		if(isError) {
			lbl_output.setStyle("-fx-text-fill: #ff5722");
		}
		else {
			lbl_output.setStyle("-fx-text-fill: #5af158");
		}
		lbl_output.setText(message);
	}

	private void clearOutput() {
		lbl_output.setText("");
	}


}

















