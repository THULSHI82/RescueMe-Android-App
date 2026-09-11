package com.example.rescueme1.DB;

public class UserModel1 {
    private int id;
    private String name;
    private String email;
    private String contact;
    private String dob;
    private byte[] profileImage;

    public UserModel1(int id, String name, String email, String dob, String contact, byte[] profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.contact = contact;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}