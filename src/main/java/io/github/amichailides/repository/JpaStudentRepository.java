package io.github.amichailides.repository;

import io.github.amichailides.model.Student;
import io.github.amichailides.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Student>  findAll() {
        try (EntityManager em = JpaUtil.getEntityManager()){
            return em.createQuery("SELECT s FROM Student s", Student.class)
                    .getResultList();
        }
    }



}
