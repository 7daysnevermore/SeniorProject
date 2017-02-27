package com.example.captain_pc.beautyblinkcustomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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

import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateRequest extends AppCompatActivity {

    private static final String AUTH_KEY = "key=AAAAU3g0xJ0:APA91bFc_Bf77RiEJwf4kWxgnqlZFl0fmIZLP32zPSL1VW3aGfyZ-Zt92JbY5_SUIHepL5ZljcEZzukypPSypg6i8U7x8Y4cGn19McqcsNfqjTK4BEaLhPYIaZLFrZHtsiz487XJXfWwLHZd8Nt0-Jb7GOwW1Hxz2g";
    private Spinner chService;
    private int SELECT_FILE =1;
    private String username;
    private TextView input_name;
    private EditText eEvent,eNperson,eMaxp,eDateD,eDateM,eDateY,eTime,eLocation,eSpecial;
    private Toolbar toolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button btnReq;
    private ImageView addP;
    private String kSer;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private StorageReference storageReference,filepath;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Request");
        progressDialog = new ProgressDialog(this);

        DatabaseReference userRootRef = FirebaseDatabase.getInstance().getReference();
        userRootRef.child("customer").child(mFirebaseUser.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null){
                    Toast.makeText(CreateRequest.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                }else {
                    username = user.firstname;
                    input_name = (TextView) findViewById(R.id.name);
                    //input_name.setText(username);
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
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                kSer=parent.getItemAtPosition(position).toString();
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
        ImageView btns = (ImageView) findViewById(R.id.imageButton);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseMessaging.getInstance().subscribeToTopic("news");

                sendWithOtherThread("topic");

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
        final String numberP = eNperson.getText().toString();
        final String maxPrice = eMaxp.getText().toString();
        final String dt_day = eDateD.getText().toString();
        final String dt_month = eDateM.getText().toString();
        final String dt_year = eDateY.getText().toString();
        final String location = eLocation.getText().toString();
        final String time = eTime.getText().toString();
        final String specialReq = eSpecial.getText().toString();
        final String status = "offer";

        if(!TextUtils.isEmpty(service) && !TextUtils.isEmpty(event) &&
                !TextUtils.isEmpty(numberP) && !TextUtils.isEmpty(maxPrice) &&
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
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mRequestRef = mRootRef.child("request");

                    String key = mRequestRef.push().getKey();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm a");
                    String date = sdf.format(c.getTime());

                    final HashMap<String, Object> RequestValues = new HashMap<String, Object>();
                    RequestValues.put("service", service);
                    RequestValues.put("event", event);
                    RequestValues.put("numberofperson", numberP);
                    RequestValues.put("maxprice", maxPrice);
                    RequestValues.put("date", dt_day + "/" + dt_month + "/" + dt_year);
                    RequestValues.put("time", time);
                    RequestValues.put("location", location);
                    RequestValues.put("specialrequest", specialReq);
                    RequestValues.put("status",status);
                    RequestValues.put("uid", mFirebaseUser.getUid().toString());
                    RequestValues.put("name", username);
                    RequestValues.put("keyrequest",key);
                   // RequestValues.put("color", "#f2f2f2");
                    RequestValues.put("color", "#FC264D");
                    RequestValues.put("currenttime", date);
                    Map<String, Object> childUpdate = new HashMap<>();
                    childUpdate.put("/request/" + key, RequestValues);

                    final HashMap<String, Object> RequestValues1 = new HashMap<String, Object>();
                    RequestValues1.put("service", service);
                    RequestValues1.put("event", event);
                    RequestValues1.put("numberofperson", numberP);
                    RequestValues1.put("maxprice", maxPrice);
                    RequestValues1.put("date", dt_day + "/" + dt_month + "/" + dt_year);
                    RequestValues1.put("time", time);
                    RequestValues1.put("location", location);
                    RequestValues1.put("specialrequest", specialReq);
                    RequestValues1.put("status","toprovide");
                    RequestValues1.put("uid", mFirebaseUser.getUid().toString());
                    RequestValues1.put("name", username);
                    RequestValues1.put("keyrequest",key);
                    // RequestValues.put("color", "#f2f2f2");
                    RequestValues1.put("color", "#FC264D");
                    RequestValues1.put("currenttime", date);
                    childUpdate.put("/customer-request/" + mFirebaseUser.getUid().toString() + "/" + key, RequestValues1);

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



                    progressDialog.dismiss();
                    sendWithOtherThread("topic");
                Intent intent = new Intent(CreateRequest.this,MainActivity.class);
                    startActivity(intent);

                }
            });

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




