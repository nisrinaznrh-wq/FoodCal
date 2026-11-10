package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "makanan")
public class Makanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    @Column(name = "kalori", nullable = false)
    private double kalori;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Makanan() {
    }

    public Makanan(String nama, double kalori) {
        this.nama = nama;
        this.kalori = kalori;
    }

    public Integer getId() { return id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getKalori() { return kalori; }
    public void setKalori(double kalori) { this.kalori = kalori; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}