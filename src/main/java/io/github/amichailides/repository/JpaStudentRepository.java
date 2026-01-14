package io.github.amichailides.repository;

import io.github.amichailides.model.Student;
import io.github.amichailides.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaStudentRepository implements StudentRepository{

    @Override
    public Student save(Student student) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            try {
                em.getTransaction().begin();
                //persist only new write | merge if !exists insert new , if exists updates
                // αποθηκευουμε το merge για να μας δωσει πισω το instance με το id απο την βαση
                student = em.merge(student);
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

    @Override
    public Optional<Student> findById(Long studentId){
        try (EntityManager em = JpaUtil.getEntityManager()){
            Student student = em.find(Student.class, studentId);
            return Optional.ofNullable(student);  // no NullPointException return
        }
    }

    @Override
    public boolean deleteById (Long studentId) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            try {
                em.getTransaction().begin();
                Student student = em.find(Student.class, studentId);
                if (student != null){
                    em.remove(student);
                    em.getTransaction().commit();
                    return true;
                }
                em.getTransaction().commit();
                return false;
            }catch (Exception e) {
                // if something goes badly, αναιρει οτι εχει προλαβει να εκτελεσει
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public boolean existsById(Long studentId) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            //  s.id = indexed , instant pull . δεν τρεχει ολη την βαση
            Long count = em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.id = :id", Long.class)
                    .setParameter("id", studentId )
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        try (EntityManager em = JpaUtil.getEntityManager()){

            return em.createQuery("SELECT s FROM Student s WHERE s.lastName = :lastName", Student.class)
                    .setParameter("lastName", lastName )
                    .getResultList();
        }
    }

}
