package pl.put.poznan.Controller.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import pl.put.poznan.Controller.GUIControllers.MainMenuController;

public class TopMenuController {
    public static final String LIBRARIANS_FXML = "/fxml/Librarians.fxml";
    public static final String READERS_FXML = "/fxml/Readers.fxml";
    public static final String AUTHORS_FXML = "/fxml/Authors.fxml";
    public static final String BOOKS_FXML = "/fxml/Books.fxml";
    public static final String BOOKCOPIES_FXML = "/fxml/BookCopies.fxml";
    public static final String LOANS_FXML = "/fxml/Loans.fxml";

    private MainMenuController mainMenuController;

    @FXML
    private ToggleButton menuBookCopies;
    @FXML
    private ToggleButton menuLoans;
    @FXML
    private ToggleButton menuBooks;
    @FXML
    private ToggleButton menuAuthors;
    @FXML
    private ToggleButton menuLibrarians;
    @FXML
    private ToggleButton menuReaders;


    @FXML
    private ToggleGroup toggleButtons;

    @FXML
    public void openReaders() {
        //System.out.println("openReaders");
        selectToogleButtons(menuReaders);
        mainMenuController.setMainPane(READERS_FXML);
    }

    @FXML
    public void openBookCopies() {
        //System.out.println("openBookCopies");
        selectToogleButtons(menuBookCopies);
        mainMenuController.setMainPane(BOOKCOPIES_FXML);
    }

    @FXML
    public void openLoans() {
        //System.out.println("openLoans");
        selectToogleButtons(menuLoans);
        mainMenuController.setMainPane(LOANS_FXML);
    }

    @FXML
    public void openBooks() {
        //System.out.println("openBooks");
        selectToogleButtons(menuBooks);
        //System.out.println("/n/n/n/n");
        mainMenuController.setMainPane(BOOKS_FXML);
    }

    @FXML
    public void openAuthors() {
        //System.out.println("openAuthors");
        selectToogleButtons(menuAuthors);
        mainMenuController.setMainPane(AUTHORS_FXML);
    }

    @FXML
    public void openLibrarians() {
        //System.out.println("openLibrarians");
        selectToogleButtons(menuLibrarians);
        mainMenuController.setMainPane(LIBRARIANS_FXML);
    }

    private void selectToogleButtons(ToggleButton toggleButton) {
        if(toggleButtons.getSelectedToggle()!=null)
            toggleButtons.getSelectedToggle().setSelected(false);   //zeby były zaznaczone po wciśnięciu
        toggleButton.setSelected(true);
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
}
