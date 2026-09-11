package com.example.rescueme1.DB;

public class AdoptionModel {

    private int adoptionId;
    private int petId;
    private int shelterId;

    private byte[] petProfileImage;
    private byte[] adopterPhoto;

    // Pet Details
    private String petName;
    private String petCategory;
    private String petAge;
    private String petGender;
    private String petDescription;

    // Adopter Details
    private String adopterName;
    private String adopterAge;
    private String adopterGender;
    private String adopterContact;
    private String adopterNIC;
    private String adopterAddress;
    private String adoptionDate;

    public AdoptionModel() {
    }


    public int getAdoptionId() {
        return adoptionId;
    }

    public void setAdoptionId(int adoptionId) {
        this.adoptionId = adoptionId;
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

    public byte[] getPetProfileImage() {
        return petProfileImage;
    }

    public void setPetProfileImage(byte[] petProfileImage) {
        this.petProfileImage = petProfileImage;
    }

    public byte[] getAdopterPhoto() {
        return adopterPhoto;
    }

    public void setAdopterPhoto(byte[] adopterPhoto) {
        this.adopterPhoto = adopterPhoto;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
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

    public String getAdopterName() {
        return adopterName;
    }

    public void setAdopterName(String adopterName) {
        this.adopterName = adopterName;
    }

    public String getAdopterAge() {
        return adopterAge;
    }

    public void setAdopterAge(String adopterAge) {
        this.adopterAge = adopterAge;
    }

    public String getAdopterGender() {
        return adopterGender;
    }

    public void setAdopterGender(String adopterGender) {
        this.adopterGender = adopterGender;
    }

    public String getAdopterContact() {
        return adopterContact;
    }

    public void setAdopterContact(String adopterContact) {
        this.adopterContact = adopterContact;
    }

    public String getAdopterNIC() {
        return adopterNIC;
    }

    public void setAdopterNIC(String adopterNIC) {
        this.adopterNIC = adopterNIC;
    }

    public String getAdopterAddress() {
        return adopterAddress;
    }

    public void setAdopterAddress(String adopterAddress) {
        this.adopterAddress = adopterAddress;
    }

    public String getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(String adoptionDate) {
        this.adoptionDate = adoptionDate;
    }
}
