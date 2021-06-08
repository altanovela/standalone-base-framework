package id.altanovela;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptorTest {

    /**
     * Encrypt your password here
     * @param args
     */
    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        System.out.println(enc.encode(new StringBuilder("operator")));
        
        StringBuilder em = new StringBuilder("admin@gmail.com");
        for(int i = 1; i < em.indexOf("@") - 1; i++) {
            em.setCharAt(i, '*');
        }
        System.out.println(em.toString());
    }
}