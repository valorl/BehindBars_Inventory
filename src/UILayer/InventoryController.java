package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class InventoryController implements Initializable, ChangeablePane{

	PaneChanger changer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@Override
	public void setPaneParent(PaneChanger parent) {
		changer = parent;		
	}
	
	
}
