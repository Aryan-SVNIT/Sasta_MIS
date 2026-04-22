package Admin;

import java.sql.*;
import javax.swing.*;
import Student.*;

public class AdminService {

    static String db = "assignment";

    public static void manageCourses() {

        String[] options = {"View", "Add", "Delete"};
        int choice = JOptionPane.showOptionDialog(null, "Select Option", "Courses",
                0, 3, null, options, options[0]);

        try {
            Connection con = DBConnection.getConnection(db);

            if (choice == 0) { // VIEW
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM courses");

                String data = "COURSES:\n";
                while (rs.next()) {
                    data += rs.getString("course_code") + " | "
                          + rs.getString("title") + " | "
                          + rs.getInt("credits") + "\n";
                }
                JOptionPane.showMessageDialog(null, data);
            }

            if (choice == 1) { // ADD
                String code = JOptionPane.showInputDialog("Course Code:");
                String title = JOptionPane.showInputDialog("Title:");
                String credits = JOptionPane.showInputDialog("Credits:");
                String timing = JOptionPane.showInputDialog("Timing:");

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO courses(course_code,title,credits,timing) VALUES(?,?,?,?)"
                );

                ps.setString(1, code);
                ps.setString(2, title);
                ps.setInt(3, Integer.parseInt(credits));
                ps.setString(4, timing);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Course Added!");
            }

            if (choice == 2) { // DELETE
                String code = JOptionPane.showInputDialog("Enter Course Code:");

                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM courses WHERE course_code=?"
                );

                ps.setString(1, code);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Course Deleted!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2️ Manage Students
    public static void manageStudents() {

        String[] options = {"View", "Update"};
        int choice = JOptionPane.showOptionDialog(null, "Select Option", "Students",
                0, 3, null, options, options[0]);

        try {
            Connection con = DBConnection.getConnection(db);

            if (choice == 0) { // VIEW
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");

                String data = "STUDENTS:\n";
                while (rs.next()) {
                    data += rs.getInt("id") + " | "
                          + rs.getString("name") + " | "
                          + rs.getString("email") + "\n";
                }
                JOptionPane.showMessageDialog(null, data);
            }

            if (choice == 1) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Student ID:"));
                String name = JOptionPane.showInputDialog("New Name:");
                String email = JOptionPane.showInputDialog("New Email:");

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET name=?, email=? WHERE id=?"
                );

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Updated!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3️ Assign Professor
    public static void assignProfessor() {

        String course = JOptionPane.showInputDialog("Course Code:");
        String profId = JOptionPane.showInputDialog("Professor ID:");

        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                "UPDATE courses SET professor_id=? WHERE course_code=?"
            );

            ps.setInt(1, Integer.parseInt(profId));
            ps.setString(2, course);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Professor Assigned!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void assignGrade() {

        try {
            Connection con = DBConnection.getConnection("assignment");

            int studentId = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Student ID:")
            );

            String courseId = JOptionPane.showInputDialog("Enter Course Code (e.g., MA106):");

            int grade = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Grade (0-10):")
            );

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE enrollments SET grade = ?, status='Completed' WHERE student_id = ? AND course_id = ?"
            );

            ps.setInt(1, grade);
            ps.setInt(2, studentId);
            ps.setString(3, courseId);

            int rows = ps.executeUpdate();

            if (rows > 0)
                JOptionPane.showMessageDialog(null, "Grade assigned successfully!");
            else
                JOptionPane.showMessageDialog(null, "Record not found!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // 4️ Handle Complaints
    public static void handleComplaints() {

        String[] options = {"View All", "Filter Pending", "Resolve"};
        int choice = JOptionPane.showOptionDialog(null, "Select Option", "Complaints",
                0, 3, null, options, options[0]);

        try {
            Connection con = DBConnection.getConnection(db);

            if (choice == 0) {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM complaints");

                showComplaints(rs);
            }

            if (choice == 1) {
                ResultSet rs = con.createStatement().executeQuery(
                    "SELECT * FROM complaints WHERE status='Pending'"
                );

                showComplaints(rs);
            }

            if (choice == 2) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Complaint ID:"));

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE complaints SET status='Resolved' WHERE id=?"
                );

                ps.setInt(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Complaint Resolved!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper
    private static void showComplaints(ResultSet rs) throws Exception {
        String data = "COMPLAINTS:\n";

        while (rs.next()) {
            data += rs.getInt("id") + " | "
                  + rs.getString("description") + " | "
                  + rs.getString("status") + "\n";
        }

        JOptionPane.showMessageDialog(null, data);
    }
}
