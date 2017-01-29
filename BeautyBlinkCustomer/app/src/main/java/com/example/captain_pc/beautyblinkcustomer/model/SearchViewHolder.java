package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 30/1/2560.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder  {

    public View mview;
    ImageView addpromotepic1, addpromotepic2, addpromotepic3,profile;
    TextView namepromote ,locationpromote ,priceS01,priceS02,priceS03,priceS04;

    public SearchViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setName(String name){
        namepromote = (TextView) mview.findViewById(R.id.namepromote);
        namepromote.setText(name);
    }
    public void setLocation(String dist,String province){
        locationpromote = (TextView) mview.findViewById(R.id.locationpromote);
        locationpromote.setText(dist+", "+province);

    }
    public void setPicture1(Context context, String image){
        addpromotepic1 = (ImageView) mview.findViewById(R.id.addpromotepic1);
        Picasso.with(context).load(image).fit().centerCrop().into(addpromotepic1);
    }

    public void setPicture2(Context context, String image) {
        addpromotepic2 = (ImageView) mview.findViewById(R.id.addpromotepic2);
        Picasso.with(context).load(image).fit().centerCrop().into(addpromotepic2);
    }

    public void setPicture3(Context context, String image) {
        addpromotepic3 = (ImageView) mview.findViewById(R.id.addpromotepic3);
        Picasso.with(context).load(image).fit().centerCrop().into(addpromotepic3);
    }

    public void setProfile(Context context, String image) {
        profile = (ImageView) mview.findViewById(R.id.picpromote);
        Picasso.with(context).load(image).fit().centerCrop().into(profile);
    }

    public void setS01(Integer price){
        priceS01 = (TextView) mview.findViewById(R.id.priceS01);
        priceS01.setText("Hair and Makeup : "+price);
    }

    public void setS02(Integer price) {
        priceS02 = (TextView) mview.findViewById(R.id.priceS02);
        priceS02.setText("Hair and Makeup : " + price);
    }

    public void setS03(Integer price) {
        priceS03 = (TextView) mview.findViewById(R.id.priceS03);
        priceS03.setText("Hair and Makeup : " + price);
    }

    public void setS04(Integer price) {
        priceS04 = (TextView) mview.findViewById(R.id.priceS04);
        priceS04.setText("Hair and Makeup : " + price);
    }

}
