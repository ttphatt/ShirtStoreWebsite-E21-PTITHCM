package com.shirtstore.controller.admin.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Shirt;


@WebServlet("/admin/add_shirt_form")
public class ShowAddShirtFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ShowAddShirtFormServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShirtDAO shirtDAO = new ShirtDAO();
		List<Shirt> listShirt = shirtDAO.listAll();
		
		request.setAttribute("listShirt", listShirt);
		
		String path = "add_shirt_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

}
