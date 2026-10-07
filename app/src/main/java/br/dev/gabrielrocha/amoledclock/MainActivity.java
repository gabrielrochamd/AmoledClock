package br.dev.gabrielrocha.amoledclock;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowInsetsController windowInsetsController = getWindow().getInsetsController();

        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars());
            windowInsetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

        TextView textViewHour = findViewById(R.id.hour), textViewMinute = findViewById(R.id.minute);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int width = getResources().getDisplayMetrics().widthPixels;
            textViewHour.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * .8f);
            textViewMinute.setTextSize(TypedValue.COMPLEX_UNIT_PX, width * .8f);
        } else {
            int height = getResources().getDisplayMetrics().heightPixels;
            textViewHour.setTextSize(TypedValue.COMPLEX_UNIT_PX, height * .8f);
            textViewMinute.setTextSize(TypedValue.COMPLEX_UNIT_PX, height * .8f);
        }

        Date date = new Date();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        textViewHour.setText(hourFormat.format(date));
        textViewMinute.setText(minuteFormat.format(date));

        Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Date date = new Date();

                textViewHour.setText(hourFormat.format(date));
                textViewMinute.setText(minuteFormat.format(date));

                Calendar now = Calendar.getInstance();

                long millis = now.get(Calendar.SECOND) * 1000L + now.get(Calendar.MILLISECOND);
                long delay = 60000L - millis;

                handler.postDelayed(this, delay);
            }
        };

        runnable.run();
    }
}
