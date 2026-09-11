package com.example.rescueme1.DB;

public class PromotionsModel {
    private int promoId;
    private String title;
    private byte[] image;

    public PromotionsModel(int promoId, String title, byte[] image) {
        this.promoId = promoId;
        this.title = title;
        this.image = image;
    }

    public int getPromoId() { return promoId; }
    public void setPromoId(int promoId) { this.promoId = promoId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}