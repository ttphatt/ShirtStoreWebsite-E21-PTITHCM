package com.shirtstore.entity;

import java.io.Serializable;
import java.util.Objects;

public class ImportDetailId implements Serializable {
    private int product_id;
    private int import_id;
    private String size;

    public ImportDetailId() {}

    public ImportDetailId(int product_id, int import_id, String size) {
        this.product_id = product_id;
        this.import_id = import_id;
        this.size = size;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
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
        if (!(o instanceof ImportDetailId)) return false;
        ImportDetailId that = (ImportDetailId) o;
        return product_id == that.product_id && import_id == that.import_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, import_id);
    }
}
