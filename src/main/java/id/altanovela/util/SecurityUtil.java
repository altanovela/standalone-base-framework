package id.altanovela.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    /**
     * Password Encryptor Utility
     * @param text
     * @return
     */
    public static String encrypt(String text) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        return enc.encode(text);
    }
    
    /**
     * Comparing Same
     * @param raw
     * @param encoded
     * @return
     */
    public static boolean same(String raw, String encoded) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        return enc.matches(raw, encoded);
    }
}
