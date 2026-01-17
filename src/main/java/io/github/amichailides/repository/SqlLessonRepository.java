package io.github.amichailides.repository;

import io.github.amichailides.model.Lesson;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class SqlLessonRepository implements LessonRepository {
    private final String url = "jdbc:postgresql://localhost:5433/music_tracker";
    private final String user = "postgres";
    private final String password = System.getenv("DB_PASSWORD");


    public Lesson save(Lesson lesson) {
        String query = "INSERT INTO lessons (lesson_date, lesson_comments, homework, student_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setObject(1, lesson.getDate());
            pstm.setString(2, lesson.getComments());
            pstm.setString(3, lesson.getHomework());
            pstm.setLong(4, lesson.getStudent().getId());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lesson.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Σφάλμα αποθήκευσης μαθήματος: " + e.getMessage(), e);
        }
        return lesson;
    }

    public List<Lesson> findByStudentId(Long studentId){
        List<Lesson> lessons = new ArrayList<>();

        String query = "SELECT * FROM lessons WHERE student_id = ? ORDER BY lesson_date DESC";
        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement pstm = conn.prepareStatement(query)){

            pstm.setLong(1, studentId);

            try (ResultSet rs = pstm.executeQuery()){
                while (rs.next()){
                    Lesson lesson = Lesson.builder()
                            .id(rs.getLong("id"))
                            .date(rs.getObject("lesson_date", LocalDate.class))
                            .comments(rs.getString("lesson_comments"))
                            .homework(rs.getString("homework"))
                            .build();

                    lessons.add(lesson);
                }
            }
            return lessons;

        } catch (SQLException e) {
            throw new RuntimeException("Σφαλμα: Δεν ηταν δυνατη η φορτωση των μαθηματων"
             + e.getMessage(), e);
        }

    }

    public boolean deleteById (Long studentId) { // αυτο εδω θα το κανουμε να παιρνει id lesson και να σβηνει καποιο μαθημα
    //TODO
        return true;
    }

}
