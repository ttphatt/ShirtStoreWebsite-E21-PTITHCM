package com.shirtstore.controller.admin.type;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.TypeServices;

@WebServlet("/admin/create_type")
public class CreateTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TypeServices typeServices = new TypeServices(request, response);

		String typeName = request.getParameter("typeName");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		if(typeServices.checkDuplicateType(typeName) == null){
			out.print("{\"valid\": " + true + "}");
			typeServices.createType();
		}
		else{
			out.print("{\"valid\": " + false + "}");
		}

		out.flush();
		out.close();
	}

}
