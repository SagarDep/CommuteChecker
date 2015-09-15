package kr.co.sangcomz.commutechecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.sangcomz.commutechecker.adapter.CommuteTimeAdapter;
import kr.co.sangcomz.commutechecker.alarm.SetAlarm;
import kr.co.sangcomz.commutechecker.bean.CommuteTimeBean;
import kr.co.sangcomz.commutechecker.db.DBAdapter;
import kr.co.sangcomz.commutechecker.util.TimeUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    ArrayList<CommuteTimeBean> commuteTimeBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SetAlarm(this);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(this, 3);
        commuteTimeBeans = new DBAdapter(this).getCommuteTime();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new CommuteTimeAdapter(commuteTimeBeans));



        final long unixTime = System.currentTimeMillis() / 1000L;
        TimeUtils.getDateString("yyyy-MM-dd HH:mm:ss", (int) unixTime);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
