package com.example.captain_pc.beautyblinkcustomer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.fragments.Search;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchLatest;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchNearby;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPopular;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPrice;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.SearchViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by NunePC on 29/1/2560.
 */

public class SearchDetails extends AppCompatActivity implements View.OnClickListener, LocationListener {


    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    public EditText word;
    public TextView popular, latest, nearby, price;
    public String search, wording, previous = null, lat, lng;
    public ImageView icon_search, icon_filter, up, down, tab_popular, tab_nearby, tab_latest, tab_price;
    public boolean checking;
    int clickcount = 0;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    public Double cur_lat, cur_lng;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        popular = (TextView) findViewById(R.id.popular);
        latest = (TextView) findViewById(R.id.latest);
        nearby = (TextView) findViewById(R.id.nearby);
        price = (TextView) findViewById(R.id.price);
        up = (ImageView) findViewById(R.id.up);
        down = (ImageView) findViewById(R.id.down);
        tab_popular = (ImageView) findViewById(R.id.tap_popular);
        tab_latest = (ImageView) findViewById(R.id.tap_latest);
        tab_nearby = (ImageView) findViewById(R.id.tap_nearby);
        tab_price = (ImageView) findViewById(R.id.tap_price);
        icon_filter = (ImageView) findViewById(R.id.icon_filter);
        word = (EditText) findViewById(R.id.word);


        search = getIntent().getStringExtra("search");
        wording = getIntent().getStringExtra("word");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");

        word.setHint(search);
        word.setFocusable(true);

        word.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    Intent cate = new Intent(SearchDetails.this, SpecificSearch.class);
                    cate.putExtra("search", search);
                    cate.putExtra("word", "");
                    startActivity(cate);
                }

                return true; // return is important...
            }
        });


        if (savedInstanceState == null) {
            //first create
            //Place fragment
            popular.setTextColor(Color.parseColor("#f07c7c"));
            previous = "popular";
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentcontainer, new SearchPopular())
                    .commit();
        }

        //tab button
        findViewById(R.id.popular).setOnClickListener(this);
        findViewById(R.id.latest).setOnClickListener(this);
        findViewById(R.id.nearby).setOnClickListener(this);
        findViewById(R.id.price).setOnClickListener(this);
        findViewById(R.id.icon_filter).setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popular:
                if (!lat.equals("")) {
                    popular.setText("Result");
                }
                if (previous.equals("latest")) {
                    tab_latest.setVisibility(View.GONE);
                    latest.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("nearby")) {
                    tab_nearby.setVisibility(View.GONE);
                    nearby.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("price")) {
                    clickcount = 0;
                    up.setImageResource(R.drawable.arrowup);
                    down.setImageResource(R.drawable.arrowdown);
                    tab_price.setVisibility(View.GONE);
                    price.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                tab_popular.setVisibility(View.VISIBLE);
                popular.setTextColor(getResources().getColor(R.color.colorPrimary));
                previous = "popular";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchPopular.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.latest:
                if (previous.equals("popular")) {
                    tab_popular.setVisibility(View.GONE);
                    popular.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("nearby")) {
                    tab_nearby.setVisibility(View.GONE);
                    nearby.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("price")) {
                    clickcount = 0;
                    up.setImageResource(R.drawable.arrowup);
                    down.setImageResource(R.drawable.arrowdown);
                    tab_price.setVisibility(View.GONE);
                    price.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                tab_latest.setVisibility(View.VISIBLE);
                latest.setTextColor(getResources().getColor(R.color.colorPrimary));
                previous = "latest";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchLatest.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nearby:

                Toast.makeText(getApplicationContext(), "Location !"+cur_lat, Toast.LENGTH_SHORT).show();

                if (previous.equals("popular")) {
                    tab_popular.setVisibility(View.GONE);
                    popular.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("latest")) {
                    tab_latest.setVisibility(View.GONE);
                    latest.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("price")) {
                    clickcount = 0;
                    up.setImageResource(R.drawable.arrowup);
                    down.setImageResource(R.drawable.arrowdown);
                    tab_price.setVisibility(View.GONE);
                    price.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                tab_nearby.setVisibility(View.VISIBLE);
                nearby.setTextColor(getResources().getColor(R.color.colorPrimary));
                previous = "nearby";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchNearby.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.price:

                if (previous.equals("popular")) {
                    tab_popular.setVisibility(View.GONE);
                    popular.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("latest")) {
                    tab_latest.setVisibility(View.GONE);
                    latest.setTextColor(getResources().getColor(R.color.streak_color_light));
                }
                if (previous.equals("nearby")) {
                    tab_nearby.setVisibility(View.GONE);
                    nearby.setTextColor(getResources().getColor(R.color.streak_color_light));
                }

                tab_price.setVisibility(View.VISIBLE);
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
                        .replace(R.id.contentcontainer, SearchPrice.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.icon_filter:
                Intent cPro = new Intent(this, Filter.class);
                startActivity(cPro);
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        cur_lat = location.getLatitude();
        cur_lng = location.getLongitude();
        Toast.makeText(getApplicationContext(), "Location !"+location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Log.d("Latitude","status");
    }


}
