package com.shirtstore.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "promotion", catalog = "shirtstoredb")
@NamedQueries({
        @NamedQuery(name = "Promotion.findAll", query = "SELECT t FROM Promotion t ORDER BY t.promotionId"),
        @NamedQuery(name = "Promotion.countAll", query = "SELECT COUNT(*) FROM Promotion"),
        @NamedQuery(name = "Promotion.findByPromotionId", query = "SELECT p FROM Promotion p WHERE p.promotionId = :promotionId"),
        @NamedQuery(name = "Promotion.countUsedAllPromotionId", query ="SELECT COUNT(op.promotion.promotionId) AS usage_count FROM Promotion p LEFT JOIN OrderPromotion op ON p.promotionId = op.promotion.promotionId GROUP BY p.promotionId"),
        @NamedQuery(name = "Promotion.countUsedPromotionId", query = "SELECT COUNT(op.promotion.promotionId) AS usage_count FROM Promotion p, OrderPromotion op WHERE p.promotionId = op.promotion.promotionId AND p.promotionId = :promotionId"),
        @NamedQuery(name = "Promotion.findPromotionsBeingDisplayed", query = "SELECT p.promotionId, p.description, p.type FROM Promotion p WHERE p.doDisplay = true"),
})


public class Promotion {
    private String promotionId;
    private int quantityInStock;
    private float priceLimit;
    private float percent;
    private float maxDiscount;
    private String description;
    private Date startDate;
    private Date endDate;
    private String type;
    private String status;
    private Set<OrderPromotion> orderPromotions = new HashSet<OrderPromotion>();

    //New attribute
    private boolean doDisplay;

    public Promotion() {
    }

    public Promotion(String promotionId) {
        this.promotionId = promotionId;
    }

    public Promotion(String promotionId, int quantityInStock, float priceLimit, float percent, float maxDiscount, String description, Date startDate, Date endDate, String type, String status) {
        this.promotionId = promotionId;
        this.quantityInStock = quantityInStock;
        this.priceLimit = priceLimit;
        this.percent = percent;
        this.maxDiscount = maxDiscount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
    }

    public Promotion(String promotionId, int quantityInStock, float priceLimit, float percent, float maxDiscount, String description, Date startDate, Date endDate, String type, String status, Set<OrderPromotion> orderPromotions) {
        this.promotionId = promotionId;
        this.quantityInStock = quantityInStock;
        this.priceLimit = priceLimit;
        this.percent = percent;
        this.maxDiscount = maxDiscount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
        this.orderPromotions = orderPromotions;
    }

    @Id
    @Column(name = "promotion_id", unique = true, nullable = false,length = 20)
    public String getPromotionId() {
        return this.promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    @Column(name = "quantity_in_stock", nullable = false)
    public int getQuantityInStock() {
        return this.quantityInStock;
    }

    public void setQuantityInStock(int quantity_in_stock) {
        this.quantityInStock = quantity_in_stock;
    }

    @Column(name = "price_limit", nullable = false)
    public float getPriceLimit() {
        return this.priceLimit;
    }

    public void setPriceLimit(float price_limit) {
        this.priceLimit = price_limit;
    }

    @Column(name = "percent", nullable = false)
    public float getPercent() {
        return this.percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Column(name = "max_discount", nullable = false)
    public float getMaxDiscount() {
        return this.maxDiscount;
    }

    public void setMaxDiscount(float max_discount) {
        this.maxDiscount = max_discount;
    }


    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false, length = 10)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false, length = 10)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date end_date) {
        this.endDate = end_date;
    }

    @Column(name = "type", nullable = false, length = 30)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "status", nullable = false, length = 30)

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "do_display")
    public boolean isDoDisplay() {
        return doDisplay;
    }

    public void setDoDisplay(boolean doDisplay) {
        this.doDisplay = doDisplay;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "promotion")
    public Set<OrderPromotion> getOrderPromotions() {
        return orderPromotions;
    }

    public void setOrderPromotions(Set<OrderPromotion> orderPromotions) {
        this.orderPromotions = orderPromotions;
    }
}
