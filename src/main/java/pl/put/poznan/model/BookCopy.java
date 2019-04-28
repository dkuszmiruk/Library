package pl.put.poznan.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "egzemplarze",indexes = @Index(columnList = "isbn", name = "isbn"))
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_egzemplarza")
    private int bookCopyId;

    @Column(name = "na_ile_wypozyczany", nullable = false)
    private int howLong;

    @Column(name = "dzial", length = 3, nullable = false)
    private String section;

    @OneToMany(mappedBy = "bookCopy")
    private List<Loan> loans;

    @ManyToOne
    @JoinColumn(name="isbn",nullable = false, referencedColumnName="isbn",foreignKey = @ForeignKey(name = "egzemplarze_ibfk_1"))
    private Book book;


    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
