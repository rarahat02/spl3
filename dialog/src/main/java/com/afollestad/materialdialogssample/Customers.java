package com.afollestad.materialdialogssample;

public class Customers
{
    public String id;
    public String name;
    public String phoneNumber;
    public int purchasedSongCount;
    public String imei_1;
    public String imei_2;
    public String imei_3;


    public String getImei_1() {
        return imei_1;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPurchasedSongCount() {
        return purchasedSongCount;
    }

    public void setPurchasedSongCount(int purchasedSongCount) {
        this.purchasedSongCount = purchasedSongCount;
    }

    public Customers(String id, String name, String phoneNumber, int purchasedSongCount, String imei1) {

        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.purchasedSongCount = purchasedSongCount;
        this.imei_1 = imei1;
        this.imei_2 = "";
        this.imei_3 = "";
    }

    public Customers()
    {

    }
}