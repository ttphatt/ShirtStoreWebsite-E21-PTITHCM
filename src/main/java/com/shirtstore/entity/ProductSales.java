package com.shirtstore.entity;

public class ProductSales {

    private int productId;
    private int quantitySold;

    public ProductSales(int productId, int quantitySold) {
        this.productId = productId;
        this.quantitySold = quantitySold;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}

