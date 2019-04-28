package pl.put.poznan.DAO;

import pl.put.poznan.model.Reader;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReaderDAO {

    private DbConnection dbConnection;
    public ReaderDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public void addReader(Reader reader) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(reader);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyReader(Reader reader) throws ApplicationException{
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            Reader modifyReader = entityManager.find(Reader.class, reader.getReaderId());
            entityManager.getTransaction().begin();
            modifyReader.setFirstName(reader.getFirstName());
            modifyReader.setLastName(reader.getLastName());
            modifyReader.setCity(reader.getCity());
            modifyReader.setEmailAddress(reader.getEmailAddress());
            modifyReader.setStreetAndHouseNumber(reader.getStreetAndHouseNumber());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void deleteReader(Reader reader) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        reader = entityManager.find(Reader.class, reader.getReaderId());
        if (reader.getLoans().size() == 0) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(reader);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                entityManager.getTransaction().rollback();
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Najpierw należy usunąć wypożyczenia których dokonywał ten czytelnik.");
        }

        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<Reader> getAllReader() {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Reader> listReader = null;
        try {
            listReader = entityManager.createQuery("SELECT r FROM Reader r", Reader.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dbConnection.disactiveEntityManager(entityManager);
        return listReader;
    }

    public List<Reader> searchReader(String pattern) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Reader> listReader = null;
        try {
            TypedQuery<Reader> readerTypedQuery = entityManager.createQuery("select r from Reader r where r.firstName LIKE CONCAT('%',:pattern,'%') or r.lastName LIKE CONCAT('%',:pattern,'%') or r.emailAddress LIKE CONCAT('%',:pattern,'%') or r.city LIKE CONCAT('%',:pattern,'%') or r.streetAndHouseNumber LIKE CONCAT('%',:pattern,'%')",Reader.class);
            //TypedQuery<Reader> readerTypedQuery = entityManager.createQuery("select r from Reader r where r.firstName LIKE CONCAT('%',:pattern,'%')",Reader.class);
            readerTypedQuery.setParameter("pattern",pattern);
            listReader = readerTypedQuery.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listReader;
    }
}
