package pl.put.poznan.DAO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.internal.SessionImpl;
import pl.put.poznan.model.Book;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class BookDAO {

    private DbConnection dbConnection;
    public BookDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public List<Book> getAllBook() {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Book> listBook = null;
        try {
            listBook = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listBook;
    }

    public void deleteBook(Book book) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        book = entityManager.find(Book.class, book.getiSBN());
        if (book.getBookCopies().size() == 0) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(book);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                entityManager.getTransaction().rollback();
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Najpierw należy usunąć egzemplarze tej książki.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void addBook(Book book) throws ApplicationException { // pomyslec o rzucaniu tego w górę
        EntityManager entityManager = dbConnection.activeEntityManager();
        if(entityManager.find(Book.class, StringUtils.repeat("0",13-book.getiSBN().length()) + book.getiSBN())==null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(book);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                dbConnection.disactiveEntityManager(entityManager);
                throw new ApplicationException(e.getMessage());
            }
        }else{
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Istnieje juz ksiazka o podanym numerze ISBN.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyBook(Book book) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            Book modifyBook = entityManager.find(Book.class, book.getiSBN());
            entityManager.getTransaction().begin();
            modifyBook.setBookTitle(book.getBookTitle());
            modifyBook.setBookType(book.getBookType());
            modifyBook.setIssueNumber(book.getIssueNumber());
            modifyBook.setIssueYear(book.getIssueYear());
            modifyBook.setBookLanguage(book.getBookLanguage());
            modifyBook.setNamePublishingHouse(book.getNamePublishingHouse());
            modifyBook.setBookDescription(book.getBookDescription());
            modifyBook.setAuthor(book.getAuthor());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<Book> searchBooks(String pattern) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Book> bookList = null;
        try {
            TypedQuery<Book> bookTypedQuery = entityManager.createQuery("select b from Book b where b.iSBN LIKE CONCAT('%',:pattern,'%') or b.bookTitle LIKE CONCAT('%',:pattern,'%') or b.bookType LIKE CONCAT('%',:pattern,'%') or b.bookLanguage LIKE CONCAT('%',:pattern,'%') or b.namePublishingHouse LIKE CONCAT('%',:pattern,'%') or b.bookDescription LIKE CONCAT('%',:pattern,'%') or b.author.firstName LIKE CONCAT('%',:pattern,'%') or b.author.lastName LIKE CONCAT('%',:pattern,'%')" ,Book.class);
            bookTypedQuery.setParameter("pattern",pattern);
            bookList = bookTypedQuery.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return bookList;
    }

    public int availableBookCounterCallFunction(String bookName){
        EntityManager entityManager = dbConnection.activeEntityManager();
        Connection cc =   ((SessionImpl) entityManager.getDelegate()).connection();
        int numOfAvailableBooks = 0;
        CallableStatement stmt;
        try {
            stmt = cc.prepareCall("{? = call available_num(?)}");
            stmt.setString(2, bookName);
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.execute();
            numOfAvailableBooks = stmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbConnection.disactiveEntityManager(entityManager);
        return numOfAvailableBooks;
    }

}
