package pl.put.poznan.Controller.GUIControllers.addControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.ReadersController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.ReaderDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Reader;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

public class AddReaderController {

    private Stage newStage;
    private DbConnection dbConnection;
    private ReaderDAO readerDAO;
    private ReadersController readersController;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField emailAddressField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField streetAndHouseNumberField;
    @FXML
    public Button addReaderButton;

    @FXML
    public void addReader() {
        Reader reader = new Reader();
        reader.setFirstName(firstNameField.getText());
        reader.setLastName(lastNameField.getText());
        reader.setEmailAddress(emailAddressField.getText());
        reader.setCity(cityField.getText());
        reader.setStreetAndHouseNumber(streetAndHouseNumberField.getText());
        reader.setIfActive('T');
        try {
            readerDAO.addReader(reader);
            newStage.hide();
            readersController.refreshTableReader();
            DialogUtils.informationDialog("Czytelnik został dodany.");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się dodać czytelnika",e.getMessage());
        }
    }

    @FXML
    public void closeAddReader() {
            newStage.close();
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        readerDAO = new ReaderDAO(dbConnection);
        setTextFieldsProperty();
        addReaderButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()).or(emailAddressField.textProperty().isEmpty()).or(cityField.textProperty().isEmpty()).or(streetAndHouseNumberField.textProperty().isEmpty()));
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

        emailAddressField.setOnKeyTyped(event -> {
            String string = emailAddressField.getText();
            if (string.length() > 50) {
                emailAddressField.setText(string.substring(0, 50));
                emailAddressField.positionCaret(string.length());
            }
        });

        cityField.setOnKeyTyped(event -> {
            String string = cityField.getText();
            if (string.length() > 20) {
                cityField.setText(string.substring(0, 20));
                cityField.positionCaret(string.length());
            }
        });

        streetAndHouseNumberField.setOnKeyTyped(event -> {
            String string = streetAndHouseNumberField.getText();
            if (string.length() > 20) {
                streetAndHouseNumberField.setText(string.substring(0, 20));
                streetAndHouseNumberField.positionCaret(string.length());
            }
        });

    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setReadersController(ReadersController readersController) {
        this.readersController = readersController;
    }
}
