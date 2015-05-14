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
import javafx.scene.layout.HBox;
import ControlLayer.ProductCtr;
import ModelLayer.Product;
import UILayer.TableData.InventoryData;

public class InventoryController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;

	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private ComboBox<String> cbox_category = new ComboBox<String>();

	@FXML
	private TableView<InventoryData> table_inventory = new TableView<InventoryData>();

	private ObservableList<InventoryData> data;

	public InventoryController() 
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
		table_inventory.setMaxWidth(1142);
		table_inventory.setPrefWidth(1142);
		table_inventory.setEditable(true);
		
		
		// ID
		TableColumn<InventoryData, Integer> idCol = new TableColumn<InventoryData, Integer>("#");
		idCol.setMinWidth(30);
		idCol.setMaxWidth(30);
		idCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Integer>("id"));
		// NAME
		TableColumn<InventoryData, String> nameCol = new TableColumn<InventoryData, String>("Name");
		nameCol.setMinWidth(220);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, String>("name"));
		// UNIT VOLUME
		TableColumn<InventoryData, Double> unitCol = new TableColumn<InventoryData, Double>("Unit Volume (cl)");
		unitCol.setMinWidth(150);
		unitCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("unitVolume"));
		
		// WEIGHT -> Full bottle , Empty Bottle
		TableColumn weightCol = new TableColumn("Weight (gr)");
		TableColumn<InventoryData, Double> fullBottleCol = new TableColumn<InventoryData, Double>("Full bottle");
		fullBottleCol.setMinWidth(150);
		fullBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("fullBottle"));
		TableColumn<InventoryData, Double> emptyBottleCol = new TableColumn<InventoryData, Double>("Empty bottle");
		emptyBottleCol.setMinWidth(150);
		emptyBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("emptyBottle"));
		weightCol.getColumns().addAll(fullBottleCol, emptyBottleCol);
		
		// COST -> Per bottle , Per cl
		TableColumn costCol = new TableColumn("Cost");
		TableColumn<InventoryData, Double> costBottleCol = new TableColumn<InventoryData, Double>("Per bottle");
		costBottleCol.setMinWidth(110);
		costBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costBottle"));
		TableColumn<InventoryData, Double> costClCol = new TableColumn<InventoryData, Double>("Per cl");
		costClCol.setMinWidth(110);
		costClCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("costCl"));
		costCol.getColumns().addAll(costBottleCol,costClCol);
		
		// RETAIL PRICE -> Per cl, Per bottle
		TableColumn retailCol = new TableColumn("Retail");

		TableColumn<InventoryData, Double> retailClCol = new TableColumn<InventoryData, Double>("Per cl");
		retailClCol.setMinWidth(110);
		retailClCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceCl"));
		TableColumn<InventoryData, Double> retailBottleCol = new TableColumn<InventoryData, Double>("Per bottle");
		retailBottleCol.setMinWidth(110);
		retailBottleCol.setCellValueFactory(
				new PropertyValueFactory<InventoryData, Double>("priceBottle"));
		retailCol.getColumns().addAll(retailClCol,retailBottleCol);
		
		table_inventory.getColumns().addAll(idCol, nameCol, unitCol, weightCol,costCol,retailCol);
		
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
	    		table_inventory.setItems(data);
	        }
	    }).start();
		
	}

	private ArrayList<InventoryData> getData(String category) 
	{
		ArrayList<InventoryData> invData = new ArrayList<InventoryData>();
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
				InventoryData newData = new InventoryData(product);
				invData.add(newData);
			}
		}
		
		return invData;

	}


}
