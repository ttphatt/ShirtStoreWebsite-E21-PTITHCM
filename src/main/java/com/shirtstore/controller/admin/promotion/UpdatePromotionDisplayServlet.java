package com.shirtstore.controller.admin.promotion;

import com.shirtstore.service.PromotionServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/updatePromotionDisplay")
public class UpdatePromotionDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdatePromotionDisplayServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("userRole").equals("admin")) {
            PromotionServices promotionServices = new PromotionServices(request, response);
            promotionServices.updatePromotionDisplay();
        }
    }
}
