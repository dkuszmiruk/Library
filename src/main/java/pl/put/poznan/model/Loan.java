package pl.put.poznan.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "wypozyczenia", indexes = {@Index(columnList = "id_bibliotekarza", name = "id_bibliotekarza"), @Index(columnList = "id_egzemplarza", name = "id_egzemplarza"), @Index(columnList = "id_czytelnika", name = "id_czytelnika")})
public class Loan {

    @EmbeddedId
    private  LoanPk loanPk;

    @Column(name = "data_zwrotu", nullable = false)
    private Timestamp returnDate;

    @Column(name = "czy_zwrocone", nullable = false)
    private char ifReturn;

    @MapsId("librarianId")
    @ManyToOne
    @JoinColumn(name="id_bibliotekarza",referencedColumnName="id_bibliotekarza",foreignKey = @ForeignKey(name = "wypozyczenia_ibfk_1"))
    private Librarian librarian;

    @MapsId("bookCopyId")
    @ManyToOne
    @JoinColumn(name="id_egzemplarza",referencedColumnName="id_egzemplarza",foreignKey = @ForeignKey(name = "wypozyczenia_ibfk_2"))
    private BookCopy bookCopy;

    @MapsId("readerId")
    @ManyToOne
    @JoinColumn(name = "id_czytelnika",referencedColumnName="id_czytelnika",foreignKey = @ForeignKey(name = "wypozyczenia_ibfk_3"))
    private Reader reader;

    public LoanPk getLoanPk() {
        return loanPk;
    }

    public void setLoanPk(LoanPk loanPk) {
        this.loanPk = loanPk;
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

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
