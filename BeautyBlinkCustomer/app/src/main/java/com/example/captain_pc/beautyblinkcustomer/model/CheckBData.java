package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 2/26/2017.
 */

public class CheckBData {
    String beauticianoffer,name,status,dateform,maxprice,uid,key;
    public CheckBData(){

    }
    public CheckBData(String beauticianoffer,String name,String status,String dateform,String maxprice,String uid,String key){
        this.beauticianoffer=beauticianoffer;
        this.uid=uid;
        this.name=name;
        this.status=status;
        this.dateform=dateform;
        this.maxprice=maxprice;
        this.key=key;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBeauticianoffer() {
        return beauticianoffer;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getDateform() {
        return dateform;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setBeauticianoffer(String beauticianoffer) {
        this.beauticianoffer = beauticianoffer;
    }

    public void setDateform(String dateform) {
        this.dateform = dateform;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
