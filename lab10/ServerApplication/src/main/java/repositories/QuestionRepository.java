package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import models.Question;
import java.util.List;

public class QuestionRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuizGamePU");

    public void create(Question question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(question);
        em.getTransaction().commit();
        em.close();
    }

    public List<Question> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        em.close();
        return questions;
    }
}