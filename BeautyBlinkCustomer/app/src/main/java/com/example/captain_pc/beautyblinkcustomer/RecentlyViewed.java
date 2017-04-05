package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static com.example.captain_pc.beautyblinkcustomer.R.id.toolbar;

/**
 * Created by CaptainPC on 20/1/2560.
 */

public class RecentlyViewed extends AppCompatActivity {

    Toolbar toolbar;
    Button btn_write_review;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentlyviewed);

        //up button
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_write_review = (Button) findViewById(R.id.btn_write_review);
        btn_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecentlyViewed.this, Review.class));
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
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
