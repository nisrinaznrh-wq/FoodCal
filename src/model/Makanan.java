package model;

public class Makanan {

    private int id;
    private String nama;
    private double kalori;

    public Makanan(int id, String nama, double kalori) {
        this.id = id;
        this.nama = nama;
        this.kalori = kalori;
    }

    public Makanan(String nama, double kalori) {
        this(0, nama, kalori);
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getKalori() {
        return kalori;
    }
}