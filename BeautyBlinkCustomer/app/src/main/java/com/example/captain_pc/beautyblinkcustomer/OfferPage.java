package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.captain_pc.beautyblinkcustomer.fragments.OfferPagePopular;
import com.example.captain_pc.beautyblinkcustomer.fragments.OfferPagePrice;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchLatest;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchNearby;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPopular;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPrice;
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

public class OfferPage extends AppCompatActivity implements View.OnClickListener {
    private TextView popular,price;
    public HashMap<String, Object> requestValues;
    private Button btnOffer;
    private String username,k,m;
    ImageView up,down;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    Boolean checking;
    Integer clickcount;
    private Toolbar toolbar;
    public String requestid,previous=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page);
        requestValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("request");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestid = requestValues.get("key").toString();

        popular = (TextView) findViewById(R.id.popular);
        price = (TextView) findViewById(R.id.price);
        up = (ImageView) findViewById(R.id.up);
        down = (ImageView) findViewById(R.id.down);

        if (savedInstanceState == null) {
            //first create
            //Place fragment
            previous = "popular";
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentcontainer, new OfferPagePopular())
                    .commit();
        }

        //tab button
        findViewById(R.id.popular).setOnClickListener(this);
        findViewById(R.id.price).setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popular:
                if (previous.equals("price")) {
                    clickcount = 0;
                    up.setImageResource(R.drawable.arrowup);
                    down.setImageResource(R.drawable.arrowdown);
                    price.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                popular.setTextColor(getResources().getColor(R.color.colorPrimary));
                previous = "popular";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, OfferPagePopular.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.price:

                if (previous.equals("popular")) {
                    popular.setTextColor(getResources().getColor(R.color.streak_color_light));
                }

                price.setTextColor(getResources().getColor(R.color.colorPrimary));
                previous = "price";
                clickcount = clickcount + 1;
                if (clickcount % 2 == 0) {
                    checking = true;
                    up.setImageResource(R.drawable.arrowup);
                    down.setImageResource(R.drawable.c_arrowupdown);
                } else {
                    checking = false;
                    up.setImageResource(R.drawable.c_arrowup);
                    down.setImageResource(R.drawable.arrowdown);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, OfferPagePrice.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
