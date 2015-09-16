package kr.co.sangcomz.commutechecker.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import kr.co.sangcomz.commutechecker.db.DBAdapter;
import kr.co.sangcomz.commutechecker.receiver.AlarmReceiver;
import kr.co.sangcomz.commutechecker.util.TimeUtils;

import static java.util.Calendar.getInstance;

/**
 * Created by sangc on 2015-09-15.
 */
public class SetAlarm {

    Calendar cal = getInstance();
    private final int ALARM_ID = 4874;


    public SetAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent appIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);

        //9시 30분으로 맞추기
        Calendar currentTime = Calendar.getInstance();
        cal.set(currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.DAY_OF_MONTH), 9, 30, 0);
        String strCurTime = TimeUtils.getDateString("HHmm", (int) (currentTime.getTimeInMillis()/1000L));
        int intCurTime = Integer.valueOf(strCurTime);
        boolean isToday = false;
        if (new DBAdapter(context).getCommuteTime().size() > 0) {
            int time = new DBAdapter(context).getCommuteTime().get(0).getCommuteTime();
            String lastAlarm = TimeUtils.getDateString("yyyyMMdd", time);
            String today = TimeUtils.getDateString("yyyyMMdd", (int) (System.currentTimeMillis()/1000L));
            System.out.println(lastAlarm + " " + today);
            if (lastAlarm.equals(today))
                isToday = true;
        }

        System.out.println("isToday :::: " + isToday);
        if (!(intCurTime >= 930 && intCurTime < 1030) || isToday) {
            cal.add(Calendar.DATE, 1);
        }

        //임시 테스트///
//        cal.set(currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH),
//                currentTime.get(Calendar.DAY_OF_MONTH), 9, 30, 0);
        /////////////////////////////////////////////////////////
        System.out.println("alarmTime :::: " + TimeUtils.getDateString("yyyyMMddHHmm", (int) (cal.getTimeInMillis()/1000L)));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, appIntent);
        //24*60*60*1000
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), 10000 , appIntent);
        //Log.d(String.valueOf(cal.getTimeInMillis()), String.valueOf(month));
    }

}
