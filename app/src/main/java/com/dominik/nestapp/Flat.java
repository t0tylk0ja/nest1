package com.dominik.nestapp;

public class Flat {

    private long id;
    private String name;
    private String address;
    private String dev;
    private String minUrl;
    private String maxUrl;
    double area;
    int rooms;
    int floor;
    //Boolean balcony
    //Boolean equip
    //Boolean parking
    //int cityDistance
    //railway distance
    //Boolean busStop


    public Flat(){}

    public Flat(String name,String address,String dev,double area, int rooms, int floor, String minUrl,String maxUrl){
        this.name=name;
        this.address=address;
        this.dev=dev;
        this.minUrl=minUrl;
        this.maxUrl=maxUrl;
        this.area=area;
        this.rooms=rooms;
        this.floor=floor;

    }

    //GETTERS

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

    public double getArea() { return area; }

    public int getRooms() { return rooms; }

    public int getFloor() { return floor; }
    //SETTERS

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

    public void setArea(double area) { this.area = area; }

    public void setRooms(int rooms) { this.rooms = rooms; }

    public void setFloor(int floor) { this.floor = floor; }

    public void setMaxUrl(String maxUrl) { this.maxUrl = maxUrl; }
}
