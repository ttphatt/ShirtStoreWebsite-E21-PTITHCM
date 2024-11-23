package com.shirtstore.service;

import com.google.gson.Gson;
import com.shirtstore.dao.*;
import com.shirtstore.entity.*;
import com.shirtstore.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportService {
    private ImportDAO importDAO;
    private ImportDetailDAO importDetailDAO;
    private ShirtDAO shirtDAO;
    private UserDAO userDAO;
    private SizeDAO sizeDAO;
    private WarehouseDAO warehouseDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ImportService(HttpServletRequest request, HttpServletResponse response) {
        this.importDAO = new ImportDAO();
        this.userDAO = new UserDAO();
        this.shirtDAO = new ShirtDAO();
        this.importDetailDAO = new ImportDetailDAO();
        this.warehouseDAO = new WarehouseDAO();
        this.request = request;
        this.response = response;
        this.sizeDAO = new SizeDAO();
    }

    public void listAllImport(String query) throws ServletException, IOException {
        List<Import> listImports = importDAO.listAll();
        List<Users> listUsers = userDAO.listAll();
        List<ImportDTO> listImportInfo = new ArrayList<>();
        int userID;
        String infoUser = "";

        List<Import> filteredImports = new ArrayList<>();
        List<Users> filteredUsers = new ArrayList<>();

        if(!query.isEmpty()) {
            for(Users user : listUsers) {
                String temp = user.getUserId() + " - " + user.getFullName();
                if(temp.contains(query)) {
                    filteredUsers.add(user);
                    for (Import i : listImports) {
                        if(i.getUser().getUserId() == user.getUserId()) {
                            filteredImports.add(i);
                        }
                    }
                }
            }
        } else {
            filteredImports = listImports;
            filteredUsers = listUsers;
        }

        for (Import i : filteredImports) {
            userID = i.getUser().getUserId();
            for (Users u : filteredUsers) {
                if (u.getUserId() == userID) {
                    infoUser = userID + " - " + u.getFullName();
                    break;
                }
            }

            listImportInfo.add(new ImportDTO(i.getImport_id(), i.getImport_date(), i.getTotal_price(), infoUser));
        }

        request.setAttribute("listImportInfo", listImportInfo);

        String path = "view_import.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public void createImport() throws ServletException, IOException {
        List<Shirt> shirts = shirtDAO.listAll();
        List<ShirtDTO> shirtList = new ArrayList<>();

        for (Shirt shirt : shirts) {
            shirtList.add(new ShirtDTO(shirt.getShirtId(), shirt.getShirtName()));
        }

        request.setAttribute("shirtList", shirtList);

        String path = "create_import.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public void search(String query) throws ServletException, IOException {
        if(Validation.isNumeric(query)) {
            int id = Integer.parseInt(query);
            List<ImportDTO> listImportInfo = new ArrayList<>();

            Import i = new ImportDAO().get(id);

            if(i != null) {
                Users user = userDAO.get(i.getUser().getUserId());

                listImportInfo.add(new ImportDTO(id, i.getImport_date(), i.getTotal_price(), user.getUserId() + " - " + user.getFullName()));
            }

            request.setAttribute("listImportInfo", listImportInfo);

            String path = "view_import.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }else {
            listAllImport(query);
        }
    }

    public void addImport() throws ServletException, IOException {
        String staffEmail = request.getParameter("staffEmail");
        int importId = Integer.parseInt(request.getParameter("importId"));
        float totalPrice = Float.parseFloat(request.getParameter("totalPriceField"));

        String dateStr = request.getParameter("createdDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createdDate;

        if (dateStr == null || dateStr.isEmpty()) {
            createdDate = new Date();
        } else {
            try {
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
                String currentTime = timeFormatter.format(new Date());

                dateStr += " " + currentTime;

                createdDate = formatter.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                createdDate = new Date();
            }
        }

        String productData = request.getParameter("productData");

        Gson gson = new Gson();
        ImportDetailDTO[] importDetailDTOs = gson.fromJson(productData, ImportDetailDTO[].class);

        List<ImportDetail> listImportDetail = new ArrayList<>();
        List<Size> listSizes = new ArrayList<>();
        for (int i = 0; i < importDetailDTOs.length; i++) {
            ImportDetail importDetail = new ImportDetail(importDetailDTOs[i].getProduct_id(), importDetailDTOs[i].getImport_id(),
                    importDetailDTOs[i].getPrice(), importDetailDTOs[i].getQuantity(), importDetailDTOs[i].getSize());
            listImportDetail.add(importDetail);

            Size size = new Size(importDetailDTOs[i].getProduct_id(), importDetailDTOs[i].getSize(), importDetailDTOs[i].getQuantity());
            listSizes.add(size);
        }

        Users user = userDAO.findByEmail(staffEmail);

        Import i = new Import(importId, createdDate, totalPrice, user);
        importDAO.create(i);


        for (ImportDetail importDetail : listImportDetail) {
            importDetailDAO.create(importDetail);

            Warehouse warehouse = warehouseDAO.get(importDetail.getProduct_id());
            warehouse.setQuantity(warehouse.getQuantity() + importDetail.getQuantity());
            warehouseDAO.update(warehouse);
        }

        for (Size size : listSizes) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("product_id", size.getProduct_id());
            parameters.put("type", size.getType());

            Size temp = sizeDAO.findWithNamedQuery("Size.findById", parameters).get(0);
            temp.setQuantity(size.getQuantity() + temp.getQuantity());
            sizeDAO.update(temp);
        }

        response.sendRedirect(request.getContextPath() + "/admin/view_import");
    }
}
