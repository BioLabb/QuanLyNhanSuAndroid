package com.example.quanlynhansu.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetSalary {
    private  int id;
    private Calendar date;

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    private String strDate;
    private double salary;
    private double bonus;
    private double sum;
    private int accountID;

    public GetSalary(int id, Calendar date, double salary, double bonus, double sum, int accountID) {
        this.id = id;
        this.date = date;
        this.salary = salary;
        this.bonus = bonus;
        this.sum = sum;
        this.accountID = accountID;
    }

    public GetSalary(int id, String strDate, double salary, double bonus, double sum, int accountID) {
        this.id = id;
        this.strDate = strDate;
        this.salary = salary;
        this.bonus = bonus;
        this.sum = sum;
        this.accountID = accountID;
    }

    public GetSalary(String strDate, double salary, double bonus, double sum, int accountID) {
        this.strDate = strDate;
        this.salary = salary;
        this.bonus = bonus;
        this.sum = sum;
        this.accountID = accountID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
