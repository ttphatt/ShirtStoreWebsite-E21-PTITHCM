package com.shirtstore.controller.admin.order;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.OrderDetail;
import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.ShirtOrder;


@WebServlet("/admin/add_shirt_to_order")
public class AddShirtToOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddShirtToOrderServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int shirtId = Integer.parseInt(request.getParameter("shirtId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		ShirtDAO shirtDAO = new ShirtDAO();
		Shirt shirt = shirtDAO.get(shirtId);
	
		HttpSession session = request.getSession();
		ShirtOrder order = (ShirtOrder) session.getAttribute("order");
		
		float subTotal = quantity * shirt.getShirtPrice();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setShirt(shirt);
		orderDetail.setQuantity(quantity);
		orderDetail.setSubTotal(subTotal);
		
//		float newTotal = order.getOrderSum() + subTotal;
//		order.setOrderSum(newTotal);
		
		boolean isShirtInOrder = false;
		Set<OrderDetail>orderDetails = order.getOrderDetails();
		
		for(OrderDetail od : orderDetails) {
			if(od.getShirt().equals(shirt)) {
				isShirtInOrder = true;
				od.setQuantity(od.getQuantity() + quantity);
				od.setSubTotal(od.getSubTotal() + subTotal);
				break;
			}
		}
		
		if(!isShirtInOrder) {
			order.getOrderDetails().add(orderDetail);
		}
		
		session.setAttribute("newShirtPendingToAddToOrder", true);
		request.setAttribute("shirt", shirt);
		
		String path = "add_shirt_result.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

}
