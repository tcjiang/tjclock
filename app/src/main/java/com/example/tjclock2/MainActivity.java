package com.example.tjclock2;

import android.app.Activity;
import android.view.MotionEvent;
import android.widget.ViewFlipper;
import android.widget.TextView;
import android.view.WindowManager.LayoutParams;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.iigo.library.ClockHelper;
import com.iigo.library.ClockView;

public class MainActivity extends Activity  {
    private ViewFlipper viewFlipper;
    private float lastX;
    private ClockView clockView;
    private ClockHelper clockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView timeView = findViewById(R.id.tjClockTextView);
        final TextView ampmView = findViewById(R.id.tjClockAmPmView);
        viewFlipper = findViewById(R.id.viewflipper);
        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);

        clockView = findViewById(R.id.clockView);
        clockHelper = new ClockHelper(clockView);
        clockHelper.start();

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

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {

                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {

                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }
}
