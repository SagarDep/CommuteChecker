package kr.co.sangcomz.commutechecker.receiver;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.DAY_OF_WEEK;


/**
 * Created by 정석원 on 2014-09-21.
 * 알람을 받는 Receiver
 */
public class AlarmReceiver extends BroadcastReceiver {
    ScanResult scanResult;
    WifiManager wifiManager;
    List apList;

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


        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
//        wifiManager.setWifiEnabled(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.getApplicationContext().registerReceiver(wifiReceiver, filter);
        wifiManager.startScan();
    }
    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                searchWifi();
            }
        }
    };

    public void searchWifi() {
        apList = wifiManager.getScanResults();
        if (wifiManager.getScanResults() != null) {
            int size = apList.size();
            for (int i = 0; i < size; i++) {
                scanResult = (ScanResult) apList.get(i);
                System.out.println("scanResult :::: " + ((ScanResult) apList.get(i)).SSID);
            }
        }
    }

}

