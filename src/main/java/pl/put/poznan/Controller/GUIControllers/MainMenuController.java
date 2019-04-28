package pl.put.poznan.Controller.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.put.poznan.utils.FxmlUtils;

public class MainMenuController {

    @FXML
    private Pane mainPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TopMenuController topMenuController;

    @FXML
    private void initialize(){
        topMenuController.setMainMenuController(this);
        topMenuController.openReaders();
    }

    public void setMainPane(String fxmlPath){
        Parent root = FxmlUtils.fxmlLoader(fxmlPath);
        if(root != null) {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(root);
        }
    }
}