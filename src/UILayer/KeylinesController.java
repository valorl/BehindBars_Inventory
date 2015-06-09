package UILayer;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import ControlLayer.InventoryCtr;
import ControlLayer.ProductCtr;
import ControlLayer.WeekCtr;
import ModelLayer.ItemResult;
import ModelLayer.Week;
import ModelLayer.WeekResult;
import UILayer.TableData.KeylinesData;

public class KeylinesController implements Initializable, ChangeablePane{

	private static final double COLUMN_CONST = 0.0656;
	private PaneChanger changer;
	private ProductCtr productCtr;
	private WeekCtr weekCtr;
	private InventoryCtr invCtr;

	private StringProperty containerProperty = new SimpleStringProperty("");
	private SimpleStringProperty unitProperty = new SimpleStringProperty("Per cl");
	private BooleanProperty weightVisible = new SimpleBooleanProperty(true);

	private StringProperty week1name = new SimpleStringProperty();
	private StringProperty week2name = new SimpleStringProperty();
	private StringProperty week3name = new SimpleStringProperty();
	private StringProperty week4name = new SimpleStringProperty();
	private StringProperty week5name = new SimpleStringProperty();

	@FXML
	private VBox mainVbox;

	@FXML
	private TextField txt_search;

	@FXML
	private Button btn_save;

	@FXML
	private Button btn_show;

	@FXML
	private Label lbl_output;

	@FXML
	private ComboBox cbox_category = new ComboBox();
	@FXML 
	private ComboBox cbox_month = new ComboBox();
	@FXML 
	private ComboBox cbox_year = new ComboBox();

	@FXML
	private TableView<KeylinesData> table_keylines = new TableView<KeylinesData>();

	private ObservableList<KeylinesData> data;

	private ObservableList<String> categories, years, months;

