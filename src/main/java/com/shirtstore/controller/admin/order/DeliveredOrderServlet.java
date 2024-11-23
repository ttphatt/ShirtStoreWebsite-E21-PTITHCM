package com.shirtstore.controller.admin.order;

import com.shirtstore.service.OrderServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/delivered_order")
public class DeliveredOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeliveredOrderServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderServices orderServices = new OrderServices(request, response);
        orderServices.deliveredOrder();
    }
}
