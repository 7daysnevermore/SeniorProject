package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 24/2/2560.
 */

public class DataPlanner {

    public String title;
    public String location;
    public String note;
    public String day,month,year;
    public String start;
    public String end;
    public String status;
    public String uid;

    public DataPlanner(){

    }

    public DataPlanner(String day,String end,String location,String month,String note
            , String start, String status,String title, String uid,String year){

        this.title = title;
        this.location = location;
        this.note = note;
        this.day = day;
        this.month = month;
        this.year = year;
        this.start = start;
        this.end = end;
        this.uid = uid;
        this.status = status;

    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){ return this.title;}

    public void setLocation(String location){ this.location = location; }

    public String getLocation(){ return this.location; }

    public void setNote(String note){ this.note = note; }

    public String getNote(){ return this.note; }

    public void setUid(String uid){this.uid = uid;}

    public String getUid(){ return this.uid;}

    public void setDay(String day){
        this.day = day;
    }

    public String getDay(){ return this.day;}

    public void setMonth(String month){ this.month = month; }

    public String getMonth(){ return this.month; }

    public void setYear(String year){ this.year = year; }

    public String getYear(){ return this.year; }

    public void setStart(String start){this.start = start;}

    public String getStart(){ return this.start;}

    public void setEnd(String end){ this.end = end; }

    public String getEnd(){ return this.end; }

    public void setStatus(String status){this.status = status;}

    public String getStatus(){ return this.status;}

}
