package com.shirtstore.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderPromotionId implements Serializable {
    private Promotion promotion;
    private ShirtOrder shirtOrder;

    public OrderPromotionId() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false, insertable = false, updatable = false)
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    public ShirtOrder getShirtOrder() {
        return shirtOrder;
    }

    public void setShirtOrder(ShirtOrder shirtOrder) {
        this.shirtOrder = shirtOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotion, shirtOrder);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        OrderPromotionId other = (OrderPromotionId) object;
        return Objects.equals(promotion,other.promotion) && Objects.equals(shirtOrder,other.shirtOrder);
    }
}
