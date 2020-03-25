package com.rj.travelguru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.location.aravind.getlocation.GeoLocator;

public class UserLocation extends AppCompatActivity {
    TextView mDisplay;
    Button mGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        mDisplay = (TextView)findViewById(R.id.displayText);
        mGetLocation = (Button)findViewById(R.id.getLocation);

        mGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    GeoLocator geoLocator = new GeoLocator(getApplicationContext(),UserLocation.this);
                    mDisplay.setText("Address :- " + geoLocator.getAddress());
                }
                catch (Exception e){
                    Toast.makeText(UserLocation.this,"Please Turn on Location Service..",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
