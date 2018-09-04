package com.example.itgadmin.servermonitoringapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;
    private RelativeLayout parentLayout;
    private PopupWindow mPopupWindow;
    private Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        mContext = getBaseContext();
        final DatabaseReference dbRef = FirebaseDatabaseUtility.getDatabase().getReference("constants/");
        getTemperature();
        parentLayout = findViewById(R.id.settings_parent_layout);
        CardView cv1, cv2, cv3, cv4;
        cv1 = findViewById(R.id.settings_temperature);
        cv2 = findViewById(R.id.settings_file_path);
        cv3=  findViewById(R.id.settings_database);
        cv4 = findViewById(R.id.settings_notification);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Temperature", Toast.LENGTH_SHORT).show();
                LayoutInflater layoutInflater = (LayoutInflater) SettingsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_settings_temperature,null);
                TextView currentTemperatureView = popupView.findViewById(R.id.current_temperature_value);
                Button closeButton = popupView.findViewById(R.id.close_button);
                Button confirmButton = popupView.findViewById(R.id.confirm_button);
                final Spinner tempSpinner = popupView.findViewById(R.id.temperature_spinner);
                List<String> temperatureRange = new ArrayList<>();
                for(int i = 0; i <= 40; i++){
                    temperatureRange.add(i+ "\u00b0C");
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.temperature_spinner_item, temperatureRange);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tempSpinner.setAdapter(dataAdapter);
                tempSpinner.setSelection(dataAdapter.getPosition(constants.getThresholdTemperature()+ "\u00b0C"));
                String tempText = constants.getThresholdTemperature()+ "\u00b0C";
                currentTemperatureView.setText(tempText);
                mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Database updated. New Threshold: "+tempSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        Constants newConstants = new Constants();
                        newConstants.setFilePath(constants.getFilePath());
                        newConstants.setThresholdTemperature(tempSpinner.getSelectedItemPosition());
                        dbRef.setValue(newConstants);
                    }
                });
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });
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

    private void getTemperature(){
        DatabaseReference constantsRef = FirebaseDatabaseUtility.getDatabase().getReference("constants");
        ValueEventListener constantsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                constants = dataSnapshot.getValue(Constants.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        constantsRef.addValueEventListener(constantsListener);
    }
}
