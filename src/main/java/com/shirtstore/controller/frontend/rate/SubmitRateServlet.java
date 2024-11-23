package com.shirtstore.controller.frontend.rate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.service.RateServices;


@WebServlet("/submit_rate")
public class SubmitRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SubmitRateServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RateServices rateService = new RateServices(request, response);
		rateService.submitRate();
	}

}
