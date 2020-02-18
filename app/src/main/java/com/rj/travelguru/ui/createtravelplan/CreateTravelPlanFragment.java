package com.rj.travelguru.ui.createtravelplan;

import androidx.lifecycle.ViewModelProviders;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.rj.travelguru.CreateTravelPlan;
import com.rj.travelguru.R;

import java.util.Calendar;

public class CreateTravelPlanFragment extends Fragment {

    private Context mContext;
    public CreateTravelPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    private EditText sTime;
    private EditText eTime;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;
    private CreateTravelPlanViewModel mViewModel;

    public static CreateTravelPlanFragment newInstance() {
        return new CreateTravelPlanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.create_travel_plan_fragment, container, false);

        sTime = view.findViewById(R.id.crete_travel_plan_startTime);
        eTime = view.findViewById(R.id.crete_travel_plan_endTime);
        calendar = Calendar.getInstance();
        sTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
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
                timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateTravelPlanViewModel.class);
        // TODO: Use the ViewModel
    }

}
