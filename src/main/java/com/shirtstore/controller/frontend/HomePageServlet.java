package com.shirtstore.controller.frontend;

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

/**
 * Servlet implementation class HomePageServlet
 */
@WebServlet("")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomePageServlet() {
        super();       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShirtDAO shirtDAO = new ShirtDAO();
		
		List<Shirt>listNewShirts = shirtDAO.listNewShirts();
		List<Shirt>listBestSellingShirts = shirtDAO.listBestSellingShirts();
		List<Shirt> listMostFavoredShirts = shirtDAO.listMostFavoredShirts();
		
		request.setAttribute("listNewShirts", listNewShirts);
		request.setAttribute("listBestSellingShirts", listBestSellingShirts);
		request.setAttribute("listMostFavoredShirts", listMostFavoredShirts);
		
		String homePage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homePage);
		dispatcher.forward(request, response);
	}



}
