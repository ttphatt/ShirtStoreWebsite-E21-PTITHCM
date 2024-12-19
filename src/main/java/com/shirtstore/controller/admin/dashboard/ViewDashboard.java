package com.shirtstore.controller.admin.dashboard;

import com.shirtstore.service.DashboardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/admin/view_dashboard")
public class ViewDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ViewDashboard() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("userRole").equals("admin")){
            DashboardService dashboardService = new DashboardService(request, response);

            try {
                dashboardService.view_dashboard();
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        DashboardService dashboardService = new DashboardService(request, response);
        try {
            dashboardService.view_dashboard();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
