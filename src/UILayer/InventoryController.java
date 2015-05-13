package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class InventoryController implements Initializable, ChangeablePane{

	PaneChanger changer;
	
	@FXML
	private HBox mainHbox;
	
	@FXML
	private Button btn_save;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		mainHbox.getStylesheets().addAll(getClass().getResource("inventory.css").toExternalForm());
		
	}
	
	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}
	
	
}
