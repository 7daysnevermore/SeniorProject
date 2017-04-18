package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.fragments.Fragment_Setting;
import com.example.captain_pc.beautyblinkcustomer.fragments.Notification;
import com.example.captain_pc.beautyblinkcustomer.fragments.Request;
import com.example.captain_pc.beautyblinkcustomer.fragments.Search;
import com.example.captain_pc.beautyblinkcustomer.fragments.UserProfilePage;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button testNo;
    private String displayname;
    private String personalEmail;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView bt_search,tap_search,bt_request,tap_request,bt_noti,tap_noti,bt_userprofile,tap_user;
    Toolbar toolbar;
    String uid, previous = null;
    EditText word;
    String menu = null;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        FirebaseMessaging.getInstance().subscribeToTopic("customer");
        if(mFirebaseUser == null){
            // Not signed in, launch the sign in activity.
            startActivity(new Intent(this, GoogleSignIn.class));

        }else {

            bt_search = (ImageView) findViewById(R.id.bt_search);
            bt_request = (ImageView) findViewById(R.id.bt_request);
            bt_noti = (ImageView) findViewById(R.id.bt_noti);
            bt_userprofile = (ImageView) findViewById(R.id.bt_userprofile);
            tap_search = (ImageView) findViewById(R.id.tap_search);
            tap_request = (ImageView) findViewById(R.id.tap_request);
            tap_noti = (ImageView) findViewById(R.id.tap_noti);
            tap_user = (ImageView) findViewById(R.id.tap_user);

            uid = mFirebaseUser.getUid().toString();


            menu = getIntent().getStringExtra("menu");
            if (menu != null) {
                if (menu.equals("request")) {
                    previous = "request";
                    bt_request.setImageResource(R.mipmap.request_702_click);
                    tap_request.setVisibility(View.VISIBLE);
                    tap_search.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.contentcontainer, new Request())
                            .commit();
                }
                if (menu.equals("search")) {
                    previous = "search";
                    bt_search.setImageResource(R.mipmap.ic_action_search_click);
                    tap_search.setVisibility(View.VISIBLE);
                    tap_search.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.contentcontainer, new Search())
                            .commit();
                }
                if (menu.equals("noti")) {
                    previous = "noti";
                    bt_noti.setImageResource(R.mipmap.noti_702_click);
                    tap_noti.setVisibility(View.VISIBLE);
                    tap_search.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.contentcontainer, new Notification())
                            .commit();
                }
                if (menu.equals("user")) {
                    previous = "user";
                    bt_userprofile.setImageResource(R.mipmap.setting_703_click);
                    tap_user.setVisibility(View.VISIBLE);
                    tap_search.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.contentcontainer, new Fragment_Setting())
                            .commit();
                }
            } else {
                //fragment
                if(savedInstanceState==null){
                    //first create
                    //Place fragment
                    previous = "search";
                    bt_search.setImageResource(R.mipmap.ic_action_search_click);
                    tap_search.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentcontainer, new Search())
                            .addToBackStack(null)
                            .commit();
                }
            }

            String beauid = getIntent().getStringExtra("chooseoffer_beauid");

            if(beauid!=null){

                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                mRootRef.child("beautician-received").addListenerForSingleValueEvent(new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot startChild : dataSnapshot.getChildren()) {


                            String key = startChild.getKey();

                            if (!key.equals(getIntent().getStringExtra("chooseoffer_beauid"))) {


                                for (DataSnapshot schild : startChild.getChildren()) {

                                    String key1 = schild.getKey();

                                    if (key1.equals(getIntent().getStringExtra("chooseoffer_requestid"))) {

                                        DatabaseReference m = FirebaseDatabase.getInstance().getReference();

                                        m.child("beautician-received").child(key).child(key1).child("status").setValue("8");


                                    }


                                }


                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                    });

                }


            word = (EditText) findViewById(R.id.word);
            word.setFocusable(false);
            word.requestFocus();

            word.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(MotionEvent.ACTION_UP == event.getAction()) {
                        Intent cate = new Intent(MainActivity.this,SpecificByUser.class);
                        startActivity(cate);
                    }

                    return true; // return is important...
                }
            });

            initInstances();
        }



    }



    private  void initInstances(){

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        //tab button
        findViewById(R.id.bt_search).setOnClickListener(this);
        findViewById(R.id.bt_request).setOnClickListener(this);
        findViewById(R.id.bt_noti).setOnClickListener(this);
        findViewById(R.id.bt_userprofile).setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                if(previous.equals("request")){
                    tap_request.setVisibility(View.GONE);
                    bt_request.setImageResource(R.mipmap.request_702);
                }
                if (previous.equals("noti")) {
                    tap_noti.setVisibility(View.GONE);
                    bt_noti.setImageResource(R.mipmap.noti_702);
                }
                if (previous.equals("user")) {
                    tap_user.setVisibility(View.GONE);
                    bt_userprofile.setImageResource(R.mipmap.setting_703);
                }

                previous = "search";
                bt_search.setImageResource(R.mipmap.ic_action_search_click);
                tap_search.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Search.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_request:
                if(previous.equals("search")){
                    tap_search.setVisibility(View.GONE);
                    bt_search.setImageResource(R.mipmap.ic_action_search1);
                }
                if (previous.equals("noti")) {
                    tap_noti.setVisibility(View.GONE);
                    bt_noti.setImageResource(R.mipmap.noti_702);
                }
                if (previous.equals("user")) {
                    tap_user.setVisibility(View.GONE);
                    bt_userprofile.setImageResource(R.mipmap.setting_703);
                }

                previous = "request";
                bt_request.setImageResource(R.mipmap.request_702_click);
                tap_request.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Request.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_noti:
                if(previous.equals("search")){
                    tap_search.setVisibility(View.GONE);
                    bt_search.setImageResource(R.mipmap.ic_action_search1);
                }
                if (previous.equals("request")) {
                    tap_request.setVisibility(View.GONE);
                    bt_request.setImageResource(R.mipmap.request_702);
                }
                if (previous.equals("user")) {
                    tap_user.setVisibility(View.GONE);
                    bt_userprofile.setImageResource(R.mipmap.setting_703);
                }

                previous = "noti";
                bt_noti.setImageResource(R.mipmap.noti_702_click);
                tap_noti.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Notification.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_userprofile:
                if(previous.equals("search")){
                    tap_search.setVisibility(View.GONE);
                    bt_search.setImageResource(R.mipmap.ic_action_search1);
                }
                if (previous.equals("request")) {
                    tap_request.setVisibility(View.GONE);
                    tap_request.setImageResource(R.mipmap.request_702);
                }
                if (previous.equals("noti")) {
                    tap_noti.setVisibility(View.GONE);
                    bt_noti.setImageResource(R.mipmap.noti_702);
                }

                previous = "user";
                bt_userprofile.setImageResource(R.mipmap.setting_703_click);
                tap_user.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Fragment_Setting.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
