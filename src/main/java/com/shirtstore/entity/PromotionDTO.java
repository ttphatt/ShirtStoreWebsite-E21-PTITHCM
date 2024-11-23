package com.shirtstore.entity;

import java.util.Date;

public class PromotionDTO {
    private String description;
    private String promotionId;
    private String type;
    private String status;
    private int quantityInStock;
    private Date startDate;
    private Date endDate;
    private Long usedPromotion;
    private boolean doDisplay;

    public PromotionDTO(String description, String promotionId, String type, String status, int quantityInStock, Date startDate, Date endDate, Long usedPromotion, boolean doDisplay) {
        this.description = description;
        this.promotionId = promotionId;
        this.type = type;
        this.status = status;
        this.quantityInStock = quantityInStock;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usedPromotion = usedPromotion;
        this.doDisplay = doDisplay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUsedPromotion() {
        return usedPromotion;
    }

    public void setUsedPromotion(Long usedPromotion) {
        this.usedPromotion = usedPromotion;
    }

    public boolean isDoDisplay() {
        return doDisplay;
    }

    public void setDoDisplay(boolean doDisplay) {
        this.doDisplay = doDisplay;
    }
}
