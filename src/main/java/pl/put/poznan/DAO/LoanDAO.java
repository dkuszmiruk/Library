package pl.put.poznan.DAO;

import pl.put.poznan.model.BookCopy;
import pl.put.poznan.model.Librarian;
import pl.put.poznan.model.Loan;
import pl.put.poznan.model.Reader;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

public class LoanDAO {

    private DbConnection dbConnection;
    public LoanDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public List<Loan> getAllLoan(char returnVal) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Loan> listLoan = null;
        try {
            TypedQuery<Loan> query = entityManager.createQuery("SELECT l FROM Loan l where l.ifReturn = :returnVal", Loan.class);
            query.setParameter("returnVal",returnVal);
            listLoan = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listLoan;
    }

    public void deleteLoan(Loan loan) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        loan = entityManager.find(Loan.class,loan.getLoanPk());
        if (loan != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(loan);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                dbConnection.disactiveEntityManager(entityManager);
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Błędne dane wypożyczenia.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void addLoan(Loan loan) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        if(entityManager.find(Loan.class,loan.getLoanPk()) == null) {
            try {
                loan.setLibrarian(entityManager.find(Librarian.class,loan.getLoanPk().getLibrarianId()));
                loan.setBookCopy(entityManager.find(BookCopy.class,loan.getLoanPk().getBookCopyId()));
                loan.setReader(entityManager.find(Reader.class,loan.getLoanPk().getReaderId()));
                entityManager.getTransaction().begin();
                entityManager.persist(loan);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                dbConnection.disactiveEntityManager(entityManager);
                throw new ApplicationException(e.getMessage());
            }
        }else{
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Nie można dodawać wypożyczeń o takich samych wartościach w tym samym czasie.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyLoan(Loan loan, Timestamp newDate) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            Loan modifyLoan = entityManager.find(Loan.class, loan.getLoanPk());
            entityManager.getTransaction().begin();
            modifyLoan.setReturnDate(newDate);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<Loan> searchLoans(String pattern, char returnValue) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Loan> loanList = null;
        try {
            TypedQuery<Loan> loanTypedQuery = entityManager.createQuery("select l from Loan l where l.ifReturn = :returnValue and (l.librarian.firstName LIKE CONCAT('%',:pattern,'%') or l.librarian.lastName LIKE CONCAT('%',:pattern,'%') or l.bookCopy.book.bookTitle LIKE CONCAT('%',:pattern,'%') or convert(l.bookCopy.bookCopyId,char) LIKE CONCAT('%',:pattern,'%') or l.reader.firstName LIKE CONCAT('%',:pattern,'%') or l.reader.lastName LIKE CONCAT('%',:pattern,'%') )",Loan.class);
            loanTypedQuery.setParameter("pattern",pattern);
            loanTypedQuery.setParameter("returnValue",returnValue);
            loanList = loanTypedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbConnection.disactiveEntityManager(entityManager);
        return loanList;
    }

    public void callProcedureReturnBooks(int bookId) throws ApplicationException{
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("return_book");
            query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            query.setParameter(1, bookId);
            query.execute();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new ApplicationException("Nie dokonano zwrotu.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }
}
