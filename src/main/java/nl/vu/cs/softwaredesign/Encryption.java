import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import java.security.SecureRandom;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileNotFoundException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Encryption {

    private static final int SALT_LENGTH = 16; // Define the length of the salt

    private static Encryption instance;
    private Map<Integer, byte[]> archivesToPasswords; // Change the map value to byte array for storing salt

    private Encryption() {
        archivesToPasswords = new HashMap<>();
    }

    public static Encryption getInstance() {
        if (instance == null) {
            instance = new Encryption();
        }
        return instance;
    }

    private void update(int archiveId, byte[] salt) {
        archivesToPasswords.put(archiveId, salt);
    }

    private boolean validatePassword(int archiveId, byte[] salt, String password) {
        if (!archivesToPasswords.containsKey(archiveId)) {
            return false; // Archive ID not found
        }

        byte[] storedSalt = archivesToPasswords.get(archiveId);
        // Compare stored salt with provided salt
        if (!Arrays.equals(salt, storedSalt)) {
            return false; // Salt mismatch
        }

        // Generate salt from provided password and compare it with stored salt
        byte[] generatedSalt = generateSalt(password, SALT_LENGTH);
        //System.out.println("Generate salt: " + Arrays.toString(generatedSalt));
        return Arrays.equals(storedSalt, generatedSalt);
    }



    public void encrypt(int archiveId, String password, File inputFile, File outputFile)
            throws CryptoException {
        byte[] salt = generateSalt(password, SALT_LENGTH); // Generate salt
        doCrypto(Cipher.ENCRYPT_MODE, password, salt, inputFile, outputFile);
        update(archiveId, salt);
    }

    public void decrypt(int archiveId, String password, File inputFile, File outputFile)
            throws CryptoException {
        byte[] salt = archivesToPasswords.get(archiveId); // Retrieve stored salt
        if (salt == null) {
            throw new CryptoException("Salt not found for archive " + archiveId);
        }

        //System.out.println("Retrieved salt: " + Arrays.toString(salt)); // Log retrieved salt

        if (!validatePassword(archiveId, salt, password)) {
            throw new CryptoException("Invalid password for archive " + archiveId);
        }

        System.out.println("Password validated successfully."); // Log successful password validation

        doCrypto(Cipher.DECRYPT_MODE, password, salt, inputFile, outputFile);
        // If decryption is successful, remove the entry from the map
        archivesToPasswords.remove(archiveId);
    }


    // Inside doCrypto method
    private void doCrypto(int cipherMode, String password, byte[] salt, File inputFile,
                          File outputFile) throws CryptoException {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Generate a random IV
            byte[] iv = new byte[cipher.getBlockSize()];
            System.arraycopy(salt, 0, iv, 0, iv.length); // Use the first 16 bytes of the salt as IV
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            cipher.init(cipherMode, secretKey, ivParameterSpec);

            FileInputStream inputStream = new FileInputStream(inputFile);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            cipherInputStream.close();
            outputStream.close();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                 | InvalidKeyException | InvalidAlgorithmParameterException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
}


    private byte[] generateSalt(String password, int length) {
        try {
            // Use SHA-256 hash function
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password bytes
            byte[] passwordBytes = password.getBytes();
            byte[] hash = digest.digest(passwordBytes);

            // Truncate or extend the hash to the desired salt length
            byte[] salt = new byte[length];
            System.arraycopy(hash, 0, salt, 0, Math.min(hash.length, length));
            return salt;
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException
            e.printStackTrace();
            return null;
        }
    }
}