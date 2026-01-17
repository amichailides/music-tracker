package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;
import io.github.amichailides.model.Student;
import io.github.amichailides.utils.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaLessonRepository implements LessonRepository{

    public Lesson save(Lesson lesson){
       try (EntityManager em = JpaUtil.getEntityManager()){
           try {
               em.getTransaction().begin();
               em.persist(lesson);
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
        //TODO
        return null;
    }

    public void deleteById (Long studentId) {
        //TODO

    }

}
