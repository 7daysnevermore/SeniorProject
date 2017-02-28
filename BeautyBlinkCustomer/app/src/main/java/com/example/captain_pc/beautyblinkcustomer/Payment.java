package com.example.captain_pc.beautyblinkcustomer;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.Offerss;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Objects;

public class Payment extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView event,service;
    private EditText date;
    private ImageView payment;
    private RadioGroup typeBank;
    private RadioButton bankBtn;
    private Button btnCon;
    private static final String TAG = "Payment";
    private String typeB;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private HashMap<String, Object> confirmValues;

    String key,keyin,uid,uBeau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        confirmValues =(HashMap<String,Object>)getIntent().getExtras().getSerializable("payment");


        date = (EditText)findViewById(R.id.editDate);
        payment = (ImageView)findViewById(R.id.paySlip);
        btnCon = (Button)findViewById(R.id.payConfirm);

        event.setText(confirmValues.get("event").toString());
        service.setText(confirmValues.get("service").toString());
        key = confirmValues.get("key").toString();
        keyin = confirmValues.get("insidekey").toString();
        Log.d("payment",""+key);
        Log.d("payment2",""+keyin);
        DatabaseReference mRootRef1 = FirebaseDatabase.getInstance().getReference().child("beautician-key/"+key);

        mRootRef1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offerss user = dataSnapshot.getValue(Offerss.class);
                if (user == null) {
                    Toast.makeText(Payment.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    uBeau=user.uid;
                    Log.d("uidd",""+uBeau);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selected = typeBank.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(selected);
                if(rb!=null)
                {
                    Toast.makeText(Payment.this,
                            "Game"+rb.getText(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Payment.this,
                            "Bye", Toast.LENGTH_SHORT).show();
                }
                //Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("/customer-request/" +uid+"/"+key);
                mRootRef.child("status").setValue("Paid");

                DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("/beauticianbusiness/" +uBeau+"/"+keyin);
                RootRef.child("afterofferstatus").setValue("Paid");


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
