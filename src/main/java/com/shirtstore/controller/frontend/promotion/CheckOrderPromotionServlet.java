package com.shirtstore.controller.frontend.promotion;

import com.shirtstore.service.PromotionServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/check_order_promotion")
public class CheckOrderPromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckOrderPromotionServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PromotionServices promotionServices = new PromotionServices(request, response);
        try {
            promotionServices.checkPromotion("Order Discount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PromotionServices promotionServices = new PromotionServices(request, response);
        try {
            promotionServices.checkPromotion2("Order Discount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
