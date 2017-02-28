package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataOffer;
import com.example.captain_pc.beautyblinkcustomer.model.DataPayment;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.example.captain_pc.beautyblinkcustomer.model.DataReview;
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

public class HiredDetails extends AppCompatActivity {

    HashMap<String, Object> requestValues;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    String beauid;
    TextView payment_date, payment_time, payment_bank, payment_amount,topic,desc;
    LinearLayout bt_payment, bt_finish, bt_review, payment,review;
    private TextView date, service, event, time, special, location, maxprice, numofPer, amount, beauname, yes, no;
    ImageView picpro, slip;
    String status;
    private AlertDialog dialog;
    private RatingBar rating_Bar;
    View view1,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_details);

        requestValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("request");
        status = getIntent().getStringExtra("status");

        bt_payment = (LinearLayout) findViewById(R.id.bt_payment);
        bt_finish = (LinearLayout) findViewById(R.id.bt_finish);
        bt_review = (LinearLayout) findViewById(R.id.bt_review);
        payment = (LinearLayout) findViewById(R.id.payment);
        review = (LinearLayout) findViewById(R.id.review);
        payment_date = (TextView) findViewById(R.id.payment_date);
        payment_time = (TextView) findViewById(R.id.payment_time);
        payment_bank = (TextView) findViewById(R.id.payment_bank);
        payment_amount = (TextView) findViewById(R.id.payment_amount);
        view1 = (View) findViewById(R.id.view1);
        view2 = (View) findViewById(R.id.view2);
        topic = (TextView) findViewById(R.id.topic);
        desc = (TextView) findViewById(R.id.des);
        slip = (ImageView) findViewById(R.id.slip);
        rating_Bar = (RatingBar) findViewById(R.id.rating);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        if (status.equals("3")) {
            bt_payment.setVisibility(View.VISIBLE);
        }
        if (status.equals("4")) {
            bt_finish.setVisibility(View.VISIBLE);
        }
        if (status.equals("5")) {
            bt_review.setVisibility(View.VISIBLE);
            payment.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);

            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("customer-payment").child(requestValues.get("uid").toString())
                    .child(requestValues.get("key").toString());

            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot datashot : dataSnapshot.getChildren()) {


                        if (datashot.getValue() == null) {
                        } else {

                            DataPayment hired = datashot.getValue(DataPayment.class);
                            Picasso.with(getApplicationContext()).load(hired.slip).fit().centerCrop().into(slip);
                            payment_date.setText(hired.date);
                            payment_time.setText(hired.time);
                            payment_bank.setText(hired.bank);
                            payment_amount.setText(hired.amount);

                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        if(status.equals("7")){
            review.setVisibility(View.VISIBLE);
            payment.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);

            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("customer-payment").child(requestValues.get("uid").toString())
                    .child(requestValues.get("key").toString());

            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot datashot : dataSnapshot.getChildren()) {


                        if (datashot.getValue() == null) {
                        } else {

                            DataPayment hired = datashot.getValue(DataPayment.class);
                            Picasso.with(getApplicationContext()).load(hired.slip).fit().centerCrop().into(slip);
                            payment_date.setText(hired.date);
                            payment_time.setText(hired.time);
                            payment_bank.setText(hired.bank);
                            payment_amount.setText(hired.amount);

                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            DatabaseReference dataReview = FirebaseDatabase.getInstance().getReference().child("customer-review").child(requestValues.get("uid").toString())
                    .child(requestValues.get("key").toString());

            dataReview.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot datashot : dataSnapshot.getChildren()) {


                        if (datashot.getValue() == null) {
                        } else {

                            DataReview rev = datashot.getValue(DataReview.class);
                            rating_Bar.setRating((float) (rev.rating*1.0));
                            topic.setText(rev.topic);
                            desc.setText(rev.desc);

                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        date = (TextView) findViewById(R.id.cusD);
        service = (TextView) findViewById(R.id.cusSer);
        event = (TextView) findViewById(R.id.cusEv);
        time = (TextView) findViewById(R.id.cusTime);
        special = (TextView) findViewById(R.id.cusSpe);
        location = (TextView) findViewById(R.id.cusLo);
        maxprice = (TextView) findViewById(R.id.cusMax);
        numofPer = (TextView) findViewById(R.id.cusNum);
        amount = (TextView) findViewById(R.id.amount);
        beauname = (TextView) findViewById(R.id.tname);
        picpro = (ImageView) findViewById(R.id.pic_pro);

        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("customer-received").child(requestValues.get("uid").toString())
                .child(requestValues.get("key").toString());

        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot datashot : dataSnapshot.getChildren()) {


                    if (datashot.getValue() == null) {
                    } else {

                        DataOffer hired = datashot.getValue(DataOffer.class);
                        if (hired.beaupic != null) {
                            Picasso.with(getApplicationContext()).load(hired.beaupic).fit().centerCrop().into(picpro);
                        }
                        beauid = hired.beauid;
                        beauname.setText(hired.beauname);
                        date.setText(hired.date);
                        service.setText(hired.service);
                        event.setText(hired.event);
                        time.setText(hired.time);
                        amount.setText(String.valueOf(hired.amount));
                        special.setText(hired.specialrequest);
                        location.setText(hired.location);
                        maxprice.setText(hired.price);
                        numofPer.setText(hired.numberofperson);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bt_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HiredDetails.this, Payment.class);
                intent.putExtra("beauid", beauid);
                intent.putExtra("request", requestValues);
                startActivity(intent);
            }

        });

        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference mCustRef = FirebaseDatabase.getInstance().getReference().child("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + requestValues.get("key").toString());
                mCustRef.child("status").setValue("5");

                DatabaseReference mBeauRef = FirebaseDatabase.getInstance().getReference().child("/beautician-received/" + beauid + "/" + requestValues.get("key").toString());
                mBeauRef.child("status").setValue("5");

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HiredDetails.this);
                final View mView = HiredDetails.this.getLayoutInflater().inflate(R.layout.dialog_review, null);

                yes = (TextView) mView.findViewById(R.id.yes);
                no = (TextView) mView.findViewById(R.id.no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(HiredDetails.this, Review.class);
                        intent.putExtra("beauid", beauid);
                        intent.putExtra("request", requestValues);
                        startActivity(intent);
                    }

                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(HiredDetails.this, MainActivity.class);
                        intent.putExtra("menu", "request");
                        startActivity(intent);
                    }

                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

            }
        });

        bt_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HiredDetails.this, Review.class);
                intent.putExtra("beauid", beauid);
                intent.putExtra("request", requestValues);
                startActivity(intent);
            }
        });


    }
}
