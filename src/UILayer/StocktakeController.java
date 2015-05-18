package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import ControlLayer.ProductCtr;
import ModelLayer.Product;
import UILayer.TableData.InventoryData;
import UILayer.TableData.StocktakeData;

public class StocktakeController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;

	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private ComboBox<String> cbox_category = new ComboBox<String>();

	@FXML
	private TableView<StocktakeData> table_stocktake = new TableView<StocktakeData>();

	private ObservableList<StocktakeData> data;

	public StocktakeController() 
	{
		productCtr = new ProductCtr();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainHbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());

		initWeightable();
	}

	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}


	private void initWeightable() 
	{
		table_stocktake.setMaxWidth(1142);
		table_stocktake.setPrefWidth(1142);
		table_stocktake.setEditable(true);
		
		

		// NAME
		TableColumn<StocktakeData, String> nameCol = new TableColumn<StocktakeData, String>("Name");
		nameCol.setMinWidth(140);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, String>("name"));
		table_stocktake.getColumns().add(nameCol);

		// STORAGE BOTTLE
		TableColumn<StocktakeData, Double> storageCol = new TableColumn<StocktakeData, Double>("Storage bottle");
		storageCol.setMinWidth(120);
		storageCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		storageCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("storage"));
		table_stocktake.getColumns().add(storageCol);
		
		
		// BAR 1 BOTTLES
		TableColumn<StocktakeData, Double> bar1Col = new TableColumn<StocktakeData, Double>("Bar 1 bottles");
		bar1Col.setMinWidth(120);
		bar1Col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar1Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar1"));
		table_stocktake.getColumns().add(bar1Col);
		
		// BAR 1 OPEN
		TableColumn<StocktakeData, Double> bar1openCol = new TableColumn<StocktakeData, Double>("Bar 1 open (gr)");
		bar1openCol.setMinWidth(120);
		bar1openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar1openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar1open"));
		table_stocktake.getColumns().add(bar1openCol);
		
		// BAR 2 BOTTLES
		TableColumn<StocktakeData, Double> bar2Col = new TableColumn<StocktakeData, Double>("Bar 2");
		bar2Col.setMinWidth(120);
		bar2Col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar2Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar2"));
		table_stocktake.getColumns().add(bar2Col);
		
		// BAR 2 OPEN
		TableColumn<StocktakeData, Double> bar2openCol = new TableColumn<StocktakeData, Double>("Bar 2 open (gr)");
		bar2openCol.setMinWidth(120);
		bar2openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar2openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar2open"));
		table_stocktake.getColumns().add(bar2openCol);

		// BAR 3 BOTTLES
		TableColumn<StocktakeData, Double> bar3Col = new TableColumn<StocktakeData, Double>("Bar 3");
		bar3Col.setMinWidth(120);
		bar3Col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar3Col.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar3"));
		table_stocktake.getColumns().add(bar3Col);
		
		// BAR 3 OPEN
		TableColumn<StocktakeData, Double> bar3openCol = new TableColumn<StocktakeData, Double>("Bar 3 open (gr)");
		bar3openCol.setMinWidth(120);
		bar3openCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		bar3openCol.setCellValueFactory(
				new PropertyValueFactory<StocktakeData, Double>("bar3open"));
		table_stocktake.getColumns().add(bar3openCol);
		
		// SALES
		TableColumn<StocktakeData, Double> salesCol = new TableColumn<StocktakeData, Double>("Sales");
		salesCol.setMinWidth(120);
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
		
		updateData();
		
	}


	private void updateData() 
	{
		new Thread(new Runnable()
	    {
	        @Override
	        public void run() {
//	        	data = FXCollections.observableArrayList(getData(cbox_category.getValue().toLowerCase()));
	        	data = FXCollections.observableArrayList(getData("Alcohol"));
	    		table_stocktake.setItems(data);
	        }
	    }).start();
		
	}

	private ArrayList<StocktakeData> getData(String category) 
	{
		ArrayList<StocktakeData> invData = new ArrayList<StocktakeData>();
		ArrayList<Product> products = null;
		try{
			products = productCtr.getAllOf(category);
		}
		catch(Exception ex)
		{
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


}
