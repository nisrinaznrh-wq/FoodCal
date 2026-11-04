package foodcal;

import java.sql.Connection;
import java.sql.SQLException;

public class TestKoneksi {

    public static void main(String[] args) {

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn != null) {
                System.out.println(
                    "Koneksi database berhasil!"
                );

                conn.close();
            }

        } catch (SQLException e) {
            System.out.println(
                "Koneksi database gagal!"
            );

            System.out.println(e.getMessage());
        }
    }
}