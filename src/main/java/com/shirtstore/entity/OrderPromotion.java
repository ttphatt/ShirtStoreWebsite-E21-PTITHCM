package com.shirtstore.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name="order_promotion",catalog = "shirtstoredb")
@NamedQueries({
        @NamedQuery(name = "OrderPromotion.findWithOrderId", query = "SELECT od FROM OrderPromotion od WHERE od.shirtOrder.orderId = :orderId"),
})
public class OrderPromotion implements java.io.Serializable {
    //OrderPromotionId la to hop khoa chinh
    private OrderPromotionId id = new OrderPromotionId();
    private ShirtOrder shirtOrder;
    private Promotion promotion;
    private float discountPrice;

    public OrderPromotion() {}

    public OrderPromotion(OrderPromotionId id, ShirtOrder shirtOrder, Promotion promotion, float discountPrice) {
        this.id = id;
        this.shirtOrder = shirtOrder;
        this.promotion = promotion;
        this.discountPrice = discountPrice;
    }


    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
            @AttributeOverride(name = "promotionId", column = @Column(name = "promotion_id", nullable = false)) })
    public OrderPromotionId getId() {
        return id;
    }

    public void setId(OrderPromotionId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="promotion_id",nullable = false, insertable = false, updatable = false)
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
        this.id.setPromotion(promotion);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    public ShirtOrder getShirtOrder() {
        return this.shirtOrder;
    }

    public void setShirtOrder(ShirtOrder shirtOrder) {
        this.shirtOrder = shirtOrder;
        this.id.setShirtOrder(shirtOrder);
    }

    @Column(name = "discount_price")
    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }
}
