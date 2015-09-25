package kr.co.sangcomz.commutechecker;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.sangcomz.commutechecker.adapter.CommuteTimeAdapter;
import kr.co.sangcomz.commutechecker.alarm.SetAlarm;
import kr.co.sangcomz.commutechecker.bean.CommuteTimeBean;
import kr.co.sangcomz.commutechecker.db.DBAdapter;
import kr.co.sangcomz.commutechecker.util.Dialogs;
import kr.co.sangcomz.commutechecker.util.TimeUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<CommuteTimeBean> commuteTimeBeans;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SetAlarm(this);
        txtTime = (TextView) findViewById(R.id.txt_time);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_walk);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(this, 4);
        commuteTimeBeans = new DBAdapter(this).getCommuteTime();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new CommuteTimeAdapter(commuteTimeBeans));

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00ffffff"));

        if (new DBAdapter(getApplicationContext()).getCommuteTime().size() > 0) {
            int time = new DBAdapter(getApplicationContext()).getCommuteTime().get(0).getCommuteTime();
            String lastAlarm = TimeUtils.getDateString("yyyyMMdd", time);
            String today = TimeUtils.getDateString("yyyyMMdd", (int) (System.currentTimeMillis() / 1000L));
            if (lastAlarm.equals(today)) {
                txtTime.setText("Today\n" + TimeUtils.getDateString("HH시 mm분", time));
            } else
                txtTime.setText("Today\n-");
        } else
            txtTime.setText("Today\n-");


        ////////////////////test Source/////////////

        Resources resources = getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotificationManager;
        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setColor(Color.parseColor("#4CAF50"))
                .setSmallIcon(R.mipmap.ic_walk)
//                .addAction(R.mipmap.ic_walk, "Previous", new PendingIntent()) // #0
                .setStyle(inboxStyle)
                .setContentTitle("출근중")
                .setContentText("즐거운 출근");
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
        //////////////////////
//        int commuteTime = (int) (System.currentTimeMillis() / 1000L);
//        for (int i = 0; i < 20; i++) {
//            new DBAdapter(this).insertCommuteTime(commuteTime);
//        }


//        final long unixTime = System.currentTimeMillis() / 1000L;
//        TimeUtils.getDateString("yyyy-MM-dd HH:mm:ss", (int) unixTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
//                int commuteTime = (int) (System.currentTimeMillis() / 1000L);
//                new DBAdapter(this).insertCommuteTime(commuteTime);
//                commuteTimeBeans = new DBAdapter(this).getCommuteTime();
//                recyclerView.setAdapter(new CommuteTimeAdapter(commuteTimeBeans));

                return true;
            case R.id.action_remove:
                Dialogs.showDeleteDialog(this,
                        "출근기록 삭제", "출근 기록이 모두 삭제됩니다.", "네", "아니오", recyclerView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
