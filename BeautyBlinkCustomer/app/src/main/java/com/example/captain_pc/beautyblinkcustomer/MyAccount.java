package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by CaptainPC on 20/1/2560.
 */

public class MyAccount extends AppCompatActivity implements View.OnClickListener {

    TextView bbuilding,fname,lname,birthday,gender,phone,addr,changeimage;
    Button edit;
    ImageView userpro;
    private Uri imageUri = null;
    private int REQUEST_CAMERA =0,SELECT_FILE=1;
    private StorageReference storageReference,filepath;

    Toolbar toolbar;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        //up button
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();

        bbuilding = (TextView) findViewById(R.id.username);
        fname = (TextView) findViewById(R.id.firstname);
        lname = (TextView) findViewById(R.id.lastname);
        birthday = (TextView) findViewById(R.id.birthday);
        gender = (TextView) findViewById(R.id.gender);
        phone = (TextView) findViewById(R.id.phone);
        addr = (TextView) findViewById(R.id.address);
        changeimage = (TextView) findViewById(R.id.changeimage);
        userpro = (ImageView) findViewById(R.id.userpro);

        edit = (Button) findViewById(R.id.btn_edit);

        storageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,SELECT_FILE);
            }
        });

        mRootRef.child("customer").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    Toast.makeText(MyAccount.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    bbuilding.setText(user.address_building);
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
    protected  void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SELECT_FILE && resultCode == RESULT_OK){
            imageUri = data.getData();
            userpro.setImageURI(imageUri);


            filepath = storageReference.child("Promotion").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri dowloadUrl = taskSnapshot.getDownloadUrl();

                    //create root of Promotion
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("customer").child(uid);
                    mRootRef.child("profile").setValue(dowloadUrl.toString());

                    final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("customer-request1").child(uid);
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot datashot : dataSnapshot.getChildren()) {

                                String reqid = datashot.getKey();


                                if (datashot.getValue() == null) {
                                } else {

                                    mRef.child(reqid).child("userprofile").setValue(dowloadUrl.toString());

                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    final DatabaseReference mBeauRef = FirebaseDatabase.getInstance().getReference().child("beautician-received");
                    mBeauRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot startChild : dataSnapshot.getChildren()) {

                                String beauid = startChild.getKey();

                                for (DataSnapshot start : startChild.getChildren()) {

                                    String key = start.getKey();
                                    DataRequest user = start.getValue(DataRequest.class);

                                    if (user == null) {
                                    } else {
                                            mBeauRef.child(beauid).child(key).child("userprofile").setValue(dowloadUrl.toString());

                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });

        }

    }

    // up button method
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(MyAccount.this,MainActivity.class);
                intent.putExtra("menu","user");
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
