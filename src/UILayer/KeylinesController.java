package UILayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ControlLayer.ProductCtr;
import ModelLayer.Product;
import UILayer.TableData.KeylinesData;

public class KeylinesController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;
	
	@FXML
	private HBox mainHbox;
	
	@FXML
	private Button btn_save;
	
	@FXML
	private TableView<KeylinesData> table_keylines = new TableView<KeylinesData>();
	
	private ObservableList<KeylinesData> data;


	public KeylinesController() 
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
		table_keylines.setMaxWidth(1142);
		table_keylines.setPrefWidth(1142);
		table_keylines.setEditable(true);
		
		
		// ID
		TableColumn<KeylinesData, Integer> idCol = new TableColumn<KeylinesData, Integer>("#");
		idCol.setMinWidth(30);
		idCol.setMaxWidth(30);
		idCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Integer>("id"));
		// NAME
		TableColumn<KeylinesData, String> nameCol = new TableColumn<KeylinesData, String>("Name");
		nameCol.setMinWidth(220);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, String>("name"));

		// WEEKS
		TableColumn<KeylinesData, Double> week1Col = new TableColumn<KeylinesData, Double>("Week 1");
		week1Col.setMinWidth(150);
		week1Col.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week1"));

		TableColumn<KeylinesData, Double> week2Col = new TableColumn<KeylinesData, Double>("Week 2");
		week2Col.setMinWidth(150);
		week2Col.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week2"));

		TableColumn<KeylinesData, Double> week3Col = new TableColumn<KeylinesData, Double>("Week 3");
		week3Col.setMinWidth(150);
		week3Col.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week3"));
		
		TableColumn<KeylinesData, Double> week4Col = new TableColumn<KeylinesData, Double>("Week 4");
		week4Col.setMinWidth(150);
		week4Col.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week4"));
		
		TableColumn<KeylinesData, Double> week5Col = new TableColumn<KeylinesData, Double>("Week 5");
		week5Col.setMinWidth(150);
		week5Col.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("week5"));
		
		// VARIANCE
		TableColumn<KeylinesData, Double> varianceCol = new TableColumn<KeylinesData, Double>("Variance");
		varianceCol.setMinWidth(150);
		varianceCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("variance"));
		
		// RETAIL PRICE
		TableColumn<KeylinesData, Double> retailCol = new TableColumn<KeylinesData, Double>("Retail");
		retailCol.setMinWidth(150);
		retailCol.setCellValueFactory(
				new PropertyValueFactory<KeylinesData, Double>("retail"));
		
		table_keylines.getColumns().addAll(idCol, nameCol, week1Col, week2Col, week3Col, week4Col, week5Col, varianceCol, retailCol);
		
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
	    		table_keylines.setItems(data);
	        }
	    }).start();
		
	}

	private ArrayList<KeylinesData> getData(String category) 
	{
		ArrayList<KeylinesData> invData = new ArrayList<KeylinesData>();
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
				KeylinesData newData = new KeylinesData(product);
				invData.add(newData);
			}
		}
		
		return invData;

	}

	
	
}
