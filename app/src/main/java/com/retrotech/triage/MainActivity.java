package com.retrotech.triage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.retrotech.triage.customviews.MonthlyView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        View decorView = this.getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.triage_main);

        init();
    }

    private void init() {

        final Button todayButton = findViewById(R.id.TodayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                MonthlyView mv = findViewById(R.id.monthlyView);
                Calendar c = mv.getActiveCalendar();
                c.add(Calendar.MONTH, 1);
                mv.setActiveCalendar(c);
            }
        });
    }

    @Override
    protected void onResume() {


        super.onResume();

    }


}
