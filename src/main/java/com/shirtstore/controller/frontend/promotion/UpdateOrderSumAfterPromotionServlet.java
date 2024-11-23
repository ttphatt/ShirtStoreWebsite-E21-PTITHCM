package com.shirtstore.controller.frontend.promotion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/update_order_sum_after_promotion")
public class UpdateOrderSumAfterPromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateOrderSumAfterPromotionServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        float updateTotalPrice = Float.parseFloat(request.getParameter("updateTotalPrice"));
        request.getSession().setAttribute("totalPrice", updateTotalPrice);
//        System.out.println("Update total price: " + request.getSession().getAttribute("totalPrice"));
    }
}
