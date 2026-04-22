package Professior;

import java.sql.*;
import javax.swing.*;

import Student.*;

public class ProfessorService {

    static String db = "assignment";

    public static void viewCourses(int professorId) {
        try {
            Connection con = DBConnection.getConnection(db);

            if (con == null) return;

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM courses WHERE professor_id=?"
            );
            ps.setInt(1, professorId);

            ResultSet rs = ps.executeQuery();

            String data = "YOUR COURSES:\n";

            while (rs.next()) {
                data += rs.getString("course_code") + " | "
                      + rs.getString("title") + " | "
                      + rs.getString("timing") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

            // Ask to update
            int choice = JOptionPane.showConfirmDialog(null, "Update any course?");
            if (choice == JOptionPane.YES_OPTION) {
                updateCourse(professorId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Course
    public static void updateCourse(int professorId) {

        String code = JOptionPane.showInputDialog("Enter Course Code:");

        String syllabus = JOptionPane.showInputDialog("Enter Syllabus:");
        String timing = JOptionPane.showInputDialog("Enter Timing:");
        String credits = JOptionPane.showInputDialog("Enter Credits:");
        String prereq = JOptionPane.showInputDialog("Enter Prerequisites:");
        String office = JOptionPane.showInputDialog("Enter Office Hours:");

        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                "UPDATE courses SET syllabus=?, timing=?, credits=?, prerequisites=?, office_hours=? WHERE course_code=? AND professor_id=?"
            );

            ps.setString(1, syllabus);
            ps.setString(2, timing);
            ps.setInt(3, Integer.parseInt(credits));
            ps.setString(4, prereq);
            ps.setString(5, office);
            ps.setString(6, code);
            ps.setInt(7, professorId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Course Updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Course!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2️⃣ View Enrolled Students
    public static void viewStudents(int professorId) {

        String courseCode = JOptionPane.showInputDialog("Enter Course Code:");

        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                "SELECT s.name, s.email, s.academic_status " +
                "FROM students s " +
                "JOIN registrations r ON s.id = r.student_id " +
                "JOIN courses c ON r.course_code = c.course_code " +
                "WHERE c.professor_id=? AND c.course_code=?"
            );

            ps.setInt(1, professorId);
            ps.setString(2, courseCode);

            ResultSet rs = ps.executeQuery();

            String data = "ENROLLED STUDENTS:\n";

            while (rs.next()) {
                data += rs.getString("name") + " | "
                      + rs.getString("email") + " | "
                      + rs.getString("academic_status") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewFeedback(String courseId) {
        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM feedback WHERE course_id = ?"
            );
            ps.setString(1, courseId);

            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append("Student ID: ").append(rs.getInt("student_id"));

                int rating = rs.getInt("rating");
                if (!rs.wasNull()) {
                    sb.append(" | Rating: ").append(rating);
                }

                String comment = rs.getString("comment");
                if (comment != null) {
                    sb.append(" | Comment: ").append(comment);
                }

                sb.append("\n");
            }

            if (sb.length() == 0) {
                sb.append("No feedback found for this course.");
            }

            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
