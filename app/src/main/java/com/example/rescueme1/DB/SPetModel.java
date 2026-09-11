package com.example.rescueme1.DB;

public class SPetModel {
    private int petId;
    private String petName;
    private String petCategory;
    private String petAge;
    private String petGender;
    private String shelterName;
    private String shelterContact;
    private byte[] petImage;
    private String petDescription;

    public SPetModel(int petId, String petName, String petCategory, String petAge, String petGender,
                     String shelterName, String shelterContact, byte[] petImage, String petDescription) {
        this.petId = petId;
        this.petName = petName;
        this.petCategory = petCategory;
        this.petAge = petAge;
        this.petGender = petGender;
        this.shelterName = shelterName;
        this.shelterContact = shelterContact;
        this.petImage = petImage;
        this.petDescription = petDescription;
    }

    public SPetModel() {
    }

    // Getters
    public int getPetId() {
        return petId;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public String getPetAge() {
        return petAge;
    }

    public String getPetGender() {
        return petGender;
    }

    public String getShelterName() {
        return shelterName;
    }

    public String getShelterContact() { // ✅ New getter
        return shelterContact;
    }

    public byte[] getPetImage() {
        return petImage;
    }

    public String getPetDescription() {
        return petDescription;
    }


    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public void setShelterContact(String shelterContact) { // ✅ New setter
        this.shelterContact = shelterContact;
    }

    public void setPetImage(byte[] petImage) {
        this.petImage = petImage;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }
}
