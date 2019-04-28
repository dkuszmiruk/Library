package pl.put.poznan.DAO;

import pl.put.poznan.model.Author;
import pl.put.poznan.utils.ApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDAO {
    private DbConnection dbConnection;
    public AuthorDAO(DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public List<Author> getAllAuthor() {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Author> listAuthor = null;
        try {
            listAuthor = entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dbConnection.disactiveEntityManager(entityManager);
        return listAuthor;
    }

    public void deleteAuthor(Author author) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        author = entityManager.find(Author.class, author.getAuthorId());
        if (author.getBooks().size() == 0) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(author);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                entityManager.getTransaction().rollback();
                throw new ApplicationException(e.getMessage());
            }
        } else {
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException("Najpierw należy usunąć lub zmodyfikować książki autorstwa tego autora.");
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void addAuthor(Author author) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public void modifyAuthor(Author author) throws ApplicationException {
        EntityManager entityManager = dbConnection.activeEntityManager();
        try {
            Author modifyAuthor = entityManager.find(Author.class, author.getAuthorId());
            entityManager.getTransaction().begin();
            modifyAuthor.setFirstName(author.getFirstName());
            modifyAuthor.setLastName(author.getLastName());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            dbConnection.disactiveEntityManager(entityManager);
            throw new ApplicationException(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
    }

    public List<Author> searchAuthors(String pattern) {
        EntityManager entityManager = dbConnection.activeEntityManager();
        List<Author> listAuthor = null;
        try {
            TypedQuery<Author> authorTypedQuery = entityManager.createQuery("select a from Author a where a.firstName LIKE CONCAT('%',:pattern,'%') or a.lastName LIKE CONCAT('%',:pattern,'%')",Author.class);
            authorTypedQuery.setParameter("pattern",pattern);
            listAuthor = authorTypedQuery.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dbConnection.disactiveEntityManager(entityManager);
        return listAuthor;
    }
}
