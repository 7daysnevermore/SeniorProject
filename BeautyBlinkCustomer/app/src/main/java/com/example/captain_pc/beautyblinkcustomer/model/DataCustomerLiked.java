package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 9/2/2560.
 */

public class DataCustomerLiked {

    String name,profile,uid;

    public DataCustomerLiked(){

    }

    public DataCustomerLiked(String name,String profile,String uid){

        this.uid = uid;
        this.name = name;
        this.profile = profile;
    }
}
