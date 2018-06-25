package com.dominik.nestapp;

public class Flat {

    private long id;
    private String name;
    private String address;
    private String dev;
    private String minUrl;
    private String maxUrl;
    private String planUrl;
    private String galleryUrl;
    private String galleryUrl2;
    double area;
    double storage;
    int rooms;
    int floor;
    int balcony;
    int equip;
    int parking;
    int garden;
    int loved;


    public Flat(){}

    public Flat(String name,String address,String dev,double area,double storage, int rooms, int floor, int balcony, int parking,
                int equip,int garden,int loved, String minUrl,String maxUrl, String planUrl, String galleryUrl, String galleryUrl2){
        this.name=name;
        this.address=address;
        this.dev=dev;
        this.area=area;
        this.storage=storage;
        this.rooms=rooms;
        this.floor=floor;
        this.balcony=balcony;
        this.parking=parking;
        this.equip=equip;
        this.garden=garden;
        this.minUrl=minUrl;
        this.maxUrl=maxUrl;
        this.planUrl=planUrl;
        this.galleryUrl=galleryUrl;
        this.galleryUrl2=galleryUrl2;
        this.loved=loved;
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

    public String getPlanUrl() { return planUrl; }

    public String getGalleryUrl() { return galleryUrl; }

    public double getArea() { return area; }

    public int getRooms() { return rooms; }

    public int getFloor() { return floor; }

    public int getBalcony() { return balcony; }

    public int getEquip() { return equip; }

    public int getParking() { return parking; }

    public int getGarden() { return garden; }

    public double getStorage() { return storage; }

    public String getGalleryUrl2() { return galleryUrl2; }

    public int getLoved() { return loved; }

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

    public void setPlanUrl(String planUrl) { this.planUrl = planUrl; }

    public void setGalleryUrl(String galleryUrl) { this.galleryUrl = galleryUrl; }

    public void setGalleryUrl2(String galleryUrl2) { this.galleryUrl2 = galleryUrl2; }

    public void setArea(double area) { this.area = area; }

    public void setRooms(int rooms) { this.rooms = rooms; }

    public void setFloor(int floor) { this.floor = floor; }

    public void setMaxUrl(String maxUrl) { this.maxUrl = maxUrl; }

    public void setBalcony(int balcony) { this.balcony = balcony; }

    public void setEquip(int equip) { this.equip = equip; }

    public void setParking(int parking) { this.parking = parking; }

    public void setGarden(int garden) { this.garden = garden; }

    public void setStorage(double storage) { this.storage = storage; }

    public void setLoved(int loved) { this.loved = loved; }
}
