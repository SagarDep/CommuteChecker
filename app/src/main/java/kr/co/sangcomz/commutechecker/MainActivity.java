package kr.co.sangcomz.commutechecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kr.co.sangcomz.commutechecker.alarm.SetAlarm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SetAlarm(this);
    }




}
