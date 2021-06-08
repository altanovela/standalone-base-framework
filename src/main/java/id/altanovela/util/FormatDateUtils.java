package id.altanovela.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatDateUtils {
    public static String formatIndonesia(String pattern, Date date){
        if(date==null){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (pattern,new Locale("in", "ID"));
        return simpleDateFormat.format(date);
    }
}
