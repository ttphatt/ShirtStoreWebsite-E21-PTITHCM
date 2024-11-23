package com.shirtstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class SizeId implements Serializable {
    private int product_id;
    private String type;

    public SizeId() {}

    public SizeId(int product_id, String type) {
        this.product_id = product_id;
        this.type = type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SizeId)) return false;
        SizeId that = (SizeId) o;
        return product_id == that.product_id && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, type);
    }
}
