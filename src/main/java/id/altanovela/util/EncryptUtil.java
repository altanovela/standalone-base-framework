package id.altanovela.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtil {

    private static final String key  = "988ED0C99DFD959E40B7AE95560AD81F";
    private static final String salt = "H7g0oLkEw3wf52fs52g3hbG1989";
    
    static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
    
    /**
     * Encrypt Text
     * @param text
     * @return
     */
    public static String encrypt(String text) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setAlgorithm("PBEWithSHA1AndDESede");
            encryptor.setPassword(key);
            encryptor.setSaltGenerator(new StringFixedSaltGenerator(salt));
            return encryptor.encrypt(text);
        } catch (Exception e) {
            logger.error("Error Encrypt : {}", e.getMessage());
        }
        return text;
    }
    
    /**
     * Decrypt Encrypted Text
     * @param encryptedText
     * @return
     */
    public static String decrypt(String encryptedText) {
        try {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setAlgorithm("PBEWithSHA1AndDESede");
            encryptor.setPassword(key);
            encryptor.setSaltGenerator(new StringFixedSaltGenerator(salt));
            return encryptor.decrypt(encryptedText);
        } catch (Exception e) {
            logger.error("Error Decrypt : {}", e.getMessage());
        }
        return encryptedText;
    }
}
