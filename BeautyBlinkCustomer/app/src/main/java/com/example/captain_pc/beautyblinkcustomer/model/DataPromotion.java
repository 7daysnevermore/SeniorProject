package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 17/1/2560.
 */

public class DataPromotion {
    public String promotion;
    private String image;
    private String details;
    private String price;
    private String sale;
    private String datefrom;
    private String dateto;
    private String uid;
    private String name,service,status,profile;

    public DataPromotion(){

    }

    public DataPromotion(String promotion,String image,String details,String price,String sale
            , String datefrom, String dateto,String uid, String name,String service,String statu,String profile){

        this.promotion=promotion;
        this.image=image;
        this.details=details;
        this.price = price;
        this.sale = sale;
        this.datefrom = datefrom;
        this.dateto = dateto;
        this.uid = uid;
        this.name = name;
        this.service = service;
        this.status = status;
        this.profile = profile;
    }
    public String getPromotion(){
        return this.promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails(){
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale() {
        return this.sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getDateFrom() {
        return this.datefrom;
    }

    public void setDateFrom(String datefrom) {
        this.datefrom = datefrom;
    }

    public String getDateTo() {
        return this.dateto;
    }

    public void setDateTo(String dateto) {
        this.dateto = dateto;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }


}
