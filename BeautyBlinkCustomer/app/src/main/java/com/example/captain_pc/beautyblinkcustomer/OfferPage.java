package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OfferPage extends AppCompatActivity {
    private TextView date,service,event,time,special,location,maxprice,numofPer,name,spe_b;
    HashMap<String, Object> requestValues;
    private Button btnOffer;
    private String username;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Toolbar toolbar;
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

        date.setText(requestValues.get("date").toString());
        service.setText((String)requestValues.get("service"));
        event.setText(requestValues.get("event").toString());
        numofPer.setText((String)requestValues.get("numberofperson"));
        //maxprice.setText("Maxprice: "+ requestValues.get("maxP").toString());



        //time.setText(requestValues.get("time").toString());
        //location.setText(requestValues.get("location").toString());
        //special.setText(requestValues.get("specialrequest").toString());

        btnOffer = (Button)findViewById(R.id.acceptOffer);

        btnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final String d = date.getText().toString();
                final String ser = service.getText().toString();
                final String eve = event.getText().toString();
                final String maxP = maxprice.getText().toString();
                final String numP = numofPer.getText().toString();
                final String ti = time.getText().toString();
                final String specus = special.getText().toString();
                final String locate = location.getText().toString();
                final String spebeau = spe_b.getText().toString();*/

                final String status = "confirm";

                if( !TextUtils.isEmpty(status)  )
                {
                    //Create root of Request
                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mRequestRef = mRootRef.child("accept");

                    String key = mRequestRef.push().getKey();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm a");
                    String date = sdf.format(c.getTime());
                    final HashMap<String, Object> RequestValues = new HashMap<String, Object>();
                    RequestValues.put("color","#f2f28f");
                    RequestValues.put("status",status);
                    RequestValues.put("type","accept");
                    RequestValues.put("currenttime", date);

                    Map<String, Object> childUpdate = new HashMap<>();
                    childUpdate.put("/request/" + key, RequestValues);
                    childUpdate.put("/customer-request/" + mFirebaseUser.getUid().toString() + "/" + key, RequestValues);
                    mRootRef.updateChildren(childUpdate);
                    // progressDialog.dismiss();
                    Intent intent = new Intent(OfferPage.this,MainActivity.class);
                    startActivity(intent);

                }

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
