package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 6/4/2560.
 */

public class DataVerified {

    public String citizenid, makeup, hairstyle, hairdressing;

    public DataVerified() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public DataVerified(String citizenid, String makeup, String hairstyle, String hairdressing){
        this.citizenid = citizenid;
        this.makeup = makeup;
        this.hairstyle = hairstyle;
        this.hairdressing = hairdressing;
    }
}
