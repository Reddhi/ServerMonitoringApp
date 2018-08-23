package com.example.itgadmin.servermonitoringapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    private TextView tempView, timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();
        tempView = findViewById(R.id.temperature_view);
        timeView = findViewById(R.id.timestamp_view);
        Button historyButton = findViewById(R.id.show_history_button);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data");
        Query lastQuery = myRef.orderByKey().limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TemperatureData temp = snapshot.getValue(TemperatureData.class);
                    DecimalFormat df = new DecimalFormat("00");
                    df.setRoundingMode(RoundingMode.HALF_UP);
                    String temperatureText = df.format(temp.getTemperature())+"\u00b0C";
                    tempView.setText(temperatureText);
                    timeView.setText(temp.getDatetime());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Handle possible errors.
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistoryActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
