package pl.put.poznan.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bibliotekarze")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bibliotekarza")
    private int librarianId;

    @Column(name = "haslo", length = 20, nullable = false)
    private String password;

    @Column(name = "imie", length = 20, nullable = false)
    private String firstName;

    @Column(name = "nazwisko", length = 30, nullable = false)
    private String lastName;

    @Column(name = "czy_zatrudniony", nullable = false)
    char ifEmployed;


    @OneToMany(mappedBy = "librarian")
    private List<Loan> loans;

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getIfEmployed() {
        return ifEmployed;
    }

    public void setIfEmployed(char ifEmployed) {
        this.ifEmployed = ifEmployed;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
