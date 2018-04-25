package com.example.shaza.episenseversion20;

/**
 * Created by Shaza on 4/25/2018.
 */

public class ProfileTemplate {

    private String fname;
    private String lname;
    private String fullname;
    private String id;
    private String email;
    private String address;
    private String dob;
    private String contactnum;
    private String docname;

    public ProfileTemplate(String fname, String lname, String fullname, String id, String email, String address, String dob, String contactnum, String docname) {
        this.fname = fname;
        this.lname = lname;
        this.fullname = fullname;
        this.id = id;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.contactnum = contactnum;
        this.docname = docname;
    }


    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public String getContactnum() {
        return contactnum;
    }

    public String getDocname() {
        return docname;
    }
}
