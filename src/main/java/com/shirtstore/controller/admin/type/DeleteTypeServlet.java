package com.shirtstore.controller.admin.type;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.TypeServices;

@WebServlet("/admin/delete_type")
public class DeleteTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public DeleteTypeServlet() {
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TypeServices typeService = new TypeServices(request, response);
		
		typeService.deleteType();
	}

}
