package pl.put.poznan.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ksiazki",indexes = {@Index(columnList = "id_autora", name = "id_autora"), @Index(columnList = "tytul", name = "ksiazka_b")})
public class Book {
    @Id
    @Column(name = "isbn", length = 13, nullable = false)
    private String iSBN;

    @Column(name = "tytul", length = 250, nullable = false)
    private String bookTitle;

    @Column(name = "gatunek", length = 250, nullable = false)
    private String bookType;

    @Column(name = "nr_wydania", nullable = false)
    private int issueNumber;

    @Column(name = "rok_wydania", nullable = false)
    private int issueYear;

    @Column(name = "jezyk", length = 30, nullable = false)
    private String bookLanguage;

    @Column(name = "nazwa_wydawnictwa", length = 250, nullable = false)
    private String namePublishingHouse;

    @Column(name = "opis_ksiazki", length = 3000)
    private String bookDescription;

    @OneToMany(mappedBy = "book")
    private List<BookCopy> bookCopies;

    @ManyToOne
    @JoinColumn(name="id_autora",nullable = false, referencedColumnName = "id_autora",foreignKey = @ForeignKey(name = "ksiazki_ibfk_1"))
    private Author author;

    public String getiSBN() {
        return iSBN;
    }

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(int issueYear) {
        this.issueYear = issueYear;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getNamePublishingHouse() {
        return namePublishingHouse;
    }

    public void setNamePublishingHouse(String namePublishingHouse) {
        this.namePublishingHouse = namePublishingHouse;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
