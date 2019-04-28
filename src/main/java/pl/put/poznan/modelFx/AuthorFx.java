package pl.put.poznan.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthorFx {

    private IntegerProperty authorId = new SimpleIntegerProperty();

    private StringProperty firstName = new SimpleStringProperty();

    private StringProperty lastName = new SimpleStringProperty();

    public int getAuthorId() {
        return authorId.get();
    }

    public IntegerProperty authorIdProperty() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId.set(authorId);
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

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
