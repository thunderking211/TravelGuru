package com.rj.travelguru.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rj.travelguru.R;
import com.rj.travelguru.ui.createtravelplan.CreateTravelPlanFragment;

public class HomeFragment extends Fragment {

    private Context mContext;
    public HomeFragment() {
        //Required empty constructor
    }

    private Button mButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        mButton = view.findViewById(R.id.goto_create_travel_plan);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mobile_navigation, new CreateTravelPlanFragment());
                ft.commit();
            }
        });
        return view;
    }
}