import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Encryption {

    private static Encryption instance;
    private Map<Integer, String> archivesToPasswords;

    private Encryption() {
        archivesToPasswords = new HashMap<>();
    }

    public static Encryption getInstance() {
        if (instance == null) {
            instance = new Encryption();
        }
        return instance;
    }

    public void update(int archiveId, String password) {
        archivesToPasswords.put(archiveId, password);
    }

    public boolean validatePassword(int archiveId, String password) {
        return archivesToPasswords.containsKey(archiveId) && archivesToPasswords.get(archiveId).equals(password);
    }

    public void encrypt(int archiveId, String password, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, password, inputFile, outputFile);
        update(archiveId, password);
    }

    public void decrypt(int archiveId, String password, File inputFile, File outputFile)
            throws CryptoException {
        if (!validatePassword(archiveId, password)) {
            throw new CryptoException("Invalid password for archive " + archiveId);
        }
        doCrypto(Cipher.DECRYPT_MODE, password, inputFile, outputFile);
        // If decryption is successful, remove the entry from the map
        archivesToPasswords.remove(archiveId);
    }

    private void doCrypto(int cipherMode, String password, File inputFile,
                          File outputFile) throws CryptoException {
        try {
            // Derive a key from the password using a key derivation function (e.g., PBKDF2)
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray());
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                 | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException | IOException | InvalidKeySpecException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
