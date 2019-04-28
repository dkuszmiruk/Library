package pl.put.poznan.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "czytelnicy")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_czytelnika")
    private int readerId;

    @Column(name = "imie", length = 20, nullable = false)
    private String firstName;

    @Column(name = "nazwisko", length = 30, nullable = false)
    private String lastName;

    @Column(name = "adres_email", length = 50, nullable = false)
    private String emailAddress;

    @Column(name = "miasto", length = 20, nullable = false)
    private String city;

    @Column(name = "ulica_i_nr_domu", length = 20, nullable = false)
    private String streetAndHouseNumber;

    @Column(name = "czy_aktualny", nullable = false)
    private char ifActive;

    @OneToMany(mappedBy = "reader")
    private List<Loan> loans;

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber = streetAndHouseNumber;
    }

    public char getIfActive() {
        return ifActive;
    }

    public void setIfActive(char ifActive) {
        this.ifActive = ifActive;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
