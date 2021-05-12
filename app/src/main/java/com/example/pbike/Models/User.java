package com.example.pbike.Models;

public class User {
    private String name,email,pass,phone,park,timeOrder,bikeNumbers;

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

    public  String getPark(){
        return park;
    }

    public void setPark(String park) {
        this.park=park;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getBikeNumbers(){
        return bikeNumbers;
    }

    public void setBikeNumbers(String bikeNumbers){
        this.bikeNumbers=bikeNumbers;
    }

}
