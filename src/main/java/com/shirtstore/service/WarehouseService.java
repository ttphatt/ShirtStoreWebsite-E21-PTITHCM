package com.shirtstore.service;

import com.shirtstore.dao.JPADAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.dao.WarehouseDAO;
import com.shirtstore.entity.ProductSales;
import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.Warehouse;
import com.shirtstore.entity.WarehouseDTO;
import com.shirtstore.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseService {
    private WarehouseDAO warehouseDAO;
    private ShirtDAO shirtDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public WarehouseService(HttpServletRequest request, HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
        shirtDAO = new ShirtDAO();
        warehouseDAO = new WarehouseDAO();
    }

    public void search(String query) throws ServletException, IOException{
        if(Validation.isNumeric(query)){
            int id = Integer.parseInt(query);
            List<WarehouseDTO> listProducts = new ArrayList<>();

            Shirt shirt = shirtDAO.get(id);
            Warehouse info_shirt = warehouseDAO.get(id);

            if(shirt != null){
                ProductSales productSales = new JPADAO<>().getProductSalesByProductId(info_shirt.getProduct_id());

                int realQuantity = info_shirt.getQuantity() - productSales.getQuantitySold();

                listProducts.add(new WarehouseDTO(info_shirt.getProduct_id(), shirt.getShirtName(),info_shirt.getQuantity(), info_shirt.getCreated_date(), info_shirt.getUpdated_date(), info_shirt.getListSizes(), realQuantity));
            }

            request.setAttribute("listProducts", listProducts);

            String path = "stock_check.jsp";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        }else {
            listShirts(query);
        }
    }

    public void listShirts(String query) throws ServletException, IOException {
        List<Shirt> listShirts = shirtDAO.listAll();
        List<Warehouse> info_of_shirts = warehouseDAO.listAll();
        List<WarehouseDTO> listProducts = new ArrayList<>();

        List<ProductSales> listProductSales = new JPADAO<>().getProductSales();

        String name = "";
        int id;

        List<Shirt> filteredShirts = new ArrayList<>();
        List<Warehouse> filteredWarehouses = new ArrayList<>();

        if (!query.isEmpty()) {
            for (Shirt shirt : listShirts) {
                if (shirt.getShirtName().contains(query)) {
                    filteredShirts.add(shirt);

                    for (Warehouse info_shirt : info_of_shirts) {
                        if (info_shirt.getProduct_id() == shirt.getShirtId()) {
                            filteredWarehouses.add(info_shirt);
                            break;
                        }
                    }
                }
            }
        } else {
            filteredShirts = listShirts;
            filteredWarehouses = info_of_shirts;
        }

        for (Warehouse infoOfShirt : filteredWarehouses) {
            id = infoOfShirt.getProduct_id();

            for (Shirt listShirt : filteredShirts) {
                if (listShirt.getShirtId() == id) {
                    name = listShirt.getShirtName();
                    break;
                } else {
                    name = "";
                }
            }

            int realQuantity = 0;
            boolean found = false;
            for(ProductSales productSales : listProductSales){
                if(productSales.getProductId() == id){
                    realQuantity = infoOfShirt.getQuantity() - productSales.getQuantitySold();
                    found = true;
                    break;
                }
            }

            if(!found) realQuantity = infoOfShirt.getQuantity();

            listProducts.add(new WarehouseDTO(id, name, infoOfShirt.getQuantity(), infoOfShirt.getCreated_date(), infoOfShirt.getUpdated_date(), infoOfShirt.getListSizes(), realQuantity));
        }

        request.setAttribute("listProducts", listProducts);

        String path = "stock_check.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }
}
