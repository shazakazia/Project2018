package com.example.shaza.episenseversion20;

/**
 * Created by Shaza on 4/22/2018.
 */

public class ContactTemplate {


    private String fname ;
    private String lname ;
    private String number ;


    public ContactTemplate(String fname, String lname, String number) {
        this.fname = fname;
        this.lname = lname;
        this.number = number;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}

