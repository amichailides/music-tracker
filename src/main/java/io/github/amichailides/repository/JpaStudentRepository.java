package io.github.amichailides.repository;

import io.github.amichailides.model.Student;
import io.github.amichailides.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class JpaStudentRepository implements StudentRepository{

    @Override
    public Student save(Student student) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            try {
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw e;
            }
        }

        return student;
    }
}
