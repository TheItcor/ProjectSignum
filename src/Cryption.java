import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cryption {
    private static final String ALGORITHM = "AES";
    private static final String key = "I4n*6H8ULhGCfNY_sq5h2aFRUf*u-f8KlY8VZRFRN5rHiXa&Jg8QR4NAU05UutJevfmj6XV37AJ0gv*TP2o&2uOhs0DS7Nd&aAdK";

    private static SecretKeySpec getSecretKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] aesKey = new byte[16]; // Используем 128-битный ключ
        System.arraycopy(keyBytes, 0, aesKey, 0, Math.min(keyBytes.length, aesKey.length));
        return new SecretKeySpec(aesKey, ALGORITHM);
    }

    public static String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes); // Кодируем в Base64 для удобства
        } catch (Exception e) {
            throw new RuntimeException("ERROR: ошибка шифрования: " + e.getMessage(), e);
        }
    }

    public static String decrypt(String encryptedInput) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput); // Декодируем из Base64
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("ERROR: ошибка расшифрования: " + e.getMessage(), e);
        }
    }
}
