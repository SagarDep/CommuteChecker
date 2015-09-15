package kr.co.sangcomz.commutechecker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kr.co.sangcomz.commutechecker.alarm.SetAlarm;

/**
 * 기기 새로 시작시 오는 신호를 받는 Receiver
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new SetAlarm(context);
    }
}


