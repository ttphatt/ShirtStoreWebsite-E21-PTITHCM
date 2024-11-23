package com.shirtstore.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.OrderServices;


@WebServlet("/show_order_detail")
public class ShowOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ShowOrderDetailServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderServices orderService = new OrderServices(request, response);
		orderService.showOrderDetailForCustomer();
	}

}
