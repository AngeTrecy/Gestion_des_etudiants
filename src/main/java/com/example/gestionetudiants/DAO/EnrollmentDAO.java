package com.example.gestionetudiants.DAO;

import com.example.gestionetudiants.model.Enrollment;
import com.example.gestionetudiants.model.Student;
import com.example.gestionetudiants.model.Classroom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    private Connection c;
    private StudentDAO studentDAO;
    private ClassroomDAO classroomDAO;

    public EnrollmentDAO() {
        this.c = DatabaseConnection.getConnection();
        this.studentDAO = new StudentDAO();
        this.classroomDAO = new ClassroomDAO();
    }

    public boolean insert(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment(student_id, classroom_id, year, date_inscription) VALUES(?, ?, ?, ?)";
        try (PreparedStatement statement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, enrollment.getStudent().getId());
            statement.setInt(2, enrollment.getClassroom().getId());
            statement.setInt(3, enrollment.getYear());
            statement.setDate(4, Date.valueOf(enrollment.getDateInscription()));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    enrollment.setId(generatedId);
                }
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM enrollment WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Enrollment enrollment) {
        String sql = "UPDATE enrollment SET student_id = ?, classroom_id = ?, year = ?, date_inscription = ? WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, enrollment.getStudent().getId());
            statement.setInt(2, enrollment.getClassroom().getId());
            statement.setInt(3, enrollment.getYear());
            statement.setDate(4, Date.valueOf(enrollment.getDateInscription()));
            statement.setInt(5, enrollment.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Enrollment> selectAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment";

        try (Statement statement = c.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Enrollment enrollment = createEnrollmentFromResultSet(rs);
                if (enrollment != null) {
                    enrollments.add(enrollment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    public Enrollment selectById(int id) {
        String sql = "SELECT * FROM enrollment WHERE id = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return createEnrollmentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Enrollment> selectByStudent(String studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE student_id = ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = createEnrollmentFromResultSet(rs);
                if (enrollment != null) {
                    enrollments.add(enrollment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    public List<Enrollment> searchByStudentName(String name) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.* FROM enrollment e " +
                "INNER JOIN student s ON e.student_id = s.id " +
                "WHERE s.nom LIKE ? OR s.prenom LIKE ?";

        try (PreparedStatement statement = c.prepareStatement(sql)) {
            String searchPattern = "%" + name + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = createEnrollmentFromResultSet(rs);
                if (enrollment != null) {
                    enrollments.add(enrollment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // MÉTHODE HELPER POUR CRÉER UN ENROLLMENT DEPUIS UN RESULTSET
    private Enrollment createEnrollmentFromResultSet(ResultSet rs) throws SQLException {
        Student student = studentDAO.selectById(rs.getString("student_id"));
        Classroom classroom = classroomDAO.selectById(rs.getInt("classroom_id"));

        if (student != null && classroom != null) {
            return new Enrollment(
                    rs.getInt("id"),
                    student,
                    classroom,
                    rs.getInt("year"),
                    rs.getDate("date_inscription").toLocalDate()
            );
        }
        return null;
    }
}