package com.shirtstore.entity;

public class ShirtDTO {
    private int shirtID;
    private String shirtName;

    public ShirtDTO(int shirtID, String shirtName) {
        this.shirtID = shirtID;
        this.shirtName = shirtName;
    }

    public int getShirtID() {
        return shirtID;
    }

    public void setShirtID(int shirtID) {
        this.shirtID = shirtID;
    }

    public String getShirtName() {
        return shirtName;
    }

    public void setShirtName(String shirtName) {
        this.shirtName = shirtName;
    }
}
