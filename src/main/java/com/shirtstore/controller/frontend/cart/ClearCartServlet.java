package com.shirtstore.controller.frontend.cart;

import com.shirtstore.entity.Customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/clear_cart")
public class ClearCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ClearCartServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");

		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		if(customer == null) {
			cart.clearCart();
		} else {
			cart.clearCart();
			cart.deleteCart(customer.getCustomerId());
		}
	
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
