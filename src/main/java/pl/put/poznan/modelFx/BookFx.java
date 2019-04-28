package pl.put.poznan.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookFx {

    private StringProperty iSBN = new SimpleStringProperty();

    private StringProperty bookTitle = new SimpleStringProperty();

    private StringProperty bookType = new SimpleStringProperty();

    private IntegerProperty issueNumber = new SimpleIntegerProperty();

    private IntegerProperty issueYear = new SimpleIntegerProperty();

    private StringProperty bookLanguage = new SimpleStringProperty();

    private StringProperty namePublishingHouse = new SimpleStringProperty();

    private StringProperty bookDescription = new SimpleStringProperty();

    private IntegerProperty bookCopyAmount = new SimpleIntegerProperty();

    private AuthorFx authorFx;

    public String getiSBN() {
        return iSBN.get();
    }

    public StringProperty iSBNProperty() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN.set(iSBN);
    }

    public String getBookTitle() {
        return bookTitle.get();
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public String getBookType() {
        return bookType.get();
    }

    public StringProperty bookTypeProperty() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType.set(bookType);
    }

    public int getIssueNumber() {
        return issueNumber.get();
    }

    public IntegerProperty issueNumberProperty() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber.set(issueNumber);
    }

    public int getIssueYear() {
        return issueYear.get();
    }

    public IntegerProperty issueYearProperty() {
        return issueYear;
    }

    public void setIssueYear(int issueYear) {
        this.issueYear.set(issueYear);
    }

    public String getBookLanguage() {
        return bookLanguage.get();
    }

    public StringProperty bookLanguageProperty() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage.set(bookLanguage);
    }

    public String getNamePublishingHouse() {
        return namePublishingHouse.get();
    }

    public StringProperty namePublishingHouseProperty() {
        return namePublishingHouse;
    }

    public void setNamePublishingHouse(String namePublishingHouse) {
        this.namePublishingHouse.set(namePublishingHouse);
    }

    public String getBookDescription() {
        return bookDescription.get();
    }

    public StringProperty bookDescriptionProperty() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription.set(bookDescription);
    }

    public int getBookCopyAmount() {
        return bookCopyAmount.get();
    }

    public IntegerProperty bookCopyAmountProperty() {
        return bookCopyAmount;
    }

    public void setBookCopyAmount(int bookCopyAmount) {
        this.bookCopyAmount.set(bookCopyAmount);
    }

    public AuthorFx getAuthorFx() {
        return authorFx;
    }

    public void setAuthorFx(AuthorFx authorFx) {
        this.authorFx = authorFx;
    }

    @Override
    public String toString() {
        return "ISBN: " + getiSBN() + "    tytuł: " + getBookTitle(); //jak nie wyjdzie to usunąć napisy przed arg
    }
}
