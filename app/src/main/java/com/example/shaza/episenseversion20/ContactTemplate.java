package com.example.shaza.episenseversion20;

/**
 * Created by Shaza on 4/22/2018.
 */

public class ContactTemplate {
    private String name ;
    private String number ;


    public ContactTemplate(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

