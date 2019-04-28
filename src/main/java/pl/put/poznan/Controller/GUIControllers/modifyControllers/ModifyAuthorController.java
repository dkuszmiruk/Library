package pl.put.poznan.Controller.GUIControllers.modifyControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.AuthorsController;
import pl.put.poznan.DAO.AuthorDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.modelFx.AuthorFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthor;

public class ModifyAuthorController {

    private Stage newStage;
    private DbConnection dbConnection;
    private AuthorDAO authorDAO;
    private AuthorsController authorsController;
    private AuthorFx authorFx;

    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public Button modifyAuthorButton;

    @FXML
    public void modifyAuthor() {
        authorFx.setFirstName(firstNameField.getText());
        authorFx.setLastName(lastNameField.getText());
        try {
            authorDAO.modifyAuthor(convertToAuthor(authorFx));
            newStage.hide();
            authorsController.refreshTableAuthor();
            DialogUtils.informationDialog("Autor został zmodyfikowany");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się zmodyfikować autora", e.getMessage());
        }
    }

    @FXML
    public void closeModifyAuthor() {
        newStage.close();
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        authorDAO = new AuthorDAO(dbConnection);
        setTextFieldsProperty();
        modifyAuthorButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()));
    }

    private void setFields() {
        firstNameField.setText(authorFx.getFirstName());
        lastNameField.setText(authorFx.getLastName());
    }

    public void setAuthorFx(AuthorFx authorFx) {
        this.authorFx = authorFx;
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
    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setAuthorsController(AuthorsController authorsController) {
        this.authorsController = authorsController;
    }
}
