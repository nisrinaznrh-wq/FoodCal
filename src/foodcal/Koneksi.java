package foodcal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL =
        "jdbc:mysql://localhost:3306/foodcal?useSSL=false&serverTimezone=Asia/Jakarta";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi()
            throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "Driver MySQL tidak ditemukan!", e
            );
        }

        return DriverManager.getConnection(
            URL, USER, PASSWORD
        );
    }
}