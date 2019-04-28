package pl.put.poznan.utils.converters;

import pl.put.poznan.model.Author;
import pl.put.poznan.modelFx.AuthorFx;

public class AuthorConverter {

    public static Author convertToAuthor(AuthorFx authorFx){
        Author author = new Author();
        author.setAuthorId(authorFx.getAuthorId());
        author.setFirstName(authorFx.getFirstName());
        author.setLastName(authorFx.getLastName());
        return author;
    }

    public static AuthorFx convertToAuthorFx(Author author){
        AuthorFx authorFx = new AuthorFx();
        authorFx.setAuthorId(author.getAuthorId());
        authorFx.setFirstName(author.getFirstName());
        authorFx.setLastName(author.getLastName());
        return authorFx;
    }

}
