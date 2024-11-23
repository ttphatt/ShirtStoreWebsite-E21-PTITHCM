package com.shirtstore.controller.admin.warehouse;

import com.shirtstore.service.WarehouseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/stock_check")
public class StockCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StockCheckServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WarehouseService warehouseService = new WarehouseService(request, response);

        warehouseService.listShirts("");
    }
}
