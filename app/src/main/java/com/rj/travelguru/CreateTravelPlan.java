package com.rj.travelguru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.rj.travelguru.ui.createtravelplan.CreateTravelPlanFragment;

import java.util.Calendar;

public class CreateTravelPlan extends AppCompatActivity {

    EditText sTime;
    EditText eTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_travel_plan_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CreateTravelPlanFragment.newInstance())
                    .commitNow();


            sTime = findViewById(R.id.crete_travel_plan_startTime);
            eTime = findViewById(R.id.crete_travel_plan_endTime);
            calendar = Calendar.getInstance();
            sTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    currentMinute = calendar.get(Calendar.MINUTE);
                    timePickerDialog = new TimePickerDialog(CreateTravelPlan.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            if(hourOfDay >= 12) {amPm = "PM";}
                            else {amPm = "AM";}
                            sTime.setText(String.format("%02d:%02d ",hourOfDay,minute)+amPm);
                        }
                    }, currentHour,currentMinute,false );
                    timePickerDialog.show();
                }
            });
            eTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar = Calendar.getInstance();
                    currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    currentMinute = calendar.get(Calendar.MINUTE);
                    timePickerDialog = new TimePickerDialog(CreateTravelPlan.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            if(hourOfDay >= 12) {amPm = "PM";}
                            else {amPm = "AM";}
                            eTime.setText(String.format("%02d:%02d ",hourOfDay,minute)+amPm);
                        }
                    }, currentHour,currentMinute,false );
                    timePickerDialog.show();
                }
            });
        }
    }
}
