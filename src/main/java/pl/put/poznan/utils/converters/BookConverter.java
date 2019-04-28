package pl.put.poznan.utils.converters;

import pl.put.poznan.model.Book;
import pl.put.poznan.modelFx.BookFx;

import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthor;
import static pl.put.poznan.utils.converters.AuthorConverter.convertToAuthorFx;

public class BookConverter {

    public static Book convertToBook(BookFx bookFx){
        Book book = new Book();
        book.setiSBN(bookFx.getiSBN());
        book.setBookTitle(bookFx.getBookTitle());
        book.setBookType(bookFx.getBookType());
        book.setIssueNumber(bookFx.getIssueNumber());
        book.setIssueYear(bookFx.getIssueYear());
        book.setBookLanguage(bookFx.getBookLanguage());
        book.setNamePublishingHouse(bookFx.getNamePublishingHouse());
        book.setBookDescription(bookFx.getBookDescription());
        book.setAuthor(convertToAuthor(bookFx.getAuthorFx()));
        return book;
    }

    public static BookFx convertToBookFx(Book book){
        BookFx bookFx = new BookFx();
        bookFx.setiSBN(book.getiSBN());
        bookFx.setBookTitle(book.getBookTitle());
        bookFx.setBookType(book.getBookType());
        bookFx.setIssueNumber(book.getIssueNumber());
        bookFx.setIssueYear(book.getIssueYear());
        bookFx.setBookLanguage(book.getBookLanguage());
        bookFx.setNamePublishingHouse(book.getNamePublishingHouse());
        bookFx.setBookDescription(book.getBookDescription());
        bookFx.setAuthorFx(convertToAuthorFx(book.getAuthor()));
        return bookFx;
    }
}
