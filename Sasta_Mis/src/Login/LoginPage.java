package Login;

/* 
ess wale me mainly login ka code hoga, jisme user id aur password leke database se check karenge, 
aur uske hisab se dashboard kholenge. Admin ke liye already implemented id, pass honge.
admin = aryan
password= aryan
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Admin.*;
import Professior.*;    
import Student.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField userField;
    JPasswordField passField;
    JButton loginBtn, backBtn, signupBtn;
    String role;

    public LoginPage(String role) {
        this.role = role;

        setTitle("SASTA MIS - " + role.toUpperCase() + " LOGIN");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("User ID:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        loginBtn = new JButton("Login");
        backBtn = new JButton("Back");
        signupBtn = new JButton("Sign Up");

        add(loginBtn);
        add(signupBtn);
        add(new JLabel()); // spacing
        add(backBtn);

        loginBtn.addActionListener(this);
        backBtn.addActionListener(this);
        signupBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backBtn) {
            new MainMIS();
            dispose();
            return;
        }
        if (e.getSource() == signupBtn) {
            if (role.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Admin can only login");
                return;
            }
            new SignupPage(role);
            dispose();
        }


        String user = userField.getText();
        String pass = new String(passField.getPassword());

        try {

            // ================= ADMIN =================
            if (role.equals("admin")) {

                if (user.equals("aryan") && pass.equals("aryan")) {
                    JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                    new AdminDashboard();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Admin Credentials!");
                }
                return;
            }

            // ================= TA LOGIN =================
            if (role.equals("TA")) {

                Connection con = DBConnection.getConnection("assignment");

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM ta WHERE email=? AND password=?"
                );

                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("ta_id");
                    String name = rs.getString("name");
                    String course = rs.getString("course_id");

                    JOptionPane.showMessageDialog(this, "TA Login Successful!");

                    TeachingAssistant ta = new TeachingAssistant(id, name, course);
                    new TADashboard(ta);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid TA credentials!");
                }

                return; // ✅ VERY IMPORTANT
            }

            // ================= STUDENT / PROFESSOR =================

            Connection con = DBConnection.getConnection("college");

            int userId;

            try {
                userId = Integer.parseInt(user);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid User ID!");
                return;
            }

            String query;

            if (role.equals("student"))
                query = "SELECT * FROM students WHERE id=? AND password=?";
            else
                query = "SELECT * FROM professors WHERE id=? AND password=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(this, "Login Successful!");

                if (role.equals("student"))
                    new StudentDashboard(userId);
                else
                    new ProfessorDashboard(userId);

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while logging in.");
        }
    }
}