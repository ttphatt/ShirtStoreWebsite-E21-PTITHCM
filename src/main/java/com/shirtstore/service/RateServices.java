package com.shirtstore.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shirtstore.csv.CSVReaderUtility;
import com.shirtstore.dao.CustomerDAO;
import com.shirtstore.dao.RateDAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.Rate;
import com.shirtstore.entity.Shirt;

public class RateServices {
	private RateDAO rateDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public RateServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.rateDAO = new RateDAO();
	}
	
	public void listAllRate(String message) throws ServletException, IOException {
		List<Rate> listRate = rateDAO.listAll();
		
		request.setAttribute("listRate", listRate);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String path = "rate_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void listAllRate() throws ServletException, IOException {
		listAllRate(null);
	}
	
	public void editRate() throws ServletException, IOException {
		Integer rateId = Integer.parseInt(request.getParameter("id"));
		
		Rate rate = rateDAO.get(rateId);
		
		request.setAttribute("rate", rate);
		
		String path = "rate_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void updateRate() throws ServletException, IOException {
		Integer rateId = Integer.parseInt(request.getParameter("rateId"));
		String headline = request.getParameter("headline");
		String ratingDetail = request.getParameter("ratingDetail");
		
		Rate rate = rateDAO.get(rateId);
		rate.setHeadline(headline);
		rate.setRatingDetail(ratingDetail);
		
		rateDAO.update(rate);
		
		String message = "The rate has been updated successfully";
		listAllRate(message);
	}

	public void updateRateCustomer() throws ServletException, IOException {
		Integer rateId = Integer.parseInt(request.getParameter("rateId"));
		String headline = request.getParameter("headline");
		String ratingDetail = request.getParameter("ratingDetail");

		Rate rate = rateDAO.get(rateId);
		rate.setHeadline(headline);
		rate.setRatingDetail(ratingDetail);
		rate.setRateTime(new Date());
		rateDAO.update(rate);

		String message = "The rate has been updated successfully";
		String path = "frontend/rate_done.jsp";

		request.setAttribute("message", message);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void deleteRate() throws ServletException, IOException {
		Integer rateId = Integer.parseInt(request.getParameter("id"));
		
		rateDAO.delete(rateId);
		
		String message = "The rate has been deleted successfully";
		
		listAllRate(message);
	}

	public void deleteRateCustomer() throws ServletException, IOException {
		Integer rateId = Integer.parseInt(request.getParameter(("id")));
		rateDAO.delete(rateId);

		String message = "Your rate has been deleted successfully";
		String path = "frontend/rate_done.jsp";

		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void showRateForm() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer shirtId = Integer.parseInt(request.getParameter("shirtId"));
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		CustomerDAO customerDAO = new CustomerDAO();
		long completedOrder = customerDAO.checkCompletedOrder(customer.getCustomerId(), shirtId);
		String path = "";

		if(completedOrder == 0){
			String message = CSVReaderUtility.loadCSVData().get("NO_ORDER_COMPLETED");
			path = "frontend/message.jsp";
			request.setAttribute("message", message);
		}
		else {
			ShirtDAO shirtDAO = new ShirtDAO();
			Shirt shirt = shirtDAO.get(shirtId);

			session.setAttribute("shirt", shirt);

			Rate existRate = rateDAO.findByCustomerAndShirt(customer.getCustomerId(), shirtId);

			path = "frontend/rate_form.jsp";

			if(existRate != null) {
				request.setAttribute("rate", existRate);
				path = "frontend/rate_info.jsp";
			}
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void submitRate() throws ServletException, IOException {
		Integer shirtId = Integer.parseInt(request.getParameter("shirtId"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String ratingDetail = request.getParameter("ratingDetail");
		
		Rate rate = new Rate();
		rate.setHeadline(headline);
		rate.setRatingDetail(ratingDetail);
		rate.setRatingStars(rating);
		
		Shirt shirt = new Shirt();
		shirt.setShirtId(shirtId);
		rate.setShirt(shirt);
		
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		rate.setCustomer(customer);
		
		rateDAO.create(rate);

		String message = CSVReaderUtility.loadCSVData().get("THANK_YOU_RATE");
		String path = "frontend/rate_done.jsp";

		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
	
	
}
