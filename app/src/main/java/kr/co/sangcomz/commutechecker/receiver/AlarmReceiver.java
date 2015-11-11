package kr.co.sangcomz.commutechecker.receiver;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import kr.co.sangcomz.commutechecker.service.WifiScanService;

import static java.util.Calendar.DAY_OF_WEEK;


/**
 * Created by 정석원 on 2014-09-21.
 * 알람을 받는 Receiver
 */
public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager nm = null;
    Context mContext;
    boolean isCommute = false;

    private final int ALARM_ID = 4874;

    Calendar dateAndtime = Calendar.getInstance(); //오늘 요일 비교!

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;


        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        switch (dateAndtime.get(DAY_OF_WEEK)) {
            case 1:
                isCommute = false;
                break;
            case 2:
                isCommute = true;
                break;
            case 3:
                isCommute = true;
                break;
            case 4:
                isCommute = true;
                break;
            case 5:
                isCommute = true;
                break;
            case 6:
                isCommute = true;
                break;
            case 7:
                isCommute = false;
                break;
        }

        if (isCommute) {
            Intent i = new Intent(context, WifiScanService.class);
            context.startService(i);
        }
    }

}

