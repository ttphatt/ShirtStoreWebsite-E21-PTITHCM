package com.shirtstore.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "import", catalog = "shirtstoredb")
@NamedQueries({
        @NamedQuery(name = "Import.findAll", query = "SELECT i FROM Import i ORDER By i.id DESC"),
        @NamedQuery(name = "Import.countAll", query = "SELECT COUNT(*) FROM Import")
})
public class Import implements java.io.Serializable{
    private int import_id;
    private Date import_date;
    private Float total_price;
    private Users user;
    private List<ImportDetail> listImportDetail = new ArrayList<ImportDetail>();

    public  Import() {}

    public Import(int import_id, Date import_date, Float total_price) {
        this.import_id = import_id;
        this.import_date = import_date;
        this.total_price = total_price;
    }

    public Import(int import_id, Date import_date, Float total_price, Users user) {
        this.import_id = import_id;
        this.import_date = import_date;
        this.total_price = total_price;
        this.user = user;
    }

    @Id
    @Column(name = "import_id", unique = true, nullable = false)
    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    @Column(name = "import_date", nullable = false)
    public Date getImport_date() {
        return import_date;
    }

    public void setImport_date(Date import_date) {
        this.import_date = import_date;
    }

    @Column(name = "total_price", nullable = false)
    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user){
        this.user = user;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "importEntity")
    public List<ImportDetail> getListImportDetail() {
        return listImportDetail;
    }

    public void setListImportDetail(List<ImportDetail> listImportDetail) {
        this.listImportDetail = listImportDetail;
    }
}
