package pl.put.poznan.DAO;

import pl.put.poznan.model.BookCopy;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookCopyDAO {

    private DbConnection dbConnection;
    public BookCopyDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public List<BookCopy> getAllBookCopy() {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<BookCopy> listBookCopy = null;
        try {
            listBookCopy = entityManager.createQuery("SELECT bc FROM BookCopy bc", BookCopy.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listBookCopy;
    }

    public void deleteBookCopy(BookCopy bookCopy) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        bookCopy = entityManager.find(BookCopy.class, bookCopy.getBookCopyId());
        if (bookCopy.getLoans().size() == 0) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(bookCopy);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                entityManager.getTransaction().rollback();
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Najpierw należy usunąć wypozyczenia i historie wypozyczeń tego egzemplarza.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void addBookCopy(BookCopy bookCopy) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(bookCopy);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyBookCopy(BookCopy bookCopy) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            BookCopy modifyBookCopy = entityManager.find(BookCopy.class, bookCopy.getBookCopyId());
            entityManager.getTransaction().begin();
            modifyBookCopy.setSection(bookCopy.getSection());
            modifyBookCopy.setHowLong(bookCopy.getHowLong());
            modifyBookCopy.setBook(bookCopy.getBook());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<BookCopy> searchBookCopies(String pattern) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<BookCopy> bookCopyList = null;
        try {
            TypedQuery<BookCopy> bookCopyTypedQuery = entityManager.createQuery("select b from BookCopy b where b.section LIKE CONCAT('%',:pattern,'%') or b.book.iSBN LIKE CONCAT('%',:pattern,'%') or b.book.bookTitle LIKE CONCAT('%',:pattern,'%') or convert(b.bookCopyId,char) LIKE CONCAT('%',:pattern,'%')",BookCopy.class);
            bookCopyTypedQuery.setParameter("pattern",pattern);
            bookCopyList = bookCopyTypedQuery.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return bookCopyList;
    }

    public List<BookCopy> getBookCopy(EntityManager entityManager) {
        List<BookCopy> listBookCopy = null;
        try {
            listBookCopy = entityManager.createQuery("SELECT bc FROM BookCopy bc", BookCopy.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listBookCopy;
    }

}
