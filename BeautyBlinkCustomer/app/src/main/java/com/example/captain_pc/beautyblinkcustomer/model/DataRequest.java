package com.example.captain_pc.beautyblinkcustomer.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chin- on 1/16/2017.
 */

public class DataRequest {
    public String date,dateform,event,service,specialrequest,status,time,location,reqpic,username,userprofile,customerid;
    public int numberofperson,maxprice;

    public DataRequest(){

    }

    public DataRequest(String dateform,String date, String event,String service,String specialrequest,String status,
                       String time, Integer numberofperson,Integer maxprice,String location,String reqpic,String username,String userprofile,String customerid){

        this.dateform=dateform;
        this.date=date;
        this.event=event;
        this.service=service;
        this.specialrequest=specialrequest;
        this.status=status;
        this.time=time;
        this.numberofperson=numberofperson;
        this.maxprice=maxprice;
        this.location=location;
        this.reqpic = reqpic;
        this.username = username;
        this.userprofile = userprofile;
        this.customerid = customerid;

    }

}
