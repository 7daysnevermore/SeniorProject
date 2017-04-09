package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.fragments.PreviewDetailFragment;
import com.example.captain_pc.beautyblinkcustomer.fragments.PreviewGalleryFragment;
import com.example.captain_pc.beautyblinkcustomer.fragments.PreviewPlannerFragment;
import com.example.captain_pc.beautyblinkcustomer.fragments.PreviewReviewFragment;
import com.example.captain_pc.beautyblinkcustomer.model.BeauticianUser;
import com.example.captain_pc.beautyblinkcustomer.model.DataPlanner;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NunePC on 23/2/2560.
 */

public class BeauticianProfile extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public HashMap<String,Integer> eventday;
    public ArrayList<HashMap<String,Integer>> listevent;

    public String b_uid,from=null;
    String previous=null;
    ImageView bt_gallery,bt_review,bt_planner,bt_detail;
    LinearLayout sendReq;
    Integer service1,service2,service3,service4;

    Toolbar toolbar;

    String uid;
    TextView previewname,verified_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauticianprofile);

        //up button
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();

        b_uid = getIntent().getStringExtra("uid");

        previewname = (TextView) findViewById(R.id.previewname);
        bt_gallery = (ImageView) findViewById(R.id.bt_gallery);
        bt_planner = (ImageView) findViewById(R.id.bt_planner);
        bt_review = (ImageView) findViewById(R.id.bt_review);
        bt_detail = (ImageView) findViewById(R.id.bt_detail);
        sendReq = (LinearLayout) findViewById(R.id.sendReq);



        verified_detail = (TextView) findViewById(R.id.verified_detail);
        verified_detail.setOnClickListener(this);


        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("beautician").child(b_uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianUser user = dataSnapshot.getValue(BeauticianUser.class);
                if (user == null) {
                    Toast.makeText(BeauticianProfile.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {
                    previewname.setText(user.username);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BeauticianProfile.this, "Failed to load user information.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mRootRef.child("beautician-profilepromote").child(b_uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dateChild : dataSnapshot.getChildren()){
                    DataProfilePromote user = dateChild.getValue(DataProfilePromote.class);
                    if (user == null) {
                        Toast.makeText(BeauticianProfile.this, "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                    } else {
                        service1 = user.S01;
                        service2 = user.S02;
                        service3 = user.S03;
                        service4 = user.S04;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BeauticianProfile.this, "Failed to load user information.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //fragment
        if(savedInstanceState==null){
            //first create
            //Place fragment
            previous = "gallery";
            bt_gallery.setImageResource(R.mipmap.camera_703_click);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.previewContainer,new PreviewGalleryFragment())
                    .commit();

        }

        //tab button
        findViewById(R.id.bt_gallery).setOnClickListener(this);
        findViewById(R.id.bt_review).setOnClickListener(this);
        findViewById(R.id.bt_planner).setOnClickListener(this);
        findViewById(R.id.bt_detail).setOnClickListener(this);
        findViewById(R.id.sendReq).setOnClickListener(this);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("beautician-planner").child(b_uid);

        listevent = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dateChild : dataSnapshot.getChildren()){


                    for (DataSnapshot startChild : dateChild.getChildren()){

                        DataPlanner planner = startChild.getValue(DataPlanner.class);

                        eventday = new HashMap<>();
                        eventday.put("month",Integer.parseInt(planner.getMonth()));
                        eventday.put("day",Integer.parseInt(planner.getDay()));
                        eventday.put("year", Integer.parseInt(planner.getYear()));

                        listevent.add(eventday);

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }

        });

    }

    // up button method
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                if(getIntent().getStringExtra("from")!=null){
                    onBackPressed();
                }else{
                    Intent cPro = new Intent(this,SearchDetails.class);
                    cPro.putExtra("search", getIntent().getStringExtra("search"));
                    cPro.putExtra("lat", getIntent().getStringExtra("lat"));
                    cPro.putExtra("lng", getIntent().getStringExtra("lng"));
                    cPro.putExtra("word", "");
                    cPro.putExtra("min", "");
                    cPro.putExtra("max", "");
                    startActivity(cPro);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verified_detail:
                startActivity(new Intent(BeauticianProfile.this, VerifiedShow.class));
                break;
            case R.id.bt_gallery:
                if(previous=="review"){
                    bt_review.setImageResource(R.mipmap.request_702);
                }
                if (previous == "planner") {
                    bt_planner.setImageResource(R.mipmap.calendar_702);
                }
                if (previous == "detail") {
                    bt_detail.setImageResource(R.mipmap.setting_703);
                }

                previous = "gallery";
                bt_gallery.setImageResource(R.mipmap.camera_703_click);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.previewContainer, PreviewGalleryFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_review:
                if(previous=="gallery"){
                    bt_gallery.setImageResource(R.mipmap.camera_703);
                }
                if (previous == "planner") {
                    bt_planner.setImageResource(R.mipmap.calendar_702);
                }
                if (previous == "detail") {
                    bt_detail.setImageResource(R.mipmap.setting_703);
                }

                previous = "review";
                bt_review.setImageResource(R.mipmap.request_702_click);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.previewContainer, PreviewReviewFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_planner:

                if(previous=="gallery"){
                    bt_gallery.setImageResource(R.mipmap.camera_703);
                }
                if(previous=="review"){
                    bt_review.setImageResource(R.mipmap.request_702);
                }
                if (previous == "detail") {
                    bt_detail.setImageResource(R.mipmap.setting_703);
                }

                previous = "planner";
                bt_planner.setImageResource(R.mipmap.calendar_702_click);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.previewContainer, PreviewPlannerFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_detail:
                if(previous=="gallery"){
                    bt_gallery.setImageResource(R.mipmap.camera_703);
                }
                if(previous=="review"){
                    bt_review.setImageResource(R.mipmap.request_702);
                }
                if (previous == "planner") {
                    bt_planner.setImageResource(R.mipmap.calendar_702);
                }

                previous = "detail";
                bt_detail.setImageResource(R.mipmap.setting_703_click);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.previewContainer, PreviewDetailFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.sendReq:
                Intent intent = new Intent(BeauticianProfile.this,CreateRequest.class);
                intent.putExtra("type",  "A");
                intent.putExtra("beau_id", b_uid);
                intent.putExtra("service1", String.valueOf(service1));
                intent.putExtra("service2", String.valueOf(service2));
                intent.putExtra("service3", String.valueOf(service3));
                intent.putExtra("service4", String.valueOf(service4));
                startActivity(intent);
                break;
        }
    }
}