	public KeylinesController() 
	{
		productCtr = new ProductCtr();
		weekCtr = new WeekCtr();
		invCtr = new InventoryCtr();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {



		mainVbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());
		mainVbox.getStyleClass().add("keylines");
		//clearOutput();
		initButtons();
		initComboBox();
		initSearch();
		addMonthYear();

		createTable();
		//updateData();

		mainVbox.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.F && e.isControlDown()) { 
				txt_search.requestFocus();
			}
		});
	}

	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}

	// Initialize Table 
	private void createTable()
	{

		table_keylines.setPlaceholder(new Label("Please pick a month and a year you would like to see key lines for."));

		// NAME
		TableColumn<KeylinesData, String> nameCol = new TableColumn<KeylinesData, String>("Name");
		nameCol.setSortType(TableColumn.SortType.ASCENDING);
		nameCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(0.125));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, String>("name"));
		table_keylines.getColumns().add(nameCol);
		table_keylines.getSortOrder().add(nameCol);

		// WEEK 1
		TableColumn week1Col = new TableColumn("Week 1");

		TableColumn<KeylinesData, Double> week1SalesCol = new TableColumn<KeylinesData, Double>("Sales");
		week1SalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week1SalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week1Sales"));
		TableColumn<KeylinesData, Double> week1DifCol = new TableColumn<KeylinesData, Double>("Difference");
		week1DifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week1DifCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week1Dif"));
		week1Col.getColumns().addAll(week1SalesCol, week1DifCol);
		table_keylines.getColumns().add(week1Col);

		//WEEK 2
		TableColumn week2Col = new TableColumn("Week 2");
		TableColumn<KeylinesData, Double> week2SalesCol = new TableColumn<KeylinesData, Double>("Sales");
		week2SalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week2SalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week2Sales"));
		TableColumn<KeylinesData, Double> week2DifCol = new TableColumn<KeylinesData, Double>("Difference");
		week2DifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week2DifCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week2Dif"));
		week2Col.getColumns().addAll(week2SalesCol, week2DifCol);
		table_keylines.getColumns().add(week2Col);

		//WEEK 3
		TableColumn week3Col = new TableColumn("Week 3");
		TableColumn<KeylinesData, Double> week3SalesCol = new TableColumn<KeylinesData, Double>("Sales");
		week3SalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week3SalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week3Sales"));
		TableColumn<KeylinesData, Double> week3DifCol = new TableColumn<KeylinesData, Double>("Difference");
		week3DifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week3DifCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week3Dif"));
		week3Col.getColumns().addAll(week3SalesCol, week3DifCol);
		table_keylines.getColumns().add(week3Col);

		//WEEK 4
		TableColumn week4Col = new TableColumn("Week 4");
		TableColumn<KeylinesData, Double> week4SalesCol = new TableColumn<KeylinesData, Double>("Sales");
		week4SalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week4SalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week4Sales"));
		TableColumn<KeylinesData, Double> week4DifCol = new TableColumn<KeylinesData, Double>("Difference");
		week4DifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week4DifCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week4Dif"));
		week4Col.getColumns().addAll(week4SalesCol, week4DifCol);
		table_keylines.getColumns().add(week4Col);

		//WEEK 5
		TableColumn week5Col = new TableColumn("Week 5");
		TableColumn<KeylinesData, Double> week5SalesCol = new TableColumn<KeylinesData, Double>("Sales");
		week5SalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week5SalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week5Sales"));
		TableColumn<KeylinesData, Double> week5DifCol = new TableColumn<KeylinesData, Double>("Difference");
		week5DifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		week5DifCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week5Dif"));
		week5Col.getColumns().addAll(week5SalesCol, week5DifCol);
		table_keylines.getColumns().add(week5Col);

		// TOTAL
		TableColumn totalCol = new TableColumn("Total");
		TableColumn<KeylinesData, Double> totalSalesCol = new TableColumn<KeylinesData, Double>("Sales");
		totalSalesCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		totalSalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("totalSales"));
		TableColumn<KeylinesData, Double> totalDifCol = new TableColumn<KeylinesData, Double>("Difference");
		totalDifCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(COLUMN_CONST));
		totalSalesCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("totalDif"));
		totalCol.getColumns().addAll(totalSalesCol, totalDifCol);
		table_keylines.getColumns().add(totalCol);

		// RETAIL PRICE
		TableColumn<KeylinesData, Double> revenueCol = new TableColumn<KeylinesData, Double>("Revenue");
		revenueCol.prefWidthProperty().bind(table_keylines.widthProperty().multiply(0.085));
		revenueCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("revenue"));
		table_keylines.getColumns().add(revenueCol);

		// Make the columns non-resizable
		for(Object column : table_keylines.getColumns().toArray()) 
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

	private void updateData()
	{
		data = FXCollections.observableArrayList(getData());
		//filterData();
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

	// Fetch data from the DB
	private ArrayList<KeylinesData> getData() 
	{

		ArrayList<KeylinesData> keyData = new ArrayList<KeylinesData>();
		//if(cbox_month.getValue() != null && cbox_year.getValue() != null) {

		//ArrayList<Week> weekList = new ArrayList<Week>();
		ArrayList<WeekResult> kls = null;
		try {
			//weekList = getWeekList();
			kls = invCtr.getKeyLines(getSelectedMonth(), Integer.parseInt(cbox_year.getValue().toString()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(kls != null && kls.size() > 0) {
			int noOfResults = kls.get(0).getResults().size();



			for(int i = 0; i < noOfResults; i++) {
				ItemResult[] irs = new ItemResult[5];

				int resultCounter = 0;
				for(WeekResult wr : kls) {
					irs[resultCounter] = wr.getResults().get(0);
				}

				KeylinesData newItem = new KeylinesData(irs);
				keyData.add(newItem);
			}


			//	}
		}		
		if(keyData.size() == 0) { 
			keyData = null;
		}		
		return keyData;


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

		cbox_month.setButtonCell(new ListCell(){

			@Override
			protected void updateItem(Object item, boolean empty) {
				super.updateItem(item, empty); 
				if(empty || item==null){
					// styled like -fx-prompt-text-fill:
					setStyle("-fx-text-fill: #fff; "
							+ "-fx-font-family: Verdana; -fx-font-size: 12px;"
							+ "-fx-effect: dropshadow( one-pass-box , rgba(100, 100, 100, 0.9) , 0, 0.0 , 0 , 1);"
							+ "-fx-font-weight: 600;");
				} else {
					setStyle("-fx-text-fill: #fff");
					setText(item.toString());
				}
			}
		});

		cbox_year.setButtonCell(new ListCell(){

			@Override
			protected void updateItem(Object item, boolean empty) {
				super.updateItem(item, empty); 
				if(empty || item==null){
					// styled like -fx-prompt-text-fill:
					setStyle("-fx-text-fill: #fff; "
							+ "-fx-font-family: Verdana; -fx-font-size: 12px;"
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
		ObservableList<KeylinesData> newItems = FXCollections.observableArrayList();

		if(data != null && data.size() > 0) {
			for(KeylinesData dataItem : data) {
				if(cbox_category.getValue().toString().toLowerCase().equals("spirits")) {
					if(TypeManager.getSpiritTypes().contains(dataItem.getProduct().getType().toLowerCase())) {
						newItems.add(dataItem);
					}
				}
				else if(dataItem.getProduct().getType().equals(cbox_category.getValue().toString().toLowerCase())) {
					newItems.add(dataItem);
				}
			}

			table_keylines.setItems(newItems);
			table_keylines.sort();
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

	private ArrayList<String> getYears() {
		ArrayList<String> stringYears = new ArrayList<String>();
		try {
			ArrayList<Integer> years = weekCtr.getYears();
			for(int year : years) {
				stringYears.add("" + year);
			}
		}
		catch(Exception ex) {

			ex.printStackTrace();
			return null;
		}
		return stringYears; 
	}

	private ArrayList<String> getMonths() {

		ArrayList<String> stringMonths = new ArrayList<String>();

		try {
			ArrayList<Integer> months = weekCtr.getMonths();

			DateFormatSymbols symbols = new DateFormatSymbols();
			String[] monthNames = symbols.getMonths();
			for(int month : months) {
				stringMonths.add(monthNames[month-1]);
			}



		}
		catch(Exception ex) {

			ex.printStackTrace();
			return null;
		}

		return stringMonths;
	}

	public void addMonthYear()
	{
		ArrayList<String> weekYears = new ArrayList<String>();
		ArrayList<String> weekMonths = new ArrayList<String>();
		years = FXCollections.observableArrayList();
		months = FXCollections.observableArrayList();

		// Getting the Years

		try {
			weekYears = getYears();
		}
		catch(Exception ex) {

			ex.printStackTrace();
		}

		if(weekYears != null && weekYears.size() > 0) {
			years.addAll(weekYears);
		}


		//Getting the months

		try {
			weekMonths = getMonths();
		}
		catch(Exception ex) {

			ex.printStackTrace();
		}
		if(weekYears != null && weekMonths.size() > 0) {
			months.addAll(weekMonths);
		}


		cbox_year.setItems(years);
		cbox_year.setPromptText("Year");
		cbox_month.setItems(months);
		cbox_month.setPromptText("Month");

	}

	private int getSelectedMonth() {

		DateFormatSymbols symbols = new DateFormatSymbols();
		String[] monthNames = symbols.getMonths();
		int monthInt = 0;

		if(cbox_month.getValue() != null) {
			for(int i = 0; i < monthNames.length; i++) {
				if(monthNames[i].equalsIgnoreCase(cbox_month.getValue().toString())) {
					monthInt = i + 1;
					break;
				}
			}
		}

		return monthInt;
	}


	private void initButtons() {

		btn_show.setOnAction((e) -> {
			updateData();
		});
	}

	public ObservableList<String> getCategories() {
		return categories;
	}
	private void initSearch() {

		txt_search.setPromptText("Search...");


		txt_search.textProperty().addListener(new InvalidationListener() {


			@Override

			public void invalidated(Observable o) {

				if(txt_search.textProperty().get().isEmpty()) {

					table_keylines.setItems(data);
					filterData();

					return;

				}

				ObservableList<KeylinesData> newItems = FXCollections.observableArrayList();


				for(KeylinesData dataItem : data) {
					if(dataItem.getProduct().getName().toLowerCase().contains(txt_search.getText().toLowerCase())) {
						newItems.add(dataItem);
					}
				}

				table_keylines.setItems(newItems);
			}


		});

	}

	private void searchResults() {

		ObservableList<KeylinesData> oldData = FXCollections.observableArrayList(data);

		if(txt_search.getText().length() > 0 ) {
			data.clear();

			for(KeylinesData dataItem : oldData) {
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

	private ArrayList<Week> getWeekList()
	{
		ArrayList<Week> weekList = new ArrayList<Week>();
		weekList = null;

		try
		{
			weekList = weekCtr.getWeeksMonthYear(Integer.parseInt(cbox_month.getValue().toString()), Integer.parseInt(cbox_year.getValue().toString()));
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}

		return weekList;
	}
}
