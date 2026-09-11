package com.example.rescueme1.DB;


import java.io.Serializable;

public class AppointmentModel1 implements Serializable {
    private int appointmentId;
    private int petId;
    private int shelterId;
    private int userId;
    private String petName;
    private String shelterName;
    private String userName;
    private String date;
    private String time;
    private byte[] petImageBlob;
    private String petCategory;
    private String petAge;
    private String petGender;
    private String petDescription;
    private String shelterContact;
    private String userContact;
    private String appointmentStatus;


    public AppointmentModel1(int appointmentId, int petId, int userId, String petName,
                             String userName, String date, String time, byte[] petImageBlob,
                             String petCategory, String petAge, String petGender,
                             String petDescription, String userContact, String appointmentStatus) {
        this.appointmentId = appointmentId;
        this.petId = petId;
        this.userId = userId;
        this.petName = petName;
        this.userName = userName;
        this.date = date;
        this.time = time;
        this.petImageBlob = petImageBlob;
        this.petCategory = petCategory;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petDescription = petDescription;
        this.userContact = userContact;
        this.appointmentStatus = appointmentStatus;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getPetImageBlob() {
        return petImageBlob;
    }

    public void setPetImageBlob(byte[] petImageBlob) {
        this.petImageBlob = petImageBlob;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getShelterContact() {
        return shelterContact;
    }

    public void setShelterContact(String shelterContact) {
        this.shelterContact = shelterContact;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    // Status check helper methods
    public boolean isPending() {
        return "pending".equalsIgnoreCase(appointmentStatus);
    }

    public boolean isApproved() {
        return "approved".equalsIgnoreCase(appointmentStatus);
    }

    public boolean isRejected() {
        return "rejected".equalsIgnoreCase(appointmentStatus);
    }

    public boolean isCompleted() {
        return "completed".equalsIgnoreCase(appointmentStatus);
    }


}
