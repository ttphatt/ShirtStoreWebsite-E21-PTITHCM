package com.shirtstore.controller.admin.promotion;

import com.shirtstore.service.PromotionServices;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/edit_promotion")
public class EditPromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditPromotionServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("userRole").equals("admin")) {
            PromotionServices promotionServices = new PromotionServices(request, response);
            promotionServices.editPromotion();
        }
    }
}
