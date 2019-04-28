package pl.put.poznan.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReaderFx {

    private IntegerProperty readerId = new SimpleIntegerProperty();

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty lastName = new SimpleStringProperty();

    private StringProperty emailAddress = new SimpleStringProperty();

    private StringProperty city = new SimpleStringProperty();

    private StringProperty streetAndHouseNumber = new SimpleStringProperty();

    private char ifActive;

    public int getReaderId() {
        return readerId.get();
    }

    public IntegerProperty readerIdProperty() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId.set(readerId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreetAndHouseNumber() {
        return streetAndHouseNumber.get();
    }

    public StringProperty streetAndHouseNumberProperty() {
        return streetAndHouseNumber;
    }

    public void setStreetAndHouseNumber(String streetAndHouseNumber) {
        this.streetAndHouseNumber.set(streetAndHouseNumber);
    }

    public char getIfActive() {
        return ifActive;
    }

    public void setIfActive(char ifActive) {
        this.ifActive = ifActive;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
