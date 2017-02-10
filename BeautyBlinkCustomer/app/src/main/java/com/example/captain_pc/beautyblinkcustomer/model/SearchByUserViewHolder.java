package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 3/2/2560.
 */

public class SearchByUserViewHolder extends RecyclerView.ViewHolder {

    public View mview;
    ImageView like,profile;
    TextView namepromote ,locationpromote ,priceS01,priceS02,priceS03,priceS04;

    public SearchByUserViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }
    public void setName(String name){
        namepromote = (TextView) mview.findViewById(R.id.name);
        namepromote.setText(name);
    }

    public void setLiked(Context context, String image) {
        like = (ImageView) mview.findViewById(R.id.like);
        Picasso.with(context).load(image).fit().centerCrop().into(like);
    }

    public void setProfile(Context context, String image) {
        profile = (ImageView) mview.findViewById(R.id.profile);
        Picasso.with(context).load(image).fit().centerCrop().into(profile);
    }
}
