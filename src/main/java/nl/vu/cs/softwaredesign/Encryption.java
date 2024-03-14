import javax.crypto;
import java.io.File;
public class Encryption{

    private static Encryption instance;
    private static Map<Integer, String> archivesToPasswords = new HashMap<>();

    private Encryption() {
    }//edit

    public static Encryption getInstance() {
        if (instance == null) {
            instance = new Encryption();
        }
        return instance;
    }//singleton design pattern

    public void update(int archiveId, String password){
        archivesToPasswords.put(archiveId, password);
    }//integrate it with another method

    public boolean validatePassword(int archiveId, String password){
        return archivesToPasswords.get(archiveId).equals(password);
    }

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
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
                 | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }//need to figure out where to generate secretKey
}
