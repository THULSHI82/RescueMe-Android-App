package com.example.rescueme1.DB;

import java.io.Serializable;

public class ReportModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String petCategory;
    private String shelterName;
    private String description;
    private double latitude;
    private double longitude;
    private byte[] image;
    private String reporttStatus;
    private int reporterAppUserId;

    public ReportModel(int id, String petCategory, String shelterName, String description, double latitude, double longitude, byte[] image, String reporttStatus, int reporterAppUserId) {
        this.id = id;
        this.petCategory = petCategory;
        this.shelterName = shelterName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.reporttStatus = reporttStatus;
        this.reporterAppUserId = reporterAppUserId;
    }

    public int getId() {
        return id;
    }

    public String getPetCategory() {
        return petCategory;
    }
    public String getShelterName() {
        return shelterName;
    }
    public String getDescription() {
        return description;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public byte[] getImage() {
        return image;
    }
    public String getReporttStatus() {
        return reporttStatus;
    }
    public void setReporttStatus(String status) {
        this.reporttStatus = status;
    }
    public int getReporterAppUserId() {
        return reporterAppUserId;
    }
}
