package pl.put.poznan.Controller.GUIControllers.addControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.AuthorsController;
import pl.put.poznan.DAO.AuthorDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Author;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

public class AddAuthorController {


    private Stage newStage;
    private DbConnection dbConnection;
    private AuthorDAO authorDAO;
    private AuthorsController authorsController;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public Button addAuthorButton;

    @FXML
    public void addAuthor() {
        Author author = new Author();
        author.setFirstName(firstNameField.getText());
        author.setLastName(lastNameField.getText());
        try {
            authorDAO.addAuthor(author);
            newStage.hide();
            authorsController.refreshTableAuthor();
            DialogUtils.informationDialog("Autor został dodany.");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się dodać autora",e.getMessage());
        }
    }

    @FXML
    public void closeAddAuthor() {
        newStage.close();
    }

    @FXML
    private void initialize() {
        dbConnection = LibraryApplication.dbConnection;
        authorDAO = new AuthorDAO(dbConnection);
        setTextFieldsProperty();
        addAuthorButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()));
    }

    private void setTextFieldsProperty() {
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
    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setAuthorsController(AuthorsController authorsController) {
        this.authorsController = authorsController;
    }
}
