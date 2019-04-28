package pl.put.poznan.Controller.GUIControllers.addControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.LoansController;
import pl.put.poznan.DAO.*;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Loan;
import pl.put.poznan.model.LoanPk;
import pl.put.poznan.modelFx.BookCopyFx;
import pl.put.poznan.modelFx.LibrarianFx;
import pl.put.poznan.modelFx.ReaderFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopy;
import static pl.put.poznan.utils.converters.BookCopyConverter.convertToBookCopyFx;
import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarian;
import static pl.put.poznan.utils.converters.LibrarianConverter.convertToLibrarianFx;
import static pl.put.poznan.utils.converters.ReaderConverter.convertToReader;
import static pl.put.poznan.utils.converters.ReaderConverter.convertToReaderFx;

public class AddLoanController {

    private Stage newStage;
    private DbConnection dbConnection;
    private LoanDAO loanDAO;
    private BookCopyDAO bookCopyDAO;
    private LibrarianDAO librarianDAO;
    private ReaderDAO readerDAO;
    private LoansController loansController;
    private ObservableList<BookCopyFx> bookCopyFxObservableList = FXCollections.observableArrayList();
    private ObjectProperty<BookCopyFx> bookCopyFxObjectProperty = new SimpleObjectProperty<>();
    private ObservableList<ReaderFx> readerFxObservableList  = FXCollections.observableArrayList();
    private ObjectProperty<ReaderFx> readerFxObjectProperty  = new SimpleObjectProperty<>();
    private ObservableList<LibrarianFx> librarianFxObservableList  = FXCollections.observableArrayList();
    private ObjectProperty<LibrarianFx> librarianFxObjectProperty = new SimpleObjectProperty<>();

    @FXML
    public ComboBox<BookCopyFx> bookCopyComboBox;
    @FXML
    public ComboBox<ReaderFx> readerComboBox;
    @FXML
    public ComboBox<LibrarianFx> librarianComboBox;
    @FXML
    public Button addLoanButton;

    @FXML
    public void addLoan() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DAY_OF_MONTH, getBookCopyFxObjectProperty().getHowLong());

        Loan loan = new Loan();
        loan.setReturnDate(new Timestamp(cal.getTime().getTime())); //+ liczba dnia na ile wypozyczany egzemplarz
        loan.setReader(convertToReader(getReaderFxObjectProperty()));
        loan.setLibrarian(convertToLibrarian(getLibrarianFxObjectProperty()));
        loan.setBookCopy(convertToBookCopy(getBookCopyFxObjectProperty()));
        loan.setIfReturn('N');
        loan.setLoanPk(new LoanPk(timestamp,getLibrarianFxObjectProperty().getLibrarianId(),getBookCopyFxObjectProperty().getBookCopyId(),getReaderFxObjectProperty().getReaderId()));

        try {
            loanDAO.addLoan(loan);
            newStage.hide();
            loansController.refreshTableLoan('N');
            DialogUtils.informationDialog("Dokonano wypożyczenia");
            newStage.close();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog("Nie udało się dokonać wypożyczenia", e.getMessage());
        }
    }

    @FXML
    public void closeAddLoan() {
        newStage.close();
    }

    @FXML
    public void onLibrarianComboBox() {
        this.setLibrarianFxObjectProperty(this.librarianComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onReaderComboBox() {
        this.setReaderFxObjectProperty(this.readerComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onBookCopyComboBox() {
        this.setBookCopyFxObjectProperty(this.bookCopyComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        loanDAO = new LoanDAO(dbConnection);
        bookCopyDAO = new BookCopyDAO(dbConnection);
        readerDAO = new ReaderDAO(dbConnection);
        librarianDAO = new LibrarianDAO(dbConnection);
        addLoanButton.disableProperty().bind(readerFxObjectProperty.isNull().or(librarianFxObjectProperty.isNull()).or(bookCopyFxObjectProperty.isNull()));
        setComboBox();
    }

    private void setComboBox() {
        bookCopyFxObservableList.clear();
        EntityManager entityManager = dbConnection.activeEntityManager();
        bookCopyDAO.getBookCopy(entityManager).forEach(bookCopy -> {
            BookCopyFx bookCopyFx = convertToBookCopyFx(bookCopy);
            Boolean pom = true;
            for(Loan loan : bookCopy.getLoans()) {
                if (loan.getIfReturn() == 'N')
                    pom = false;
            }
            if(pom){
            bookCopyFxObservableList.add(bookCopyFx);}
        });
        dbConnection.disactiveEntityManager(entityManager);
        this.bookCopyComboBox.setItems(bookCopyFxObservableList);
        librarianFxObservableList.clear();
        librarianDAO.getAllLibrarian().forEach(librarian -> librarianFxObservableList.add(convertToLibrarianFx(librarian)));
        this.librarianComboBox.setItems(librarianFxObservableList);

        readerFxObservableList.clear();
        readerDAO.getAllReader().forEach(reader -> readerFxObservableList.add(convertToReaderFx(reader)));
        this.readerComboBox.setItems(readerFxObservableList);
    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

    public void setLoansController(LoansController loansController) {
        this.loansController = loansController;
    }

    private void setBookCopyFxObjectProperty(BookCopyFx bookCopyFxObjectProperty) {
        this.bookCopyFxObjectProperty.set(bookCopyFxObjectProperty);
    }

    private void setReaderFxObjectProperty(ReaderFx readerFxObjectProperty) {
        this.readerFxObjectProperty.set(readerFxObjectProperty);
    }

    private void setLibrarianFxObjectProperty(LibrarianFx librarianFxObjectProperty) {
        this.librarianFxObjectProperty.set(librarianFxObjectProperty);
    }

    public BookCopyFx getBookCopyFxObjectProperty() {
        return bookCopyFxObjectProperty.get();
    }

    public ObjectProperty<BookCopyFx> bookCopyFxObjectPropertyProperty() {
        return bookCopyFxObjectProperty;
    }

    public ReaderFx getReaderFxObjectProperty() {
        return readerFxObjectProperty.get();
    }

    public ObjectProperty<ReaderFx> readerFxObjectPropertyProperty() {
        return readerFxObjectProperty;
    }

    public LibrarianFx getLibrarianFxObjectProperty() {
        return librarianFxObjectProperty.get();
    }

    public ObjectProperty<LibrarianFx> librarianFxObjectPropertyProperty() {
        return librarianFxObjectProperty;
    }
}
