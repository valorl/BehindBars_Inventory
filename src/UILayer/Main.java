package UILayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.setProperty("prism.lcdtext", "false"); // Improves font quality
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		
		primaryStage.setTitle("Behind Bars Inventory");
		primaryStage.setScene(scene);
		primaryStage.show();
		}
	public static void main(String[] args) {
		launch(args);
	}

}
