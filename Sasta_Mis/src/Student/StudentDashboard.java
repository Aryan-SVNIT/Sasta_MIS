package Student;

import javax.swing.*;
import Login.*;
import java.awt.*;
import java.awt.event.*;

public class StudentDashboard extends JFrame implements ActionListener {

    private int studentId;

    JButton Blogout, exitBtn;
    JButton viewCourses, registerCourse, viewSchedule, trackProgress, dropCourse, complaint , feed;

    public StudentDashboard(int userId) {

        this.studentId = userId; // ✅ correct

        setTitle("Student Dashboard");
        setSize(600, 500);
        setLayout(new GridLayout(9, 1, 10, 10)); // ✅ FIXED

        viewCourses = new JButton("View Available Courses");
        registerCourse = new JButton("Register Course");
        viewSchedule = new JButton("View Schedule");
        trackProgress = new JButton("Track Academic Progress");
        dropCourse = new JButton("Drop Course");
        complaint = new JButton("Submit Complaint");
        feed = new JButton("Submit Feedback");
        Blogout = new JButton("Logout");
        exitBtn = new JButton("Exit");

        add(viewCourses);
        add(registerCourse);
        add(viewSchedule);
        add(trackProgress);
        add(dropCourse);
        add(complaint);
        add(feed);
        add(Blogout);
        add(exitBtn);

        viewCourses.addActionListener(this);
        registerCourse.addActionListener(this);
        viewSchedule.addActionListener(this);
        trackProgress.addActionListener(this);
        dropCourse.addActionListener(this);
        complaint.addActionListener(this);
        feed.addActionListener(this);

        Blogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            new MainMIS();
            dispose();
        });

        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == viewCourses) {
            StudentService.viewCourses();
        }

        if (e.getSource() == registerCourse) {
            StudentService.registerCourse();
        }

        if (e.getSource() == viewSchedule) {
            StudentService.viewSchedule();
        }

        if (e.getSource() == trackProgress) {
            StudentService.trackProgress(studentId);
        }

        if (e.getSource() == dropCourse) {
            StudentService.dropCourse();
        }

        if (e.getSource() == complaint) {
            StudentService.submitComplaint();
        }

        if (e.getSource() == feed) {
            StudentService.submitFeedback(studentId);
        }
    }
}