package com.shirtstore.controller.frontend.rate;

import com.shirtstore.service.RateServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_rate")
public class DeleteRateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteRateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RateServices rateServices = new RateServices(request, response);
        rateServices.deleteRateCustomer();
    }
}