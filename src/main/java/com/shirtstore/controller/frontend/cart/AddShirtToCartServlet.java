package com.shirtstore.controller.frontend.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Cart;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.Shirt;
import com.shirtstore.service.CartService;


@WebServlet("/add_to_cart")
public class AddShirtToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddShirtToCartServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer shirtId = Integer.parseInt(request.getParameter("shirtId"));
		String size = request.getParameter("size");
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		ShoppingCart shoppingCart = null;
		
		if(cartObject != null && cartObject instanceof ShoppingCart) {
			shoppingCart = (ShoppingCart) cartObject;
		}
		else {
			shoppingCart = new ShoppingCart();
			request.getSession().setAttribute("cart", shoppingCart);
		}

		ShirtDAO shirtDAO = new ShirtDAO();
		Shirt shirt =  shirtDAO.get(shirtId);
		
		shoppingCart.addItem(shirt, size);
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		if(customer != null) {
			new CartService(request).saveCart(customer.getCustomerId());
		}
	
		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
