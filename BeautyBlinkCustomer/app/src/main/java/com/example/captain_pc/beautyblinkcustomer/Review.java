package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Review extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText reviewTopic, reviewDesc;
    private ImageView reviewImg;
    private Uri reviewImg_show;

    private int SELECT_FILE = 1;

    //Rating Bar
    private TextView txtView1;
    private RatingBar rating_Bar;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        txtView1 = (TextView)findViewById(R.id.service);
        rating_Bar = (RatingBar)findViewById(R.id.ratingBar);

        rating_Bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                str = String.valueOf(rating);
                txtView1.setText("Your Selected : " + str);
                Toast.makeText(Review.this, str, Toast.LENGTH_LONG).show();
            }
        });

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
