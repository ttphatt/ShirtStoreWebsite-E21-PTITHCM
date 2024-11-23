package com.shirtstore.controller.admin.order;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shirtstore.entity.OrderDetail;
import com.shirtstore.entity.ShirtOrder;


@WebServlet("/admin/remove_shirt_from_order")
public class RemoveShirtFromOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RemoveShirtFromOrderServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int shirtId = Integer.parseInt(request.getParameter("shirtId"));
		HttpSession session = request.getSession();
		ShirtOrder order = (ShirtOrder) session.getAttribute("order");
		
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();	
	
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			
			if(orderDetail.getShirt().getShirtId() == shirtId) {
//				float newSum = order.getOrderSum() - orderDetail.getSubTotal();
//				order.setOrderSum(newSum);
				iterator.remove();
			}
		}
		
		String path = "order_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

}
