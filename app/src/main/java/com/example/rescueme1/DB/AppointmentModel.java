package com.example.rescueme1.DB;

public class AppointmentModel {
    private int appointmentId;
    private int petId;
    private int shelterId;
    private String petName;
    private String shelterName;
    private String date;
    private String time;
    private byte[] petImageBlob;
    private String appointmentStatus;

    // Existing fields
    private String petCategory;
    private String petAge;
    private String petGender;
    private String petDescription;
    private String shelterContact;

    public AppointmentModel(int appointmentId, int petId, int shelterId, String petName, String shelterName,
                            String date, String time, byte[] petImageBlob,
                            String petCategory, String petAge, String petGender, String petDescription,
                            String shelterContact, String appointmentStatus) {
        this.appointmentId = appointmentId;
        this.petId = petId;
        this.shelterId = shelterId;
        this.petName = petName;
        this.shelterName = shelterName;
        this.date = date;
        this.time = time;
        this.petImageBlob = petImageBlob;
        this.petCategory = petCategory;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petDescription = petDescription;
        this.shelterContact = shelterContact;
        this.appointmentStatus = appointmentStatus;
    }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getShelterId() { return shelterId; }
    public void setShelterId(int shelterId) { this.shelterId = shelterId; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public byte[] getPetImageBlob() { return petImageBlob; }
    public void setPetImageBlob(byte[] petImageBlob) { this.petImageBlob = petImageBlob; }

    public String getPetCategory() { return petCategory; }
    public void setPetCategory(String petCategory) { this.petCategory = petCategory; }

    public String getPetAge() { return petAge; }
    public void setPetAge(String petAge) { this.petAge = petAge; }

    public String getPetGender() { return petGender; }
    public void setPetGender(String petGender) { this.petGender = petGender; }

    public String getPetDescription() { return petDescription; }
    public void setPetDescription(String petDescription) { this.petDescription = petDescription; }

    public String getShelterContact() { return shelterContact; }
    public void setShelterContact(String shelterContact) { this.shelterContact = shelterContact; }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

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
