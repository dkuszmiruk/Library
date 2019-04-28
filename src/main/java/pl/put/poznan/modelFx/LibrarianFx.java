package pl.put.poznan.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibrarianFx {

    private IntegerProperty librarianId = new SimpleIntegerProperty();

    private StringProperty password = new SimpleStringProperty();

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty lastName = new SimpleStringProperty();

    private char ifEmployed;

    public int getLibrarianId() {
        return librarianId.get();
    }

    public IntegerProperty librarianIdProperty() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId.set(librarianId);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public char getIfEmployed() {
        return ifEmployed;
    }

    public void setIfEmployed(char ifEmployed) {
        this.ifEmployed = ifEmployed;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
