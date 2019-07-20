package com.example.tjclock2;

import android.app.Activity;
import java.util.Timer;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Calendar;
import java.util.TimerTask;

public class MainActivity extends Activity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView timeView = findViewById(R.id.tjClockTextView);
        final TextView ampmView = findViewById(R.id.tjClockAmPmView);

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn12Format = rightNow.get(Calendar.HOUR);
                int currentMinIn12Format = rightNow.get(Calendar.MINUTE);
                int currentNoon = rightNow.get(Calendar.AM);
                String currentTime = currentHourIn12Format + ":" + currentMinIn12Format;
                timeView.setText(currentTime);
                ampmView.setText(currentNoon == 0 ? "am" : "pm");
            }
        };
        Timer timer = new Timer("Timer");

        long delay  = 1000L; // delay 1 sec
        long period = 1000L; // refresh every sec
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
