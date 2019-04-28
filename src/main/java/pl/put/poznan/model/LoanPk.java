package pl.put.poznan.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class LoanPk implements Serializable {
    @Column(name = "data_wypozyczenia")
    private Timestamp rentalDate;
    @Column(name = "id_bibliotekarza")
    private int librarianId;
    @Column(name  = "id_egzemplarza")
    private int bookCopyId;
    @Column(name = "id_czytelnika")
    private int readerId;

    public LoanPk(){

    }
    public LoanPk(Timestamp rentalDate, int librarianId, int bookCopyId, int readerId) {
        this.rentalDate = rentalDate;
        this.librarianId = librarianId;
        this.bookCopyId = bookCopyId;
        this.readerId = readerId;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPk loanPk = (LoanPk) o;
        return librarianId == loanPk.librarianId &&
                bookCopyId == loanPk.bookCopyId &&
                readerId == loanPk.readerId &&
                Objects.equals(rentalDate, loanPk.rentalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalDate, librarianId, bookCopyId, readerId);
    }
}
