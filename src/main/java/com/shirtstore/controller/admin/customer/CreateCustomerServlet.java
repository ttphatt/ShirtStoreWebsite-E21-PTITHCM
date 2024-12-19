package com.shirtstore.controller.admin.customer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.CustomerDAO;
import com.shirtstore.service.CustomerServices;


@WebServlet("/admin/create_customer")
public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateCustomerServlet() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerServices customerService = new CustomerServices(request, response);
		CustomerDAO customerDAO = new CustomerDAO();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");

		if(customerDAO.findByEmail(email) == null){
			out.print("{\"valid\": " + true + "}");
			customerService.createCustomer();
		}else{
			out.print("{\"valid\": " + false + "}");
		}

		out.flush();
		out.close();
	}

}
