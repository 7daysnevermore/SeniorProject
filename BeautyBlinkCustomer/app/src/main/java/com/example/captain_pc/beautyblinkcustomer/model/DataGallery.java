package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 24/2/2560.
 */

public class DataGallery {

    public String image,caption,username,uid;
    public Long timestamp;

    public DataGallery() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public DataGallery(String image,String caption,String username,String uid,Long timestamp) {

        this.image = image;
        this.caption = caption;
        this.username = username;
        this.uid = uid;
        this.timestamp = timestamp;

    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){ return this.image;}

    public void setCaption(String caption){ this.caption = caption; }

    public String getCaption(){ return this.caption; }

    public void setName(String username){ this.username = username; }

    public String getName(){ return this.username; }

    public void setUid(String uid){this.uid = uid;}

    public String getUid(){ return this.uid;}

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }
}