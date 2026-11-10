package model;

import java.util.List;

public class FoodCalModel {

    private UserDAO userDAO = new UserDAO();
    private MakananDAO makananDAO = new MakananDAO();

    // Registrasi
    public boolean registrasi(String nama, String password) {

        if (nama == null || nama.trim().length() < 3) {
            throw new IllegalArgumentException("Username minimal 3 karakter.");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password minimal 6 karakter.");
        }

        return userDAO.registrasi(nama.trim(), password);
    }

    // Login
    public boolean login(String nama, String password) {

        if (nama == null || nama.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        return userDAO.login(nama.trim(), password);
    }

    // Menampilkan makanan
    public List<Makanan> getSemuaMakanan() {
        return makananDAO.getSemuaMakanan();
    }

    // Tambah makanan
    public void tambahMakanan(String nama, double kalori) {

        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama makanan harus diisi.");
        }

        if (!Double.isFinite(kalori) || kalori <= 0) {
            throw new IllegalArgumentException("Kalori harus lebih dari 0.");
        }

        makananDAO.tambah(nama.trim(), kalori);
    }

    // Hapus makanan
    public boolean hapusMakanan(int id) {
        return makananDAO.hapus(id);
    }
}