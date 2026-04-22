package Student;

import java.sql.*;

public class DBConnection {
    public static Connection getConnection(String DBname) throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/" + DBname;
        String user = "root";
        String pass = "aryan@A2006";
        Connection conn = DriverManager.getConnection(url, user, pass);
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connected to database!");

        return conn;
    }

}