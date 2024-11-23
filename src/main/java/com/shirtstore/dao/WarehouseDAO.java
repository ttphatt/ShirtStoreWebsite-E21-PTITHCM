package com.shirtstore.dao;

import com.shirtstore.entity.Warehouse;

import java.util.Date;
import java.util.List;

public class WarehouseDAO extends JPADAO<Warehouse> implements GenericDAO<Warehouse>{
    @Override
    public Warehouse create(Warehouse warehouse){
        warehouse.setCreated_date(new Date());
        return super.create(warehouse);
    }

    @Override
    public Warehouse update(Warehouse warehouse){
        warehouse.setUpdated_date(new Date());
        return super.update(warehouse);
    }

    @Override
    public Warehouse get(Object id) {
        return super.find(Warehouse.class, id);
    }

    @Override
    public void delete(Object id) {
        super.delete(Warehouse.class, id);
    }

    @Override
    public List<Warehouse> listAll() {
        return super.findWithNamedQuery("Warehouse.findAll");
    }

    @Override
    public long count() {
        return super.countWithNamedQuery("Warehouse.count");
    }
}
