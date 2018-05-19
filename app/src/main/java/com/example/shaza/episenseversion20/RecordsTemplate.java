package com.example.shaza.episenseversion20;

/**
 * Created by Shaza on 4/22/2018.
 */

public class RecordsTemplate {
    private String date;
    private String time;

    public RecordsTemplate(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;

    }

    public String getTime() {
        return time;
    }
}

