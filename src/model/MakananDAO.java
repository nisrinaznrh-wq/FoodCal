package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MakananDAO {

    public List<Makanan> getSemuaMakanan() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Makanan m ORDER BY m.nama", Makanan.class
            ).list();
        }
    }

    public void tambah(String nama, double kalori) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(new Makanan(nama, kalori));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public boolean hapus(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Makanan m = session.find(Makanan.class, id);
            if (m == null) {
                tx.rollback();
                return false;
            }
            session.remove(m);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}