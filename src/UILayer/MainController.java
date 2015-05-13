package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import com.enartee.FlatButton;

public class MainController implements Initializable{

	private static final String MENU_COLOR = "#24aad7";
	private static final String MENU_COLOR_HOVER = "#50BBDF";
	
	@FXML
	private HBox navbox;
	
	@FXML
	private Parent contentPane;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		constructButtons();	
		
		contentPane = new GridPane();
		
		contentPane.setStyle("-fx-background-color: #fff");
	}

	private void constructButtons() {	
			FlatButton btn_inventory = new FlatButton("Inventory");
			btn_inventory.setMaxWidth(Double.MAX_VALUE);
			btn_inventory.getStyleClass().add("navfirst");
			btn_inventory.setStartColor(Color.web(MENU_COLOR));
			btn_inventory.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_inventory, Priority.ALWAYS);
			navbox.getChildren().add(btn_inventory);
			
			
			
			FlatButton btn_stocktake = new FlatButton("Stocktake");
			btn_stocktake.setMaxWidth(Double.MAX_VALUE);
			btn_stocktake.setStartColor(Color.web(MENU_COLOR));
			btn_stocktake.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_stocktake, Priority.ALWAYS);
			navbox.getChildren().add(btn_stocktake);
			
			FlatButton btn_results = new FlatButton("Results");
			btn_results.setMaxWidth(Double.MAX_VALUE);
			btn_results.setStartColor(Color.web(MENU_COLOR));
			btn_results.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_results, Priority.ALWAYS);
			navbox.getChildren().add(btn_results);
			
			FlatButton btn_keylines = new FlatButton("Key Lines");
			btn_keylines.setMaxWidth(Double.MAX_VALUE);
			btn_keylines.setStartColor(Color.web(MENU_COLOR));
			btn_keylines.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_keylines, Priority.ALWAYS);
			navbox.getChildren().add(btn_keylines);
			
			FlatButton btn_employees = new FlatButton("E");

			btn_employees.getStyleClass().addAll("flat-button-small");
			btn_employees.setStartColor(Color.web(MENU_COLOR));
			btn_employees.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_employees, Priority.NEVER);
			navbox.getChildren().add(btn_employees);
			
			FlatButton btn_settings = new FlatButton("S");

			btn_settings.getStyleClass().addAll("navlast","flat-button-small");
			btn_settings.setStartColor(Color.web(MENU_COLOR));
			btn_settings.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_settings, Priority.NEVER);
			
			navbox.getChildren().add(btn_settings);		
			
		}
		
	}

