package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.enartee.FlatButton;



public class MainController implements Initializable{

	private static final String MENU_COLOR = "#24aad7";
	private static final String MENU_COLOR_HOVER = "#50BBDF";
	
	public static String inventoryID = "inventory";
    public static String inventoryFXML = "inventory.fxml";
    public static String stocktakeID = "stocktake";
    public static String stocktakeFXML = "stocktake.fxml";
    public static String resultsID = "results";
    public static String resultsFXML = "results.fxml";
    public static String keylinesID = "keylines";
    public static String keylinesFXML = "keylines.fxml";
	 
    private PaneChanger changer;
    
	@FXML
	private HBox navbox;
	
	@FXML
	private StackPane contentPane;
		
	@FXML
	private GridPane mainGrid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		constructButtons();	
		constructContent();
	}
	
	private void constructContent() 
	{
		
		contentPane.setMaxWidth(Double.MAX_VALUE);
		contentPane.setMaxHeight(Double.MAX_VALUE);
		
		changer = new PaneChanger();
		changer.loadPane(inventoryID, inventoryFXML);
		changer.loadPane(stocktakeID, stocktakeFXML);
		changer.loadPane(resultsID, resultsFXML);
		changer.loadPane(keylinesID, keylinesFXML);
		
		
		changer.setMaxHeight(Double.MAX_VALUE);
		changer.setMaxWidth(Double.MAX_VALUE);
		
		
		//Group content = new Group();
		//content.getChildren().add(changer);
		contentPane.setAlignment(Pos.TOP_LEFT);
		contentPane.getChildren().add(changer);
	}

	private void constructButtons() {	
		
			// INVENTORY
		
			FlatButton btn_inventory = new FlatButton("Inventory");
			btn_inventory.setMaxWidth(Double.MAX_VALUE);
			btn_inventory.getStyleClass().add("navfirst");
			btn_inventory.setStartColor(Color.web(MENU_COLOR));
			btn_inventory.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_inventory, Priority.ALWAYS);
			
			btn_inventory.setOnAction((e) -> {
				changer.setPane(inventoryID);			    
			});
			
			navbox.getChildren().add(btn_inventory);
			
			// STOCKTAKE
			
			FlatButton btn_stocktake = new FlatButton("Stocktake");
			btn_stocktake.setMaxWidth(Double.MAX_VALUE);
			btn_stocktake.setStartColor(Color.web(MENU_COLOR));
			btn_stocktake.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_stocktake, Priority.ALWAYS);
			
			btn_stocktake.setOnAction((e) -> {
				changer.setPane(stocktakeID);			    
			});
			
			navbox.getChildren().add(btn_stocktake);
			
			// RESULTS
			
			FlatButton btn_results = new FlatButton("Results");
			btn_results.setMaxWidth(Double.MAX_VALUE);
			btn_results.setStartColor(Color.web(MENU_COLOR));
			btn_results.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_results, Priority.ALWAYS);
			
			btn_results.setOnAction((e) -> {
				changer.setPane(resultsID);			    
			});
			
			navbox.getChildren().add(btn_results);
			
			// KEY LINES
			
			FlatButton btn_keylines = new FlatButton("Key Lines");
			btn_keylines.setMaxWidth(Double.MAX_VALUE);
			btn_keylines.setStartColor(Color.web(MENU_COLOR));
			btn_keylines.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_keylines, Priority.ALWAYS);
			
			btn_keylines.setOnAction((e) -> {
				changer.setPane(keylinesID);			    
			});
			
			navbox.getChildren().add(btn_keylines);
			
			// EMPLOYEES
			
			FlatButton btn_employees = new FlatButton("E");

			btn_employees.getStyleClass().addAll("flat-button-small");
			btn_employees.setStartColor(Color.web(MENU_COLOR));
			btn_employees.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_employees, Priority.NEVER);			
			navbox.getChildren().add(btn_employees);
			
			// SETTINGS
			
			FlatButton btn_settings = new FlatButton("S");

			btn_settings.getStyleClass().addAll("navlast","flat-button-small");
			btn_settings.setStartColor(Color.web(MENU_COLOR));
			btn_settings.setEndColor(Color.web(MENU_COLOR_HOVER));
			HBox.setHgrow(btn_settings, Priority.NEVER);
			
			navbox.getChildren().add(btn_settings);		
			
			
			// Navigation keyboard shortcuts

			mainGrid.setOnKeyPressed((e) -> {
				
				
				if(e.isShortcutDown()) 
				{
					switch (e.getCode()) {
					case DIGIT1:
						btn_inventory.fire();
						break;
					case DIGIT2:
						btn_stocktake.fire();
						break;
					case DIGIT3:
						btn_results.fire();
						break;
					case DIGIT4:
						btn_keylines.fire();
						break;
					case E:
						btn_employees.fire();
						break;
					case S:
						btn_settings.fire();
						break;

					default:
						break;
					}
				}
			});
			
			btn_settings.setOnAction((e) -> {
				Stage configStage = new Stage();
				Parent parent = null;
				ConfigController controller;

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("configuration.fxml"));
					parent = loader.load();
					controller = loader.<ConfigController>getController();
					controller.init(configStage);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}

				Scene scene = new Scene(parent);
				//scene.getStylesheets().add(getClass().getResource(Main.getMainCss()).toExternalForm());

				configStage.setTitle("Inventory configuration");
				configStage.setScene(scene);
				configStage.show();
			});
			
		}
		
	}

