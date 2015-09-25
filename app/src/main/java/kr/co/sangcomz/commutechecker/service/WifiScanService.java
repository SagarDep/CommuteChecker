package kr.co.sangcomz.commutechecker.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import kr.co.sangcomz.commutechecker.R;
import kr.co.sangcomz.commutechecker.db.DBAdapter;
import kr.co.sangcomz.commutechecker.util.TimeUtils;


public class WifiScanService extends Service {
    ScanResult scanResult;
    WifiManager wifiManager;
    List apList;
    DBAdapter dbAdapter;
    Handler handler;
    Runnable runnable;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_walk)
                .setContentTitle("출근중")
                .setContentText("즐거운 출근");
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

        dbAdapter = new DBAdapter(this);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        runnable = new Runnable() {
            @Override
            public void run() {
                wifiManager.startScan();
                handler.postDelayed(this, 60 * 2000);
//                handler.postDelayed(this, 1000);
            }
        };
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
//        wifiManager.setWifiEnabled(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        handler = new Handler();
        handler.post(runnable);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return null;
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
                if (((ScanResult) apList.get(i)).SSID.equals("hansigan_dev_5")) {
                    Intent intent = new Intent(this, WifiScanService.class);
                    stopService(intent);
                    completeCommute();
                }
            }
        }
    }

    public void completeCommute() {
        int time;
        String lastAlarm ="lastAlarm";
        String today="today";
        if (new DBAdapter(getApplicationContext()).getCommuteTime().size() > 0) {
            time = new DBAdapter(getApplicationContext()).getCommuteTime().get(0).getCommuteTime();
            lastAlarm = TimeUtils.getDateString("yyyyMMdd", time);
            today = TimeUtils.getDateString("yyyyMMdd", (int) (System.currentTimeMillis() / 1000L));
        }
        if (!lastAlarm.equals(today)) {
            int commuteTime = (int) (System.currentTimeMillis() / 1000L);

            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            //long[] pattern = {200, 2000, 100, 1700, 200, 2000, 100, 1700, 200, 2000, 100, 1700};          //  무진동, 진동 순이다.
            //vibe.vibrate(pattern, 1);                                 // 패턴을 지정하고 반복횟수를 지정  숫자 2가 계속 반복이다.
            vibe.vibrate(1000);

            mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.mipmap.ic_walk)
                    .setContentTitle("출근 완료")
                    .setContentText("출근 시간 : " + String.valueOf(TimeUtils.getDateString("HH:mm", commuteTime)));
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, mBuilder.build());
            dbAdapter.insertCommuteTime(commuteTime);
            handler.removeCallbacks(runnable);
        }


    }
}
