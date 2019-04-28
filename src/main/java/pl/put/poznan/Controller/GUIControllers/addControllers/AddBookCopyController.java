package pl.put.poznan.Controller.GUIControllers.addControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.BookCopiesController;
import pl.put.poznan.DAO.BookCopyDAO;
import pl.put.poznan.DAO.BookDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.BookCopy;
import pl.put.poznan.modelFx.BookFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import static pl.put.poznan.utils.converters.BookConverter.convertToBook;
import static pl.put.poznan.utils.converters.BookConverter.convertToBookFx;

public class AddBookCopyController {

    private Stage newStage;
    private DbConnection dbConnection;
    private BookCopyDAO bookCopyDAO;
    private BookDAO bookDAO;
    private BookCopiesController bookCopiesController;
    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();
    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>();
    @FXML
    public ComboBox<BookFx> bookComboBox;
    @FXML
    public TextField howLongField;
    @FXML
    public TextField sectionField;
    @FXML
    public Button addBookCopyButton;

    @FXML
    public void addBookCopy() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setHowLong(Integer.parseInt(howLongField.getText()));
        bookCopy.setSection(sectionField.getText());
        bookCopy.setBook(convertToBook(getBookFxObjectProperty()));
        try {
            bookCopyDAO.addBookCopy(bookCopy);
            newStage.hide();
            bookCopiesController.refreshTableBookCopy();
            DialogUtils.informationDialog("Egzemplarz został dodany");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się dodać egzemplarza", e.getMessage());
        }
    }

    @FXML
    public void closeAddBookCopy() {
        newStage.close();
    }

    @FXML
    public void onBookComboBox() {
        this.setBookFxObjectProperty(this.bookComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void initialize() {
        dbConnection = LibraryApplication.dbConnection;
        bookDAO = new BookDAO(dbConnection);
        bookCopyDAO = new BookCopyDAO(dbConnection);
        setTextFieldsProperty();
        setComboBox();
        addBookCopyButton.disableProperty().bind(howLongField.textProperty().isEmpty().or(sectionField.textProperty().isEmpty()).or(bookFxObjectProperty.isNull()));
    }

    private void setComboBox() {
        bookFxObservableList.clear();
        bookDAO.getAllBook().forEach(book -> bookFxObservableList.add(convertToBookFx(book)));
        this.bookComboBox.setItems(bookFxObservableList);
    }

    private void setTextFieldsProperty() {
        howLongField.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    howLongField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        howLongField.setOnKeyTyped(event -> {
            String string = howLongField.getText();
            if (string.length() > 10) {
                howLongField.setText(string.substring(0, 10));
                howLongField.positionCaret(string.length());
            }
        });

        sectionField.setOnKeyTyped(event -> {
            String string = sectionField.getText();
            if (string.length() > 3) {
                sectionField.setText(string.substring(0, 3));
                sectionField.positionCaret(string.length());
            }
        });


    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setBookCopiesController(BookCopiesController bookCopiesController) {
        this.bookCopiesController = bookCopiesController;
    }

    private void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    private BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }
}
