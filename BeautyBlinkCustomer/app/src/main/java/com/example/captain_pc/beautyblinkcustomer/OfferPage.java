package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.example.captain_pc.beautyblinkcustomer.model.Offerss;
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

public class OfferPage extends AppCompatActivity {
    private TextView date,service,event,time,special,location,maxprice,numofPer,name,spe_b,t;
    HashMap<String, Object> requestValues;
    private Button btnOffer;
    private String username,k,m;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Toolbar toolbar;
    String np,mp,sp,loca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page);
        requestValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("request");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        k=requestValues.get("keyrequest").toString();
        m=requestValues.get("keyoffer").toString();
        Log.d("orange",""+k);
      Log.d("jajajajaja",""+m);

        t= (TextView)findViewById(R.id.tg);
        date= (TextView)findViewById(R.id.cusD);
        service =(TextView)findViewById(R.id.cusSer);
        event =(TextView)findViewById(R.id.cusEve);
        time = (TextView)findViewById(R.id.cusTime);
        special = (TextView)findViewById(R.id.cusSpe);
        location = (TextView)findViewById(R.id.cusLo);
        maxprice = (TextView)findViewById(R.id.cusMax);
        name = (TextView)findViewById(R.id.tname);
        numofPer = (TextView)findViewById(R.id.cusNum);
        spe_b = (TextView)findViewById(R.id.speB);


      DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("offer/"+k+"/"+m);

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offerss user = dataSnapshot.getValue(Offerss.class);
                if (user == null) {
                    Toast.makeText(OfferPage.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    service.setText("Service: "+user.service);
                    date.setText("Date: "+user.date);
                    event.setText("Event: "+user.event);
                    time.setText("Time: "+user.time);
                    special.setText("Special requirement: "+user.specialcus);
                    location.setText("Location: "+user.location);
                    maxprice.setText("Maxprice: "+user.maxprice);
                    name.setText(user.namecus);
                    numofPer.setText("Number/person: "+user.numberofcustomer);
                    spe_b.setText(user.beauticianoffer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
