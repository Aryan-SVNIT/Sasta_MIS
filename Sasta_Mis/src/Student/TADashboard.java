package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TADashboard extends JFrame implements ActionListener {

    JButton viewGrades, updateGrades, logout;
    TeachingAssistant ta;

    public TADashboard(TeachingAssistant ta) {
        this.ta = ta;

        setTitle("TA Dashboard");
        setSize(400, 300);
        setLayout(new GridLayout(3,1,10,10));

        viewGrades = new JButton("View Student Grades");
        updateGrades = new JButton("Update Student Grade");
        logout = new JButton("Logout");

        add(viewGrades);
        add(updateGrades);
        add(logout);

        viewGrades.addActionListener(this);
        updateGrades.addActionListener(this);

        logout.addActionListener(e -> dispose());

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == viewGrades) {
            ta.viewStudentGrades();
        }

        if (e.getSource() == updateGrades) {
            int sid = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
            int grade = Integer.parseInt(JOptionPane.showInputDialog("Enter Grade:"));

            ta.updateStudentGrade(sid, grade);
        }
    }
}