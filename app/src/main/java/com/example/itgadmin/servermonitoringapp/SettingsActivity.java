package com.example.itgadmin.servermonitoringapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        mContext = getBaseContext();
        CardView cv1, cv2, cv3, cv4;
        cv1 = findViewById(R.id.settings_temperature);
        cv2 = findViewById(R.id.settings_file_path);
        cv3=  findViewById(R.id.settings_database);
        cv4 = findViewById(R.id.settings_notification);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Temperature", Toast.LENGTH_SHORT).show();
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "File", Toast.LENGTH_SHORT).show();
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "DB", Toast.LENGTH_SHORT).show();
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Notification", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
