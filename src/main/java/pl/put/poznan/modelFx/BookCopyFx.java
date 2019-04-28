package pl.put.poznan.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookCopyFx {

    private IntegerProperty bookCopyId = new SimpleIntegerProperty();

    private IntegerProperty howLong = new SimpleIntegerProperty();

    private StringProperty section = new SimpleStringProperty();


    private BookFx bookFx;

    public int getBookCopyId() {
        return bookCopyId.get();
    }

    public IntegerProperty bookCopyIdProperty() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId.set(bookCopyId);
    }

    public int getHowLong() {
        return howLong.get();
    }

    public IntegerProperty howLongProperty() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong.set(howLong);
    }

    public String getSection() {
        return section.get();
    }

    public StringProperty sectionProperty() {
        return section;
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public BookFx getBookFx() {
        return bookFx;
    }

    public void setBookFx(BookFx bookFx) {
        this.bookFx = bookFx;
    }

    @Override
    public String toString() {
        return "ID: " + getBookCopyId()+ ", tytuł: " + bookFx.getBookTitle(); // wywalic napisy jak nie ładnie
    }
}
