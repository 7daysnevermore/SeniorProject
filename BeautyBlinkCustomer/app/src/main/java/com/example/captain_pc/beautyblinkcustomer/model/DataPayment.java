package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 28/2/2560.
 */

public class DataPayment {


    public String amount,bank,currenttime,customerid,date,slip,status,time;

    public DataPayment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public DataPayment(String amount,String bank,String currenttime,String customerid,String date,String slip,String status,String time) {

        this.amount = amount;
        this.bank = bank;
        this.currenttime = currenttime;
        this.customerid = customerid;
        this.date = date;
        this.slip = slip;
        this.status = status;
        this.time = time;

    }
}
