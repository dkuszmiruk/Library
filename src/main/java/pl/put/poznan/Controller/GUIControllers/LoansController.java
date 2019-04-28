package pl.put.poznan.Controller.GUIControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.put.poznan.Controller.GUIControllers.addControllers.AddLoanController;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.DAO.LoanDAO;
import pl.put.poznan.LibraryApplication;
import pl.put.poznan.model.Loan;
import pl.put.poznan.modelFx.BookCopyFx;
import pl.put.poznan.modelFx.LibrarianFx;
import pl.put.poznan.modelFx.LoanFx;
import pl.put.poznan.modelFx.ReaderFx;
import pl.put.poznan.utils.ApplicationException;
import pl.put.poznan.utils.DialogUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static pl.put.poznan.utils.converters.LoanConverter.convertToLoan;
import static pl.put.poznan.utils.converters.LoanConverter.convertToLoanFx;

public class LoansController {

    private DbConnection dbConnection;
    private LoanDAO loanDAO;
    private ObservableList<LoanFx> loanObservableList = FXCollections.observableArrayList();

    @FXML
    public CheckBox historyCheckBox;
    @FXML
    public TextField findTextField;
    @FXML
    public TableView<LoanFx> loanTable;
    @FXML
    public TableColumn<LoanFx,Timestamp> rentalDate;
    @FXML
    public TableColumn<LoanFx,ReaderFx> readerId;
    @FXML
    public TableColumn<LoanFx,BookCopyFx> bookCopyId;
    @FXML
    public TableColumn<LoanFx,LibrarianFx> librarianId;
    @FXML
    public TableColumn<LoanFx,Timestamp> returnDate;

    @FXML
    public void addLoan() {
        if(!findTextField.getText().isEmpty()) {
            findTextField.clear();
            refreshTableLoan('N');
        }
        if(historyCheckBox.isSelected()) {
            historyCheckBox.setSelected(false);
            refreshTableLoan('N');
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/adds/AddLoan.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(root!=null) {
            AddLoanController addLoanController = loader.getController();
            Scene newScene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Wypożyczanie");
            stage.setScene(newScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            addLoanController.setNewStage(stage);
            addLoanController.setLoansController(this);
            stage.showAndWait();
        }
    }

    @FXML
    public void modifyLoan() {
        LoanFx loanFx = loanTable.getSelectionModel().getSelectedItem();
        if(!historyCheckBox.isSelected()) {
            if (loanFx != null) {
                if (!findTextField.getText().isEmpty()) {
                    findTextField.clear();
                    refreshTableLoan('N');
                }
                Timestamp timestamp = loanFx.getReturnDate();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp.getTime());
                cal.add(Calendar.DAY_OF_MONTH, loanFx.getBookCopyFx().getHowLong());

                try {
                    loanDAO.modifyLoan(convertToLoan(loanFx),new Timestamp(cal.getTime().getTime()));
                    refreshTableLoan('N');
                    DialogUtils.informationDialog("Przedłużono wypożyczenie o " + loanFx.getBookCopyFx().getHowLong() + " dni");
                } catch (ApplicationException e) {
                    DialogUtils.errorDialog("Nie udało się przedłużyć wypożyczenia", e.getMessage());

                }
            }
        }
    }

    @FXML
    public void returnBook() {
        if (!historyCheckBox.isSelected()) {
            LoanFx loanFx = loanTable.getSelectionModel().getSelectedItem();
            if (loanFx != null) {
                try {
                    loanDAO.callProcedureReturnBooks(loanFx.getBookCopyId());
                    //historyCheckBox.setSelected(true);
                    refreshTableLoan('N');
                    DialogUtils.informationDialog("Książka została zwrócona");
                } catch (ApplicationException e) {
                    DialogUtils.errorDialog("Nie zwrócono książki", e.getMessage());
                }
            }
        }
    }

    @FXML
    public void deleteLoan() {
        LoanFx loanFx = loanTable.getSelectionModel().getSelectedItem();
        if (loanFx != null) {
            Loan loan = convertToLoan(loanFx);
            try {
                loanDAO.deleteLoan(loan);
                if(historyCheckBox.isSelected())
                    refreshTableLoan('T');
                else
                    refreshTableLoan('N');
                DialogUtils.informationDialog("Wpożyczenie zostało usunięte");
            } catch (ApplicationException e) {
                DialogUtils.errorDialog("Nie usunięto wypożyczenia", e.getMessage());
            }
        }
    }

    @FXML
    public void findLoans() {
        String findText = findTextField.getText();
        if (!findText.isEmpty()) {
            if(historyCheckBox.isSelected())
                refreshTableWithPattern(findText,'T');
            else
                refreshTableWithPattern(findText,'N');
        } else {
            if(historyCheckBox.isSelected())
                refreshTableLoan('T');
            else
                refreshTableLoan('N');
        }
    }

    @FXML
    public void historyAction() {
        findTextField.clear();
        if(historyCheckBox.isSelected())
            refreshTableLoan('T');
        else
            refreshTableLoan('N');
    }

    @FXML
    private void initialize(){
        dbConnection = LibraryApplication.dbConnection;
        loanDAO = new LoanDAO(dbConnection);
        bindingsLoanTableView();
        refreshTableLoan('N');
    }

    public void refreshTableLoan(char returnVal) {
        List<Loan> loanList = loanDAO.getAllLoan(returnVal);
        loanObservableList.clear();
        loanList.forEach(loan -> {
            LoanFx loanFx = convertToLoanFx(loan);
            loanObservableList.add(loanFx);
        });
    }

    private void refreshTableWithPattern(String pattern, char returnVal) {
        List<Loan> loanList = loanDAO.searchLoans(pattern,returnVal);
        loanObservableList.clear();
        loanList.forEach(loan -> {
            LoanFx loanFx = convertToLoanFx(loan);
            loanObservableList.add(loanFx);
        });
    }


    private void bindingsLoanTableView() {
        this.loanTable.setItems(this.getLoanObservableList());
        this.rentalDate.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
        this.readerId.setCellValueFactory(new PropertyValueFactory<>("readerFx"));
        this.bookCopyId.setCellValueFactory(new PropertyValueFactory<>("bookCopyFx"));
        this.librarianId.setCellValueFactory(new PropertyValueFactory<>("librarianFx"));
        this.returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }


    private ObservableList<LoanFx> getLoanObservableList() {
        return loanObservableList;
    }


}
