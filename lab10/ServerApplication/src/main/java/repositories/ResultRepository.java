package repositories;

import jakarta.persistence.EntityManager;
import models.Result;

public class ResultRepository {

    public void create(Result result) {
        EntityManager em = DatabaseManager.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(result);
        em.getTransaction().commit();
        em.close();
    }
}