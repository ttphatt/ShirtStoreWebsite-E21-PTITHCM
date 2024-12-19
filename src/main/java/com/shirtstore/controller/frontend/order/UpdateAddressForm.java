package com.shirtstore.controller.frontend.order;

import com.shirtstore.entity.Customer;
import com.shirtstore.service.OrderServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateAddress")
public class UpdateAddressForm extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateAddressForm() {super();}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderServices orderService = new OrderServices(request, response);
        Customer customer = orderService.getAddressForm();
        orderService.showCheckOutForm(customer);
    }
}
