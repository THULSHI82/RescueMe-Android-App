package com.example.rescueme1.DB;

public class UHelpModel {
    private int id;
    private String name;
    private String contact;
    private String message;
    private String date;
    private String time;

    public UHelpModel(int id, String name, String contact, String message, String date, String time) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}