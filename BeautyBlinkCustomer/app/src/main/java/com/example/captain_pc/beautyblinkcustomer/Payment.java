package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.Offerss;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Payment extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editDate,editTime,editBank,editPrice;
    private ImageView paySlip;
    private TextView send;
    private static final String TAG = "Payment";
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private HashMap<String, Object> confirmValues;
    private StorageReference storageReference,filepath;
    private String beauid;
    private DatabaseReference databaseReference;
    private Uri imageUri = null;
    private int SELECT_FILE =1;

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


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Payment");
        confirmValues =(HashMap<String,Object>)getIntent().getExtras().getSerializable("request");
        beauid = getIntent().getStringExtra("beauid");


        editDate = (EditText)findViewById(R.id.editDate);
        editTime = (EditText) findViewById(R.id.editTime);
        editBank = (EditText) findViewById(R.id.editBank);
        editPrice = (EditText) findViewById(R.id.editPrice);
        paySlip = (ImageView)findViewById(R.id.paySlip);
        send = (TextView) findViewById(R.id.send);

        paySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,SELECT_FILE);
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ddate = editDate.getText().toString();
                final String time = editTime.getText().toString();
                final String bank = editBank.getText().toString();
                final String price = editPrice.getText().toString();
                final String status = "4";

                if(!TextUtils.isEmpty(ddate) && !TextUtils.isEmpty(bank) &&
                        !TextUtils.isEmpty(time) && !TextUtils.isEmpty(price) &&
                        imageUri !=null){


                    filepath = storageReference.child("Payment").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri dowloadUrl = taskSnapshot.getDownloadUrl();

                            //Create root of Request
                            final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference mPaymentRef = mRootRef.child("payment");

                            final String key = mPaymentRef.push().getKey();
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm a");
                            String date = sdf.format(c.getTime());

                            final HashMap<String, Object> RequestValues = new HashMap<String, Object>();
                            RequestValues.put("bank", bank);
                            RequestValues.put("amount", price);
                            RequestValues.put("date", ddate);
                            RequestValues.put("time", time);
                            RequestValues.put("status",status);
                            RequestValues.put("customerid", mFirebaseUser.getUid().toString());
                            RequestValues.put("currenttime", date);
                            RequestValues.put("slip",dowloadUrl.toString());

                            final Map<String, Object> childUpdate = new HashMap<>();
                            childUpdate.put("/payment/" + key, RequestValues);
                            childUpdate.put("/customer-payment/" + mFirebaseUser.getUid().toString()+ "/" + confirmValues.get("key").toString() + "/" + key, RequestValues);

                            mRootRef.updateChildren(childUpdate);

                            DatabaseReference mCustRef = FirebaseDatabase.getInstance().getReference().child("/customer-request1/" + mFirebaseUser.getUid().toString()+"/"+confirmValues.get("key").toString());
                            mCustRef.child("status").setValue(status);

                            DatabaseReference mBeauRef = FirebaseDatabase.getInstance().getReference().child("/beautician-received/" +beauid+"/"+confirmValues.get("key").toString());
                            mBeauRef.child("status").setValue(status);

                        }
                    });

                    Intent intent = new Intent(Payment.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SELECT_FILE && resultCode == RESULT_OK){
            imageUri = data.getData();
            paySlip.setImageURI(imageUri);

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
