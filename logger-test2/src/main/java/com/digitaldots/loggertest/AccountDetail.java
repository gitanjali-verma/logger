package com.digitaldots.loggertest;

public class AccountDetail {
    private String firstName;
    private String lastName;
    private AddressDetail address;
    private CreditCardDetail creditCardDetail;

    public AccountDetail(String firstName, String lastName, AddressDetail address, CreditCardDetail creditCardDetail) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.creditCardDetail = creditCardDetail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDetail getAddress() {
        return address;
    }

    public void setAddress(AddressDetail address) {
        this.address = address;
    }

    public CreditCardDetail getCreditCardDetail() {
        return creditCardDetail;
    }

    public void setCreditCardDetail(CreditCardDetail creditCardDetail) {
        this.creditCardDetail = creditCardDetail;
    }

}