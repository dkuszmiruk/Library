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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.addControllers.AddBookCopyController;
import pl.put.poznan.Controller.GUIControllers.modifyControllers.ModifyBookCopyController;
import pl.put.poznan.DAO.BookCopyDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.BookCopy;
import pl.put.poznan.modelFx.BookCopyFx;
import pl.put.poznan.modelFx.BookFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.util.List;

import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopy;
import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopyFx;

public class BookCopiesController {


    private DbConnection dbConnection;
    private BookCopyDAO bookCopyDAO;

    private ObservableList<BookCopyFx> bookCopyObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField findTextField;
    @FXML
    public TableView<BookCopyFx> bookCopiesTable;
    @FXML
    public TableColumn<BookCopyFx,Number> bookCopyId;
    @FXML
    public TableColumn<BookCopyFx,String> section;
    @FXML
    public TableColumn<BookCopyFx,BookFx> book;
    @FXML
    public TableColumn<BookCopyFx,Number> howLong;

    @FXML
    public void addBookCopy() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableBookCopy();
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddBookCopy.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(root!=null) {
            AddBookCopyController addBookCopyController = loader.getController();
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie egzemplarza");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addBookCopyController.setNewStage(stage);
            addBookCopyController.setBookCopiesController(this);
            stage.showAndWait();
        }
    }

    @FXML
    public void modifyBookCopy() {
        BookCopyFx bookCopyFx = bookCopiesTable.getSelectionModel().getSelectedItem();
        if (bookCopyFx != null) {
            if(!findTextField.getText().isEmpty()) {
                findTextField.clear();
                refreshTableBookCopy();
            }
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/modify/ModifyBookCopy.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(root!=null) {
                ModifyBookCopyController modifyBookCopyController = loader.getController();

                Scene newScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modyfikacja egzemplarza");
                stage.setScene(newScene);
                stage.initModality(Modality.APPLICATION_MODAL);
                modifyBookCopyController.setNewStage(stage);
                modifyBookCopyController.setBookCopiesController(this);
                modifyBookCopyController.setBookCopyFx(bookCopyFx);
                stage.showAndWait();
            }

        }
    }

    @FXML
    public void deleteBookCopy() {
        BookCopyFx bookCopyFx = bookCopiesTable.getSelectionModel().getSelectedItem();
        if (bookCopyFx != null) {
            BookCopy bookCopy = convertToBookCopy(bookCopyFx);
            try {
                bookCopyDAO.deleteBookCopy(bookCopy);
                if(findTextField.getText().isEmpty())
                    refreshTableBookCopy();
                else
                    refreshTableWithPattern(findTextField.getText());
                DialogUtils.informationDialog("Egzemplarz został usunięty");
            } catch (ApplicationException e) {
                DialogUtils.errorDialog("Nie usunięto egzemplarza", e.getMessage());
            }

        }
    }

    @FXML
    public void findBookCopies() {
        String findText = findTextField.getText();
        if(!findText.isEmpty()){
            refreshTableWithPattern(findText);
        }else{
            refreshTableBookCopy();
        }
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        bookCopyDAO = new BookCopyDAO(dbConnection);
        bindingsReaderTableView();
        refreshTableBookCopy();

    }

    public void refreshTableBookCopy() {
        List<BookCopy> bookCopyList = bookCopyDAO.getAllBookCopy();
        bookCopyObservableList.clear();
        bookCopyList.forEach(bookCopy -> {
            BookCopyFx bookCopyFx = convertToBookCopyFx(bookCopy);
            bookCopyObservableList.add(bookCopyFx);
        });
    }

    private void refreshTableWithPattern(String pattern) {
        List<BookCopy> bookCopyList = bookCopyDAO.searchBookCopies(pattern);
        bookCopyObservableList.clear();
        bookCopyList.forEach(bookCopy -> {
            BookCopyFx bookCopyFx = convertToBookCopyFx(bookCopy);
            bookCopyObservableList.add(bookCopyFx);
        });
    }

    private void bindingsReaderTableView() {
        this.bookCopiesTable.setItems(this.getBookCopyObservableList());
        this.bookCopyId.setCellValueFactory(cellData -> cellData.getValue().bookCopyIdProperty());
        this.section.setCellValueFactory(cellData -> cellData.getValue().sectionProperty());
        this.howLong.setCellValueFactory(cellData -> cellData.getValue().howLongProperty());
        this.book.setCellValueFactory(new PropertyValueFactory<>("bookFx"));
    }

    public ObservableList<BookCopyFx> getBookCopyObservableList() {
        return bookCopyObservableList;
    }

}
