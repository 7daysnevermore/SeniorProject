package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Review extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText reviewTopic, reviewDesc;
    private ImageView reviewImg;
    private Uri reviewImg_show;

    private int SELECT_FILE = 1;

    //Rating Bar
    private TextView send_review;
    private RatingBar rating_Bar;
    private Integer str,custnumber;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private HashMap<String, Object> confirmValues;
    private StorageReference storageReference,filepath;
    private Integer ratingSum;
    private String beauid;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Review");

        confirmValues =(HashMap<String,Object>)getIntent().getExtras().getSerializable("request");
        beauid = getIntent().getStringExtra("beauid");

        reviewTopic = (EditText) findViewById(R.id.review_topic);
        reviewDesc = (EditText) findViewById(R.id.review_desc);
        reviewImg = (ImageView) findViewById(R.id.review_img);

        reviewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,SELECT_FILE);
            }
        });

        send_review = (TextView)findViewById(R.id.send_review);
        rating_Bar = (RatingBar)findViewById(R.id.ratingBar);

        rating_Bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                str = Math.round(rating);

            }
        });

        send_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String topic = reviewTopic.getText().toString();
                final String desc = reviewDesc.getText().toString();

                if(!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(desc) &&
                        str!=null && reviewImg_show !=null) {

                    final DatabaseReference ratingB = FirebaseDatabase.getInstance().getReference().child("beautician-profilepromote").child(beauid);
                    ratingB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot startChild : dataSnapshot.getChildren()){

                                DataProfilePromote user = startChild.getValue(DataProfilePromote.class);
                                String key = startChild.getKey();

                                if(user == null){
                                    Toast.makeText(Review.this,"Error: could not fetch user.",Toast.LENGTH_LONG).show();
                                }else {

                                    DatabaseReference ratingPro = FirebaseDatabase.getInstance().getReference().child("profilepromote");

                                    if(!user.rating.equals("")){
                                        ratingSum = user.sumrate+str;
                                        custnumber = user.custnumber+1;
                                        Integer sum = ratingSum/custnumber;
                                        ratingB.child(key).child("sumrate").setValue(ratingSum);
                                        ratingB.child(key).child("custnumber").setValue(custnumber);
                                        ratingB.child(key).child("rating").setValue(String.valueOf(sum));
                                        ratingPro.child(key).child("sumrate").setValue(ratingSum);
                                        ratingPro.child(key).child("custnumber").setValue(custnumber);
                                        ratingPro.child(key).child("rating").setValue(String.valueOf(sum));
                                        finished(topic,desc);
                                    }
                                    else{
                                        ratingSum = str;
                                        custnumber = 1;
                                        Integer sum = ratingSum/custnumber;
                                        ratingB.child(key).child("sumrate").setValue(ratingSum);
                                        ratingB.child(key).child("custnumber").setValue(custnumber);
                                        ratingB.child(key).child("rating").setValue(String.valueOf(sum));
                                        ratingPro.child(key).child("sumrate").setValue(ratingSum);
                                        ratingPro.child(key).child("custnumber").setValue(custnumber);
                                        ratingPro.child(key).child("rating").setValue(String.valueOf(sum));
                                        finished(topic,desc);
                                    }
                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

        });


    }

    public void finished(final String topic, final String desc){
        filepath = storageReference.child("Review").child(reviewImg_show.getLastPathSegment());
        filepath.putFile(reviewImg_show).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri dowloadUrl = taskSnapshot.getDownloadUrl();

                //Create root of Request
                final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mReviewRef = mRootRef.child("review");

                final String key = mReviewRef.push().getKey();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm a");
                String date = sdf.format(c.getTime());

                final HashMap<String, Object> RequestValues = new HashMap<String, Object>();
                RequestValues.put("topic", topic);
                RequestValues.put("desc", desc);
                RequestValues.put("image", dowloadUrl.toString());
                RequestValues.put("rating", str);
                RequestValues.put("name", confirmValues.get("custname").toString());
                if(confirmValues.get("userprofile")!=null){
                    RequestValues.put("profile", confirmValues.get("userprofile").toString());
                }
                RequestValues.put("customerid", mFirebaseUser.getUid().toString());
                RequestValues.put("currenttime", date);
                RequestValues.put("beauid", beauid);
                RequestValues.put("requestid", confirmValues.get("key").toString());

                final Map<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("/review/" + key, RequestValues);
                childUpdate.put("/customer-review/" + mFirebaseUser.getUid().toString() + "/" + confirmValues.get("key").toString() + "/" + key, RequestValues);
                childUpdate.put("/beautician-review/" + beauid + "/" + key, RequestValues);

                mRootRef.updateChildren(childUpdate);

                DatabaseReference ratingB = FirebaseDatabase.getInstance().getReference().child("profilepromote").child(beauid);


                DatabaseReference mCustRef = FirebaseDatabase.getInstance().getReference().child("/customer-request1/" + mFirebaseUser.getUid().toString() + "/" + confirmValues.get("key").toString());
                mCustRef.child("status").setValue("7");

                DatabaseReference mBeauRef = FirebaseDatabase.getInstance().getReference().child("/beautician-received/" + beauid + "/" + confirmValues.get("key").toString());
                mBeauRef.child("status").setValue("7");

            }
        });


        Intent intent = new Intent(Review.this, MainActivity.class);
        intent.putExtra("menu", "request");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK){
            reviewImg_show = data.getData();
            reviewImg.setImageURI(reviewImg_show);
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
