package com.shirtstore.controller.admin.type;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.TypeServices;

/**
 * Servlet implementation class ListTypeServlet
 */
@WebServlet("/admin/list_type")
public class ListTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListTypeServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TypeServices typeServices = new TypeServices(request, response);
		
		typeServices.listType();
	}

}
