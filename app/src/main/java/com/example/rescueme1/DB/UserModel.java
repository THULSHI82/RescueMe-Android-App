package com.example.rescueme1.DB;

public class UserModel {
    private String name;
    private String email;
    private String dob;
    private String contact;
    private byte[] profileImage;
    private String gender;

    public UserModel(String name, String email, String dob, String contact, byte[] profileImage) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.contact = contact;
        this.profileImage = profileImage;
    }

    public UserModel() {

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}