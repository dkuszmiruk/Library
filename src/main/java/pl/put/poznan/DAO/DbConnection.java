package pl.put.poznan.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private EntityManagerFactory entityManagerFactory;

    public void initDbOperations() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
    }

    public EntityManager activeEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void disactiveEntityManager(EntityManager entityManager) {
        entityManager.close();
    }

    public void closeDbOperations() {
        entityManagerFactory.close();
    }
}
