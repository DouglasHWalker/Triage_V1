package com.retrotech.triage.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.retrotech.triage.MainActivity;
import com.retrotech.triage.Models.Event;
import com.retrotech.triage.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class mRecyclerViewAdapter extends RecyclerView.Adapter<mViewHolder> {

    private List<Event> dataSet = Collections.emptyList();
    Context context;

    public mRecyclerViewAdapter(List<Event> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell, parent, false);
        mViewHolder holder = new mViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {

        // - get element from your dataset at this position
        String startTime = new SimpleDateFormat("h a").format(new Date(dataSet.get(position).getStartTime())).toLowerCase();

        // - replace the contents of the view with that element
        holder.timeTextView.setText(startTime);
        holder.titleTextView.setText(dataSet.get(position).getTitle());
//        holder.completeTextView.setText(Boolean.toString(dataSet.get(position).isComplete()));

        // animate(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //insert an item into the view at a predefined positon
    public void insert(int position, Event event){
        dataSet.add(position, event);
        notifyItemInserted(position);
    }

    public void remove(Event event){
        int position = dataSet.indexOf(event);
        dataSet.remove(position);
        notifyItemRemoved(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
