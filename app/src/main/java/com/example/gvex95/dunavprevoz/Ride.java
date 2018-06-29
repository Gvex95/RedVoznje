package com.example.gvex95.dunavprevoz;

import android.widget.Spinner;

public class Ride {
    private int id;
    private int polazak;
    private int dolazak;
    private String dan;
    private String mesto_polaska;
    private String mesto_dolaska;
    private String notifikacijaText;
    private int spinnerIndex;
    private String notificationColor;

    public Ride(int id, int polazak, int dolazak, String dan, String mesto_polaska, String mesto_dolaska, String notfikacijaText) {
        this.id = id;
        this.polazak = polazak;
        this.dolazak = dolazak;
        this.dan = dan;
        this.mesto_polaska = mesto_polaska;
        this.mesto_dolaska = mesto_dolaska;
        this.notifikacijaText = notfikacijaText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolazak() {
        return polazak;
    }

    public void setPolazak(int polazak) {
        this.polazak = polazak;
    }

    public int getDolazak() {
        return dolazak;
    }

    public void setDolazak(int dolazak) {
        this.dolazak = dolazak;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getMesto_polaska() {
        return mesto_polaska;
    }

    public void setMesto_polaska(String mesto_polaska) {
        this.mesto_polaska = mesto_polaska;
    }

    public String getMesto_dolaska() {
        return mesto_dolaska;
    }

    public void setMesto_dolaska(String mestod_dolaska) {
        this.mesto_dolaska = mestod_dolaska;
    }

    public String getNotifikacijaText() {
        return notifikacijaText;
    }

    public void setNotifikacijaText(String notifikacijaText) {
        this.notifikacijaText = notifikacijaText;
    }


    public String getNotificationColor() {
        return notificationColor;
    }

    public void setNotificationColor(String notificationColor) {
        this.notificationColor = notificationColor;
    }

    public int getSpinnerIndex() {
        return spinnerIndex;
    }

    public void setSpinnerIndex(int spinnerIndex) {
        this.spinnerIndex = spinnerIndex;
    }
}