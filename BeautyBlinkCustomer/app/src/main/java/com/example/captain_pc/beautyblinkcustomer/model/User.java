package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by chin- on 1/17/2017.
 */

public class User {

    public String email, firstname, lastname, phone, address_number,
            address_sub_district, address_district, address_province, address_code, birthday, gender,profile;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String email,String firstname,String lastname,String phone,String address_number,
                String address_sub_district,String address_district,String address_province,String address_code,
                String birthday,String gender,String profile) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address_number = address_number;
        this.address_sub_district = address_sub_district;
        this.address_district = address_district;
        this.address_province = address_province;
        this.address_code = address_code;
        this.birthday = birthday;
        this.gender = gender;
        this.profile = profile;
    }
}
