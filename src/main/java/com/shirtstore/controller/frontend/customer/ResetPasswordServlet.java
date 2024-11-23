package com.shirtstore.controller.frontend.customer;

import com.shirtstore.dao.CustomerDAO;
import com.shirtstore.entity.Customer;
import com.shirtstore.service.CustomerServices;
import com.shirtstore.service.MailServices;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/reset_password")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

//    private String host;
//    private String port;
//    private String email;
//    private String name;
//    private String pass;

//    public void init() {
//        // reads SMTP server setting from web.xml file
//        ServletContext context = getServletContext();
//        host = context.getInitParameter("host");
//        port = context.getInitParameter("port");
//        email = context.getInitParameter("email");
//        name = context.getInitParameter("name");
//        pass = context.getInitParameter("pass");
//    }

    public ResetPasswordServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = "frontend/reset_password.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request,response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        String recipient = request.getParameter("email");
        Customer existingCustomer = customerDAO.findByEmail(recipient);
        if (existingCustomer == null) {
            // Nếu khách hàng chưa tồn tại
            String path = "frontend/message.jsp";
            String message = "The account belonging to this email does not exist in this website";
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }else{
            String subject = "Your Password Has Been Reset";
            CustomerServices customerServices = new CustomerServices(request, response);
            String newPassword = customerServices.resetCustomerPassword(recipient);
            System.out.println(newPassword);
            String content = "PHK SHIRT" +
                    " STORE\r\n"
                    + "\r\n"
                    + "Dear " + recipient+ ",\r\n"
                    + "\r\n"
                    + "This is your new password: " + newPassword
                    + "\r\n"
                    + "For security reason, "
                    + "you must change your password after logging in.";

            String message = "";

            try {
                MailServices.SendMail(recipient, subject, content);
                System.out.println("Send mail sucessfully");
                message = "Your password has been reset. Please check your e-mail.";
            } catch (Exception ex) {
                ex.printStackTrace();
                message = "There were an error: " + ex.getMessage();
            } finally {
                String path = "frontend/message.jsp";
                request.setAttribute("message", message);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
                requestDispatcher.forward(request, response);
            }
        }

    }

}

