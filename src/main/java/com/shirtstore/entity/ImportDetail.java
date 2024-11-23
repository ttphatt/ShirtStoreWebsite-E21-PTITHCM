package com.shirtstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "import_detail", catalog = "shirtstoredb")
@IdClass(ImportDetailId.class)
@NamedQueries({
        @NamedQuery(name = "ImportDetail.findByImportID", query = "SELECT i FROM ImportDetail i WHERE i.import_id = :import_id")
})
public class ImportDetail implements java.io.Serializable {
    private int product_id;
    private int import_id;
    private Float price;
    private int quantity;
    private String size;
    private Import importEntity;

    public ImportDetail() {}

    public ImportDetail(int product_id, int import_id, Float price, int quantity) {
        this.product_id = product_id;
        this.import_id = import_id;
        this.price = price;
        this.quantity = quantity;
    }

    public ImportDetail(int product_id, int import_id, Float price, int quantity, String size) {
        this.product_id = product_id;
        this.import_id = import_id;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Id
    @Column(name = "import_id", nullable = false)
    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    @Column(name = "price", nullable = false)
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "import_id", insertable = false, updatable = false, nullable = false)
    public Import getImportEntity() {
        return importEntity;
    }

    public void setImportEntity(Import importEntity) {
        this.importEntity = importEntity;
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
