package com.shirtstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart", catalog = "shirtstoredb")
@IdClass(CartId.class)
@NamedQueries({
        @NamedQuery(name = "Cart.findAllByCustomer_id", query = "SELECT c FROM Cart c WHERE c.customer_id = :customer_id")
})
public class Cart implements java.io.Serializable{
    private int customer_id;
    private int product_id;
    private int quantity;
    private String size;

    public Cart(int customer_id, int product_id, int quantity, String size) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.size = size;
    }

    public Cart() {}

    @Id
    @Column(name = "customer_id", nullable = false)
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Id
    @Column(name = "size", nullable = false)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
