package pl.put.poznan.utils.converters;

import pl.put.poznan.model.Librarian;
import pl.put.poznan.modelFx.LibrarianFx;

public class LibrarianConverter {

    public static Librarian convertToLibrarian(LibrarianFx librarianFx){
        Librarian librarian = new Librarian();
        librarian.setLibrarianId(librarianFx.getLibrarianId());
        librarian.setFirstName(librarianFx.getFirstName());
        librarian.setLastName(librarianFx.getLastName());
        librarian.setPassword(librarianFx.getPassword());
        librarian.setIfEmployed(librarianFx.getIfEmployed());
        return librarian;
    }

    public static LibrarianFx convertToLibrarianFx(Librarian librarian){
        LibrarianFx librarianFx = new LibrarianFx();
        librarianFx.setLibrarianId(librarian.getLibrarianId());
        librarianFx.setFirstName(librarian.getFirstName());
        librarianFx.setLastName(librarian.getLastName());
        librarianFx.setPassword(librarian.getPassword());
        librarianFx.setIfEmployed(librarian.getIfEmployed());
        return librarianFx;
    }
}
