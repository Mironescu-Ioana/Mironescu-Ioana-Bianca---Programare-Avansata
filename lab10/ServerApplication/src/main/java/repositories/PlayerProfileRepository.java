package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import models.PlayerProfile;

public class PlayerProfileRepository {

    public void create(PlayerProfile player) {
        EntityManager em = DatabaseManager.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(player);
        em.getTransaction().commit();
        em.close();
    }

    public PlayerProfile findByName(String name) {
        EntityManager em = DatabaseManager.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM PlayerProfile p WHERE p.name = :name", PlayerProfile.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}