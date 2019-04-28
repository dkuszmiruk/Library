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
import pl.put.poznan.Controller.GUIControllers.addControllers.AddAuthorController;
import pl.put.poznan.Controller.GUIControllers.modifyControllers.ModifyAuthorController;
import pl.put.poznan.DAO.AuthorDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Author;
import pl.put.poznan.modelFx.AuthorFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.util.List;

import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthor;
import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthorFx;

public class AuthorsController {

    private DbConnection dbConnection;
    private AuthorDAO authorDAO;

    private ObservableList<AuthorFx> authorObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField findTextField;
    @FXML
    public TableView<AuthorFx> authorTable;
    @FXML
    public TableColumn<AuthorFx,String> firstName;
    @FXML
    public TableColumn<AuthorFx,String> lastName;

    @FXML
    public void addAuthor() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableAuthor();
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddAuthor.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddAuthorController addAuthorController = loader.getController();
        if (root != null) {
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie autora");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addAuthorController.setNewStage(stage);
            addAuthorController.setAuthorsController(this);
            stage.showAndWait();
        }
    }

    @FXML
    public void modifyAuthor() {
        AuthorFx authorFx = authorTable.getSelectionModel().getSelectedItem();
        if (authorFx != null) {
            if (!findTextField.getText().isEmpty()) {
                findTextField.clear();
                refreshTableAuthor();
            }
            //refreshTableAuthor();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/modify/ModifyAuthor.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ModifyAuthorController modifyAuthorController = loader.getController();
            if (root != null) {
                Scene newScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modyfikacja autora");
                stage.setScene(newScene);
                stage.initModality(Modality.APPLICATION_MODAL);
                modifyAuthorController.setNewStage(stage);
                modifyAuthorController.setAuthorsController(this);
                modifyAuthorController.setAuthorFx(authorFx);
                stage.showAndWait();
            }
        }
    }

    @FXML
    public void deleteAuthor() {
        AuthorFx authorFx = authorTable.getSelectionModel().getSelectedItem();
        if (authorFx != null) {
            Author author = convertToAuthor(authorFx);
            try {
                authorDAO.deleteAuthor(author);
                if (findTextField.getText().isEmpty())
                refreshTableAuthor();
                else
                    refreshTableWithPattern(findTextField.getText());
                DialogUtils.informationDialog("Autor został usunięty");
            } catch( ApplicationException e){
                DialogUtils.errorDialog("Nie usunięto autora",e.getMessage());
            }
        }
    }

    @FXML
    public void findAuthor() {
        String findText = findTextField.getText();
        if (!findText.isEmpty()) {
            refreshTableWithPattern(findText);
        } else {
            refreshTableAuthor();
        }
    }

    @FXML
    public void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        authorDAO = new AuthorDAO(dbConnection);
        bindingsAuthorTableView();
        refreshTableAuthor();
    }

    public void refreshTableAuthor() {
        List<Author> authorList = authorDAO.getAllAuthor();
        authorObservableList.clear();
        authorList.forEach(author->{
            AuthorFx authorFx = convertToAuthorFx(author);
            authorObservableList.add(authorFx);
        });
    }

    public void refreshTableWithPattern(String pattern) {
        List<Author> authorList = authorDAO.searchAuthors(pattern);
        authorObservableList.clear();
        authorList.forEach(author -> {
            AuthorFx authorFx = convertToAuthorFx(author);
            authorObservableList.add(authorFx);
        });
    }

    private void bindingsAuthorTableView() {
        this.authorTable.setItems(this.getAuthorObservableList());
        this.firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    }

    public ObservableList<AuthorFx> getAuthorObservableList() {
        return authorObservableList;
    }

}
