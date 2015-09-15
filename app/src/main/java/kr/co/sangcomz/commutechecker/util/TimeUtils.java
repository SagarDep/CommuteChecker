package kr.co.sangcomz.commutechecker.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by sangc on 2015-09-15.
 */
public class TimeUtils {
    public static String getDateString(String form, int time) {
        Date timestampDate = new Date(time * 1000L);
        SimpleDateFormat format = new SimpleDateFormat(form);
        format.setTimeZone(TimeZone.getDefault());
        return format.format(timestampDate);
    }
}
