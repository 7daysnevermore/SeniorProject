package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.model.DataOffer;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by NunePC on 28/2/2560.
 */

public class OfferDetails extends AppCompatActivity {

    private TextView date,service,event,time,special,location,maxprice,numofPer,amount,beauname,hire,decline,settime,setlocation,see;
    ImageView picpro,attachphoto,offerphoto;
    HashMap<String, Object> requestValues;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerdetails);
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
        amount = (TextView)findViewById(R.id.amount);
        beauname = (TextView) findViewById(R.id.tname);
        settime = (TextView) findViewById(R.id.settime);
        setlocation = (TextView) findViewById(R.id.setlocation);
        picpro = (ImageView) findViewById(R.id.pic_pro);
        attachphoto = (ImageView) findViewById(R.id.attachphoto);
        offerphoto = (ImageView) findViewById(R.id.offerphoto);
        see = (TextView)findViewById(R.id.see);

        if(requestValues.get("beauprofile")!=null){
            Picasso.with(getApplicationContext()).load(requestValues.get("beauprofile").toString()).fit().centerCrop().into(picpro);
        }
        beauname.setText(requestValues.get("beauticianname").toString());
        date.setText(requestValues.get("date").toString());
        service.setText((String)requestValues.get("service"));
        event.setText(requestValues.get("event").toString());
        time.setText(requestValues.get("time").toString());
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("request1").child(requestValues.get("requestid").toString());
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataRequest request = dataSnapshot.getValue(DataRequest.class);
                if(dataSnapshot.getValue() == null ){
                }
                else{
                    if(!request.time.equals(requestValues.get("time").toString())){
                        time.setTextColor(Color.parseColor("#ff0000"));
                        time.setTypeface(null, Typeface.BOLD);
                        settime.setTextColor(Color.parseColor("#ff0000"));
                        settime.setTypeface(null, Typeface.BOLD);
                    }
                    if (!request.location.equals(requestValues.get("location").toString())) {
                        location.setTextColor(Color.parseColor("#ff0000"));
                        location.setTypeface(null, Typeface.BOLD);
                        setlocation.setTextColor(Color.parseColor("#ff0000"));
                        setlocation.setTypeface(null, Typeface.BOLD);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        amount.setText(requestValues.get("amount").toString());
        special.setText(requestValues.get("specialrequest").toString());
        location.setText(requestValues.get("location").toString());
        maxprice.setText(requestValues.get("price").toString()+" ฿");
        numofPer.setText(requestValues.get("numberofperson").toString());
       if(requestValues.get("requestpic")!=null){
            Picasso.with(getApplicationContext()).load(requestValues.get("requestpic").toString()).into(attachphoto);
        }
        if (requestValues.get("offerpic")!=null) {
            Picasso.with(getApplicationContext()).load(requestValues.get("offerpic").toString()).into(offerphoto);
        }

        hire = (TextView) findViewById(R.id.hire);

        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference mCustReqRef = mRootRef.child("customer-request1").child(requestValues.get("custid").toString()).child(requestValues.get("requestid").toString());
                final DatabaseReference mBeauReqRef = mRootRef.child("beautician-received").child(requestValues.get("beauid").toString()).child(requestValues.get("requestid").toString());
                final DatabaseReference CustRef = mRootRef.child("customer-received").child(requestValues.get("custid").toString()).child(requestValues.get("requestid").toString()).child(requestValues.get("offerid").toString());

                DatabaseReference deleteReq = mRootRef.child("customer-received").child(requestValues.get("custid").toString()).child(requestValues.get("requestid").toString());
                deleteReq.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot datashot : dataSnapshot.getChildren()) {

                            if (datashot.getValue() == null) {
                            } else {

                                DataOffer hired = datashot.getValue(DataOffer.class);
                                if (!hired.offerid.equals(requestValues.get("offerid").toString())) {
                                    datashot.getRef().setValue(null);
                                }

                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                mCustReqRef.child("status").setValue("3");
                mBeauReqRef.child("status").setValue("3");
                CustRef.child("status").setValue("3");

                Intent intent = new Intent(OfferDetails.this,MainActivity.class);
                intent.putExtra("menu",  "request");
                intent.putExtra("chooseoffer_beauid", requestValues.get("beauid").toString());
                intent.putExtra("chooseoffer_requestid", requestValues.get("requestid").toString());
                startActivity(intent);

            }

        });

        decline = (TextView) findViewById(R.id.decline);

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("beautician-received").child(requestValues.get("beauid").toString()).child(requestValues.get("requestid").toString());
                mRootRef.child("status").setValue("6");
                Intent intent = new Intent(OfferDetails.this, MainActivity.class);
                intent.putExtra("menu", "request");
                startActivity(intent);

            }

        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferDetails.this, BeauticianProfile.class);
                intent.putExtra("from", "offer");
                intent.putExtra("uid", requestValues.get("beauid").toString());
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
