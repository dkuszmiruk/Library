package pl.put.poznan.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;

public class DialogUtils {
    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Błąd");
        errorAlert.setHeaderText(error);
        errorAlert.showAndWait();
    }

    public static void errorDialog(String error, String instruction){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Błąd");
        errorAlert.setHeaderText(error);
        TextArea textArea = new TextArea(instruction);
        textArea.setPrefRowCount(4);
        textArea.setEditable(false);
        textArea.setMouseTransparent(true);
        textArea.setWrapText(true);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    public static void informationDialog(String information){
        Alert informationDialog = new Alert(Alert.AlertType.INFORMATION);
        informationDialog.setTitle("Informacja");
        informationDialog.setGraphic(null); //ew to zostawic
        informationDialog.setHeaderText(null);
        informationDialog.setContentText(information);
        informationDialog.showAndWait();
    }

    public static void descriptionDialog(String title,String name, String description){
        Alert informationDialog = new Alert(Alert.AlertType.INFORMATION);
        informationDialog.setTitle(title); //dodac jakiegoś pane + textfield + przycisk ok który bedzie to zamykał
        informationDialog.setGraphic(null);
        informationDialog.setHeaderText(name);
        TextArea textArea = new TextArea(description);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        informationDialog.getDialogPane().setContent(textArea);
        informationDialog.showAndWait();
    }
}
