package model;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {

    private static final String CARI_USER =
        "FROM User u WHERE LOWER(u.nama) = LOWER(:nama)";

    public boolean registrasi(String nama, String password) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            boolean sudahAda = session.createQuery(CARI_USER, User.class)
                .setParameter("nama", nama)
                .uniqueResultOptional()
                .isPresent();

            if (sudahAda) {
                tx.rollback();
                return false;
            }

            session.persist(new User(nama, PasswordUtil.hash(password)));
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public boolean login(String nama, String password) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = session.createQuery(CARI_USER, User.class)
                .setParameter("nama", nama)
                .uniqueResultOptional()
                .orElse(null);

            if (user == null) {
                tx.rollback();
                return false;
            }

            String passwordDB = user.getPassword();

            if (PasswordUtil.verify(password, passwordDB)) {
                tx.rollback();
                return true;
            }

            // Akun lama dengan password teks biasa: ubah jadi hash
            if (password.equals(passwordDB)) {
                user.setPassword(PasswordUtil.hash(password));
                tx.commit();
                return true;
            }

            tx.rollback();
            return false;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}