package Login;
/* 
 ess wale me main file hoygi.. or login page the interface honga
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMIS extends JFrame implements ActionListener {

    JButton stuBtn, profBtn, adminBtn, exitBtn , tabtn;

    public MainMIS() {
        setTitle("Sasta MIS - Login Portal");
        setSize(400, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        stuBtn = new JButton("Student Login");
        profBtn = new JButton("Professor Login");
        adminBtn = new JButton("Admin Login");
        tabtn = new JButton("Teaching Assistant Login");
        exitBtn = new JButton("Exit");


        add(stuBtn);
        add(profBtn);
        add(tabtn);
        add(adminBtn);
        add(exitBtn);

        stuBtn.addActionListener(this);
        profBtn.addActionListener(this);
        tabtn.addActionListener(this);
        adminBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == stuBtn) {
            new LoginPage("student");
            dispose();
        }

        if (e.getSource() == profBtn) {
            new LoginPage("professor");
            dispose();
        }

        if (e.getSource() == adminBtn) {
            new LoginPage("admin");
            dispose();
        }

        if (e.getSource() == tabtn) {
            new LoginPage("TA");
            dispose();
        }

        if (e.getSource() == exitBtn) {
            System.exit(0); // safe exit
        }
    }

    public static void main(String[] args) {
        new MainMIS();
    }
}