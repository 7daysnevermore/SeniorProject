package com.example.captain_pc.beautyblinkcustomer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by NunePC on 29/1/2560.
 */

public class SearchDetails extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    public EditText word;
    public String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();



        word = (EditText) findViewById(R.id.word);

        search = getIntent().getStringExtra("search");

        word.setHint(search);
        word.setFocusableInTouchMode(true);
        word.requestFocus();

        word.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(SearchDetails.this, word.getText().toString(),
                            Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

            if(savedInstanceState==null){
                //first create
                //Place fragment

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentcontainer,new SearchPopular())
                        .commit();
            }

        //tab button
        findViewById(R.id.popular).setOnClickListener(this);
        findViewById(R.id.latest).setOnClickListener(this);
        findViewById(R.id.nearby).setOnClickListener(this);
        findViewById(R.id.price).setOnClickListener(this);

    }

    @Override
    protected void onStart(){
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popular:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer,SearchPopular.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.latest:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchLatest.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nearby:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchNearby.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.price:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchPrice.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }



}
