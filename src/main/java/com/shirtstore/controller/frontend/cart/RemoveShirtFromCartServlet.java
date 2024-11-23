package com.shirtstore.controller.frontend.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.CartDAO;
import com.shirtstore.entity.CartId;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.Shirt;


@WebServlet("/remove_from_cart")
public class RemoveShirtFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveShirtFromCartServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer shirtId = Integer.parseInt(request.getParameter("shirtId"));
		String size = request.getParameter("size");
		
		Object cartObject = request.getSession().getAttribute("cart");
		
		ShoppingCart shoppingCart = (ShoppingCart)cartObject;
		
		
		shoppingCart.removeItem(shirtId,size);

		Customer customer = (Customer)request.getSession().getAttribute("loggedCustomer");
		if(customer != null) {
			new CartDAO().delete(new CartId(customer.getCustomerId(), shirtId, size));
		}

		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
