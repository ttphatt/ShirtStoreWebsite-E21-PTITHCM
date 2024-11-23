package com.shirtstore.controller.frontend.rate;

import com.shirtstore.service.RateServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update_rate")
public class UpdateRateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateRateServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RateServices rateServices = new RateServices(request, response);
        rateServices.updateRateCustomer();
    }
}
