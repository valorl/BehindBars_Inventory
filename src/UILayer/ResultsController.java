package UILayer;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import ControlLayer.InventoryCtr;
import ControlLayer.ProductCtr;
import ControlLayer.SettingsCtr;
import ModelLayer.ItemResult;
import ModelLayer.WeekResult;
import UILayer.TableData.ResultsData;

public class ResultsController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;
	InventoryCtr inventoryCtr;
	
	private String currency;

	private StringProperty week1name = new SimpleStringProperty();
	private StringProperty week2name = new SimpleStringProperty();

	@FXML
	private VBox mainVbox;

	@FXML
	private Button btn_show;

	@FXML
	private ComboBox<String> cbox_category = new ComboBox<String>();

	private ToggleGroup group;

	@FXML
	private RadioButton radio_week, radio_month;

	@FXML
	private Label lbl_output;

	@FXML
	private TextField txt_search;

	@FXML
	private DatePicker datepicker;

	@FXML
	private TableView<ResultsData> table_results = new TableView<ResultsData>();

	private ObservableList<ResultsData> data;
	private ObservableList<String> categories;

	public ResultsController() 
	{
		SettingsCtr setCtr = new SettingsCtr();
		try {
			currency = setCtr.findSetting("CURR", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(currency == null) {
			currency = "";
		}
		
		productCtr = new ProductCtr();
		inventoryCtr = new InventoryCtr();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainVbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());
		mainVbox.getStyleClass().add("results");

		group = new ToggleGroup();
		group.getToggles().addAll(radio_week, radio_month);

		radio_week.setSelected(true);
		radio_month.setDisable(true);  // Coming soon...
		//radio_week.setVisible(false);
		//radio_month.setVisible(false);

		clearOutput();
		initButtons();
		initComboBox();
		initSearch();		
		createTable();

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

	private void initButtons() {
		btn_show.setOnAction((e) -> {
			updateData();
		});
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

	private void updateColumns(String category) {

		//
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

	private void filterData() {
		ObservableList<ResultsData> newItems = FXCollections.observableArrayList();

		if(data != null && data.size() > 0) {
			for(ResultsData dataItem : data) {
				if(cbox_category.getValue().toString().toLowerCase().equals("spirits")) {
					if(TypeManager.getSpiritTypes().contains(dataItem.getProduct().getType().toLowerCase())) {
						newItems.add(dataItem);
					}
				}
				else if(dataItem.getProduct().getType().equals(cbox_category.getValue().toString().toLowerCase())) {
					newItems.add(dataItem);
				}
			}

			table_results.setItems(newItems);
			table_results.sort();
		}
	}

	private void createTable() 
	{
		//table_results.setMaxWidth(1142);
		//table_results.setPrefWidth(1142);
		//table_results.setEditable(true);

		table_results.setPlaceholder(new Label("Please pick a date within a week you would like to see results for."));


		// NAME
		TableColumn<ResultsData, String> nameCol = new TableColumn<ResultsData, String>("Name");
		nameCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.125));
		nameCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, String>("name"));

		// WEEK 1 // 
		TableColumn week1col = new TableColumn();	
		week1col.textProperty().bind(week1name);
		// Bars
		TableColumn<ResultsData, Double> storageCol = new TableColumn<ResultsData, Double>("Storage");
		storageCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		storageCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("storage"));
		
		TableColumn<ResultsData, Double> bar1Col = new TableColumn<ResultsData, Double>("Bar 1");
		bar1Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar1Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar1"));

		TableColumn<ResultsData, Double> bar2Col = new TableColumn<ResultsData, Double>("Bar 2");
		bar2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar2"));

		TableColumn<ResultsData, Double> bar3Col = new TableColumn<ResultsData, Double>("Bar 3");
		bar3Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar3Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar3"));
		// Total
		TableColumn<ResultsData, Double> totalCol = new TableColumn<ResultsData, Double>("Total");
		totalCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		totalCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("total"));
		week1col.getColumns().addAll(storageCol, bar1Col, bar2Col, bar3Col, totalCol);

		// WEEK 2 //
		TableColumn week2col = new TableColumn();
		week2col.textProperty().bind(week2name);
		//Bars
		TableColumn<ResultsData, Double> storageWeek2Col = new TableColumn<ResultsData, Double>("Storage");
		storageWeek2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		storageWeek2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("storageWeek2"));
		TableColumn<ResultsData, Double> bar1Week2Col = new TableColumn<ResultsData, Double>("Bar 1");
		bar1Week2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar1Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar1Week2"));

		TableColumn<ResultsData, Double> bar2Week2Col = new TableColumn<ResultsData, Double>("Bar 2");
		bar2Week2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar2Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar2Week2"));

		TableColumn<ResultsData, Double> bar3Week2Col = new TableColumn<ResultsData, Double>("Bar 3");
		bar3Week2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		bar3Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar3Week2"));
		// Total
		TableColumn<ResultsData, Double> totalWeek2Col = new TableColumn<ResultsData, Double>("Total");
		totalWeek2Col.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		totalWeek2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("totalWeek2"));

		week2col.getColumns().addAll(storageWeek2Col, bar1Week2Col, bar2Week2Col, bar3Week2Col, totalWeek2Col);

		// SALES // 
		TableColumn salesCol = new TableColumn("Sales");
		// Theoretical
		TableColumn<ResultsData, Double> thSalesCol = new TableColumn<ResultsData, Double>("Theoretical");
		thSalesCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.076));
		thSalesCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("thSales"));
		// Actual
		TableColumn<ResultsData, Double> actSalesCol = new TableColumn<ResultsData, Double>("Actual");
		actSalesCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		actSalesCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("actSales"));
		salesCol.getColumns().addAll(thSalesCol, actSalesCol);

		// REVENUE // 
		TableColumn revenueCol = new TableColumn("Revenue (" + currency + ")");
		// Theoretical
		TableColumn<ResultsData, Double> thRevenueCol = new TableColumn<ResultsData, Double>("Theoretical");
		thRevenueCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.076));
		thRevenueCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("thRevenue"));
		// Actual
		TableColumn<ResultsData, Double> actRevenueCol = new TableColumn<ResultsData, Double>("Actual");
		actRevenueCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.053));
		actRevenueCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("actRevenue"));
		revenueCol.getColumns().addAll(thRevenueCol, actRevenueCol);

		// LOSS/GAIN
		TableColumn<ResultsData, Double> lossGainCol = new TableColumn<ResultsData, Double>("L/G (" + currency + ")");
		lossGainCol.setMinWidth(90);
		lossGainCol.prefWidthProperty().bind(table_results.widthProperty().multiply(0.082));
		lossGainCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("lossGain"));

		table_results.getColumns().addAll(nameCol, week1col, week2col, salesCol, revenueCol, lossGainCol);

		for(Object column : table_results.getColumns().toArray()) 
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

		// Special style classes for sub columns
		ObservableList<TableColumn<ResultsData, ?>> subColumns = FXCollections.observableArrayList();
		subColumns.addAll(week1col.getColumns());
		subColumns.addAll(week2col.getColumns());
		subColumns.addAll(salesCol.getColumns());
		subColumns.addAll(revenueCol.getColumns());
		for(TableColumn<ResultsData, ?> subColumn : subColumns) {
			subColumn.getStyleClass().add("results-subcolumn");
		}
	}


	private void updateData() 
	{
		data = FXCollections.observableArrayList(getData());
		filterData();
	}

	private ArrayList<ResultsData> getData()
	{
		ArrayList<ResultsData> resData = new ArrayList<ResultsData>();

		if(datepicker.getValue() != null) {
			LocalDate ld = datepicker.getValue();
			Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			System.out.println("DATE: " + date);

			WeekResult wr = null;
			try {
				wr = inventoryCtr.getResults(date);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(wr != null) {
				week1name.set("Week " + wr.getWeekA().getNumber());
				week2name.set("Week " + wr.getWeekB().getNumber());
				
				for(ItemResult result : wr.getResults()) {
					ResultsData newItem = new ResultsData(result);
					resData.add(newItem);
				}
			}
		}		
		if(resData.size() == 0) {
			resData = null;
		}		
		return resData;

	}

	private void initSearch() {

		txt_search.setPromptText("Search...");

		txt_search.textProperty().addListener(new InvalidationListener() {


			@Override

			public void invalidated(Observable o) {

				if(txt_search.textProperty().get().isEmpty()) {

					table_results.setItems(data);
					filterData();

					return;

				}

				ObservableList<ResultsData> newItems = FXCollections.observableArrayList();

				if(data != null && data.size() > 0) {
					for(ResultsData dataItem : data) {
						if(dataItem.getProduct().getName().toLowerCase().contains(txt_search.getText().toLowerCase())) {
							newItems.add(dataItem);
						}
					}

					table_results.setItems(newItems);
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
}
