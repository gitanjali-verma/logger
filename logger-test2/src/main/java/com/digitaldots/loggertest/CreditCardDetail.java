package com.digitaldots.loggertest;

public class CreditCardDetail {
    private String cardNumber;
    private String cvv;
    private String expDate;

    public CreditCardDetail(String cardNumber, String cvv, String expDate) {
        super();
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expDate = expDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

}