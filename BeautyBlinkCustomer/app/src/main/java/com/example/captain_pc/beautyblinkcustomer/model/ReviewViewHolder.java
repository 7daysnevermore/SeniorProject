package com.example.captain_pc.beautyblinkcustomer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by NunePC on 1/3/2560.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder  {

    public View mview;

    public ReviewViewHolder(View itemView){
        super(itemView);
        mview=itemView;

    }

    public void setImage(Context context, String image){
        ImageView img = (ImageView)mview.findViewById(R.id.pic_pro);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }

    public void setName(String name) {
        TextView n = (TextView) mview.findViewById(R.id.tname);
        n.setText(name);
    }

    public void setTopic(String topic) {
        TextView n = (TextView) mview.findViewById(R.id.topic);
        n.setText(topic);
    }

    public void setDesc(String desc) {
        TextView n = (TextView) mview.findViewById(R.id.des);
        n.setText(desc);
    }

    public void setReviewPic(Context context, String image) {
        ImageView img = (ImageView)mview.findViewById(R.id.post_image);
        Picasso.with(context).load(image).fit().centerCrop().into(img);
    }

    public void setRating(Integer rating) {
        RatingBar img = (RatingBar) mview.findViewById(R.id.rating);
        img.setRating((float) (rating*1.0));
    }
}
