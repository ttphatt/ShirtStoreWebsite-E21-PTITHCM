package com.shirtstore.service;

import com.shirtstore.dao.OrderPromotionDAO;
import com.shirtstore.entity.OrderPromotion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderPromotionServices {
    private OrderPromotionDAO orderPromotionDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public OrderPromotionServices(HttpServletRequest request, HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;

        orderPromotionDAO = new OrderPromotionDAO();
    }

    public void createOrderPromotion() {

    }

}
