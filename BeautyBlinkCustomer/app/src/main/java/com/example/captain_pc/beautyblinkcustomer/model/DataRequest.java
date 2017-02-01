package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 1/16/2017.
 */

public class DataRequest {
    private String date,event,service,special_cus,status,time,numP,maxP,special_beau,locate;
    private int color;

    public DataRequest(){

    }

    public DataRequest(int color,String date, String event,String service,String special_cus,String status,
                       String time, String numP,String maxP,String special_beau,String locate){

        this.color=color;
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

    }
    public String getLocate(){
        return this.locate;
    }
    public void setLocate(String locate){
        this.locate=locate;
    }
    public String getNumP(){
        return this.numP;
    }
    public void setNumP(String numP){
        this.numP=numP;
    }
    public String getMaxP(){
        return this.maxP;
    }
    public void setMaxP(String maxP){this.maxP=maxP;}
    public String getBeau(){
        return  this.special_beau;
    }
    public void setBeau(String special_beau){
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
    public String getCus(){
        return this.special_cus;
    }
    public void setCus(String special_cus){
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
