package com.digitaldots.loggertest;

public class AddressDetail {

    private String addressLine1;
    private String city;
    private String state;
    private String pincode;
    private String country;

    public AddressDetail(String addressLine1, String city, String state, String pincode, String country) {
        super();
        this.addressLine1 = addressLine1;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}