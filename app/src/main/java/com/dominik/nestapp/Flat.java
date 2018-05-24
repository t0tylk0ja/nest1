package com.dominik.nestapp;

public class Flat {

    private long id;
    private String name;
    private String address;
    private String dev;
    private String minUrl;
    private String maxUrl;

    public Flat(){}

    public Flat(String name,String address,String dev,String minUrl,String maxUrl){
        this.name=name;
        this.address=address;
        this.dev=dev;
        this.minUrl=minUrl;
        this.maxUrl=maxUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDev() {
        return dev;
    }

    public String getMinUrl() {
        return minUrl;
    }

    public String getMaxUrl() { return maxUrl; }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public void setMinUrl(String minUrl) {
        this.minUrl = minUrl;
    }

    public void setMaxUrl(String maxUrl) { this.maxUrl = maxUrl;
    }
}
