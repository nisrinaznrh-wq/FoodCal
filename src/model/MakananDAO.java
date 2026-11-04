package model;

import foodcal.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MakananDAO {

    // Mengambil semua makanan
    public List<Makanan> getSemuaMakanan()
            throws SQLException {

        List<Makanan> daftar = new ArrayList<>();

        String sql =
            "SELECT id, nama, kalori FROM makanan ORDER BY nama";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Makanan m = new Makanan(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getDouble("kalori")
                );

                daftar.add(m);
            }
        }

        return daftar;
    }

    // Menambahkan makanan
    public void tambah(
            String nama, double kalori)
            throws SQLException {

        String sql =
            "INSERT INTO makanan (nama, kalori) VALUES (?, ?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setDouble(2, kalori);

            ps.executeUpdate();
        }
    }

    // Menghapus makanan
    public boolean hapus(int id)
            throws SQLException {

        String sql = "DELETE FROM makanan WHERE id=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}