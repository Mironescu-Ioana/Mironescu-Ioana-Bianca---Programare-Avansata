package repositories;

import jakarta.persistence.EntityManager;
import models.GameSession;

public class GameSessionRepository {

    public void create(GameSession game) {
        EntityManager em = DatabaseManager.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
        em.close();
    }
}