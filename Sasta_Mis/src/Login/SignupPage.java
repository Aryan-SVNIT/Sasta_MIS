package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Student.*;

public class SignupPage extends JFrame implements ActionListener {

    JTextField nameField, emailField, idField;
    JPasswordField passField;
    JButton registerBtn, backBtn;
    String role;

    public SignupPage(String role) {
        this.role = role;

        setTitle(role.toUpperCase() + " SIGN UP");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        registerBtn = new JButton("Register");
        backBtn = new JButton("Back");

        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backBtn) {
            new LoginPage(role);
            dispose();
            return;
        }

        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection("assignment");

            String query = "";

            if (role.equals("student")) {
                query = "INSERT INTO students(id,name,email,password) VALUES(?,?,?,?)";
            } else if (role.equals("professor")) {
                query = "INSERT INTO professors(id,name,email,password) VALUES(?,?,?,?)";

            }
                else if (role.equals("TA")){
                query = "INSERT INTO ta(ta_id,name,email,password) VALUES(?,?,?,?)";
            }
            else {
                JOptionPane.showMessageDialog(this, "Admin signup not allowed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, pass);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");

            new LoginPage(role);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}