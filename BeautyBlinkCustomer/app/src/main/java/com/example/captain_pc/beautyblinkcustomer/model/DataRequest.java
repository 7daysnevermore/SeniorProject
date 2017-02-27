package com.example.captain_pc.beautyblinkcustomer.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chin- on 1/16/2017.
 */

public class DataRequest {
    private String name,date,dateform,event,service,special_cus,status,time,numP,maxP,special_beau,locate,key;
    private int color;

    public DataRequest(){

    }

    public DataRequest(int color,String dateform,String date, String event,String service,String special_cus,String status,
                       String time, String numP,String maxP,String special_beau,String locate,String name,String key){
        this.key=key;
        this.color=color;
        this.dateform=dateform;
        this.date=date;
        this.event=event;
        this.service=service;
        this.special_cus=special_cus;
        this.status=status;
        this.time=time;
        this.numP=numP;
        this.maxP=maxP;
        this.special_beau=special_beau;
        this.locate=locate;
        this.name=name;

    }
    public String getName(){return this.name;}
    public void setName(String name){this.name=name;}
    public String getDateform(){return  this.dateform;}
    public void setDateform(String dateform){this.dateform=dateform;}
    public String getCurrenttime(){return  this.dateform;}
    public void setCurrenttime(String dateform){this.dateform=dateform;}
    public String getLocation(){
        return this.locate;
    }
    public void setLocation(String locate){
        this.locate=locate;
    }
    public String getNumberofcustomer(){
        return this.numP;
    }
    public void setNumberofcustomer(String numP){
        this.numP=numP;
    }
    public String getMaxP(){return this.maxP;}
    public void setMaxP(String maxP){this.maxP=maxP;}
    public String getMaxprice(){
        return this.maxP;
    }
    public void setMaxprice(String maxP){this.maxP=maxP;}
    public String getBeauticianoffer(){
        return  this.special_beau;
    }
    public void setBeauticianoffer(String special_beau){
        this.special_beau = special_beau;
    }
     public int getColor(){
       return  this.color;
   }
    public void setColor(int color){
        this.color=color;
    }
    public String getDate(){
        return  this.date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getEvent(){
        return this.event;
    }
    public void setEvent(String event){
        this.event=event;
    }
    public String getService(){
        return this.service;
    }
    public void setService(String service){
        this.service=service;
    }
    public String getSpecialcus(){
        return this.special_cus;
    }
    public void setSpecialcus(String special_cus){
        this.special_cus=special_cus;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getTime(){
        return  this.time;
    }
    public void setTime(String time){
        this.time=time;
    }

}
