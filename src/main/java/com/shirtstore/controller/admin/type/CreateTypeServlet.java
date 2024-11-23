package com.shirtstore.controller.admin.type;

import java.io.IOException;

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
		TypeServices typeService = new TypeServices(request, response);
		
		typeService.createType();
	}

}
