package com.example.itgadmin.servermonitoringapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TemperatureDataAdapter  extends RecyclerView.Adapter<TemperatureDataAdapter.ViewHolder> {
    private ArrayList<TemperatureData> mDataSet;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView temperatureView;
        TextView dateTimeView;

        ViewHolder(LinearLayout view) {
            super(view);
            temperatureView = view.findViewById(R.id.temperature_value);
            dateTimeView = view.findViewById(R.id.date_time_value);
        }
    }

    TemperatureDataAdapter(ArrayList<TemperatureData> myDataSet) {
        mDataSet = myDataSet;
    }

    @Override
    public TemperatureDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.temperature_list_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.temperatureView.setText(mDataSet.get(position).getTemperature());
        holder.dateTimeView.setText(mDataSet.get(position).getDatetime());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

