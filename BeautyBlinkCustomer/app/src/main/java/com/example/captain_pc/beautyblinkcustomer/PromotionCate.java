package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.captain_pc.beautyblinkcustomer.fragments.FragmentProSer1;
import com.example.captain_pc.beautyblinkcustomer.fragments.FragmentProSer2;
import com.example.captain_pc.beautyblinkcustomer.fragments.FragmentProSer3;
import com.example.captain_pc.beautyblinkcustomer.fragments.FragmentProSer4;
import com.example.captain_pc.beautyblinkcustomer.fragments.Search;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.DataPromotion;
import com.example.captain_pc.beautyblinkcustomer.model.PromotionViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NunePC on 5/4/2560.
 */

public class PromotionCate  extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Spinner chService;
    private String previous = null;
    private TextView ser1,ser2,ser3,ser4;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null){
            //first create
            //Place fragment
            previous = "S01";
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentcontainer, new FragmentProSer1())
                    .addToBackStack(null)
                    .commit();
        }

        ser1 = (TextView) findViewById(R.id.S01);
        ser2 = (TextView) findViewById(R.id.S02);
        ser3 = (TextView) findViewById(R.id.S03);
        ser4 = (TextView) findViewById(R.id.S04);

        //tab button
        findViewById(R.id.S01).setOnClickListener(this);
        findViewById(R.id.S02).setOnClickListener(this);
        findViewById(R.id.S03).setOnClickListener(this);
        findViewById(R.id.S04).setOnClickListener(this);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent(PromotionCate.this,MainActivity.class);
                intent.putExtra("menu","search");
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.S01:
                if(previous.equals("S02")){
                    ser2.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S03")) {
                    ser3.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S04")) {
                    ser4.setTextColor(Color.parseColor("#8B8282"));
                }

                previous = "S01";
                ser1.setTextColor(Color.parseColor("#f07c7c"));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, FragmentProSer1.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.S02:
                if (previous.equals("S01")) {
                    ser1.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S03")) {
                    ser3.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S04")) {
                    ser4.setTextColor(Color.parseColor("#8B8282"));
                }

                previous = "S02";
                ser2.setTextColor(Color.parseColor("#f07c7c"));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, FragmentProSer2.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.S03:
                if (previous.equals("S01")) {
                    ser1.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S02")) {
                    ser2.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S04")) {
                    ser4.setTextColor(Color.parseColor("#8B8282"));
                }

                previous = "S03";
                ser3.setTextColor(Color.parseColor("#f07c7c"));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, FragmentProSer3.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.S04:
                if (previous.equals("S01")) {
                    ser1.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S03")) {
                    ser3.setTextColor(Color.parseColor("#8B8282"));
                }
                if (previous.equals("S02")) {
                    ser2.setTextColor(Color.parseColor("#8B8282"));
                }

                previous = "S04";
                ser4.setTextColor(Color.parseColor("#f07c7c"));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, FragmentProSer4.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
