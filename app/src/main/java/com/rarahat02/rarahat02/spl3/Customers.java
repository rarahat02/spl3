package com.rarahat02.rarahat02.spl3;

public class Customers
{
    public String id;
    public String name;
    public String phoneNumber;
    public int purchasedSongCount;


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

    public Customers(String id, String name, String phoneNumber, int purchasedSongCount) {

        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.purchasedSongCount = purchasedSongCount;
    }

    public Customers()
    {

    }
}