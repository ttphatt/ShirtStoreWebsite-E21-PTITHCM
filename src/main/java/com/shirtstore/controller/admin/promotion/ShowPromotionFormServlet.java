package com.shirtstore.controller.admin.promotion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/promotion_form")
public class ShowPromotionFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowPromotionFormServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/admin/promotion_form.jsp";
        request.getSession().setAttribute("isUpdate",false);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);

    }
}