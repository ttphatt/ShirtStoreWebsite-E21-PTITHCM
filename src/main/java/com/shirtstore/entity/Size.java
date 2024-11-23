package com.shirtstore.entity;


import javax.persistence.*;

@Entity
@Table(name = "size", catalog = "shirtstoredb")
@IdClass(SizeId.class)
@NamedQueries({
        @NamedQuery(name = "Size.findAll", query = "SELECT s FROM Size s"),
        @NamedQuery(name = "Size.findById", query = "SELECT s FROM Size s WHERE s.product_id = :product_id AND s.type = :type")
})
public class Size {
    private int product_id;
    private String type;
    private int quantity;
    private Warehouse warehouse;

    public Size() {}

    public Size(int product_id, String type, int quantity) {
        this.product_id = product_id;
        this.type = type;
        this.quantity = quantity;
    }

    @Id
    @Column(name = "product_id", unique = true, nullable = false)
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Id
    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
