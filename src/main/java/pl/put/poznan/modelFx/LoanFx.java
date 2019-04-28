package pl.put.poznan.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Timestamp;

public class LoanFx {

    private Timestamp rentalDate;
    private Timestamp returnDate;
    private LibrarianFx librarianFx;
    private BookCopyFx bookCopyFx;
    private ReaderFx readerFx;

    private char ifReturn;
    private int librarianId;
    private int bookCopyId;
    private int readerId;

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public char getIfReturn() {
        return ifReturn;
    }

    public void setIfReturn(char ifReturn) {
        this.ifReturn = ifReturn;
    }

    public LibrarianFx getLibrarianFx() {
        return librarianFx;
    }

    public void setLibrarianFx(LibrarianFx librarianFx) {
        this.librarianFx = librarianFx;
    }

    public BookCopyFx getBookCopyFx() {
        return bookCopyFx;
    }

    public void setBookCopyFx(BookCopyFx bookCopyFx) {
        this.bookCopyFx = bookCopyFx;
    }

    public ReaderFx getReaderFx() {
        return readerFx;
    }

    public void setReaderFx(ReaderFx readerFx) {
        this.readerFx = readerFx;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}
//        dateTime.setTimestamp(LocalDateTime.now());
//        System.out.println(new Timestamp(new Date().getTime()).toString());
//        dateTime.setTimestamp(new Timestamp(new Date().getTime()));