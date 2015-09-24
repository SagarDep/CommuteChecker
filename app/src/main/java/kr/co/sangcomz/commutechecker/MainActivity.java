package kr.co.sangcomz.commutechecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SetAlarm(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_walk);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(this, 4);
        commuteTimeBeans = new DBAdapter(this).getCommuteTime();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new CommuteTimeAdapter(commuteTimeBeans));

//        int commuteTime = (int) (System.currentTimeMillis() / 1000L);
//        for (int i = 0; i < 10; i++) {
//            new DBAdapter(this).insertCommuteTime(commuteTime);
//        }


        final long unixTime = System.currentTimeMillis() / 1000L;
        TimeUtils.getDateString("yyyy-MM-dd HH:mm:ss", (int) unixTime);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }


}
