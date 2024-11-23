package com.shirtstore.entity;

import java.util.Date;

public class ImportDTO {
    private int import_id;
    private Date created_date;
    private Float total_price;
    private String infoUser;

    public ImportDTO(int import_id, Date created_date, Float total_price, String infoUser) {
        this.import_id = import_id;
        this.created_date = created_date;
        this.total_price = total_price;
        this.infoUser = infoUser;
    }

    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public String getInfoUser() {
        return infoUser;
    }

    public void setInfoUser(String infoUser) {
        this.infoUser = infoUser;
    }
}
