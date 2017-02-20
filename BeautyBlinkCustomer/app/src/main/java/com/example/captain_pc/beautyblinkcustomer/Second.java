package com.example.captain_pc.beautyblinkcustomer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Second extends AppCompatActivity {
    private String img;
    private ImageView imV;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView txt = (TextView) findViewById(R.id.textView);
        imV = (ImageView)findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                txt.append(key + ": " + value + "\n\n");
                img=value.toString();
            }
        }
        //img=txt.getText().toString();
        Log.d("Text","="+img);
        bitmap = getBitmapFromURL("http://opsbug.com/static/google-io.jpg");
        imV.setImageBitmap(bitmap);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        }
        public Bitmap getBitmapFromURL(String src){
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;


            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

