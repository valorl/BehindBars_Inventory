package UILayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	//	System.setProperty("prism.lcdtext", "false"); // Improves font quality
		
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(1000);
		primaryStage.setTitle("Inventory");
		primaryStage.setScene(scene);
		primaryStage.show();
		}
	
	 @Override
	  public void init() throws Exception {
	    super.init();
	    
	    Font.loadFont(getClass().getResource("UBUNTU-REGULAR.TTF").toExternalForm(), 12);	 
	    Font.loadFont(getClass().getResource("Roboto-Medium.ttf").toExternalForm(), 12);
	    Font.loadFont(getClass().getResource("Roboto-Bold.ttf").toExternalForm(), 12);
	  }
	
	public static void main(String[] args) {
		launch(args);
	}

}
