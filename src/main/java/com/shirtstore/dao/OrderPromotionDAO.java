package com.shirtstore.dao;

import com.shirtstore.entity.OrderPromotion;

import java.util.List;

public class OrderPromotionDAO extends JPADAO<OrderPromotion> implements GenericDAO<OrderPromotion> {
    public OrderPromotionDAO() {
    }

    @Override
    public OrderPromotion create(OrderPromotion orderPromotion) {
        return super.create(orderPromotion);
    }

    @Override
    public OrderPromotion get(Object id) {
        return null;
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<OrderPromotion> listAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    public List<OrderPromotion> getOrderPromotions(Integer orderId) {
        return super.findWithNamedQuery("OrderPromotion.findWithOrderId", "orderId", orderId);
    }
}
