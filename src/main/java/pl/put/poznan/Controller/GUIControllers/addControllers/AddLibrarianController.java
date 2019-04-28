package pl.put.poznan.Controller.GUIControllers.addControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.LibrariansController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.LibrarianDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Librarian;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

public class AddLibrarianController {

    private Stage newStage;
    private DbConnection dbConnection;
    private LibrarianDAO librarianDAO;
    private LibrariansController librariansController;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button addLibrarianButton;

    @FXML
    public void addLibrarian() {
        Librarian librarian = new Librarian();
        librarian.setFirstName(firstNameField.getText());
        librarian.setLastName(lastNameField.getText());
        librarian.setPassword(passwordField.getText());
        librarian.setIfEmployed('T');
        try {
            librarianDAO.addLibrarian(librarian);
            newStage.hide();
            librariansController.refreshTableLibrarian();
            DialogUtils.informationDialog("Bibliotekarz został dodany.");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się dodać bibliotekarza",e.getMessage());
        }
    }

    @FXML
    public void closeAddLibrarian() {
        newStage.close();
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        librarianDAO = new LibrarianDAO(dbConnection);
        setTextFieldsProperty();
        addLibrarianButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()).or(passwordField.textProperty().isEmpty()));
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
