package com.rj.travelguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPhoneNo;
    Button mRegisterBtn;
    TextView mLogin;
    private FirebaseFirestore db;
    private String Tag = "Success Messsage";
    private String ErrorTag = "Error Messsage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullname = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.PPassword);
        mRegisterBtn = findViewById(R.id.registerbtn);
        mPhoneNo = findViewById(R.id.phoneno);
        mLogin = findViewById(R.id.login);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullname.getText().toString();
                String phoneNo = mPhoneNo.getText().toString();
                final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(EMAIL_PATTERN);
                Matcher matcher = pattern.matcher(email);

                final String nameKey = "name";
                final String emailKey = "email";
                final String phoneKey = "phone";
                final String passwordkey = "password";

                if(TextUtils.isEmpty(name)){
                    mFullname.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(phoneNo)){
                    mPhoneNo.setError("Phone Number is Required.");
                    return;
                }

                if(!TextUtils.isDigitsOnly(phoneNo)){
                    mPhoneNo.setError("Enter a valid Phone Number");
                    return;
                }

                if (phoneNo.length() < 10 || phoneNo.length() > 10) {
                    mPhoneNo.setError("Enter a valid Phone Number");
                    return;
                }

                if(!matcher.matches()){
                    mPhoneNo.setError("Enter a valid Email Address");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is Required.");
                    return;
                }

                if(password.length() < 6 && password.length() >16){
                    mPassword.setError("Password must be of 6-16 characters");
                    return;
                }


                //register user in firbasse

                
            }
        });
    }
}
