package pl.put.poznan.Controller.GUIControllers.modifyControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.ReadersController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.ReaderDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.modelFx.ReaderFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import static pl.put.poznan.utils.converters.ReaderConverter.convertToReader;

public class ModifyReaderController {


    private Stage newStage;
    private DbConnection dbConnection;
    private ReaderDAO readerDAO;
    private ReadersController readersController;
    private ReaderFx readerFx;

    @FXML
    public Button modifyReaderButton;
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
    public void modifyReader() {
        readerFx.setFirstName(firstNameField.getText());
        readerFx.setLastName(lastNameField.getText());
        readerFx.setEmailAddress(emailAddressField.getText());
        readerFx.setCity(cityField.getText());
        readerFx.setStreetAndHouseNumber(streetAndHouseNumberField.getText());
        try {
            readerDAO.modifyReader(convertToReader(readerFx));
            newStage.hide();
            readersController.refreshTableReader();
            DialogUtils.informationDialog("Czytelnik został zmodyfikowany");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się zmodyfikować czytelnika", e.getMessage());
        }
    }

    @FXML
    public void closeModifyReader() {
        newStage.close();
    }

    @FXML
    private void initialize() {
        dbConnection = LibraryApplication.dbConnection;
        readerDAO = new ReaderDAO(dbConnection);
        setTextFieldsProperty();
        modifyReaderButton.disableProperty().bind(firstNameField.textProperty().isEmpty().or(lastNameField.textProperty().isEmpty()).or(emailAddressField.textProperty().isEmpty()).or(cityField.textProperty().isEmpty()).or(streetAndHouseNumberField.textProperty().isEmpty()));
    }


    private void setFields() {
        firstNameField.setText(readerFx.getFirstName());
        lastNameField.setText(readerFx.getLastName());
        emailAddressField.setText(readerFx.getEmailAddress());
        cityField.setText(readerFx.getCity());
        streetAndHouseNumberField.setText(readerFx.getStreetAndHouseNumber());
    }

    public void setReaderFx(ReaderFx readerFx) {
        this.readerFx = readerFx;
        setFields();
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
