package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 2/25/2017.
 */

public class ModelRequest {
    private String event,service,currenttime,location,date,maxprice,status;
    public ModelRequest(){

    }
    public ModelRequest(String event,String service,String currenttime,String location,String date,String maxprice,String status){
        this.event=event;
        this.service=service;
        this.currenttime=currenttime;
        this.location=location;
        this.date=date;
        this.maxprice=maxprice;
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaxprice(){
        return this.maxprice;
    }
    public void setMaxprice(String maxprice){
        this.maxprice=maxprice;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getLocation(){
        return this.location;
    }
    public void setLocation(String location){
        this.location=location;
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
    public String getCurrenttime(){
        return this.currenttime;
    }
    public void setCurrenttime(String currenttime){
        this.currenttime=currenttime;
    }
}
