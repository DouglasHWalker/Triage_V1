package com.retrotech.triage.Utilities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.retrotech.triage.R;

public class mViewHolder extends RecyclerView.ViewHolder {

    // views
    public TextView timeTextView;
    public TextView titleTextView;
    public Button completeTextView;

    public mViewHolder(View v) {
        super(v);
        timeTextView = v.findViewById(R.id.timeTextView);
        titleTextView = v.findViewById(R.id.titleTextView);
        completeTextView = v.findViewById(R.id.completeTextView);
    }

}
