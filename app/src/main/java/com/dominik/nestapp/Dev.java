package com.dominik.nestapp;

import android.graphics.drawable.Drawable;

public class Dev {
    public int devLogo;
    public String devName;
    public String devEmail;
    public String devAddress;


    public Dev(String devName, String devEmail, String devAddress, int devLogo) {
        this.devName = devName;
        this.devEmail = devEmail;
        this.devAddress = devAddress;
        this.devLogo=devLogo;
    }



    public int getDevLogo() {
        return devLogo;
    }

    public void setDevLogo(int devLogo) {
        this.devLogo=devLogo;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevEmail() {
        return devEmail;
    }

    public void setDevEmail(String devEmail) {
        this.devEmail = devEmail;
    }

    public String getDevAddress() {
        return devAddress;
    }

    public void setDevAddress(String devAddress) {
        this.devAddress = devAddress;
    }
}
