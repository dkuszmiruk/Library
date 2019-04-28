package pl.put.poznan.utils.converters;

import pl.put.poznan.model.BookCopy;
import pl.put.poznan.modelFx.BookCopyFx;

import static pl.put.poznan.utils.converters.BookConverter.convertToBook;
import static pl.put.poznan.utils.converters.BookConverter.convertToBookFx;

public class BookCopyConverter {

    public static BookCopy convertToBookCopy(BookCopyFx bookCopyFx){
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(bookCopyFx.getBookCopyId());
        bookCopy.setSection(bookCopyFx.getSection());
        bookCopy.setHowLong(bookCopyFx.getHowLong());
        bookCopy.setBook(convertToBook(bookCopyFx.getBookFx()));
        return bookCopy;
    }

    public static BookCopyFx convertToBookCopyFx(BookCopy bookCopy){
        BookCopyFx bookCopyFx = new BookCopyFx();
        bookCopyFx.setBookCopyId(bookCopy.getBookCopyId());
        bookCopyFx.setSection(bookCopy.getSection());
        bookCopyFx.setHowLong(bookCopy.getHowLong());
        bookCopyFx.setBookFx(convertToBookFx(bookCopy.getBook()));
        return bookCopyFx;
    }

}
