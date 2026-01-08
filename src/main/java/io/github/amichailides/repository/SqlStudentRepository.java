package io.github.amichailides.repository;

import io.github.amichailides.model.SkillLevel;
import io.github.amichailides.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqlStudentRepository implements IStudentRepository {

    private final String url = "jdbc:postgresql://localhost:5433/music_tracker";
    private final String user = "postgres";
    private final String password = System.getenv("DB_PASSWORD");

    @Override
    public Student save(Student student) {
        String query = "INSERT INTO Students (first_name, last_name, email, mobile, skill_level)" +
                "VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ) {


                pstm.setString(1, student.getFirstName());
                pstm.setString(2, student.getLastName());
                pstm.setString(3, student.getEmail());
                pstm.setString(4, student.getMobile());
                pstm.setString(5, String.valueOf(student.getLevel()));

                pstm.executeUpdate();
            try(ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(url,user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
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
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on loading students from Db", e);
        }

        return students;
    }

    @Override
    public Optional<Student> findById(Long id) {
        //TODO
        return Optional.empty();
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        //TODO
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public void deleteById(Long id) {
        //TODO
    }

}