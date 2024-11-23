package com.shirtstore.entity;

import com.shirtstore.dao.ShirtDAO;

public class CartDTO {
    private int customer_id;
    private Shirt shirt;
    private int quantity;
    private String size;

    public CartDTO(int customer_id, Shirt shirt, int quantity, String size) {
        this.customer_id = customer_id;
        this.shirt = shirt;
        this.quantity = quantity;
        this.size = size;
    }

    public CartDTO() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Shirt getShirt() {
        return shirt;
    }

    public void setShirt(Shirt shirt) {
        this.shirt = shirt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
