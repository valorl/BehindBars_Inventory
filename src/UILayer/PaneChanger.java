package UILayer;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class PaneChanger  extends StackPane {
    //Holds the panes to be displayed

    private HashMap<String, Node> panes = new HashMap<>();
    
    public PaneChanger() {
        super();
    }

    //Add the pane to the collection
    public void addPane(String name, Node pane) {
        panes.put(name, pane);
    }

    //Returns the Node with the appropriate name
    public Node getPane(String name) {
        return panes.get(name);
    }

    //Loads the fxml file, add the pane to the panes collection and
    //finally injects the panePane to the controller.
    public boolean loadPane(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent loadPane = (Parent) loader.load();
            ChangeablePane myPaneController = ((ChangeablePane) loader.getController());
            myPaneController.setPaneParent(this);
            addPane(name, loadPane);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    //This method tries to displayed the pane with a predefined name.
    //First it makes sure the pane has been already loaded.  Then if there is more than
    //one pane the new pane is been added second, and then the current pane is removed.
    // If there isn't any pane being displayed, the new pane is just added to the root.
    
    public boolean setPane(final String name) {       
        if (panes.get(name) != null) {   //pane loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one pane
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    //remove the displayed pane
                        getChildren().add(0, panes.get(name));     //add the pane
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(panes.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("pane hasn't been loaded!!! \n");
            return false;
        }

    }

    public boolean unloadPane(String name) {
        if (panes.remove(name) == null) {
            System.out.println("Pane didn't exist");
            return false;
        } else {
            return true;
        }
    }
}

