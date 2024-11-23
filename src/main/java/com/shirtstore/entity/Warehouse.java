package com.shirtstore.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "warehouse", catalog = "shirtstoredb")
@NamedQueries({
        @NamedQuery(name = "Warehouse.findAll", query = "SELECT w FROM Warehouse w"),
        @NamedQuery(name = "Warehouse.countAll", query = "SELECT COUNT(*) FROM Warehouse")
})
public class Warehouse implements java.io.Serializable{
    private int product_id;
    private int quantity;
    private Date created_date;
    private Date updated_date;
    private Set<Shirt> listShirts = new HashSet<>(0);
    private List<Size> listSizes = new ArrayList<>(0);

    public Warehouse() {}

    public Warehouse(int product_id, int quantity, Date created_date, Date updated_date) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Warehouse(int product_id, int quantity, Date created_date, Date updated_date, List<Size> listSizes) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.listSizes = listSizes;
    }

    @Id
    @Column(name = "product_id", unique = true, nullable = false)
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

    @Column(name = "created_date", nullable = false)
    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    @Column(name = "updated_date", nullable = false)
    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse")
    public Set<Shirt> getListShirts() {
        return this.listShirts;
    }

    public void setListShirts(Set<Shirt> listShirts) {
        this.listShirts = listShirts;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "warehouse")
    public List<Size> getListSizes() {
        return listSizes;
    }

    public void setListSizes(List<Size> listSizes) {
        this.listSizes = listSizes;
    }
}
