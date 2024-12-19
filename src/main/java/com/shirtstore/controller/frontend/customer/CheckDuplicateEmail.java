package com.shirtstore.controller.frontend.customer;

import com.shirtstore.dao.CustomerDAO;
import com.shirtstore.entity.Customer;
import com.shirtstore.service.CustomerServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/check_duplicate_email")
public class CheckDuplicateEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckDuplicateEmail() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerEmail = request.getParameter("customerEmail");
        CustomerServices customerServices = new CustomerServices(request, response);

        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.findByEmail(customerEmail);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        if(customer != null) {
            System.out.println("Duplicated email");
            out.print("{\"isDuplicated\": " + true + "}");
        }
        else{
            System.out.println("Non-duplicated email");
            out.print("{\"isDuplicated\": " + false + "}");
//            customerServices.registerAsCustomer();
        }

        out.flush();
        out.close();
    }
}
