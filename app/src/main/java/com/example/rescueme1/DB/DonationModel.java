package com.example.rescueme1.DB;

public class DonationModel {
    private int donationId;
    private int userId;
    private int shelterId;
    private String firstName;
    private String lastName;
    private double amount;
    private String remark;
    private byte[] userImage;

    public DonationModel(int donationId, int userId, int shelterId, String firstName, String lastName, double amount, String remark, byte[] userImage){
        this.donationId = donationId;
        this.userId = userId;
        this.shelterId = shelterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.remark = remark;
        this.userImage = userImage;
    }

    public int getDonationId() {
        return donationId;
    }

    public int getUserId() {
        return userId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getAmount() {
        return amount;
    }

    public String getRemark() {
        return remark;
    }

    public byte[] getUserImage() {
        return userImage;
    }
}
