package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.fragments.Fragment_Setting;
import com.example.captain_pc.beautyblinkcustomer.fragments.Notification;
import com.example.captain_pc.beautyblinkcustomer.fragments.Request;
import com.example.captain_pc.beautyblinkcustomer.fragments.Search;
import com.example.captain_pc.beautyblinkcustomer.fragments.UserProfilePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button testNo;
    private String displayname;
    private String personalEmail;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null){
            // Not signed in, launch the sign in activity.
            startActivity(new Intent(this, GoogleSignIn.class));

        }else {

            uid = mFirebaseUser.getUid().toString();
            //fragment
            if(savedInstanceState==null){
                //first create
                //Place fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, new Search())
                        .addToBackStack(null)
                        .commit();
            }

        }
        initInstances();

    }


    private  void initInstances(){
        testNo = (Button)findViewById(R.id.testNoti);
        testNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestNoti.class);
                startActivity(intent);
            }
        });
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Search.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_request:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Request.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_noti:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, Notification.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.bt_userprofile:
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
        finish();
    }


}
