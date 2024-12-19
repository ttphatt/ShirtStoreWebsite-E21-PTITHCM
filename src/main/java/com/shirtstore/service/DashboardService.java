package com.shirtstore.service;

import com.shirtstore.dao.JPADAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.ReportDTO;
import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.ShirtDTO;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardService {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ShirtDAO shirtDAO;

    public DashboardService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        shirtDAO = new ShirtDAO();
    }

    public void view_dashboard() throws ServletException, IOException, ParseException {
        Date start_date;
        Date end_date;
        int step;
        int product_id;

        String temp_start_date = request.getParameter("start_date");
        String temp_end_date = request.getParameter("end_date");
        String temp_step = request.getParameter("step");
        String temp_product_id = request.getParameter("product_id");
        String typeReport = request.getParameter("typeReport");

        if(temp_start_date != null && temp_end_date != null) {
            start_date = new SimpleDateFormat("yyyy-MM-dd").parse(temp_start_date);
            end_date = new SimpleDateFormat("yyyy-MM-dd").parse(temp_end_date);
            step = Integer.parseInt(temp_step);
            product_id = Integer.parseInt(temp_product_id);
        }else {
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            start_date = sdf.parse(sdf.format(today));
            end_date = sdf.parse(sdf.format(today));
            step = 1;
            product_id = 0;
            typeReport = "Revenue";
        }

        List<ReportDTO> listReports = new ArrayList<>();

        if(typeReport.equals("Revenue")){
            listReports = new JPADAO<>().getOrderRevenue(start_date, end_date, step, product_id);
        } else if (typeReport.equals("Profits")) {
            listReports = new JPADAO<>().getProfits(start_date, end_date, step, product_id);
        }

        double total = 0;
        for (ReportDTO reportDTO: listReports){
            total += reportDTO.getTotal();
        }

        double maxTotal = getMaxTotal(listReports);

        double maxY =  Math.ceil(maxTotal / 10) * 10;

        List<Shirt> shirts = shirtDAO.listAll();

        List<ShirtDTO> listShirts = new ArrayList<>();

        for (Shirt shirt : shirts) {
            ShirtDTO shirtDTO = new ShirtDTO(shirt.getShirtId(), shirt.getShirtName());

            listShirts.add(shirtDTO);
        }

        request.setAttribute("maxY", maxY);
        request.setAttribute("listReports", listReports);
        request.setAttribute("listShirts", listShirts);
        request.setAttribute("total",total);

        String path = "dashboard.jsp";

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    private double getMaxTotal(List<ReportDTO> listReports){
        double max = 100.0;
        for(ReportDTO report:listReports){
            if (report.getTotal() > max){
                max = report.getTotal();
            }
        }

        return max;
    }

    public void printReport() throws ParseException, JRException, FileNotFoundException {
        // Lấy các tham số từ request
        String temp_start_date = request.getParameter("start_date");
        String temp_end_date = request.getParameter("end_date");
        String temp_step = request.getParameter("step");
        String temp_product_id = request.getParameter("product_id");
        String format = request.getParameter("format");
        String typeReport = request.getParameter("typeReport");

        Date start_date;
        Date end_date;
        int step;
        int product_id;

        if(!temp_start_date.isEmpty() && !temp_end_date.isEmpty()) {
            start_date = new SimpleDateFormat("yyyy-MM-dd").parse(temp_start_date);
            end_date = new SimpleDateFormat("yyyy-MM-dd").parse(temp_end_date);
            step = Integer.parseInt(temp_step);
            product_id = Integer.parseInt(temp_product_id);
        }else {
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            start_date = sdf.parse(sdf.format(today));
            end_date = sdf.parse(sdf.format(today));
            step = Integer.parseInt(temp_step);
            product_id = Integer.parseInt(temp_product_id);
            typeReport = "Revenue";
        }

        // Gọi JasperReportService để tạo báo cáo PDF hoặc XLSX
        if ("PDF".equalsIgnoreCase(format)) {
            new JasperReportService().reportByDateRange("pdf", start_date, end_date, step, product_id, typeReport);
        } else if ("XLSX".equalsIgnoreCase(format)) {
            new JasperReportService().reportByDateRange("xlsx", start_date, end_date, step, product_id, typeReport);
        }
    }
}
