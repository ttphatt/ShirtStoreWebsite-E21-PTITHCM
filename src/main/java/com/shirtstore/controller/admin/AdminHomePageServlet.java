package com.shirtstore.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.OrderDAO;
import com.shirtstore.dao.UserDAO;
import com.shirtstore.entity.Rate;
import com.shirtstore.entity.ShirtOrder;
import com.shirtstore.dao.CustomerDAO;
import com.shirtstore.dao.RateDAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.dao.TypeDAO;

/**
 * Servlet implementation class AdminHomePageServlet
 */
@WebServlet("/admin/")
public class AdminHomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AdminHomePageServlet() {
        super();
        
    }
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String homePage = "index.jsp";
		
//		//Lấy ra danh sách các đơn đặt hàng để hiển thị lên trang index của admin
//		OrderDAO orderDAO = new OrderDAO();
//		List<ShirtOrder> listMostRecentSales = orderDAO.listMostRecentSales();
//
//		//Lấy ra danh sách các đánh giá để để hiển thị trên trand index của admin
//		RateDAO rateDAO = new RateDAO();
//		List<Rate> listMostRecentRates = rateDAO.listMostRecentRates();
//
//		List<String> listRatingStars = rateDAO.listRatingStars();
//		List<Integer> countRatingStars = rateDAO.countRatingStars();
//
//		//Đếm số lượng user có trong database
//		UserDAO userDAO = new UserDAO();
//		long totalUsers = userDAO.count();
//
//		//Đếm số lượng áo có trong database
//		ShirtDAO shirtDAO = new ShirtDAO();
//		long totalShirts = shirtDAO.count();
//		List<String>soldShirtName = shirtDAO.listSoldShirtName();
//		List<Integer>eachShirtRevenue = shirtDAO.listEachShirtRevenue();
//
//		List<Integer>shirtsByTypes = shirtDAO.countShirtsByTypes();
//
//		//Đếm số lượng khách hàng có trong database
//		CustomerDAO customerDAO = new CustomerDAO();
//		long totalCustomers = customerDAO.count();
//
//		TypeDAO typeDAO = new TypeDAO();
//		long totalTypes = typeDAO.count();
//
//		//Lấy ra danh sách chứa tên các loại
//		List<String>typeNames = typeDAO.listTypeName();
//		//Đếm số lượng đánh giá
//		long totalRates = rateDAO.count();
//
//		//Đếm số lượng đơn hàng
//		long totalOrders = orderDAO.count();
//
//		//Đẩy dữ liệu qua view
//		request.setAttribute("listMostRecentSales", listMostRecentSales);
//		request.setAttribute("listMostRecentRates", listMostRecentRates);
//
//		request.setAttribute("listRatingStars", listRatingStars);
//		request.setAttribute("countRatingStars", countRatingStars);
//
//		request.setAttribute("totalUsers", totalUsers);
//		request.setAttribute("totalShirts", totalShirts);
//
//		request.setAttribute("shirtsByTypes", shirtsByTypes);
//		request.setAttribute("soldShirtName", soldShirtName);
//		request.setAttribute("eachShirtRevenue", eachShirtRevenue);
//
//		request.setAttribute("totalCustomers", totalCustomers);
//		request.setAttribute("totalTypes", totalTypes);
//		request.setAttribute("typeNames", typeNames);
//		request.setAttribute("totalRates", totalRates);
//		request.setAttribute("totalOrders", totalOrders);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homePage);
		dispatcher.forward(request, response);
	}
}
