package com.example.rescueme1.DB;

public class PetModel {

    private int petId;
    private int shelterId;
    private byte[] profileImage;
    private String category;
    private String petName;
    private String petAge;
    private String petGender;
    private String petDescription;

    public PetModel(int petId, int shelterId, byte[] profileImage, String category, String petName,
                    String petAge, String petGender, String petDescription) {
        this.petId = petId;
        this.shelterId = shelterId;
        this.profileImage = profileImage;
        this.category = category;
        this.petName = petName;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petDescription = petDescription;
    }

    public PetModel() {
    }


    public int getPetId() {
        return petId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public String getCategory() {
        return category;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public String getPetGender() {
        return petGender;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }
}
