package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 5/4/2560.
 */

public class PromotionViewHolder extends RecyclerView.ViewHolder  {

    public View mview;

    public PromotionViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setPromotion(String promotion){
        TextView post_promotion = (TextView)mview.findViewById(R.id.promotion);
        post_promotion.setText(promotion);
    }
    public void setImage(Context context, String image){
        ImageView img = (ImageView)mview.findViewById(R.id.post_image);

        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }
    public void setPrice(String price){
        TextView post_price = (TextView)mview.findViewById(R.id.price);
        post_price.setText(price+" Baht");
        post_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
    public void setSale(String sale){
        TextView post_sale= (TextView)mview.findViewById(R.id.sale);
        post_sale.setText(sale);
    }

    public void setService(String service){
        TextView post_sale= (TextView)mview.findViewById(R.id.service);
        if(service.equals("S01")){
            post_sale.setText("MakeupandHair");
        }
        if (service.equals("S02")) {
            post_sale.setText("Makeup");
        }
        if (service.equals("S03")) {
            post_sale.setText("Hairstyle");
        }if (service.equals("S04")) {
            post_sale.setText("Hairdressing");
        }

    }
}