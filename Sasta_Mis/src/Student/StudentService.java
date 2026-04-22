package Student;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class StudentService {

    static String db = "assignment";

    // 1. View Courses
    public static void viewCourses() {
        try {
            Connection con = DBConnection.getConnection(db);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM courses");

            String data = "COURSES:\n";
            while (rs.next()) {
                data += rs.getString("course_code") + " | "
                      + rs.getString("title") + " | "
                      + rs.getString("professor") + " | "
                      + rs.getInt("credits") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. Register Course
    public static void registerCourse() {
        String courseId = JOptionPane.showInputDialog("Enter Course Code:");

        try {
            Connection con = DBConnection.getConnection(db);

            // Check total credits
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT SUM(c.credits) FROM registrations r JOIN courses c ON r.course_code = c.course_code WHERE r.student_id=?"
            );
            ps1.setInt(1, 1); // replace with actual logged-in student ID
            ResultSet rs1 = ps1.executeQuery();

            int totalCredits = 0;
            if (rs1.next()) {
                totalCredits = rs1.getInt(1);
                if (rs1.wasNull()) {
                    totalCredits = 0;
                }
            }

            // Get course credits
            PreparedStatement ps2 = con.prepareStatement(
                    "SELECT credits FROM courses WHERE course_code=?"
            );
            ps2.setString(1, courseId);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Course Code!");
                return;
            }

            int courseCredits = rs2.getInt("credits");

            // Credit limit check
            if (totalCredits + courseCredits > 20) {
                JOptionPane.showMessageDialog(null, "Credit limit exceeded!");
                return;
            }

            // Register course
            PreparedStatement ps3 = con.prepareStatement(
                    "INSERT INTO registrations (student_id, course_code) VALUES (?, ?)"
            );
            ps3.setInt(1, 1); // replace with actual student ID
            ps3.setString(2, courseId);
            ps3.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course Registered Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. View Schedule
    public static void viewSchedule() {
        try {
            Connection con = DBConnection.getConnection(db);

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT c.title, c.timing, c.professor FROM registrations r JOIN courses c ON r.course_code=c.course_code WHERE r.student_id=1"
            );

            String data = "SCHEDULE:\n";
            while (rs.next()) {
                data += rs.getString("title") + " | "
                      + rs.getString("timing") + " | "
                      + rs.getString("professor") + "\n";
            }

            JOptionPane.showMessageDialog(null, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Track Progress
//    public static void trackProgress() {
//        try {
//            Connection con = DBConnection.getConnection(db);
//
//            ResultSet rs = con.createStatement().executeQuery(
//                "SELECT grade, credits FROM results WHERE student_id=1"
//            );
//
//            double totalPoints = 0, totalCredits = 0;
//
//            while (rs.next()) {
//                int grade = rs.getInt("grade");
//                int credits = rs.getInt("credits");
//
//                totalPoints += grade * credits;
//                totalCredits += credits;
//            }
//
//            double gpa = totalPoints / totalCredits;
//
//            JOptionPane.showMessageDialog(null, "GPA: " + gpa);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void trackProgress(int studentId) {

        try {
            Connection con = DBConnection.getConnection("assignment");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT course_id, semester, grade FROM enrollments WHERE student_id=? AND status='Completed'"
            );

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            // Store semester-wise grades
            HashMap<Integer, ArrayList<Integer>> semMap = new HashMap<>();

            StringBuilder display = new StringBuilder();
            display.append("=== Academic Progress ===\n\n");

            while (rs.next()) {
                String course = rs.getString("course_id");
                int sem = rs.getInt("semester");
                int grade = rs.getInt("grade");

                // Show individual course
                display.append("Course: ").append(course)
                        .append(" | Sem: ").append(sem)
                        .append(" | Grade: ").append(grade)
                        .append("\n");

                // Store for SGPA calculation
                semMap.putIfAbsent(sem, new ArrayList<>());
                semMap.get(sem).add(grade);
            }

            if (semMap.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No completed courses yet!");
                return;
            }

            display.append("\n--- SGPA ---\n");

            double totalSGPA = 0;
            int semCount = 0;

            for (int sem : semMap.keySet()) {
                ArrayList<Integer> grades = semMap.get(sem);

                double sum = 0;
                for (int g : grades) {
                    sum += g;
                }

                double sgpa = sum / grades.size(); // simple average
                totalSGPA += sgpa;
                semCount++;

                display.append("Semester ").append(sem)
                        .append(": ").append(String.format("%.2f", sgpa))
                        .append("\n");
            }

            double cgpa = totalSGPA / semCount;

            display.append("\nCGPA: ").append(String.format("%.2f", cgpa));

            JOptionPane.showMessageDialog(null, display.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading progress!");
        }
    }

    // 5. Drop Course
    public static void dropCourse() {
        String courseId = JOptionPane.showInputDialog("Enter Course Code to Drop:");

        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM registrations WHERE student_id=1 AND course_code=?"
            );
            ps.setString(1, courseId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course Dropped!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6. Complaint
    public static void submitComplaint() {
        String text = JOptionPane.showInputDialog("Enter Complaint:");

        try {
            Connection con = DBConnection.getConnection(db);

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO complaints(student_id, description, status) VALUES (?, ?, 'Pending')"
            );
            ps.setInt(1, 1);
            ps.setString(2, text);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Complaint Submitted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       // ─────────────────────────────────────────
       // 7.Feedback
       // ─────────────────────────────────────────

    public static void submitFeedback(int userId) {

        String courseStr = JOptionPane.showInputDialog("Enter Course ID:");
         if (courseStr == null) return;

         String courseId = JOptionPane.showInputDialog("Enter Course ID:");
         String ratingStr = JOptionPane.showInputDialog("Enter Rating (1-5) or leave blank:");
         String comment = JOptionPane.showInputDialog("Enter Comment (optional):");

         Integer rating = null;

         if (ratingStr != null && !ratingStr.trim().isEmpty()) {
             rating = Integer.parseInt(ratingStr);
         }

         try {
             Connection con = DBConnection.getConnection(db);

             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO feedback(student_id, course_id, rating, comment) VALUES (?, ?, ?, ?)"
             );

             ps.setInt(1, userId);
             ps.setString(2, courseId);

             if (rating != null)
                 ps.setInt(3, rating);
                 else
                 ps.setNull(3, java.sql.Types.INTEGER);

            if (comment != null && !comment.trim().isEmpty())
                 ps.setString(4, comment);
            else
                 ps.setNull(4, java.sql.Types.VARCHAR);

             ps.executeUpdate();

             JOptionPane.showMessageDialog(null, "Feedback Submitted!");

       } catch (Exception e) {
             e.printStackTrace();
         }
     }

}