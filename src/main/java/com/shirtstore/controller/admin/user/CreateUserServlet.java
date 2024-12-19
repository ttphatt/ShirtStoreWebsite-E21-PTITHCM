package com.shirtstore.controller.admin.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.UserServices;


@WebServlet("/admin/create_user")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userRole").equals("admin")) {
			UserServices userServices = new UserServices(request, response);

			String email = request.getParameter("email");

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

			if(userServices.checkDuplicateEmail(email) == null){
				out.print("{\"valid\": " + true + "}");
				userServices.createUser();
			}
			else{
				out.print("{\"valid\": " + false + "}");
			}
			out.flush();
			out.close();
		}
	}
}
