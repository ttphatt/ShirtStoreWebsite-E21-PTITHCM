package com.shirtstore.controller.frontend.cart;

import com.shirtstore.service.CartService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/handleSaveCart")
public class HandleSaveCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String customerId = request.getParameter("customerId");

        if ("save".equals(action)) {
            new CartService(request).saveCart(Integer.parseInt(customerId));
        }else {
            ShoppingCart shoppingCart = new ShoppingCart();
            request.getSession().setAttribute("cart", shoppingCart);
        }

        String profilePath = "frontend/customer_profile.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(profilePath);
        requestDispatcher.forward(request, response);
    }
}