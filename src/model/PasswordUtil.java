package model;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 120000;
    private static final int KEY_LENGTH = 256;

    public static String hash(String password) {

        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );

            byte[] hash = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec)
                .getEncoded();

            spec.clearPassword();

            return Base64.getEncoder().encodeToString(salt)
                + "$"
                + Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(
            String password, String stored) {

        try {
            String[] parts = stored.split("\\$", 2);

            byte[] salt =
                Base64.getDecoder().decode(parts[0]);

            byte[] expected =
                Base64.getDecoder().decode(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );

            byte[] actual = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec)
                .getEncoded();

            spec.clearPassword();

            if (actual.length != expected.length) {
                return false;
            }

            int diff = 0;

            for (int i = 0; i < actual.length; i++) {
                diff |= actual[i] ^ expected[i];
            }

            return diff == 0;

        } catch (Exception e) {
            return false;
        }
    }
}