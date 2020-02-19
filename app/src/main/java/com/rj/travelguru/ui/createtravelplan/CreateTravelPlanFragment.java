package com.rj.travelguru.ui.createtravelplan;

import androidx.lifecycle.ViewModelProviders;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rj.travelguru.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    private TextView sTime;
    private TextView eTime;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;

    //Variables for firebase connectivity
    FirebaseFirestore db;
    private Button mSubmit;
    private EditText mAddress,mPlaces;
    private String addressKey="address",placesKey="places",startHourKey="startHour",startMinuteKey="startMinute",startAmPmKey="startAmPm";
    private String endHourKey="endHour",endMinuteKey="endMinute",endAmPmKey="endAmPm";
    private int startHour,startMinute;
    private int endHour,endMinute;
    private String startAmPm,endAmPm;
    private String Tag = "MyMessage";
    private String ErrorTag = "Error Message";

    private CreateTravelPlanViewModel mViewModel;

    public static CreateTravelPlanFragment newInstance() {
        return new CreateTravelPlanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.create_travel_plan_fragment, container, false);

        sTime = (TextView)view.findViewById(R.id.crete_travel_plan_startTime);
        eTime = (TextView)view.findViewById(R.id.crete_travel_plan_endTime);
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
                        sTime.setTextSize(20);
                        sTime.setText(String.format("%02d:%02d ",hourOfDay,minute)+amPm);
                        startHour = hourOfDay;
                        startMinute = minute;
                        startAmPm = amPm;
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
                        eTime.setTextSize(20);
                        eTime.setText(String.format("%02d:%02d ",hourOfDay,minute)+amPm);

                        endHour = hourOfDay;
                        endMinute = minute;
                        endAmPm = amPm;
                    }
                }, currentHour,currentMinute,false );
                timePickerDialog.show();
            }
        });
        mSubmit = view.findViewById(R.id.submit_btn);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                String addressString,placesString;
                mAddress = (EditText)view.findViewById(R.id.crete_travel_plan_address);
                mPlaces = (EditText)view.findViewById(R.id.crete_travel_plan_places);
                addressString = mAddress.getText().toString();
                placesString = mPlaces.getText().toString();

                //Create a new map object to send the data to firebase
                Map<String, Object> info = new HashMap<>();
                info.put(addressKey,addressString);
                info.put(placesKey,placesString);
                info.put(startHourKey,startHour);
                info.put(startMinuteKey,startMinute);
                info.put(startAmPmKey,startAmPm);
                info.put(endHourKey,endHour);
                info.put(endMinuteKey,endMinute);
                info.put(endAmPmKey,endAmPm);

                // add a new document and collection
                db.collection("TravelPlanInitialData")
                        .add(info)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(Tag,"DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(ErrorTag,"Error While Adding Document",e);
                            }
                        });
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
