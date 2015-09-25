package com.example.test.mobilesafe.domain;

/**
 * Created by test on 9/25/2015.
 */
public class SmsInfo {
    private String id;
    private String date;
    private String address;
    private String body;
    private int type;

    public SmsInfo() {

    }

    public SmsInfo(String id, String date, String address, String body, int type) {
        this.id = id;
        this.date = date;
        this.address = address;
        this.body = body;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
