package com.shirtstore.controller.frontend.cart;

import com.shirtstore.entity.Customer;
import com.shirtstore.service.CartService;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/view_cart")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);

			Customer customer = (Customer) session.getAttribute("loggedCustomer");

			if(customer != null) {
				new CartService(request).saveCart(customer.getCustomerId());
			}
		}

		request.setAttribute("cart", cart);

		String path = "frontend/shopping_cart.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

}
