package com.shirtstore.controller.frontend.cart;

import com.shirtstore.entity.CartDTO;
import com.shirtstore.entity.Customer;
import com.shirtstore.service.CartService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/update_cart")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateCartServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arrShirtIds = request.getParameterValues("shirtId");
		String[] arrQuantities = new String[arrShirtIds.length];
		String[] arrSizes = new String[arrShirtIds.length];
		CartService cartService = new CartService(request);

		String tempQuantities;
		String tempSizes;

		for (int i = 1; i <= arrQuantities.length; i++) {
			tempQuantities = request.getParameter("quantity" + i);
			tempSizes = request.getParameter("size" + i);
			arrQuantities[i - 1] = tempQuantities;
			arrSizes[i - 1] = tempSizes;
		}

		int[] shirtIds = Arrays.stream(arrShirtIds).mapToInt(Integer::parseInt).toArray();
		int[] quantities = Arrays.stream(arrQuantities).mapToInt(Integer::parseInt).toArray();

		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");

		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");

		if(customer == null) {
			cart.updateCart(shirtIds, quantities, arrSizes);
		} else {
			cart.updateCart(shirtIds, quantities, arrSizes);
			request.getSession().setAttribute("cart", cart);
			cartService.updateCart(customer.getCustomerId());
		}


		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}


}
