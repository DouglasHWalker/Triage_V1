package com.retrotech.triage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.retrotech.triage.Models.Event;
import com.retrotech.triage.Utilities.mRecyclerViewAdapter;
import com.retrotech.triage.customviews.MonthlyView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Event> dataSet = new LinkedList<Event>();

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

        //dataset
        dataSet = initData();

        // Recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter recycleAdapter = new mRecyclerViewAdapter(dataSet, getApplication());
        recyclerView.setAdapter(recycleAdapter);


    }


    private List<Event> initData() {

        Calendar workingCal = Calendar.getInstance();
        List<Event> data = new LinkedList<Event>();

        data.add(new Event("Interaction Design Revision", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Data Structures: Lecture", workingCal.getTimeInMillis() + 2000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Interaction Design Medical Assignment", workingCal.getTimeInMillis() + 4000000, workingCal.getTimeInMillis(), null, null, true));
        data.add(new Event("Interface Experience: Marshall", workingCal.getTimeInMillis() + 6000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 5", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 6", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 1", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 2", workingCal.getTimeInMillis() + 20000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 3", workingCal.getTimeInMillis() + 4000000, workingCal.getTimeInMillis(), null, null, true));
        data.add(new Event("Event 4", workingCal.getTimeInMillis() + 6000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 5", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 6", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 1", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 2", workingCal.getTimeInMillis() + 2000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 3", workingCal.getTimeInMillis() + 4000000, workingCal.getTimeInMillis(), null, null, true));
        data.add(new Event("Event 4", workingCal.getTimeInMillis() + 6000000, workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 5", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));
        data.add(new Event("Event 6", workingCal.getTimeInMillis(), workingCal.getTimeInMillis(), null, null, false));


        return data;
    }

}
