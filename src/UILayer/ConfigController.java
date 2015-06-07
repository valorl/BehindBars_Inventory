package UILayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ControlLayer.SettingsCtr;

public class ConfigController implements Initializable{

	private Stage window;

	@FXML
	private TextField txt_coName;

	@FXML
	private ComboBox<String> cbox_currency;

	@FXML
	private Button btn_ok, btn_cancel;

	@FXML
	private Hyperlink link_about;

	private SettingsCtr settingsCtr;

	public ConfigController() {
		settingsCtr = new SettingsCtr();
	}

	private ObservableList<String> currencies = FXCollections.observableArrayList("Kr", "€", "$");

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void init(Stage stage) {
		window = stage;

		String setting = null;
		try {
			setting = settingsCtr.findSetting("CO_NAME", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(setting != null) {
			txt_coName.setText(setting);
		}

		initComboCurr();
		initButtons();
		initAbout();
	}

	private void initComboCurr() {

		cbox_currency.setItems(currencies);
		String curr_setting = null;
		try {
			curr_setting = settingsCtr.findSetting("CURR", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(curr_setting == null || curr_setting.length() == 0) {
			cbox_currency.setValue(cbox_currency.getItems().get(0));
		}
		else {
			if(currencies.contains(curr_setting)) {
				cbox_currency.setValue(curr_setting);
			}
			else {
				cbox_currency.setValue(cbox_currency.getItems().get(0));
			}
		}
	}

	private void initButtons() 
	{
		btn_ok.setOnAction(e -> {
			saveData();
			closeWindow();
		});

		btn_cancel.setOnAction(e -> {
			closeWindow();
		});
	}

	private void saveData() 
	{
		if(txt_coName.getText().length() > 0) {
			try {
				settingsCtr.updateSetting("CO_NAME", txt_coName.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(cbox_currency.getValue() != null) {
			try {
				settingsCtr.updateSetting("CURR", cbox_currency.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initAbout() 
	{
		link_about.setOnAction(e -> {
			Stage aboutStage = new Stage();
			Parent parent = null;
			AboutController controller;

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
				parent = loader.load();
				controller = loader.<AboutController>getController();
				controller.init(aboutStage);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}

			Scene scene = new Scene(parent);
			scene.setOnKeyReleased(event -> {
				aboutStage.close();
			});
			//scene.getStylesheets().add(getClass().getResource(Main.getMainCss()).toExternalForm());

			aboutStage.initStyle(StageStyle.UNDECORATED);
			aboutStage.initModality(Modality.APPLICATION_MODAL);
			aboutStage.setScene(scene);
			aboutStage.show();
		});
	}

	private void closeWindow() 
	{
		window.close();
	}



}
