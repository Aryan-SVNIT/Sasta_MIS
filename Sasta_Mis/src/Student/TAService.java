package Student;

import java.sql.*;

public class TAService {

    public static void viewGrades(String courseId) {
        try {
            Connection con = DBConnection.getConnection("assignment");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT student_id, grade FROM enrollments WHERE course_id = ?"
            );
            ps.setString(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Student: " + rs.getInt("student_id") +
                        " | Grade: " + rs.getInt("grade"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void updateGrade(int studentId, String courseId, int grade) {
        try {
            Connection con = DBConnection.getConnection("assignment");

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE enrollments SET grade = ? WHERE student_id = ? AND course_id = ?"
            );

            ps.setInt(1, grade);
            ps.setInt(2, studentId);
            ps.setString(3, courseId);

            ps.executeUpdate();

            System.out.println("Grade updated successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}