package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 28/2/2560.
 */

public class DataOffer {

        public String date,event,service,specialrequest,status,time,location,reqpic,username,userprofile,customerid,currenttime,
                        beauid,beauname,offerid,offerpic,requestid,beauprofile,numberofperson,price,rating;
        public int amount;

        public DataOffer(){

        }

        public DataOffer(String date, String event,String service,String specialrequest,String status,String currenttime,
                           String rating,Integer amount,String time, String numberofperson,String price,String location,String reqpic,String username,
                         String userprofile,String customerid,String beauid,String beauname,String offerid,String offerpic,String requestid,String beauprofile){

            this.date=date;
            this.event=event;
            this.service=service;
            this.specialrequest=specialrequest;
            this.status=status;
            this.time=time;
            this.numberofperson=numberofperson;
            this.price=price;
            this.location=location;
            this.reqpic = reqpic;
            this.username = username;
            this.userprofile = userprofile;
            this.customerid = customerid;
            this.currenttime = currenttime;
            this.amount = amount;
            this.beauid = beauid;
            this.beauname = beauname;
            this.beauprofile = beauprofile;
            this.offerid = offerid;
            this.offerpic = offerpic;
            this.requestid = requestid;
            this.rating = rating;

        }

        public void setCurrenttime(String currenttime){
            this.currenttime = currenttime;
        }

        public String getCurrenttime() {
            return this.currenttime;
        }

    }
