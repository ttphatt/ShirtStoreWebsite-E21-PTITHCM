package com.shirtstore.controller.admin.promotion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.PromotionServices;


/**
 * Servlet implementation class ListTypeServlet
 */
@WebServlet("/admin/list_promotion")
public class ListPromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListPromotionServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("userRole").equals("admin")) {
            PromotionServices promotionServices = new PromotionServices(request, response);
            promotionServices.listPromotion();
        }
    }

}
