package io.github.amichailides.repository;

import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlStudentRepository implements StudentRepository {

    private final String url = "jdbc:postgresql://localhost:5433/music_tracker";
    private final String user = "postgres";
    private final String password = System.getenv("DB_PASSWORD");

    @Override
    public Student save(Student student) {

        if (student.getId() != null) {
            return update(student);
        }

        String query = "INSERT INTO students (first_name, last_name, email, mobile, skill_level) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, student.getFirstName());
            pstm.setString(2, student.getLastName());
            pstm.setString(3, student.getEmail());
            pstm.setString(4, student.getMobile());
            pstm.setString(5, (student.getLevel() != null) ? student.getLevel().name() : null);

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting student", e);
        }
        return student;
    }


    private Student update(Student student) {
        String query = "UPDATE students SET first_name = ?, last_name = ?, email = ?, mobile = ?, skill_level = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, student.getFirstName());
            pstm.setString(2, student.getLastName());
            pstm.setString(3, student.getEmail());
            pstm.setString(4, student.getMobile());
            pstm.setString(5, (student.getLevel() != null) ? student.getLevel().name() : null);
            pstm.setLong(6, student.getId()); //

            int rowsAffected = pstm.executeUpdate();


            if (rowsAffected == 0) {
                throw new RuntimeException("Student with ID " + student.getId() + " not found to update.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
        return student;
    }



    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery(query)) {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setMobile(rs.getString("mobile"));
                String levelFromDb = rs.getString("skill_level");
                if (levelFromDb != null) {
                    student.setLevel(SkillLevel.valueOf(levelFromDb.toUpperCase()));
                }
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on loading students from Db", e);
        }

        return students;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Objects.requireNonNull(id, "Id can't be null");

        String query = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setLong(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setEmail(rs.getString("email"));
                    student.setMobile(rs.getString("mobile"));
                    String levelFromDb = rs.getString("skill_level");
                    if (levelFromDb != null) {
                        student.setLevel(SkillLevel.valueOf(levelFromDb.toUpperCase()));
                    }
                     return Optional.of(student);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't load student from DB");
        }
    }

        @Override
        public List<Student> findByLastName (String lastName){
            //TODO
            return null;
        }

        @Override
        public boolean existsById (Long id){
            return findById(id).isPresent();
        }

        @Override
        public void deleteById (Long id){
            //TODO
        }

    }