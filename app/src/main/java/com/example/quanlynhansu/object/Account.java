package com.example.quanlynhansu.object;

import android.database.sqlite.SQLiteDatabase;

public class Account {

    private int accountID;

    private String userName;
    private String passWord;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private int roomID;

    public Account(int accountID,  String userName, String passWord, String fullName, String email, String phone, String address,int roomID) {
        this.accountID = accountID;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roomID = roomID;
    }
    public Account(  String userName, String passWord, String fullName, String email, String phone, String address,int roomID) {

        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roomID = roomID;
    }

    public Account( String userName, String passWord, String fullName, String email,int roomID) {
        this.roomID = roomID;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
