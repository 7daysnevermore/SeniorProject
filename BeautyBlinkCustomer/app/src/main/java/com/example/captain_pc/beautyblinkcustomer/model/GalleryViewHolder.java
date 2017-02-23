package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 24/2/2560.
 */

public class GalleryViewHolder extends RecyclerView.ViewHolder  {

    public View mview;

    public GalleryViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setImage(Context context, String image){
        ImageView img = (ImageView)mview.findViewById(R.id.post_gall);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }
}