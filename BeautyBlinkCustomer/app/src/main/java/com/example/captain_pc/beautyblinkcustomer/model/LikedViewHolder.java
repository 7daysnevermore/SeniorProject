package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 10/2/2560.
 */

public class LikedViewHolder extends RecyclerView.ViewHolder {

    public View mview;
    public ImageView like;
    ImageView profile;
    TextView namepromote;

    public LikedViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setLike(){
        like = (ImageView) mview.findViewById(R.id.like);
        like.setImageResource(R.mipmap.liked);
    }

    public void setName(String name){
        namepromote = (TextView) mview.findViewById(R.id.name);
        namepromote.setText(name);
    }

    public void setProfile(Context context, String image) {
        profile = (ImageView) mview.findViewById(R.id.profile);
        Picasso.with(context).load(image).fit().centerCrop().into(profile);
    }


}
