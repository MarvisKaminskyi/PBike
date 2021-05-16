package com.example.pbike.Models;

public class User {
    private String name, email, pass, phone, park;
    int bikeNumbers,timeOrder,costRent;

    //public User() {
    //this.name = name;
    // this.email = email;
    //this.pass = pass;
    //this.phone = phone;
    //}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public int getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(int timeOrder) {
        this.timeOrder = timeOrder;
    }

    public int getBikeNumbers() {
        return bikeNumbers;
    }

    public void setBikeNumbers(int bikeNumbers) {
        this.bikeNumbers = bikeNumbers;
    }

    public int getCostRent() {
        return costRent;
    }

    public void setCostRent(int costRent) {
        this.costRent = costRent;
    }



}
