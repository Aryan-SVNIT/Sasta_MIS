package Admin;

import javax.swing.*;

import Login.*;

import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {
    
    JButton logoutBtn, exitBtn, assignGrade;
    JButton manageCourses, manageStudents, assignProf, handleComplaints;
    
    public AdminDashboard() {
        
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLayout(new GridLayout(4, 1, 10, 10));
        
        manageCourses = new JButton("Manage Course Catalog");
        manageStudents = new JButton("Manage Student Records");
        assignProf = new JButton("Assign Professor");
        handleComplaints = new JButton("Handle Complaints");
        assignGrade = new JButton("Assign Grades");
        logoutBtn = new JButton("Logout");
        exitBtn = new JButton("Exit");

        add(manageCourses);
        add(manageStudents);
        add(assignProf);
        add(handleComplaints);
        add(assignGrade);
        add(logoutBtn);
        add(exitBtn);

        manageCourses.addActionListener(this);
        manageStudents.addActionListener(this);
        assignProf.addActionListener(this);
        handleComplaints.addActionListener(this);
        assignGrade.addActionListener(this);
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
        
        if (e.getSource() == manageCourses)
            AdminService.manageCourses();
        
        if (e.getSource() == manageStudents)
            AdminService.manageStudents();
        
        if (e.getSource() == assignProf)
            AdminService.assignProfessor();

        if (e.getSource() == handleComplaints)
            AdminService.handleComplaints();

        if (e.getSource() == assignGrade) {
            AdminService.assignGrade();
        }
    }
}

