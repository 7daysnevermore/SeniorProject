package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 1/16/2017.
 */

public class DataRequest {
    private String promotion,image,details,price,
            sale,datefrom,dateto,uid,name;

    public DataRequest(){

    }

    public DataRequest(String promotion,String image,String details,String price,String sale
            , String datefrom, String dateto,String uid, String name){

        this.promotion=promotion;
        this.image=image;
        this.details=details;
        this.price = price;
        this.sale = sale;
        this.datefrom = datefrom;
        this.dateto = dateto;
        this.uid = uid;
        this.name = name;

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

    public void setDetails(String detail) {
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


}
