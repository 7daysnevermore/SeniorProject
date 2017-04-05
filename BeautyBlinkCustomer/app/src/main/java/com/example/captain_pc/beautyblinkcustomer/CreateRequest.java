package com.example.captain_pc.beautyblinkcustomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataPlanner;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateRequest extends AppCompatActivity {

    private static final String AUTH_KEY = "key=AAAAU3g0xJ0:APA91bFc_Bf77RiEJwf4kWxgnqlZFl0fmIZLP32zPSL1VW3aGfyZ-Zt92JbY5_SUIHepL5ZljcEZzukypPSypg6i8U7x8Y4cGn19McqcsNfqjTK4BEaLhPYIaZLFrZHtsiz487XJXfWwLHZd8Nt0-Jb7GOwW1Hxz2g";
    private Spinner chService;
    private int SELECT_FILE =1;
    private EditText eEvent,eNperson,eMaxp,eLocation,eSpecial;
    private Toolbar toolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    String servicecode = null,suggestimage;
    private Button btnReq;
    private ImageView addP,image1,image2,image3,image4,image5;
    private String kSer;
    private Uri imageUri = null;
    private DatePicker datePicker;
    private int mHour, mMinute;
    private int year, month, day,yyyy,mm,dd,hour1,minute1,hour2,minute2;
    private TextView set_date,set_time,male,female;
    private ProgressDialog progressDialog;
    private StorageReference storageReference,filepath;
    private DatabaseReference databaseReference;
    private String username,userprofile,choose="male",suggest = "";
    private String type=null;
    ArrayList<HashMap<String,String>> BeauID,CateID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tmp = "";
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                tmp += key + ": " + value + "\n\n";
            }
            //mTextView.setText(tmp);
        }

        set_date = (TextView) findViewById(R.id.set_date);
        set_time = (TextView) findViewById(R.id.set_time);

        type = getIntent().getStringExtra("type");

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
                if(type.equals("A")){
                   if(kSer.equals("MakeupandHair")){
                        if(getIntent().getStringExtra("service1").equals("0") ){
                            Toast.makeText(CreateRequest.this, "This beautician does not have this service.", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (kSer.equals("HairStyle")) {
                        if (getIntent().getStringExtra("service2").equals("0")) {
                            Toast.makeText(CreateRequest.this, "This beautician does not have this service.", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (kSer.equals("MakeUp")) {
                        if (getIntent().getStringExtra("service3").equals("0")) {
                            Toast.makeText(CreateRequest.this, "This beautician does not have this service.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        if (getIntent().getStringExtra("service4").equals("0")) {
                            Toast.makeText(CreateRequest.this, "This beautician does not have this service.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    if(kSer.equals("MakeupandHair"))
                    { servicecode = "S01";
                        BeauID = new ArrayList<>();
                        ShowSuggestion();
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
                        });



                    }
                    else if (kSer.equals("HairStyle"))
                    { servicecode = "S02";
                        ShowSuggestion();
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
                        });
                    }
                    else if(kSer.equals("MakeUp"))
                    { servicecode = "S03";
                        ShowSuggestion();
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
                        ShowSuggestion();
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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        eEvent =(EditText)findViewById(R.id.event);
        eNperson=(EditText)findViewById(R.id.nPerson);
        eMaxp=(EditText)findViewById(R.id.max_price);
        eLocation=(EditText)findViewById(R.id.location);
        eSpecial=(EditText)findViewById(R.id.speacial);
        image1 =(ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);

        /*ImageView btns = (ImageView) findViewById(R.id.imageButton);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseMessaging.getInstance().subscribeToTopic("news");

                sendWithOtherThread("topic");

            }
        });*/

        male=(TextView) findViewById(R.id.male);
        female =(TextView) findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "male";
                male.setTextColor(R.color.colorPrimary);
                female.setTextColor(R.color.streak_color_light);
                ShowSuggestion();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "female";
                female.setTextColor(R.color.colorPrimary);
                male.setTextColor(R.color.streak_color_light);
                ShowSuggestion();
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose.equals("male")&&kSer.equals("MakeupandHair")){
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517";
                }
                if (choose.equals("female") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair1.jpg?alt=media&token=bccca92d-0aa3-4d79-a20a-d330fb8b2f21").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair1.jpg?alt=media&token=bccca92d-0aa3-4d79-a20a-d330fb8b2f21";
                }
                if (choose.equals("male") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558";
                }
                if (choose.equals("female") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair1.jpg?alt=media&token=aac409cd-c28c-41c8-ae4a-e6701a099244").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair1.jpg?alt=media&token=aac409cd-c28c-41c8-ae4a-e6701a099244";
                }
                if (choose.equals("male") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517";
                }
                if (choose.equals("female") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup1.jpg?alt=media&token=d37cbd13-1f19-4f8b-924e-07ab1f905aff").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup1.jpg?alt=media&token=d37cbd13-1f19-4f8b-924e-07ab1f905aff";
                }
                if (choose.equals("male") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558";
                }
                if (choose.equals("female") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut1.jpg?alt=media&token=ce5e0a1e-5852-4ac5-9164-083822fc5218").into(addP);
                    suggest ="https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut1.jpg?alt=media&token=ce5e0a1e-5852-4ac5-9164-083822fc5218";
                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose.equals("male")&&kSer.equals("MakeupandHair")){
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713";
                }
                if (choose.equals("female") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair2.jpg?alt=media&token=e766d9c5-78e2-442f-acc8-85a74798e536").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair2.jpg?alt=media&token=e766d9c5-78e2-442f-acc8-85a74798e536";
                }
                if (choose.equals("male") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f";
                }
                if (choose.equals("female") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair2.jpg?alt=media&token=d89740cf-5865-4b01-9c19-62c10312375b").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair2.jpg?alt=media&token=d89740cf-5865-4b01-9c19-62c10312375b";
                }
                if (choose.equals("male") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713";
                }
                if (choose.equals("female") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup2.jpg?alt=media&token=9e6af840-c498-4dbd-8b1c-0b30586f52f4").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup2.jpg?alt=media&token=9e6af840-c498-4dbd-8b1c-0b30586f52f4";
                }
                if (choose.equals("male") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f";
                }
                if (choose.equals("female") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut2.jpg?alt=media&token=c28c141e-b51c-4533-bfbf-8110e1525952").into(addP);
                    suggest ="https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut2.jpg?alt=media&token=c28c141e-b51c-4533-bfbf-8110e1525952";
                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choose.equals("male") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840";
                }
                if (choose.equals("female") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair3.jpg?alt=media&token=2edad147-0e47-41e2-b957-55409d44cc3d").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair3.jpg?alt=media&token=2edad147-0e47-41e2-b957-55409d44cc3d";
                }
                if (choose.equals("male") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357";
                }
                if (choose.equals("female") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair3.jpg?alt=media&token=ab7b47f2-5fd8-4562-a8e8-289fdd89fde9").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair3.jpg?alt=media&token=ab7b47f2-5fd8-4562-a8e8-289fdd89fde9";
                }
                if (choose.equals("male") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840";
                }
                if (choose.equals("female") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup3.jpg?alt=media&token=fd8f5040-86b3-457b-a7cd-0b8570f3b492").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup3.jpg?alt=media&token=fd8f5040-86b3-457b-a7cd-0b8570f3b492";
                }
                if (choose.equals("male") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357";
                }
                if (choose.equals("female") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut3.jpg?alt=media&token=c5acfe48-4d04-459b-863e-735289b6c179").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut3.jpg?alt=media&token=c5acfe48-4d04-459b-863e-735289b6c179";
                }
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choose.equals("male") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933";
                }
                if (choose.equals("female") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair4.jpg?alt=media&token=fe802627-c5f1-47ab-9330-ed109a40f0e5").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair4.jpg?alt=media&token=fe802627-c5f1-47ab-9330-ed109a40f0e5";
                }
                if (choose.equals("male") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013";
                }
                if (choose.equals("female") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair4.png?alt=media&token=09b18e37-29ab-436d-9f06-b650fbcb7f3c").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair4.png?alt=media&token=09b18e37-29ab-436d-9f06-b650fbcb7f3c";
                }
                if (choose.equals("male") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933";
                }
                if (choose.equals("female") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup4.jpg?alt=media&token=7ea01b86-5264-493d-ab96-6cb1a8664bcf").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup4.jpg?alt=media&token=7ea01b86-5264-493d-ab96-6cb1a8664bcf";
                }
                if (choose.equals("male") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013";
                }
                if (choose.equals("female") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut4.jpg?alt=media&token=3090744f-edb5-4199-9c11-fb18ee29d0d6").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut4.jpg?alt=media&token=3090744f-edb5-4199-9c11-fb18ee29d0d6";
                }
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choose.equals("male") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8";
                }
                if (choose.equals("female") && kSer.equals("MakeupandHair")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair5.jpg?alt=media&token=e858e297-9408-41e4-98da-734867ff610b").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair5.jpg?alt=media&token=e858e297-9408-41e4-98da-734867ff610b";
                }
                if (choose.equals("male") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair5.jpg?alt=media&token=33fedb88-88fc-46d0-9c71-6be932b4d179").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair5.jpg?alt=media&token=33fedb88-88fc-46d0-9c71-6be932b4d179";
                }
                if (choose.equals("female") && kSer.equals("HairStyle")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair5.jpg?alt=media&token=73e75b09-770f-4314-bfdb-0ab73516b733").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair5.jpg?alt=media&token=73e75b09-770f-4314-bfdb-0ab73516b733";
                }
                if (choose.equals("male") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8";
                }
                if (choose.equals("female") && kSer.equals("MakeUp")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup5.jpg?alt=media&token=6a103eff-5f4a-4dcf-b291-11a2f3bfc43b").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup5.jpg?alt=media&token=6a103eff-5f4a-4dcf-b291-11a2f3bfc43b";
                }
                if (choose.equals("male") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair6.jpg?alt=media&token=af8b9f69-4c30-46a2-bd59-f8c4f615da3f").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair6.jpg?alt=media&token=af8b9f69-4c30-46a2-bd59-f8c4f615da3f";
                }
                if (choose.equals("female") && kSer.equals("Hairdressing")) {
                    Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut5.jpg?alt=media&token=a5ac8aa9-1466-4344-9128-d66d619ae418").into(addP);
                    suggest = "https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut5.jpg?alt=media&token=a5ac8aa9-1466-4344-9128-d66d619ae418";
                }
            }
        });

        btnReq=(Button)findViewById(R.id.sendReq);
        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRequest();
            }
        });


    }

    public void ShowSuggestion(){

        if(choose.equals("male")&&kSer.equals("MakeupandHair")){
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8").fit().centerCrop().into(image5);
        }
        if (choose.equals("female") && kSer.equals("MakeupandHair")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair1.jpg?alt=media&token=bccca92d-0aa3-4d79-a20a-d330fb8b2f21").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair2.jpg?alt=media&token=e766d9c5-78e2-442f-acc8-85a74798e536").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair3.jpg?alt=media&token=2edad147-0e47-41e2-b957-55409d44cc3d").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair4.jpg?alt=media&token=fe802627-c5f1-47ab-9330-ed109a40f0e5").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeupandhair5.jpg?alt=media&token=e858e297-9408-41e4-98da-734867ff610b").fit().centerCrop().into(image5);
        }
        if (choose.equals("male") && kSer.equals("HairStyle")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair5.jpg?alt=media&token=33fedb88-88fc-46d0-9c71-6be932b4d179").fit().centerCrop().into(image5);
        }
        if (choose.equals("female") && kSer.equals("HairStyle")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair1.jpg?alt=media&token=aac409cd-c28c-41c8-ae4a-e6701a099244").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair2.jpg?alt=media&token=d89740cf-5865-4b01-9c19-62c10312375b").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair3.jpg?alt=media&token=ab7b47f2-5fd8-4562-a8e8-289fdd89fde9").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair4.png?alt=media&token=09b18e37-29ab-436d-9f06-b650fbcb7f3c").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwhair5.jpg?alt=media&token=73e75b09-770f-4314-bfdb-0ab73516b733").fit().centerCrop().into(image5);
        }
        if (choose.equals("male") && kSer.equals("MakeUp")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh1.jpg?alt=media&token=4118165d-164f-4abe-98c1-2f9f88b07517").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh2.png?alt=media&token=8317f3c7-cd8c-4609-a2f3-b0d47ddf2713").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh3.jpg?alt=media&token=cc1a534d-1f55-4515-a062-0a14ebad8840").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh4.jpg?alt=media&token=88da5814-e360-48bd-ade5-9e5b20468933").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmmakeupandh5.png?alt=media&token=eee6a02c-ad9d-44f6-885e-daf40403d7a8").fit().centerCrop().into(image5);
        }
        if (choose.equals("female") && kSer.equals("MakeUp")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup1.jpg?alt=media&token=d37cbd13-1f19-4f8b-924e-07ab1f905aff").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup2.jpg?alt=media&token=9e6af840-c498-4dbd-8b1c-0b30586f52f4").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup3.jpg?alt=media&token=fd8f5040-86b3-457b-a7cd-0b8570f3b492").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup4.jpg?alt=media&token=7ea01b86-5264-493d-ab96-6cb1a8664bcf").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwmakeup5.jpg?alt=media&token=6a103eff-5f4a-4dcf-b291-11a2f3bfc43b").fit().centerCrop().into(image5);
        }
        if (choose.equals("male") && kSer.equals("Hairdressing")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair1.jpg?alt=media&token=dd786ae5-f5ad-430f-9a82-e354d7fe3558").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair2.jpg?alt=media&token=df0865ca-5934-4226-8004-982789f0ab8f").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair3.jpg?alt=media&token=5f381a81-aa4a-4cce-8d3d-027246bc8357").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair4.jpg?alt=media&token=e9db5ae8-0ed0-4d37-8d50-350c17377013").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fmhair6.jpg?alt=media&token=af8b9f69-4c30-46a2-bd59-f8c4f615da3f").fit().centerCrop().into(image5);
        }
        if (choose.equals("female") && kSer.equals("Hairdressing")) {
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut1.jpg?alt=media&token=ce5e0a1e-5852-4ac5-9164-083822fc5218").fit().centerCrop().into(image1);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut2.jpg?alt=media&token=c28c141e-b51c-4533-bfbf-8110e1525952").fit().centerCrop().into(image2);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut3.jpg?alt=media&token=c5acfe48-4d04-459b-863e-735289b6c179").fit().centerCrop().into(image3);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut4.jpg?alt=media&token=3090744f-edb5-4199-9c11-fb18ee29d0d6").fit().centerCrop().into(image4);
            Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/beautyblink-d8643.appspot.com/o/StyleSuggestion%2Fwcut5.jpg?alt=media&token=a5ac8aa9-1466-4344-9128-d66d619ae418").fit().centerCrop().into(image5);
        }
    }

    public void sendToken(View view) {
        sendWithOtherThread("token");
    }

    public void sendTokens(View view) {
        sendWithOtherThread("tokens");
    }

    public void sendTopic(View view) {
        sendWithOtherThread("topic");
    }
    private void sendWithOtherThread(final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pushNotification(type);
            }
        }).start();
    }
    private void pushNotification(String type) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            jNotification.put("title", "Google I/O 2016");
            jNotification.put("body", "Firebase Cloud Messaging (App)");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            jData.put("picture_url", "http://opsbug.com/static/google-io.jpg");

            switch(type) {
                case "tokens":
                    JSONArray ja = new JSONArray();
                    ja.put("c5pBXXsuCN0:APA91bH8nLMt084KpzMrmSWRS2SnKZudyNjtFVxLRG7VFEFk_RgOm-Q5EQr_oOcLbVcCjFH6vIXIyWhST1jdhR8WMatujccY5uy1TE0hkppW_TSnSBiUsH_tRReutEgsmIMmq8fexTmL");
                    ja.put(FirebaseInstanceId.getInstance().getToken());
                    jPayload.put("registration_ids", ja);
                    break;
                case "topic":
                    jPayload.put("to", "/topics/news");
                    Log.d("TEst","Noti");
                    break;
                case "condition":
                    jPayload.put("condition", "'sport' in topics || 'news' in topics");
                    break;
                default:
                    jPayload.put("to", FirebaseInstanceId.getInstance().getToken());
            }

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data", jData);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", AUTH_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    //mTextView.setText(resp);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        set_date.setText(day+"/"+month+"/"+year);
        dd = day;
        mm = month;
        yyyy = year;
    }

    public void setTime1(View view){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        set_time.setText(hourOfDay + " : " + minute);
                        hour1 = hourOfDay;
                        minute1 = minute;

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();


    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
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
        progressDialog.setMessage("Requesting ...");
        progressDialog.show();


        final String service = kSer;
        final String event = eEvent.getText().toString();
        final Integer numberP = Integer.parseInt(eNperson.getText().toString());
        final Integer maxPrice = Integer.parseInt(eMaxp.getText().toString());
        final String location = eLocation.getText().toString();
        final String specialReq = eSpecial.getText().toString();
        final String status = "1";

        if(!TextUtils.isEmpty(service) && !TextUtils.isEmpty(event) &&
                numberP!=0 && maxPrice!=0 ){

            if(imageUri!=null){
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
                        RequestValues.put("date", dd+"-"+mm+"-"+yyyy);
                        RequestValues.put("time", hour1+":"+minute1);
                        RequestValues.put("location", location);
                        RequestValues.put("specialrequest", specialReq);
                        RequestValues.put("status",status);
                        RequestValues.put("customerid", mFirebaseUser.getUid().toString());
                        RequestValues.put("currenttime", date);
                        RequestValues.put("reqpic",dowloadUrl.toString());

                        final Map<String, Object> childUpdate = new HashMap<>();
                        childUpdate.put("/request1/" + key, RequestValues);
                        childUpdate.put("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + key, RequestValues);

                        if(type.equals("A")){
                            childUpdate.put("/beautician-received/" + getIntent().getStringExtra("beau_id") + "/" + key, RequestValues);
                        }else{
                            for (final HashMap<String,String> hash : BeauID){
                                if(Integer.parseInt(hash.get("price").toString())<maxPrice||Integer.parseInt(hash.get("price").toString())==maxPrice){

                                    childUpdate.put("/beautician-received/" + hash.get("uid").toString() + "/" + key, RequestValues);

                                }
                            }
                        }

                        final HashMap<String,Object> request_noti = new HashMap<String, Object>();
                        request_noti.put("name",username);
                        request_noti.put("service",service);
                        request_noti.put("currenttime",date);
                        request_noti.put("status",status);
                        request_noti.put("event",event);
                        //Map<String,Object>requestNotiUpdate = new HashMap<>();
                        childUpdate.put("/requestnotifromcus/"+key,request_noti);


                        final HashMap<String,Object> statusS = new HashMap<String, Object>();
                        statusS.put("status",status.toString());
                        statusS.put("name",username);
                        statusS.put("service",service);

                        childUpdate.put("/statusfornoti/",statusS);

                        mRootRef.updateChildren(childUpdate);

                    }
                });
            }
            if(imageUri == null){

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
                        RequestValues.put("date", dd+"-"+mm+"-"+yyyy);
                        RequestValues.put("time", hour1+":"+minute1);
                        RequestValues.put("location", location);
                        RequestValues.put("specialrequest", specialReq);
                        RequestValues.put("status",status);
                        RequestValues.put("customerid", mFirebaseUser.getUid().toString());
                        RequestValues.put("currenttime", date);
                        RequestValues.put("reqpic",suggest);

                        final Map<String, Object> childUpdate = new HashMap<>();
                        childUpdate.put("/request1/" + key, RequestValues);
                        childUpdate.put("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + key, RequestValues);

                        if (type.equals("A")) {
                            childUpdate.put("/beautician-received/" + getIntent().getStringExtra("beau_id") + "/" + key, RequestValues);
                        } else {
                            for (final HashMap<String, String> hash : BeauID) {
                                if (Integer.parseInt(hash.get("price").toString()) < maxPrice || Integer.parseInt(hash.get("price").toString()) == maxPrice) {

                                        childUpdate.put("/beautician-received/" + hash.get("uid").toString() + "/" + key, RequestValues);

                                    }
                                }
                        }

                        final HashMap<String,Object> request_noti = new HashMap<String, Object>();
                        request_noti.put("name",username);
                        request_noti.put("service",service);
                        request_noti.put("currenttime",date);
                        request_noti.put("status",status);
                        request_noti.put("event",event);
                        //Map<String,Object>requestNotiUpdate = new HashMap<>();
                        childUpdate.put("/requestnotifromcus/"+key,request_noti);


                        final HashMap<String,Object> statusS = new HashMap<String, Object>();
                        statusS.put("status",status.toString());
                        statusS.put("name",username);
                        statusS.put("service",service);

                        childUpdate.put("/statusfornoti/",statusS);

                        mRootRef.updateChildren(childUpdate);

            }

            progressDialog.dismiss();
            Intent intent = new Intent(CreateRequest.this,MainActivity.class);
            intent.putExtra("menu","request");
            startActivity(intent);
            }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(CreateRequest.this,MainActivity.class);
                intent.putExtra("menu","request");
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }




