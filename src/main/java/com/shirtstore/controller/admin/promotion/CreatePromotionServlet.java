package com.shirtstore.controller.admin.promotion;


import com.shirtstore.service.PromotionServices;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/create_promotion")
public class CreatePromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreatePromotionServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("userRole").equals("admin")) {
            PromotionServices promotionServices = new PromotionServices(request, response);

            String promotionId = request.getParameter("promotionId");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            if(promotionServices.checkDuplicatePromotion(promotionId) == null){
                out.print("{\"valid\": " + true + "}");
                try {
                    promotionServices.createPromotion();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                out.print("{\"valid\": " + false + "}");
            }
            out.flush();
            out.close();
        }
    }
}
