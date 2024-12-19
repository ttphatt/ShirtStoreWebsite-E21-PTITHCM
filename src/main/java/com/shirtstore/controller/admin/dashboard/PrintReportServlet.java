package com.shirtstore.controller.admin.dashboard;

import com.shirtstore.service.DashboardService;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

@WebServlet("/admin/print_report")
public class PrintReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PrintReportServlet() {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DashboardService dashboardService = new DashboardService(request, response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            dashboardService.printReport();
            out.print("{\"valid\": " + true + "}");
        } catch (ParseException e) {
            out.print("{\"valid\": " + false + "}");
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        out.flush();
        out.close();
    }
}
