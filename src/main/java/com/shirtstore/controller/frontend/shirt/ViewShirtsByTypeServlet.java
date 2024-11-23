package com.shirtstore.controller.frontend.shirt;

import com.shirtstore.service.ShirtServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/view_type")
public class ViewShirtsByTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewShirtsByTypeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShirtServices shirtServices = new ShirtServices(request, response);
		shirtServices.listShirtsByType();
	}

}
