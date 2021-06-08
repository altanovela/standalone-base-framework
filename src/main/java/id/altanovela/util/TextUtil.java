package id.altanovela.util;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {
    
    /**
     * Hash Email Character partially
     * @param email
     * @return
     */
    public static String hashEmail(String email) {
        StringBuilder em = new StringBuilder(email);
        for(int i = 1; i < em.indexOf("@") - 1; i++) {
            em.setCharAt(i, '*');
        }
        return em.toString();
    }
    
    /**
     * Check if a text is HTML or Script
     * @param content
     * @return
     */
    public final static boolean isContainHtml(String content) {
        String data = StringUtils.trimToEmpty(content);
        return 
                data.contains("<") || 
                data.contains(">") || 
                data.contains("&gt;") || 
                data.contains("&lt;");
    }
}
