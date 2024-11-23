package com.shirtstore.service;

import com.shirtstore.dao.ImportDAO;
import com.shirtstore.dao.ImportDetailDAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Import;
import com.shirtstore.entity.ImportDetail;
import com.shirtstore.entity.ImportDetailDTO;
import com.shirtstore.entity.Shirt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportDetailService {
    private ImportDetailDAO importDetailDAO;
    private ImportDAO importDAO;
    private ShirtDAO shirtDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ImportDetailService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        importDetailDAO = new ImportDetailDAO();
        importDAO = new ImportDAO();
        shirtDAO = new ShirtDAO();
    }

    public void detailReceipt() throws ServletException, IOException {
        int import_id = Integer.parseInt(request.getParameter("import_id"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("import_id", import_id);

        List<ImportDetail> importDetails = importDetailDAO.findWithNamedQuery("ImportDetail.findByImportID", parameters);
        List<ImportDetailDTO> listImportDetails = new ArrayList<>();
        List<Shirt> shirts = new ArrayList<>();
        shirts = shirtDAO.listAll();
        String product_name = "";

        for (ImportDetail importDetail : importDetails) {
            for (Shirt shirt : shirts) {
                if (shirt.getShirtId() == importDetail.getProduct_id()){
                    product_name = shirt.getShirtName();
                    break;
                }
            }

            listImportDetails.add(new ImportDetailDTO(product_name, importDetail.getQuantity(), importDetail.getPrice(), importDetail.getSize()));
        }


        Import import_info = importDAO.get(import_id);

        request.setAttribute("listImportDetails", listImportDetails);
        request.setAttribute("import_info", import_info);

        String path = "receipt_detail.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }
}
