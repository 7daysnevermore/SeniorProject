package com.example.captain_pc.beautyblinkcustomer.model;

import com.google.firebase.database.Exclude;

/**
 * Created by NunePC on 29/1/2560.
 */

public class DataProfilePromote {

    public String sub_district,district,province,username,picture1,picture2,picture3,BeauticianProfile,rating,uid,latitude,longitude;
    public Integer S01,S02,S03,S04;


    public DataProfilePromote(){

    }

    public DataProfilePromote(Integer S01,Integer S02,Integer S03,Integer S04,String sub_district, String district, String province,String username,String picture1,String picture2,String picture3, String BeauticianProfile, String rating, String uid
            , String latitude,String longitude){

        this.S01 = S01;
        this.S02 = S02;
        this.S03 = S03;
        this.S04 = S04;
        this.sub_district = sub_district;
        this.district = district;
        this.province = province;
        this.username = username;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.BeauticianProfile = BeauticianProfile;
        this.rating = rating;
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
    }



}
