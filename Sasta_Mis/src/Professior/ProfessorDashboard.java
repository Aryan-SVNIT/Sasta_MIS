package Professior;

import javax.swing.*;

import Login.*;
import Student.DBConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Professior.ProfessorService.db;

public class ProfessorDashboard extends JFrame implements ActionListener {
    
    JButton logoutBtn, exitBtn, manageCourses, viewStudents , feed;
    int professorId;
    
    public ProfessorDashboard(int id) {
        this.professorId = id;
        
        setTitle("Professor Dashboard");
        setSize(500, 300);
        setLayout(new GridLayout(2, 1, 10, 10));
        
        manageCourses = new JButton("Manage Courses");
        viewStudents = new JButton("View Enrolled Students");
        feed = new JButton("See Feedback");
        logoutBtn = new JButton("Logout");
        exitBtn = new JButton("Exit");
        
        add(manageCourses);
        add(viewStudents);
        add(feed);
        add(logoutBtn);
        add(exitBtn);
        
        manageCourses.addActionListener(this);
        viewStudents.addActionListener(this);
        feed.addActionListener(this);

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            new MainMIS();   // back to start
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

        if (e.getSource() == manageCourses) {
            ProfessorService.viewCourses(professorId);
        }

        if (e.getSource() == viewStudents) {
            ProfessorService.viewStudents(professorId);
        }

        if (e.getSource() == feed) {

            String courseStr = JOptionPane.showInputDialog("Enter Course ID:");
            if (courseStr == null) return;

            String courseId = JOptionPane.showInputDialog("Enter Course ID:");

            ProfessorService.viewFeedback(courseId);
        }


        }
    }

