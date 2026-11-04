package model;

import foodcal.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // REGISTRASI
    public boolean registrasi(
            String nama, String password)
            throws SQLException {

        String cek =
            "SELECT id FROM users WHERE LOWER(nama)=LOWER(?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(cek)) {

            ps.setString(1, nama);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            }
        }

        String sql =
            "INSERT INTO users (nama, password) VALUES (?, ?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setString(2, PasswordUtil.hash(password));

            ps.executeUpdate();

            return true;
        }
    }

    // LOGIN
    public boolean login(
            String nama, String password)
            throws SQLException {

        String sql =
            "SELECT password FROM users WHERE LOWER(nama)=LOWER(?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String passwordDB =
                    rs.getString("password");

                // Password hasil hashing
                if (PasswordUtil.verify(
                        password, passwordDB)) {
                    return true;
                }

                // Kompatibilitas akun lama yang masih
                // menyimpan password teks biasa.
                if (password.equals(passwordDB)) {

                    updatePasswordHash(nama, password);

                    return true;
                }
            }
        }

        return false;
    }

    private void updatePasswordHash(
            String nama, String password)
            throws SQLException {

        String sql =
            "UPDATE users SET password=? WHERE LOWER(nama)=LOWER(?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hash(password));
            ps.setString(2, nama);

            ps.executeUpdate();
        }
    }
}