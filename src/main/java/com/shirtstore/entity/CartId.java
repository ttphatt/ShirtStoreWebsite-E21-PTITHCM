package com.shirtstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {
    private int customer_id;
    private int product_id;
    private String size;

    public CartId() {
    }

    public CartId(int customer_id, int product_id, String size) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.size = size;
    }

    public CartId(int customer_id, int product_id) {
        this.customer_id = customer_id;
        this.product_id = product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return customer_id == cartId.customer_id && product_id == cartId.product_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer_id, product_id);
    }
}
