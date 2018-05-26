package com.dominik.nestapp;

public class Dev {
    public String devLogo;
    public String devName;
    public String devEmail;
    public String devAddress;

    public Dev(String devLogo, String devName, String devEmail, String devAddress) {
        this.devLogo = devLogo;
        this.devName = devName;
        this.devEmail = devEmail;
        this.devAddress = devAddress;
    }

    public String getDevLogo() {
        return devLogo;
    }

    public void setDevLogo(String devLogo) {
        this.devLogo = devLogo;
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
