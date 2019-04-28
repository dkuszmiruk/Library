package pl.put.poznan.Controller.GUIControllers.modifyControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.LibrariansController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.LibrarianDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.modelFx.LibrarianFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarian;

public class ModifyLibrarianController {

    private Stage newStage;
    private DbConnection dbConnection;
    private LibrarianDAO librarianDAO;
    private LibrariansController librariansController;
    private LibrarianFx librarianFx;

    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button modifyLibrarianButton;

    @FXML
    public void modifyLibrarian() {
        librarianFx.setFirstName(firstNameField.getText());
        librarianFx.setLastName(lastNameField.getText());
        librarianFx.setPassword(passwordField.getText());
        try {
            librarianDAO.modifyLibrarian(convertToLibrarian(librarianFx));
            newStage.hide();
            librariansController.refreshTableLibrarian();
            DialogUtils.informationDialog("Bibliotekarz został zmodyfikowany");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się zmodyfikować bibliotekarza", e.getMessage());
        }
    }

    @FXML
    public void closeModifyLibrarian() {
        newStage.close();
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        librarianDAO = new LibrarianDAO(dbConnection);
        setTextFieldsProperty();
        modifyLibrarianButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()).or(passwordField.textProperty().isEmpty()));
    }


    private void setFields() {
        firstNameField.setText(librarianFx.getFirstName());
        lastNameField.setText(librarianFx.getLastName());
        passwordField.setText(librarianFx.getPassword());
    }

    public void setLibrarianFx(LibrarianFx librarianFx) {
        this.librarianFx = librarianFx;
        setFields();
    }

    private void setTextFieldsProperty(){
        firstNameField.setOnKeyTyped(event -> {
            String string = firstNameField.getText();
            if (string.length() > 20) {
                firstNameField.setText(string.substring(0, 20));
                firstNameField.positionCaret(string.length());
            }
        });

        lastNameField.setOnKeyTyped(event -> {
            String string = lastNameField.getText();
            if (string.length() > 30) {
                lastNameField.setText(string.substring(0, 30));
                lastNameField.positionCaret(string.length());
            }
        });

        passwordField.setOnKeyTyped(event -> {
            String string = passwordField.getText();
            if (string.length() > 20) {
                passwordField.setText(string.substring(0, 20));
                passwordField.positionCaret(string.length());
            }
        });
    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setLibrariansController(LibrariansController librariansController) {
        this.librariansController = librariansController;
    }
}
