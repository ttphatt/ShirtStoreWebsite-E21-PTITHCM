package com.shirtstore.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.entity.Customer;
import com.shirtstore.service.OrderServices;

@WebServlet("/checkout")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CheckOutServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderServices orderService = new OrderServices(request, response);
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		orderService.showCheckOutForm(customer);
	}

}
