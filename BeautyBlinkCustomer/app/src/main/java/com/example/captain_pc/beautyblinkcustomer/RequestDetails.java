package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NunePC on 27/2/2560.
 */

public class RequestDetails extends AppCompatActivity {
    private TextView date,service,event,time,special,location,maxprice,numofPer,bt_coffer;
    HashMap<String, Object> requestValues;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button cancel;
    private ImageView photo;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestdetails);
        requestValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("request");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        date= (TextView)findViewById(R.id.cusD);
        service =(TextView)findViewById(R.id.cusSer);
        event =(TextView)findViewById(R.id.cusEv);
        time = (TextView)findViewById(R.id.cusTime);
        special = (TextView)findViewById(R.id.cusSpe);
        location = (TextView)findViewById(R.id.cusLo);
        maxprice = (TextView)findViewById(R.id.cusMax);
        numofPer = (TextView)findViewById(R.id.cusNum);
        photo = (ImageView)findViewById(R.id.attachphoto);
        cancel = (Button) findViewById(R.id.cancel);

        date.setText(requestValues.get("date").toString());
        service.setText((String)requestValues.get("service"));
        event.setText(requestValues.get("event").toString());
        time.setText(requestValues.get("time").toString());
        special.setText(requestValues.get("specialrequest").toString());
        location.setText(requestValues.get("location").toString());
        maxprice.setText(requestValues.get("maxprice").toString()+" ฿");
        numofPer.setText(requestValues.get("numberofperson").toString());
        if(!requestValues.get("reqpic").toString().equals("")){
            Picasso.with(getApplicationContext()).load(requestValues.get("reqpic").toString()).into(photo);
        }

        bt_coffer = (TextView) findViewById(R.id.bt_coffer);

        bt_coffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(RequestDetails.this,OfferPage.class);
                    intent.putExtra("request",  requestValues);
                    startActivity(intent);

            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                    if(key.equals(requestValues.get("key").toString())){
                                        mBeauRef.child(beauid).child(key).child("status").setValue("8");
                                    }
                                }

                            }


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference mCustRef = FirebaseDatabase.getInstance().getReference().child("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + requestValues.get("key").toString());
                mCustRef.child("status").setValue("8");

                Intent intent = new Intent(RequestDetails.this, MainActivity.class);
                intent.putExtra("menu", "request");
                startActivity(intent);

            }

        });


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
