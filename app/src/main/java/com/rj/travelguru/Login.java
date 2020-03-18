package com.rj.travelguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText mUsername,mPassword;
    private Button mLogin;
    private TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogin = (Button)findViewById(R.id.loginbtn);
        mRegister = (TextView)findViewById(R.id.create_account);
        mUsername = (EditText)findViewById(R.id.Username);
        mPassword = (EditText)findViewById(R.id.PPassword);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                final String uEnteredUsername = mUsername.getText().toString().trim();
                final String uEnteredPassword = mPassword.getText().toString().trim();

                if(uEnteredUsername.isEmpty()) {
                    mUsername.setError("UserName is Required");
                    return;
                }
                if(uEnteredPassword.isEmpty()) {
                    mPassword.setError("Password is Required");
                    return;
                }
                if(uEnteredPassword.length() <6 || uEnteredPassword.length() > 16) {
                    mPassword.setError("Password Must within 6-16 Characters");
                }

                //Proceed to fetch data
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(uEnteredUsername);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String passwordFromDb = dataSnapshot.child(uEnteredUsername).child("username").getValue(String.class);
                            if(passwordFromDb.equals(uEnteredPassword)){
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                            }
                            else{
                                mPassword.setError("Wrong Password");
                                mPassword.requestFocus();
                                return;
                            }
                        }
                        else{
                            mUsername.setError("Wrong Username");
                            mUsername.requestFocus();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
