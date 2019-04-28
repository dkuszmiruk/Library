package pl.put.poznan.Controller.GUIControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.addControllers.AddLibrarianController;
import pl.put.poznan.Controller.GUIControllers.modifyControllers.ModifyLibrarianController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.LibrarianDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Librarian;
import pl.put.poznan.modelFx.LibrarianFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.util.List;

import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarian;
import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarianFx;

public class LibrariansController {

    private DbConnection dbConnection;
    private LibrarianDAO librarianDAO;

    private ObservableList<LibrarianFx> librarianObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField findTextField;
    @FXML
    public TableView<LibrarianFx> librarianTable;

    @FXML
    public TableColumn<LibrarianFx, String> firstName;

    @FXML
    public TableColumn<LibrarianFx, String> lastName;

    @FXML
    public TableColumn<LibrarianFx, String> password;

    @FXML
    public void addLibrarian() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableLibrarian();
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddLibrarian.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddLibrarianController addLibrarianController = loader.getController();
        if (root != null) {
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie bibliotekarza");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addLibrarianController.setNewStage(stage);
            addLibrarianController.setLibrariansController(this);
            stage.showAndWait();
        }
    }


    @FXML
    public void modifyLibrarian() {
        LibrarianFx librarianFx = librarianTable.getSelectionModel().getSelectedItem();
        if (librarianFx != null) {
            if(!findTextField.getText().isEmpty()) {
                findTextField.clear();
                refreshTableLibrarian();
            }
            //refreshTableLibrarian();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/modify/ModifyLibrarian.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ModifyLibrarianController modifyLibrarianController = loader.getController();
            if (root != null) {
                Scene newScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modyfikacja bibliotekarza");
                stage.setScene(newScene);
                stage.initModality(Modality.APPLICATION_MODAL);
                modifyLibrarianController.setNewStage(stage);
                modifyLibrarianController.setLibrariansController(this);
                modifyLibrarianController.setLibrarianFx(librarianFx);
                stage.showAndWait();
            }

        }
    }

    @FXML
    public void deleteLibrarian() {
        LibrarianFx librarianFx = librarianTable.getSelectionModel().getSelectedItem();
        if (librarianFx != null) {
            Librarian librarian = convertToLibrarian(librarianFx);
            try {
                librarianDAO.deleteLibrarian(librarian);
                if (findTextField.getText().isEmpty())
                    refreshTableLibrarian();
                else
                    refreshTableWithPattern(findTextField.getText());
                DialogUtils.informationDialog("Bibliotekarz został usunięty");
            } catch (ApplicationException e) {
                DialogUtils.errorDialog("Nie usunięto bibliotekarza", e.getMessage());
            }
        }
    }

    @FXML
    public void findLibrarian() {
        String findText = findTextField.getText();
        if (!findText.isEmpty()) {
            refreshTableWithPattern(findText);
        } else {
            refreshTableLibrarian();
        }
    }

    @FXML
    private void initialize() {
        dbConnection = LibraryApplication.dbConnection;
        librarianDAO = new LibrarianDAO(dbConnection);
        bindingsLibrarianTableView();
        refreshTableLibrarian();
    }


    public void refreshTableLibrarian() {
        List<Librarian> librarianList = librarianDAO.getAllLibrarian();
        librarianObservableList.clear();
        librarianList.forEach(librarian -> {
            LibrarianFx librarianFx = convertToLibrarianFx(librarian);
            librarianObservableList.add(librarianFx);
        });
    }

    public void refreshTableWithPattern(String pattern) {
        List<Librarian> librarianList = librarianDAO.searchLibrarians(pattern);
        librarianObservableList.clear();
        librarianList.forEach(librarian -> {
            LibrarianFx librarianFx = convertToLibrarianFx(librarian);
            librarianObservableList.add(librarianFx);
        });
    }

    private void bindingsLibrarianTableView() {
        this.librarianTable.setItems(this.getLibrarianObservableList());
        this.firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.password.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        this.lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    private ObservableList<LibrarianFx> getLibrarianObservableList() {
        return librarianObservableList;
    }

}
