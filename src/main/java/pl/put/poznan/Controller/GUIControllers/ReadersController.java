package pl.put.poznan.Controller.GUIControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.addControllers.AddReaderController;
import pl.put.poznan.Controller.GUIControllers.modifyControllers.ModifyReaderController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.ReaderDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Reader;
import pl.put.poznan.modelFx.ReaderFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.util.List;

import static pl.put.poznan.utils.converters.ReaderConverter.convertToReader;
import static pl.put.poznan.utils.converters.ReaderConverter.convertToReaderFx;

public class ReadersController {

    private DbConnection dbConnection;
    private ReaderDAO readerDAO;

    private ObservableList<ReaderFx> readerObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField findTextField;
    @FXML
    public Button deleteReaderButton;
    @FXML
    public TableView<ReaderFx> readerTable;
    @FXML
    public TableColumn<ReaderFx, String> firstName;
    @FXML
    public TableColumn<ReaderFx, String> lastName;
    @FXML
    public TableColumn<ReaderFx, String> emailAddress;
    @FXML
    public TableColumn<ReaderFx, String> city;
    @FXML
    public TableColumn<ReaderFx, String> streetAndHouseNumber;

    @FXML
    public void addReader() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableReader();
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddReader.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(root!=null) {
            AddReaderController addReaderController = loader.getController();
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie czytelnika");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addReaderController.setNewStage(stage);
            addReaderController.setReadersController(this);
            stage.showAndWait();
        }
        //refreshTableReader();
    }

    @FXML
    public void modifyReader() {
        ReaderFx readerFx = readerTable.getSelectionModel().getSelectedItem();
        if (readerFx != null) {
            if(!findTextField.getText().isEmpty()) {
                findTextField.clear();
                refreshTableReader();
            }
            //refreshTableReader();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/modify/ModifyReader.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(root!=null) {
                ModifyReaderController modifyReaderController = loader.getController();

                Scene newScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modyfikacja czytelnika");
                stage.setScene(newScene);
                stage.initModality(Modality.APPLICATION_MODAL);
                modifyReaderController.setNewStage(stage);
                modifyReaderController.setReadersController(this);
                modifyReaderController.setReaderFx(readerFx);
                stage.showAndWait();
            }

        }
    }

    @FXML
    public void deleteReader() {
        ReaderFx readerFx = readerTable.getSelectionModel().getSelectedItem();
        if (readerFx != null) {
            Reader reader = convertToReader(readerFx);
            try {
                readerDAO.deleteReader(reader);
                if(findTextField.getText().isEmpty())
                    refreshTableReader();
                else
                    refreshTableWithPattern(findTextField.getText());
                DialogUtils.informationDialog("Czytelnik został usunięty");
            } catch (ApplicationException e) {
                DialogUtils.errorDialog("Nie usunięto czytelnika", e.getMessage());
            }
        }
    }
    @FXML
    public void findReadersButton() {
        String findText = findTextField.getText();
        if(!findText.isEmpty()){
            refreshTableWithPattern(findText);
        }else{
            refreshTableReader();
        }
    }

    @FXML
    private void initialize() {
        //addSurnameButton.disableProperty().bind(surnameTextField.textProperty().isEmpty());
        //deleteReaderButton.disableProperty().bind();
        dbConnection = LibraryApplication.dbConnection;
        readerDAO = new ReaderDAO(dbConnection);
        bindingsReaderTableView();
        refreshTableReader();

//        findTextField.textProperty().addListener(new ChangeListener<String>() {
//
//            public void changed(ObservableValue<? extends String> observable, String oldValue,
//                                String newValue) {
//                    findTextField.setText(newValue.replaceAll("\\s", ""));
//            }
//        });
    }


    public void refreshTableReader() {
        List<Reader> readerList = readerDAO.getAllReader();
        readerObservableList.clear();
        readerList.forEach(reader -> {
            ReaderFx readerFx = convertToReaderFx(reader);
            readerObservableList.add(readerFx);
        });
    }

    private void refreshTableWithPattern(String pattern) {
        List<Reader> readerList = readerDAO.searchReader(pattern);
        readerObservableList.clear();
        readerList.forEach(reader -> {
            ReaderFx readerFx = convertToReaderFx(reader);
            readerObservableList.add(readerFx);
        });
    }


    private void bindingsReaderTableView() {
        this.readerTable.setItems(this.getReaderObservableList());
        this.firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.emailAddress.setCellValueFactory(cellData -> cellData.getValue().emailAddressProperty());
        this.lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        this.city.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        this.streetAndHouseNumber.setCellValueFactory(cellData -> cellData.getValue().streetAndHouseNumberProperty());
    }


    private ObservableList<ReaderFx> getReaderObservableList() {
        return readerObservableList;
    }

}

/*
w ksiazce, przy opisie książki to wyrzucić
        readerTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    //System.out.println(readerTable.getSelectionModel().getSelectedItem().getReaderId());
                    DialogUtils.descriptionDialog("Opis ksiazki",readerTable.getSelectionModel().getSelectedItem().getFirstName(),readerTable.getSelectionModel().getSelectedItem().getEmailAddress());
                }
            }
        });

*/