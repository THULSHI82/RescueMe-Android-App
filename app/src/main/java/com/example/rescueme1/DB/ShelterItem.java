package com.example.rescueme1.DB;

public class ShelterItem {
    private int id;
    private String shelterName;

    public ShelterItem(int id, String name) {
        this.id = id;
        this.shelterName = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return shelterName;
    }

    @Override
    public String toString() {
        return shelterName;
    }
}
