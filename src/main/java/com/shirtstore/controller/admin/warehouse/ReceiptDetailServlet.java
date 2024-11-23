package com.shirtstore.controller.admin.warehouse;

import com.shirtstore.service.ImportDetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/receipt_detail")
public class ReceiptDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReceiptDetailServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ImportDetailService importDetailService = new ImportDetailService(request, response);

        importDetailService.detailReceipt();
    }
}
