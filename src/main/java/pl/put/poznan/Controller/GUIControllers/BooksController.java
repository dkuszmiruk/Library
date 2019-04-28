package pl.put.poznan.Controller.GUIControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.addControllers.AddBookController;
import pl.put.poznan.Controller.GUIControllers.modifyControllers.ModifyBookController;
import pl.put.poznan.DAO.BookDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Book;
import pl.put.poznan.modelFx.AuthorFx;
import pl.put.poznan.modelFx.BookFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.util.List;

import static pl.put.poznan.utils.converters.BookConverter.convertToBook;
import static pl.put.poznan.utils.converters.BookConverter.convertToBookFx;

public class BooksController {

    private DbConnection dbConnection;
    private BookDAO bookDAO;

    private ObservableList<BookFx> bookObservableList = FXCollections.observableArrayList();

    @FXML
    public TextField findTextField;
    @FXML
    public TableView<BookFx> bookTable;

    @FXML
    public TableColumn<BookFx,String> isbn;

    @FXML
    public TableColumn<BookFx,String> title;

    @FXML
    public TableColumn<BookFx,String> bookType;

    @FXML
    public TableColumn<BookFx,Number> issueNumber;

    @FXML
    public TableColumn<BookFx,AuthorFx> bookAuthor;

    @FXML
    public TableColumn<BookFx,Number> issueYear;

    @FXML
    public TableColumn<BookFx,String> bookLanguage;

    @FXML
    public TableColumn<BookFx,String> namePublishingHouse;

    @FXML
    public TableColumn<BookFx,Number> bookCopyAmount;

    @FXML
    public TableColumn<BookFx,String> bookDescription;

    @FXML
    public void addBook() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableBook();
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddBook.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(root!=null) {
            AddBookController addBookController = loader.getController();
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie książki");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addBookController.setNewStage(stage);
            addBookController.setBooksController(this);
            stage.showAndWait();
        }
        //refreshTableReader();
    }

    @FXML
    public void modifyBook() {
        BookFx bookFx = bookTable.getSelectionModel().getSelectedItem();
        if (bookFx != null) {
            if(!findTextField.getText().isEmpty()) {
                findTextField.clear();
                refreshTableBook();
            }
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/modify/ModifyBook.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(root!=null) {
                ModifyBookController modifyBookController = loader.getController();

                Scene newScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modyfikacja książki");
                stage.setScene(newScene);
                stage.initModality(Modality.APPLICATION_MODAL);
                modifyBookController.setNewStage(stage);
                modifyBookController.setBooksController(this);
                modifyBookController.setBookFx(bookFx);
                stage.showAndWait();
            }

        }
    }

    @FXML
    public void deleteBook() {
        BookFx bookFx = bookTable.getSelectionModel().getSelectedItem();
        if (bookFx != null) {
            Book book = convertToBook(bookFx);
            try {
                bookDAO.deleteBook(book);
                if(findTextField.getText().isEmpty())
                    refreshTableBook();
                else
                    refreshTableWithPattern(findTextField.getText());
                DialogUtils.informationDialog("Książka została usunięta");
            } catch (ApplicationException e) {
                DialogUtils.errorDialog("Nie usunięto książki", e.getMessage());
            }

        }
    }

    @FXML
    public void findBooks() {
        String findText = findTextField.getText();
        if(!findText.isEmpty()){
            refreshTableWithPattern(findText);
        }else{
            refreshTableBook();
        }
    }


    @FXML
    private void initialize(){
        bookTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                    DialogUtils.descriptionDialog("Opis ksiazki",bookTable.getSelectionModel().getSelectedItem().getBookTitle(),bookTable.getSelectionModel().getSelectedItem().getBookDescription());
                }
            }
        });
        dbConnection = LibraryApplication.dbConnection;
        bookDAO = new BookDAO(dbConnection);
        bindingsReaderTableView();
        refreshTableBook();
    }

    public void refreshTableBook() {
        List<Book> bookList = bookDAO.getAllBook();
        bookObservableList.clear();
        bookList.forEach(book->{
            BookFx bookFx = convertToBookFx(book);
            bookFx.setBookCopyAmount(bookDAO.availableBookCounterCallFunction(bookFx.getiSBN()));
            bookObservableList.add(bookFx);
        });
    }

    private void refreshTableWithPattern(String pattern) {
        List<Book> bookList = bookDAO.searchBooks(pattern);
        bookObservableList.clear();
        bookList.forEach(book -> {
            BookFx bookFx = convertToBookFx(book);
            bookFx.setBookCopyAmount(bookDAO.availableBookCounterCallFunction(bookFx.getiSBN()));
            bookObservableList.add(bookFx);
        });
    }


    private void bindingsReaderTableView() {
        this.bookTable.setItems(this.getBookObservableList());
        this.isbn.setCellValueFactory(cellData -> cellData.getValue().iSBNProperty());
        this.title.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        this.bookType.setCellValueFactory(cellData -> cellData.getValue().bookTypeProperty());
        this.issueNumber.setCellValueFactory(cellData -> cellData.getValue().issueNumberProperty());
        this.issueYear.setCellValueFactory(cellData -> cellData.getValue().issueYearProperty());
        this.bookLanguage.setCellValueFactory(cellData -> cellData.getValue().bookLanguageProperty());
        this.namePublishingHouse.setCellValueFactory(cellData -> cellData.getValue().namePublishingHouseProperty());
        this.bookCopyAmount.setCellValueFactory(cellData -> cellData.getValue().bookCopyAmountProperty());
        this.bookDescription.setCellValueFactory(cellData -> cellData.getValue().bookDescriptionProperty());
        this.bookAuthor.setCellValueFactory(new PropertyValueFactory<>("authorFx"));
    }


    public ObservableList<BookFx> getBookObservableList() {
        return bookObservableList;
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