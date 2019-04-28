package pl.put.poznan.DAO;

import pl.put.poznan.model.Librarian;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class LibrarianDAO {

    private DbConnection dbConnection;
    public LibrarianDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public List<Librarian> getAllLibrarian() {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Librarian> listLibrarian = null;
        try {
            listLibrarian = entityManager.createQuery("SELECT l FROM Librarian l", Librarian.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dbConnection.disactiveEntityManager(entityManager);
        return listLibrarian;
    }

    public void deleteLibrarian(Librarian librarian) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        librarian = entityManager.find(Librarian.class, librarian.getLibrarianId());
        if (librarian.getLoans().size() == 0) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(librarian);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                entityManager.getTransaction().rollback();
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Najpierw należy usunąć wypożyczenia których dokonywał ten bibliotekarz.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void addLibrarian(Librarian librarian) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(librarian);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());

        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyLibrarian(Librarian librarian) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            Librarian modifyLibrarian = entityManager.find(Librarian.class, librarian.getLibrarianId());
            entityManager.getTransaction().begin();
            modifyLibrarian.setFirstName(librarian.getFirstName());
            modifyLibrarian.setLastName(librarian.getLastName());
            modifyLibrarian.setPassword(librarian.getPassword());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<Librarian> searchLibrarians(String pattern) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Librarian> listLibrarian = null;
        try {
            TypedQuery<Librarian> librarianTypedQuery = entityManager.createQuery("select l from Librarian l where l.firstName LIKE CONCAT('%',:pattern,'%') or l.lastName LIKE CONCAT('%',:pattern,'%') or l.password LIKE CONCAT('%',:pattern,'%')",Librarian.class);
            librarianTypedQuery.setParameter("pattern",pattern);
            listLibrarian = librarianTypedQuery.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listLibrarian;
    }
}
