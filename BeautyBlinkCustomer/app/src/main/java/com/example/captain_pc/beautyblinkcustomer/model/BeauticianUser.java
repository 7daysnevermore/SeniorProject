package com.example.captain_pc.beautyblinkcustomer.model;

/**
 * Created by NunePC on 24/2/2560.
 */

public class BeauticianUser {

    public String username, latitude, longitude, building, profile,email, firstname, lastname, phone, address_number,
            address_sub_district, address_district, address_province, address_code, birthday, gender;

    public BeauticianUser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public BeauticianUser(String profile, String username, String email,String firstname,String lastname,String phone,String address_number,
                String building, String address_sub_district,String address_district,String address_province,String address_code,
                String birthday,String gender, String latitude, String longitude) {

        this.profile = profile;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address_number = address_number;
        this.building = building;
        this.address_sub_district = address_sub_district;
        this.address_district = address_district;
        this.address_province = address_province;
        this.address_code = address_code;
        this.birthday = birthday;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
