package UILayer;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import ControlLayer.ProductCtr;
import ControlLayer.WeekCtr;
import ModelLayer.Measurable;
import ModelLayer.Product;
import ModelLayer.ProductState;
import ModelLayer.QuantLoc;
import ModelLayer.Week;
import UILayer.Converters.DoubleStringConverter;
import UILayer.Converters.IntegerStringConverter;
import UILayer.TableData.StocktakeData;


public class StocktakeController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;
	WeekCtr weekCtr;

	Date date; 

	private static final double DEFAULT_COLUMN_WIDTH = 85;
	private static final double STRETCHED_COLUMN_WIDTH = 120;

	private BooleanProperty weightVisible = new SimpleBooleanProperty(true);
	private DoubleProperty columnWidth = new SimpleDoubleProperty(DEFAULT_COLUMN_WIDTH);

	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private Button btn_clear;

	@FXML
	private Button btn_assistant;

	@FXML
	private ComboBox cbox_category = new ComboBox();

	@FXML
	private TableView<StocktakeData> table_stocktake = new TableView<StocktakeData>();

	@FXML
	private TextField txt_search;

	@FXML
	private Label lbl_output, lbl_week, lbl_date;

	private ObservableList<StocktakeData> data;

	private ObservableList<String> categories;


	public StocktakeController() 
	{
		productCtr = new ProductCtr();
		weekCtr = new WeekCtr();

		date = new Date();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainHbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());
		clearOutput();
		initButtons();
		initDates();
		initSearch();
		initComboBox();
		initSearch();
		createTable();
		updateData();


		mainHbox.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.F && e.isControlDown()) { 
				txt_search.requestFocus();
			}
		});
	}

	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}


	private void createTable() 
	{
		table_stocktake.setMaxWidth(DEFAULT_COLUMN_WIDTH*10+2);
		table_stocktake.setPrefWidth(DEFAULT_COLUMN_WIDTH*10+2);
		table_stocktake.setEditable(true);



		// NAME
		TableColumn<StocktakeData, String> nameCol = new TableColumn<StocktakeData, String>("Name");
		nameCol.prefWidthProperty().bind(columnWidth.multiply(2));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, String>("name"));
		table_stocktake.getColumns().add(nameCol);

		// STORAGE BOTTLES
		TableColumn<StocktakeData, Integer> storageCol = new TableColumn<StocktakeData, Integer>("Storage");
		storageCol.prefWidthProperty().bind(columnWidth);
		storageCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		storageCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Integer>("storage"));
		table_stocktake.getColumns().add(storageCol);



		// FULL BOTTLES
		TableColumn fullCol = new TableColumn("Full (pcs)");
		fullCol.prefWidthProperty().bind(columnWidth.multiply(3));
		// BAR 1 BOTTLES
		TableColumn<StocktakeData, Integer> bar1Col = new TableColumn<StocktakeData, Integer>("Bar 1");
		bar1Col.prefWidthProperty().bind(columnWidth);
		bar1Col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		bar1Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Integer>("bar1"));
		fullCol.getColumns().add(bar1Col);
		// BAR 2 BOTTLES
		TableColumn<StocktakeData, Integer> bar2Col = new TableColumn<StocktakeData, Integer>("Bar 2");
		bar2Col.prefWidthProperty().bind(columnWidth);
		bar2Col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		bar2Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Integer>("bar2"));
		fullCol.getColumns().add(bar2Col);
		// BAR 3 BOTTLES
		TableColumn<StocktakeData, Integer> bar3Col = new TableColumn<StocktakeData, Integer>("Bar 3");
		bar3Col.prefWidthProperty().bind(columnWidth);
		bar3Col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		bar3Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Integer>("bar3"));
		fullCol.getColumns().add(bar3Col);
		table_stocktake.getColumns().add(fullCol);


		// OPENED BOTTLES (GR)
		TableColumn openedCol = new TableColumn("Opened (gr)");
		openedCol.prefWidthProperty().bind(columnWidth.multiply(3));
		openedCol.visibleProperty().bind(weightVisible);
		// BAR 1 OPEN
		TableColumn<StocktakeData, Double> bar1openCol = new TableColumn<StocktakeData, Double>("Bar 1");
		bar1openCol.prefWidthProperty().bind(columnWidth);
		bar1openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar1openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar1open"));
		openedCol.getColumns().add(bar1openCol);

		// BAR 2 OPEN
		TableColumn<StocktakeData, Double> bar2openCol = new TableColumn<StocktakeData, Double>("Bar 2");
		bar2openCol.prefWidthProperty().bind(columnWidth);
		bar2openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar2openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar2open"));
		openedCol.getColumns().add(bar2openCol);

		// BAR 3 OPEN
		TableColumn<StocktakeData, Double> bar3openCol = new TableColumn<StocktakeData, Double>("Bar 3");
		bar3openCol.prefWidthProperty().bind(columnWidth);
		bar3openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar3openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar3open"));
		openedCol.getColumns().add(bar3openCol);
		table_stocktake.getColumns().add(openedCol);


		// SALES
		TableColumn<StocktakeData, Double> salesCol = new TableColumn<StocktakeData, Double>("Sales");
		salesCol.prefWidthProperty().bind(columnWidth);
		salesCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		salesCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("sales"));
		table_stocktake.getColumns().add(salesCol);


		//table_stocktake.getColumns().addAll(nameCol, storageCol, bar1Col, bar1openCol, bar2Col, bar2openCol, bar3Col, bar3openCol, salesCol);

		for(Object column : table_stocktake.getColumns().toArray()) 
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

				table_stocktake.setMaxWidth(STRETCHED_COLUMN_WIDTH*7+2);
				table_stocktake.setPrefWidth(STRETCHED_COLUMN_WIDTH*7+2);
			}
			else if(newVisible) {
				table_stocktake.setMaxWidth(DEFAULT_COLUMN_WIDTH*10+2);
				table_stocktake.setPrefWidth(DEFAULT_COLUMN_WIDTH*10+2);
			}
		});


	}

	private void initButtons() {
		btn_save.setOnAction((e) -> {
			saveData();
		});
		btn_clear.setOnAction((e) -> {
			clearTable();
		});

	}

	private void initDates() {
		SimpleDateFormat mainDF = new SimpleDateFormat("d. MMMM yyyy");
		SimpleDateFormat weekNr = new SimpleDateFormat("w");

		lbl_date.setText(mainDF.format(date));
		lbl_week.setText("Week " + weekNr.format(date));
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

	private ArrayList<String> getTypes() {
		try {
			return productCtr.getTypes();
		}
		catch(Exception ex) {

			ex.printStackTrace();
			return null;
		}
	}


	private void updateData() 
	{

		data = FXCollections.observableArrayList(getData(null));
		filterData();

	}

	private void filterData() {
		ObservableList<StocktakeData> newItems = FXCollections.observableArrayList();

		if(data != null && data.size() > 0) {
			for(StocktakeData dataItem : data) {
				if(cbox_category.getValue().toString().toLowerCase().equals("spirits")) {
					if(TypeManager.getSpiritTypes().contains(dataItem.getProduct().getType().toLowerCase())) {
						newItems.add(dataItem);
					}
				}
				else if(dataItem.getProduct().getType().equals(cbox_category.getValue().toString().toLowerCase())) {
					newItems.add(dataItem);
				}
			}

			table_stocktake.setItems(newItems);
			table_stocktake.sort();
		}
	}

	private void updateColumns(String category) {

		//containerProperty.set("Per " + TypeManager.getUnit(category));

		if(TypeManager.isMeasurableType(category) || category.equals("spirits")) {

			weightVisible.set(true);
			this.columnWidth.set(DEFAULT_COLUMN_WIDTH);

		}
		else {
			weightVisible.set(false);
			this.columnWidth.set(STRETCHED_COLUMN_WIDTH);

		}
	}

	private ArrayList<StocktakeData> getData(String category) 
	{
		ArrayList<StocktakeData> invData = new ArrayList<StocktakeData>();
		ArrayList<Product> products = new ArrayList<Product>();

		try {
			products = productCtr.getAllProducts();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		if(products != null) {
			for(Product product : products) {
				StocktakeData newData = new StocktakeData(product);
				invData.add(newData);
			}
		}

		return invData;

	}

	private void saveData()
	{
		boolean success = false;
		//Week week = weekCtr.createWeek(date);
		Date nextDate = null;
				
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			nextDate = df.parse("02.06.2015");
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		Week week = weekCtr.createWeek(nextDate);


		//ArrayList<QuantLoc> quantLocs = new ArrayList<QuantLoc>();
		//DBProductState dbState = new DBProductState();

		ObservableList<StocktakeData> stData = data;

		for(StocktakeData item : stData) {
			ProductState productState = new ProductState();

			Product product = item.getProduct();

			QuantLoc storage = new QuantLoc();
			QuantLoc bar1 = new QuantLoc();
			QuantLoc bar2 = new QuantLoc();
			QuantLoc bar3 = new QuantLoc();		

			//productState.setQuantLocs(quantLocs);

			productState.setProduct(product);
			storage.setLocation("storage");
			storage.setQuantity(item.getStorage() * product.getUnitVolume());
			productState.addQuantLoc(storage);


			bar1.setLocation("bar1");
			bar1.setQuantity((item.getBar1() * product.getUnitVolume()));

			bar2.setLocation("bar2");
			bar2.setQuantity((item.getBar2() * product.getUnitVolume()));

			bar3.setLocation("bar3");
			bar3.setQuantity((item.getBar3() * product.getUnitVolume()));

			if(product instanceof Measurable) {
				Measurable mes = (Measurable) product;
				double density;
				try {
					density = mes.calculateDensity();
				}
				catch(Exception ex) {
					density = 0;
					success = false;
					ex.printStackTrace();

				}

				if(density != 0) {
					bar1.setQuantity(bar1.getQuantity() + (item.getBar1open()/density)/10);
					bar2.setQuantity(bar2.getQuantity() + (item.getBar2open()/density)/10);
					bar3.setQuantity(bar3.getQuantity() + (item.getBar3open()/density)/10);
				}
				else {
					bar1.setQuantity(0);
					bar2.setQuantity(0);
					bar3.setQuantity(0);
				}

			}

			productState.addQuantLoc(bar1);
			productState.addQuantLoc(bar2);
			productState.addQuantLoc(bar3);

			productState.setSold(item.getSales());

			week.addState(productState);

			//dbState.insertProductState(productState, week.getId());


		}

		try {
			//Week existingWeek =  weekCtr.findWeek(date);
			Week existingWeek = weekCtr.findWeek(nextDate);
			if(existingWeek == null) {
				weekCtr.insertWeek(week);
			}
			else {
				weekCtr.updateWeek(week);
			}
			//ArrayList<Week> weekList = new ArrayList<Week>();
			//weekList.add(week); 
			success = true;

		}
		catch(Exception ex) {
			ex.printStackTrace();
			success = false;

		}

		if(success) {
			output("Stocktake for " + lbl_week.getText().toLowerCase() + " saved successfully.", false);
		}
		else {
			output("An error occured while saving stocktake for " + lbl_week.getText().toLowerCase(), true);
		}

	}



	private void initSearch() {

		txt_search.setPromptText("Search...");

		txt_search.textProperty().addListener(new InvalidationListener() {


			@Override

			public void invalidated(Observable o) {

				if(txt_search.textProperty().get().isEmpty()) {

					table_stocktake.setItems(data);

					return;

				}

				ObservableList<StocktakeData> newItems = FXCollections.observableArrayList();

				if(data != null && data.size() > 0) {
					for(StocktakeData dataItem : data) {
						if(dataItem.getProduct().getName().toLowerCase().contains(txt_search.getText().toLowerCase())) {
							newItems.add(dataItem);
						}
					}

					table_stocktake.setItems(newItems);
				}
			}


		});
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

	public void clearTable()
	{	
		table_stocktake.getItems().clear();
		filterData();

	}

}
