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
import UILayer.TableData.ResultsData;

public class ResultsController implements Initializable, ChangeablePane{

	PaneChanger changer;
	ProductCtr productCtr;

	@FXML
	private HBox mainHbox;

	@FXML
	private Button btn_save;

	@FXML
	private ComboBox<String> cbox_category = new ComboBox<String>();

	@FXML
	private TableView<ResultsData> table_results = new TableView<ResultsData>();

	private ObservableList<ResultsData> data;

	public ResultsController() 
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
		table_results.setMaxWidth(1142);
		table_results.setPrefWidth(1142);
		table_results.setEditable(true);
		
		
		// ID
		TableColumn<ResultsData, Integer> idCol = new TableColumn<ResultsData, Integer>("#");
		idCol.setMinWidth(30);
		idCol.setMaxWidth(30);
		idCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Integer>("id"));
		// NAME
		TableColumn<ResultsData, String> nameCol = new TableColumn<ResultsData, String>("Name");
		nameCol.setMinWidth(220);
		nameCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, String>("name"));

		// Bars - week1
		TableColumn<ResultsData, Double> bar1Col = new TableColumn<ResultsData, Double>("Bar 1");
		bar1Col.setMinWidth(150);
		bar1Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar1"));

		TableColumn<ResultsData, Double> bar2Col = new TableColumn<ResultsData, Double>("Bar 2");
		bar2Col.setMinWidth(150);
		bar2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar2"));

		TableColumn<ResultsData, Double> bar3Col = new TableColumn<ResultsData, Double>("Bar 3");
		bar3Col.setMinWidth(150);
		bar3Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar3"));
		
		// TOTAL WEEK 1
		TableColumn<ResultsData, Double> totalCol = new TableColumn<ResultsData, Double>("Total");
		totalCol.setMinWidth(150);
		totalCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("total"));
		
		// SALES WEEK 1
		TableColumn<ResultsData, Double> salesCol = new TableColumn<ResultsData, Double>("Sales");
		salesCol.setMinWidth(150);
		salesCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("sales"));
		
		// BARS WEEK 2
		TableColumn<ResultsData, Double> bar1Week2Col = new TableColumn<ResultsData, Double>("Bar 1");
		bar1Week2Col.setMinWidth(150);
		bar1Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar1Week2"));

		TableColumn<ResultsData, Double> bar2Week2Col = new TableColumn<ResultsData, Double>("Bar 2");
		bar2Week2Col.setMinWidth(150);
		bar2Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar2Week2"));

		TableColumn<ResultsData, Double> bar3Week2Col = new TableColumn<ResultsData, Double>("Bar 3");
		bar3Week2Col.setMinWidth(150);
		bar3Week2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("bar3Week2"));
		
		// TOTAL WEEK 2
		TableColumn<ResultsData, Double> totalWeek2Col = new TableColumn<ResultsData, Double>("Total");
		totalWeek2Col.setMinWidth(150);
		totalWeek2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("totalWeek2"));
		
		// SALES WEEK 2
		TableColumn<ResultsData, Double> salesWeek2Col = new TableColumn<ResultsData, Double>("Sales");
		salesWeek2Col.setMinWidth(150);
		salesWeek2Col.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("salesWeek2"));
		
		// VARIANCE
		TableColumn<ResultsData, Double> varianceCol = new TableColumn<ResultsData, Double>("Variance");
		varianceCol.setMinWidth(150);
		varianceCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("variance"));
		
		// REVENUE
		TableColumn<ResultsData, Double> revenueCol = new TableColumn<ResultsData, Double>("Revenue");
		revenueCol.setMinWidth(150);
		revenueCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("revenue"));

		// DIFFERENCE FROM SALES
		TableColumn<ResultsData, Double> differenceCol = new TableColumn<ResultsData, Double>("Difference from sales");
		differenceCol.setMinWidth(150);
		differenceCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("difference"));

		// DIFFERENCE FROM SALES
		TableColumn<ResultsData, Double> lossGainCol = new TableColumn<ResultsData, Double>("L/G");
		lossGainCol.setMinWidth(150);
		lossGainCol.setCellValueFactory(
				new PropertyValueFactory<ResultsData, Double>("lossGain"));
				
		table_results.getColumns().addAll(idCol, nameCol, bar1Col, bar2Col, bar3Col, totalCol, salesCol, bar1Week2Col, bar2Week2Col, bar3Week2Col, totalWeek2Col, salesWeek2Col, varianceCol, revenueCol, differenceCol, lossGainCol);
		
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
	    		table_results.setItems(data);
	        }
	    }).start();
		
	}

	private ArrayList<ResultsData> getData(String category) 
	{
		ArrayList<ResultsData> invData = new ArrayList<ResultsData>();
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
				ResultsData newData = new ResultsData(product);
				invData.add(newData);
			}
		}
		
		return invData;

	}


}
