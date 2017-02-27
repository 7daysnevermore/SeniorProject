package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 2/26/2017.
 */

public class Offerss {

    public String service,key,event,namecus,numberofcustomer,specialcus,
    beauticianoffer,date,location,time,maxprice,uid;

    public Offerss() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Offerss(String service,String key,String event,String namecus,String numberofcustomer,
                   String specialcus,String beauticianoffer,String date,String location,String time,String maxprice,String uid) {
        this.event=event;
        this.uid=uid;
        this.namecus=namecus;
        this.numberofcustomer=numberofcustomer;
        this.specialcus=specialcus;
        this.beauticianoffer=beauticianoffer;
        this.date=date;
        this.location=location;
        this.service=service;
        this.time=time;
        this.maxprice=maxprice;
        this.key=key;
    }
}
