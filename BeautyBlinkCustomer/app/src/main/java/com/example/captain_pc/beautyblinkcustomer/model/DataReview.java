package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 28/2/2560.
 */

public class DataReview {

    public String topic,desc,currenttime,customerid,image,name,profile,beauid,requestid;
    public Integer rating;

    public DataReview() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public DataReview(String topic,String desc,String currenttime,String customerid,String image,Integer rating,String name,String profile,
                      String beauid,String requestid) {

        this.topic = topic;
        this.desc = desc;
        this.currenttime = currenttime;
        this.customerid = customerid;
        this.image = image;
        this.rating = rating;
        this.name = name;
        this.profile = profile;
        this.beauid = beauid;
        this.requestid = requestid;

    }
}
