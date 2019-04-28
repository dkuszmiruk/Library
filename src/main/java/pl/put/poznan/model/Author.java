package pl.put.poznan.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "autorzy", indexes = @Index(columnList = "nazwisko", name = "autor_nazwisko"))
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autora")
    private int authorId;

    @Column(name = "imie", length = 20, nullable = false)
    private String firstName;

    @Column(name = "nazwisko",length = 30, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
