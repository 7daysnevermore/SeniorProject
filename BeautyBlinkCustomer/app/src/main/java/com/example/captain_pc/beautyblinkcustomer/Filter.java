package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by NunePC on 10/2/2560.
 */

public class Filter extends AppCompatActivity {

    EditText min,max;
    TextView range1,range2,range3,range4,range5,range6;
    Button apply,cancel;
    String condition=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        min = (EditText) findViewById(R.id.min);
        max = (EditText) findViewById(R.id.max);
        range1 = (TextView) findViewById(R.id.range1);
        range2 = (TextView) findViewById(R.id.range2);
        range3 = (TextView) findViewById(R.id.range3);
        range4 = (TextView) findViewById(R.id.range4);
        range5 = (TextView) findViewById(R.id.range5);
        range6 = (TextView) findViewById(R.id.range6);
        apply = (Button) findViewById(R.id.apply);
        cancel = (Button) findViewById(R.id.cancel);

        range1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("0");
                max.setText("800");
            }

        });
        range2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("801");
                max.setText("1500");
            }

        });
        range3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("1501");
                max.setText("2500");
            }

        });
        range4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("2501");
                max.setText("3500");
            }

        });
        range5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("3501");
                max.setText("4500");
            }

        });
        range6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min.setText("4501");
                max.setText("100000");
            }

        });
        verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verified.setBackgroundColor(Color.parseColor("#FFE4E1"));
                unverified.setBackgroundColor(Color.parseColor("#E8E8E8"));
                condition = "verified";
            }

        });
        unverified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unverified.setBackgroundColor(Color.parseColor("#FFE4E1"));
                verified.setBackgroundColor(Color.parseColor("#E8E8E8"));
                condition = "unverified";
            }

        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mintext = min.getText().toString();
                String maxtext = max.getText().toString();

                if(TextUtils.isEmpty(mintext)){
                    mintext = "";
                }
                if (TextUtils.isEmpty(maxtext)) {
                    maxtext = "";
                }

                Intent cPro = new Intent(Filter.this, SearchDetails.class);
                cPro.putExtra("search",getIntent().getStringExtra("search"));
                cPro.putExtra("word",getIntent().getStringExtra("word"));
                cPro.putExtra("lat", getIntent().getStringExtra("lat"));
                cPro.putExtra("lng", getIntent().getStringExtra("lng"));
                cPro.putExtra("min", mintext);
                cPro.putExtra("max", maxtext);
                startActivity(cPro);
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });


    }
}
