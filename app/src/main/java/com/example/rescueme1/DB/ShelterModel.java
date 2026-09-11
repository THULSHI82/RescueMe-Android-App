package com.example.rescueme1.DB;

import java.io.Serializable;

public class ShelterModel implements Serializable {
    private int sid;
    private String name, owner, email, contact, lat, lon, regNo, estDate, description;
    private byte[] image;

    public ShelterModel(int sid, String name, String owner, String email, String contact, String lat, String lon, String regNo, String estDate, String description, byte[] image) {
        this.sid = sid;
        this.name = name;
        this.owner = owner;
        this.email = email;
        this.contact = contact;
        this.lat = lat;
        this.lon = lon;
        this.regNo = regNo;
        this.estDate = estDate;
        this.description = description;
        this.image = image;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEstDate() {
        return estDate;
    }

    public void setEstDate(String estDate) {
        this.estDate = estDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ShelterModel() {
    }

    public int getId() {
        return sid;
    }
}
