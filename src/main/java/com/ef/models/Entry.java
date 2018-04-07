package com.ef.models;

public class Entry {
    private String date;
    private String IP;
    private String request;
    private String status;
    private String userAgent;

    public Entry() {
    }

    public String getDate() {
        return date;
    }

    public Entry setDate(String date) {
        this.date = date;
        return this;
    }

    public String getIP() {
        return IP;
    }

    public Entry setIP(String IP) {
        this.IP = IP;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public Entry setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Entry setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Entry setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public Entry build() {
        return this;
    }
}
