package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CaptainPC on 24/1/2560.
 */

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    EditText firstname, lastname, phone, address_number,
            address_sub_district, address_district, address_province, address_code;

    Button editprofile;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();

        firstname = (EditText) findViewById(R.id.fname);
        lastname = (EditText) findViewById(R.id.lname);
        phone = (EditText) findViewById(R.id.phone);
        address_number = (EditText) findViewById(R.id.addressnum);
        address_sub_district = (EditText) findViewById(R.id.sub_district);
        address_district = (EditText) findViewById(R.id.district);
        address_province = (EditText) findViewById(R.id.province);
        address_code = (EditText) findViewById(R.id.code);

        editprofile = (Button) findViewById(R.id.btn_editprofile);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("customer").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    Toast.makeText(EditProfile.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    firstname.setText(user.firstname);
                    lastname.setText(user.lastname);
                    phone.setText(user.phone);
                    address_number.setText(user.address_number);
                    address_sub_district.setText(user.address_sub_district);
                    address_district.setText(user.address_district);
                    address_province.setText(user.address_province);
                    address_code.setText(user.address_code);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        editprofile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_editprofile:
                Editprofile();
                break;

        }

    }

    public void Editprofile(){

        final String fname = firstname.getText().toString();
        final String lname = lastname.getText().toString();
        final String in_phone = phone.getText().toString();
        final String addr_num = address_number.getText().toString();
        final String addr_s_dist = address_sub_district.getText().toString();
        final String addr_dist = address_district.getText().toString();
        final String addr_province = address_province.getText().toString();
        final String addr_code = address_code.getText().toString();

        //Get current to pull UID and email
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //create root of Beautician
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mUsersRef = mRootRef.child("beautician");

        HashMap<String, Object> UserUpdate = new HashMap<>();
        UserUpdate.put("firstname", fname);
        UserUpdate.put("lastname", lname);
        UserUpdate.put("phone", in_phone);
        UserUpdate.put("address_number", addr_num);
        UserUpdate.put("address_sub_district", addr_s_dist);
        UserUpdate.put("address_district", addr_dist);
        UserUpdate.put("address_province", addr_province);
        UserUpdate.put("address_code", addr_code);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(mFirebaseUser.getUid(), UserUpdate);

        mUsersRef.updateChildren(childUpdates);

        startActivity(new Intent(EditProfile.this, MyAccount.class));

    }
}
