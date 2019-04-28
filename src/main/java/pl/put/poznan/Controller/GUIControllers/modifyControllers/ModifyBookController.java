package pl.put.poznan.Controller.GUIControllers.modifyControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.BooksController;
import pl.put.poznan.DAO.AuthorDAO;
import pl.put.poznan.DAO.BookDAO;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.modelFx.AuthorFx;
import pl.put.poznan.modelFx.BookFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthorFx;
import static pl.put.poznan.utils.converters.BookConverter.convertToBook;

public class ModifyBookController {

    private Stage newStage;
    private DbConnection dbConnection;
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private BooksController booksController;
    private BookFx bookFx;
    private ObservableList<AuthorFx> authorObservableList = FXCollections.observableArrayList();
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>();
    @FXML
    public TextField titleField;
    @FXML
    public TextField bookTypeField;
    @FXML
    public TextField issueNumberField;
    @FXML
    public TextField issueYearField;
    @FXML
    public TextField bookLanguageField;
    @FXML
    public TextField namePublishingHouseField;
    @FXML
    public ComboBox<AuthorFx> authorComboBox;
    @FXML
    public TextArea bookDescriptionField;
    @FXML
    public Button modifyBookButton;

    @FXML
    public void modifyBook() {
        bookFx.setBookTitle(titleField.getText());
        bookFx.setBookType(bookTypeField.getText());
        bookFx.setIssueNumber(Integer.parseInt(issueNumberField.getText()));
        bookFx.setIssueYear(Integer.parseInt(issueYearField.getText()));
        bookFx.setBookLanguage(bookLanguageField.getText());
        bookFx.setNamePublishingHouse(namePublishingHouseField.getText());
        bookFx.setBookDescription(bookDescriptionField.getText());
        bookFx.setAuthorFx(getAuthorFxObjectProperty());
        try {
            bookDAO.modifyBook(convertToBook(bookFx));
            newStage.hide();
            booksController.refreshTableBook();
            DialogUtils.informationDialog("Książka została zmodyfikowana");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się zmodyfikować książki", e.getMessage());
        }
    }

    @FXML
    public void closeAddBook() {
        newStage.close();
    }

    @FXML
    private void initialize() {
        dbConnection = LibraryApplication.dbConnection;
        bookDAO = new BookDAO(dbConnection);
        authorDAO = new AuthorDAO(dbConnection);
        setTextFieldsProperty();
        modifyBookButton.disableProperty().bind(titleField.textProperty().isEmpty().or(bookTypeField.textProperty().isEmpty()).or(issueNumberField.textProperty().isEmpty()).or(issueYearField.textProperty().isEmpty()).or(bookLanguageField.textProperty().isEmpty()).or(namePublishingHouseField.textProperty().isEmpty()).or(authorFxObjectProperty.isNull()));
        setComboBox();
    }

    @FXML
    public void onAuthorComboBox() {
        this.setAuthorFxObjectProperty(this.authorComboBox.getSelectionModel().getSelectedItem());
    }

    private void setComboBox() {
        authorObservableList.clear();
        authorDAO.getAllAuthor().forEach(author -> {
            authorObservableList.add(convertToAuthorFx(author));
        });
        this.authorComboBox.setItems(authorObservableList);
    }

    private void setFields() {
        titleField.setText(bookFx.getBookTitle());
        bookTypeField.setText(bookFx.getBookType());
        issueNumberField.setText(String.valueOf(bookFx.getIssueNumber()));
        issueYearField.setText(String.valueOf(bookFx.getIssueYear()));
        bookLanguageField.setText(bookFx.getBookLanguage());
        namePublishingHouseField.setText(bookFx.getNamePublishingHouse());
        bookDescriptionField.setText(bookFx.getBookDescription());

        authorComboBox.getSelectionModel().select(bookFx.getAuthorFx());
        onAuthorComboBox();
    }

    public void setBookFx(BookFx bookFx) {
        this.bookFx = bookFx;
        setFields();
    }

    private void setTextFieldsProperty() {
        titleField.setOnKeyTyped(event -> {
            String string = titleField.getText();
            if (string.length() > 250) {
                titleField.setText(string.substring(0, 250));
                titleField.positionCaret(string.length());
            }
        });

        bookTypeField.setOnKeyTyped(event -> {
            String string = bookTypeField.getText();
            if (string.length() > 250) {
                bookTypeField.setText(string.substring(0, 250));
                bookTypeField.positionCaret(string.length());
            }
        });

        issueNumberField.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    issueNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        issueNumberField.setOnKeyTyped(event -> {
            String string = issueNumberField.getText();
            if (string.length() > 10) {
                issueNumberField.setText(string.substring(0, 10));
                issueNumberField.positionCaret(string.length());
            }
        });

        issueYearField.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    issueYearField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        issueYearField.setOnKeyTyped(event -> {
            String string = issueYearField.getText();
            if (string.length() > 10) {
                issueYearField.setText(string.substring(0, 10));
                issueYearField.positionCaret(string.length());
            }
        });

        bookLanguageField.setOnKeyTyped(event -> {
            String string = bookLanguageField.getText();
            if (string.length() > 30) {
                bookLanguageField.setText(string.substring(0, 30));
                bookLanguageField.positionCaret(string.length());
            }
        });

        namePublishingHouseField.setOnKeyTyped(event -> {
            String string = namePublishingHouseField.getText();
            if (string.length() > 250) {
                namePublishingHouseField.setText(string.substring(0, 250));
                namePublishingHouseField.positionCaret(string.length());
            }
        });

        bookDescriptionField.setOnKeyTyped(event -> {
            String string = bookDescriptionField.getText();
            if (string.length() > 3000) {
                bookDescriptionField.setText(string.substring(0, 3000));
                bookDescriptionField.positionCaret(string.length());
            }
        });

    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setBooksController(BooksController booksController) {
        this.booksController = booksController;
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }
}
