package com.dominik.nestapp;

public class Flat {

    private long id;
    private String name;
    private String address;
    private String dev;

    public Flat(){}

    public Flat(String name,String address,String dev){
        this.name=name;
        this.address=address;
        this.dev=dev;
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
}
