package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class PromotionDetails extends AppCompatActivity implements View.OnClickListener {

    HashMap<String, Object> promotionValues;
    private TextView proTopic,proPrice,proSale,proDF,proDT,proDetails,name ;
    ImageView proImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpromotion);

        promotionValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("promotion");

        name = (TextView)findViewById(R.id.name);
        proTopic = (TextView)findViewById(R.id.proTopic);
        proPrice = (TextView) findViewById(R.id.proPrice);
        proSale = (TextView) findViewById(R.id.proSale);
        proDF = (TextView) findViewById(R.id.proDF);
        proDT = (TextView) findViewById(R.id.proDT);
        proDetails = (TextView) findViewById(R.id.proDetails);
        proImg = (ImageView) findViewById(R.id.proImg);

        name.setText(promotionValues.get("name").toString());
        proTopic.setText(promotionValues.get("promotion").toString());
        proPrice.setText(promotionValues.get("price").toString()+" Bath");
        proPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        proSale.setText(promotionValues.get("sale").toString()+" Bath");
        proDF.setText("Date from : "+promotionValues.get("dateFrom").toString());
        proDT.setText("Date to     : "+promotionValues.get("dateTo").toString());
        proDetails.setText(promotionValues.get("details").toString());

        Picasso.with(this).load(promotionValues.get("image").toString()).into(proImg);

        findViewById(R.id.btn_usepromotion).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_usepromotion:

                Intent cPro = new Intent(PromotionDetails.this,MainActivity.class);
                cPro.putExtra("promotion",  promotionValues);
                startActivity(cPro);

                break;

        }

    }
}
