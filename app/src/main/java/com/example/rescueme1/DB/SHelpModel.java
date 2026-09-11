package com.example.rescueme1.DB;

public class SHelpModel {
    private int id;
    private String shelterName;
    private String contact;
    private String message;
    private String date;
    private String time;

    public SHelpModel(int id, String shelterName, String contact, String message, String date, String time) {
        this.id = id;
        this.shelterName = shelterName;
        this.contact = contact;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public int getId() { return id; }
    public String getShelterName() { return shelterName; }
    public String getContact() { return contact; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
