package com.shirtstore.entity;


public class ImportDetailDTO {
    private int import_id;
    private int product_id;
    private String product_name;
    private int quantity;
    private float price;
    private String size;

    public ImportDetailDTO(int import_id, int product_id, int quantity, float price) {
        this.import_id = import_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public ImportDetailDTO(String product_name, int quantity, float price) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public ImportDetailDTO(String product_name, int quantity, float price, String size) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quntity) {
        this.quantity = quntity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
