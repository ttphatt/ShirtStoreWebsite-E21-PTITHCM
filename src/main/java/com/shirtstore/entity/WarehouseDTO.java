package com.shirtstore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WarehouseDTO {
    private int product_id;
    private String product_name;
    private int quantity;
    private Date created_date;
    private Date updated_date;
    private int realQuantity;
    private List<Size> listSizes = new ArrayList<>();

    public WarehouseDTO(int product_id, String product_name, int quantity, Date created_date, Date updated_date) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public WarehouseDTO(int product_id, String product_name, int quantity, Date created_date, Date updated_date, List<Size> size, int realQuantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.listSizes = size;
        this.realQuantity = realQuantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public List<Size> getListSizes() {
        return listSizes;
    }

    public void setListSizes(List<Size> listSizes) {
        this.listSizes = listSizes;
    }

    public int getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(int realQuantity) {
        this.realQuantity = realQuantity;
    }
}
