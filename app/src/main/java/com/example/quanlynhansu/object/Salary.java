package com.example.quanlynhansu.object;

import java.util.Calendar;

public class Salary {
    private int id;
    private double amount;
    private Calendar date;
    private int account_id;

    public Salary(int id, double amount, Calendar date, int account_id){
        this.setId(id);
        this.setAmount(amount);
        this.setDate(date);
        this.setAccount_id(account_id);
    }

    public Salary(double amount, Calendar date, double aDouble, double cursorDouble, double v, int account_id){
        this.setAmount(amount);
        this.setDate(date);
        this.setAccount_id(account_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return String.valueOf(date);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
