package kr.co.sangcomz.commutechecker.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kr.co.sangcomz.commutechecker.receiver.AlarmReceiver;

import static java.util.Calendar.getInstance;

/**
 * Created by sangc on 2015-09-15.
 */
public class SetAlarm {

    Calendar cal = getInstance();
    private final int ALARM_ID = 4874;
    SimpleDateFormat compare_format = new SimpleDateFormat("H:mm");
    SimpleDateFormat dformat = new SimpleDateFormat("yyyy년 MM월 dd일");


    public SetAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent appIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);

        //9시 30분으로 맞추기
        Calendar current_time = getInstance();
        cal.set(current_time.get(Calendar.YEAR), current_time.get(Calendar.MONTH),
                current_time.get(Calendar.DAY_OF_MONTH), 9, 30, 0);
        if (dformat.format(cal.getTime()).compareTo(dformat.format(current_time.getTime())) <= 0) {
            if (compare_format.format(cal.getTime()).compareTo(compare_format.format(current_time.getTime())) < 0) {
                current_time.set(Calendar.HOUR_OF_DAY, 9);
                current_time.set(Calendar.MINUTE, 30);
                current_time.set(Calendar.SECOND, 0);
                current_time.add(Calendar.DATE, 1);
                cal.setTime(current_time.getTime());
            } else {
                current_time.set(Calendar.HOUR_OF_DAY, 10);
                current_time.set(Calendar.MINUTE, 22);
                current_time.set(Calendar.SECOND, 0);
                cal.setTime(current_time.getTime());
            }
        }
        System.out.println("cal.getTimeInMillis() :::: " + compare_format.format(cal.getTimeInMillis()));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, appIntent);
        //24*60*60*1000
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), 10000 , appIntent);
        //Log.d(String.valueOf(cal.getTimeInMillis()), String.valueOf(month));
    }

}
