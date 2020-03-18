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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPhoneNo,mUsername;
    Button mRegisterBtn;
    TextView mLogin;
    private FirebaseFirestore db;
    FirebaseDatabase root;
    DatabaseReference reference;
    private String Tag = "Success Messsage";
    private String ErrorTag = "Error Messsage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullname =(EditText) findViewById(R.id.fullname);
        mEmail =(EditText) findViewById(R.id.Email);
        mPassword =(EditText) findViewById(R.id.PPassword);
        mUsername = (EditText)findViewById(R.id.Username);
        mRegisterBtn =(Button) findViewById(R.id.registerbtn);
        mPhoneNo =(EditText) findViewById(R.id.phoneno);
        mLogin =(TextView) findViewById(R.id.LoginTxt);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullname.getText().toString();
                String phoneNo = mPhoneNo.getText().toString();
                String username = mUsername.getText().toString().trim();
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

                if(TextUtils.isEmpty(username)){

                    mUsername.setError("UserName is Required.");
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

                if (phoneNo.length() != 10) {
                    mPhoneNo.setError("Enter a valid Phone Number");
                    return;
                }

                if(!matcher.matches()){
                    mEmail.setError("Enter a valid Email Address");
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


                //register user in firbasse firestore

                /*db = FirebaseFirestore.getInstance();
                Map<String,Object> userData = new HashMap<>();
                userData.put(nameKey,name);
                userData.put(emailKey,email);
                userData.put(phoneKey,phoneNo);
                userData.put(passwordkey,password);

                db.collection("Users")
                        .add(userData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(Tag,"DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(Register.this,"Successfull",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this,"UnSuccessfull",Toast.LENGTH_SHORT).show();
                                Log.w(ErrorTag,"Error While Adding Document",e);
                            }
                        });*/

                //sending data into realtime database

                root = FirebaseDatabase.getInstance();
                reference = root.getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            mUsername.setError("User Already Exists");
                            mUsername.requestFocus();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                UserHelperClass user = new UserHelperClass(name,username,phoneNo,email,password);
                reference.child(username).setValue(user);

                Intent i = new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });



        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
