package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;
import io.github.amichailides.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.util.Optional;
import java.util.List;


public class JpaLessonRepository implements LessonRepository{

    public Lesson save(Lesson lesson){
       try (EntityManager em = JpaUtil.getEntityManager()){
           try {
               em.getTransaction().begin();
               em.merge(lesson);
               em.getTransaction().commit();
           } catch (Exception e) {
               if (em.getTransaction().isActive()) {
                   em.getTransaction().rollback();
               }
               throw new RuntimeException("Αδυναμια αποθηκευσης του μαθηματος ->", e);
           }
       }
       return lesson;
    }

    public List<Lesson> findByStudentId(Long studentId){
        try (EntityManager em = JpaUtil.getEntityManager()){
            return em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.student.id = :studentId ORDER BY l.date DESC", Lesson.class)
                    .setParameter("studentId", studentId)
                    .getResultList();
        }
    }

    public boolean existsById(Long lessonId) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            //  l.id = indexed , instant pull . δεν τρεχει ολη την βαση
            return em.createQuery("SELECT COUNT(l) FROM Lesson l WHERE l.id = :id", Long.class)
                    .setParameter("id", lessonId )
                    .getSingleResult() > 0;
        }
    }

    public boolean deleteById (Long lessonId) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            try {
                em.getTransaction().begin();
                Lesson lesson = em.find(Lesson.class, lessonId);

                if (lesson != null){
                    em.remove(lesson);
                    em.getTransaction().commit();
                    return true;
                }
                //in case lesson == null -> rollback
                em.getTransaction().rollback();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Σφαλμα κατα την διαγραφη μαθηματος ->" + e.getMessage() );
            }
        }
        return false;
    }

    public Optional<Lesson> findById(Long lessonId){
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Lesson.class, lessonId));
        } catch (PersistenceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
