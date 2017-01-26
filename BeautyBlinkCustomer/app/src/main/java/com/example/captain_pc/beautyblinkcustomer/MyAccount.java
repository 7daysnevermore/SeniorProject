package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by CaptainPC on 20/1/2560.
 */

public class MyAccount extends AppCompatActivity implements View.OnClickListener {

    TextView fname,lname,birthday,gender,phone,addr;
    Button edit;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();

        fname = (TextView) findViewById(R.id.firstname);
        lname = (TextView) findViewById(R.id.lastname);
        birthday = (TextView) findViewById(R.id.birthday);
        gender = (TextView) findViewById(R.id.gender);
        phone = (TextView) findViewById(R.id.phone);
        addr = (TextView) findViewById(R.id.address);

        edit = (Button) findViewById(R.id.btn_edit);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("customer").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    Toast.makeText(MyAccount.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    fname.setText(user.firstname);
                    lname.setText(user.lastname);
                    birthday.setText(user.birthday);
                    gender.setText(user.gender);
                    phone.setText(user.phone);
                    if (user.address_number != null || user.address_sub_district != null || user.address_district != null
                            || user.address_province != null || user.address_code != null){
                        addr.setText(user.address_number+" "+user.address_sub_district+", "+user.address_district+", "
                            +user.address_province+" "+user.address_code);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                startActivity(new Intent(MyAccount.this, EditProfile.class));
                break;

        }
    }
}
