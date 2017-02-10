package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 2/6/2017.
 */

public class ChatStructure {
    private String msg,name;

    public ChatStructure(){

    }
    public ChatStructure(String name,String msg){
        this.name=name;
        this.msg=msg;

    }
    public String getName(){return this.name;}
    public void setName(String name){this.name=name;}
    public String getMsg(){return this.msg;}
    public void setMsg(String msg){this.msg=msg;}

}
