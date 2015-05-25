package UILayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	private static final String ROOT_FXML = "main.fxml";
	private static final String MAIN_CSS = "main.css";

	@Override
	public void start(Stage primaryStage) throws Exception {

		//	System.setProperty("prism.lcdtext", "false"); // Improves font quality

		Parent root = FXMLLoader.load(getClass().getResource(ROOT_FXML));

		Scene scene = new Scene(root,1200,600);
		scene.getStylesheets().add(getClass().getResource(MAIN_CSS).toExternalForm());

		scene.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.F12) { 
				if(primaryStage.isFullScreen()){
					primaryStage.setFullScreen(false);
				}
				else {
					primaryStage.setFullScreen(true);
				}
			}
		});

		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(1200);
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

	public static String getRootFxml() {
		return ROOT_FXML;
	}

	public static String getMainCss() {
		return MAIN_CSS;
	}

}
