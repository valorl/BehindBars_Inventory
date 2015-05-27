package UILayer;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AboutController implements Initializable{

	Stage window;
	
	private static final String WEB_PAGE = "http://www.behindbarsconsulting.com/";
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	private ImageView img_logo;
	
	public void init(Stage stage) {
		window = stage;
		
		img_logo.setImage(new Image(getClass().getResourceAsStream("bbc_logo.png")));
	}

}
