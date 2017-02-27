package com.example.captain_pc.beautyblinkcustomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateRequest extends AppCompatActivity {

    private Spinner chService;
    private int SELECT_FILE =1;
    private EditText eEvent,eNperson,eMaxp,eDateD,eDateM,eDateY,eTime,eLocation,eSpecial;
    private Toolbar toolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    String servicecode = null;
    private Button btnReq;
    private ImageView addP;
    private String kSer;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private StorageReference storageReference,filepath;
    private DatabaseReference databaseReference;
    private String username,userprofile;
    ArrayList<HashMap<String,String>> BeauID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request");
        progressDialog = new ProgressDialog(this);

        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("customer").child(mFirebaseUser.getUid());
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null){
                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                }else {
                    username = user.firstname+" "+user.lastname;
                    userprofile = user.profile;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addP = (ImageView)findViewById(R.id.addPic);
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,SELECT_FILE);
            }
        });


        chService = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.pro_sercive,android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        chService.setAdapter(adapter);

        chService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                kSer=parent.getItemAtPosition(position).toString();
                DatabaseReference userRootRef = FirebaseDatabase.getInstance().getReference().child("profilepromote");
                if(kSer.equals("MakeupandHair"))
                { servicecode = "S01";
                    BeauID = new ArrayList<>();
                    Query databaseQuery = userRootRef.orderByChild("S01").startAt(1);
                    databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot startChild : dataSnapshot.getChildren()){

                                DataProfilePromote user = startChild.getValue(DataProfilePromote.class);

                                if(user == null){
                                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                                }else {

                                    HashMap<String,String> hashID = new HashMap<>();
                                    hashID.put("uid",user.uid);
                                    hashID.put("price",String.valueOf(user.S01));
                                    BeauID.add(hashID);
                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });}
                else if (kSer.equals("HairStyle"))
                { servicecode = "S02";
                    BeauID = new ArrayList<>();
                    Query databaseQuery = userRootRef.orderByChild("S02").startAt(1);
                    databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot startChild : dataSnapshot.getChildren()){

                                DataProfilePromote user = startChild.getValue(DataProfilePromote.class);

                                if(user == null){
                                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                                }else {

                                    HashMap<String,String> hashID = new HashMap<>();
                                    hashID.put("uid",user.uid);
                                    hashID.put("price",String.valueOf(user.S02));
                                    BeauID.add(hashID);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });}
                else if(kSer.equals("Makeup"))
                { servicecode = "S03";
                    BeauID = new ArrayList<>();
                    Query databaseQuery = userRootRef.orderByChild("S03").startAt(1);
                    databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot startChild : dataSnapshot.getChildren()){

                                DataProfilePromote user = startChild.getValue(DataProfilePromote.class);

                                if(user == null){
                                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                                }else {

                                    HashMap<String,String> hashID = new HashMap<>();
                                    hashID.put("uid",user.uid);
                                    hashID.put("price",String.valueOf(user.S03));
                                    BeauID.add(hashID);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                { servicecode = "S04";
                    BeauID = new ArrayList<>();
                    Query databaseQuery = userRootRef.orderByChild("S04").startAt(1);
                    databaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot startChild : dataSnapshot.getChildren()){

                                DataProfilePromote user = startChild.getValue(DataProfilePromote.class);

                                if(user == null){
                                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                                }else {

                                    HashMap<String,String> hashID = new HashMap<>();
                                    hashID.put("uid",user.uid);
                                    hashID.put("price",String.valueOf(user.S04));
                                    BeauID.add(hashID);
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        eEvent =(EditText)findViewById(R.id.event);
        eNperson=(EditText)findViewById(R.id.nPerson);
        eMaxp=(EditText)findViewById(R.id.max_price);
        eDateD = (EditText) findViewById(R.id.dateTo_day);
        eDateM = (EditText) findViewById(R.id.dateTo_month);
        eDateY = (EditText) findViewById(R.id.dateTo_year);
        eTime = (EditText)findViewById(R.id.time);
        eLocation=(EditText)findViewById(R.id.location);
        eSpecial=(EditText)findViewById(R.id.speacial);

        btnReq=(Button)findViewById(R.id.sendReq);
        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRequest();
            }
        });
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SELECT_FILE && resultCode == RESULT_OK){
            imageUri = data.getData();
            addP.setImageURI(imageUri);

        }

    }
    private void startRequest(){
        //progressDialog.setMessage("Requesting ...");
        //progressDialog.show();


        final String service = kSer;
        final String event = eEvent.getText().toString();
        final Integer numberP = Integer.parseInt(eNperson.getText().toString());
        final Integer maxPrice = Integer.parseInt(eMaxp.getText().toString());
        final String dt_day = eDateD.getText().toString();
        final String dt_month = eDateM.getText().toString();
        final String dt_year = eDateY.getText().toString();
        final String location = eLocation.getText().toString();
        final String time = eTime.getText().toString();
        final String specialReq = eSpecial.getText().toString();
        final String status = "1";

        if(!TextUtils.isEmpty(service) && !TextUtils.isEmpty(event) &&
                numberP!=0 && maxPrice!=0 &&
                !TextUtils.isEmpty(dt_day) && !TextUtils.isEmpty(dt_month) &&
                !TextUtils.isEmpty(dt_year) && !TextUtils.isEmpty(time)&&
                !TextUtils.isEmpty(location) && !TextUtils.isEmpty(specialReq) &&
                imageUri !=null){


            filepath = storageReference.child("Request").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri dowloadUrl = taskSnapshot.getDownloadUrl();

                    //Create root of Request
                    final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mRequestRef = mRootRef.child("request1");

                    final String key = mRequestRef.push().getKey();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm a");
                    String date = sdf.format(c.getTime());

                    final HashMap<String, Object> RequestValues = new HashMap<String, Object>();
                    RequestValues.put("service", service);
                    RequestValues.put("event", event);
                    RequestValues.put("username", username);
                    RequestValues.put("userprofile", userprofile);
                    RequestValues.put("numberofperson", numberP);
                    RequestValues.put("maxprice", maxPrice);
                    RequestValues.put("date", dt_day + "/" + dt_month + "/" + dt_year);
                    RequestValues.put("time", time);
                    RequestValues.put("location", location);
                    RequestValues.put("specialrequest", specialReq);
                    RequestValues.put("status",status);
                    RequestValues.put("customerid", mFirebaseUser.getUid().toString());
                    RequestValues.put("currenttime", date);
                    RequestValues.put("reqpic",dowloadUrl.toString());


                    final Map<String, Object> childUpdate = new HashMap<>();
                    childUpdate.put("/request1/" + key, RequestValues);
                    childUpdate.put("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + key, RequestValues);

                    for (HashMap<String,String> hash : BeauID){
                        if(Integer.parseInt(hash.get("price").toString())<maxPrice){
                            childUpdate.put("/beautician-received/" + hash.get("uid").toString() + "/" + key, RequestValues);
                        }
                    }
                    mRootRef.updateChildren(childUpdate);
                }
            });



            progressDialog.dismiss();
            Intent intent = new Intent(CreateRequest.this,MainActivity.class);
            startActivity(intent);
            }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }




