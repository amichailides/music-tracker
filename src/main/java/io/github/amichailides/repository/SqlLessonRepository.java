package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;

import java.sql.*;


public class SqlLessonRepository implements LessonRepository {
    private final String url = "jdbc:postgresql://localhost:5433/music_tracker";
    private final String user = "postgres";
    private final String password = System.getenv("DB_PASSWORD");

    public Lesson save(Lesson lesson, Long studentId) {
        String query = "INSERT INTO lessons (lesson_date, lesson_comments, homework, student_id)" +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            pstm.setObject(1, lesson.getDate());
            pstm.setString(2, lesson.getComments());
            pstm.setString(3, lesson.getHomework());
            pstm.setLong(4, studentId);

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    lesson.setId(generatedKeys.getLong(1));
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException("Σφαλμα αποθηκευσης μαθηματος" + e.getMessage(), e);
        }
        return lesson;
    }
}
