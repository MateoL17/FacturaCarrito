package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    // Conexion a la base de datos mediante la URL
    private static String url = "jdbc:mysql://localhost:3306/sistemaventa?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "";

    // COnstructor para obtener la conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
