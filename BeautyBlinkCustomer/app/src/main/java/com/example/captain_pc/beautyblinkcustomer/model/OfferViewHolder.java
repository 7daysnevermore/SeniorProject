package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 28/2/2560.
 */

public class OfferViewHolder extends RecyclerView.ViewHolder {

    public View mview;

    public OfferViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setImage(Context context, String image){
        ImageView img = (ImageView)mview.findViewById(R.id.beauprofile);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }

    public void setRequestPic(Context context, String image) {
        ImageView img = (ImageView) mview.findViewById(R.id.regpic);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }

    public void setOfferPic(Context context, String image) {
        ImageView img = (ImageView) mview.findViewById(R.id.offerpic);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }

    public void setUsername(String name){
        TextView text = (TextView) mview.findViewById(R.id.username);
        text.setText(name);
    }

    public void setPrice(String price) {
        TextView text = (TextView) mview.findViewById(R.id.price);
        text.setText(price+"฿");
    }

    public void setRating(String rating){
        TextView text = (TextView) mview.findViewById(R.id.rating);
        text.setText("Rating: "+rating);
    }
}
