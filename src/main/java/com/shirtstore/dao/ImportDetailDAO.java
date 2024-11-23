package com.shirtstore.dao;

import com.shirtstore.entity.ImportDetail;

import java.util.List;

public class ImportDetailDAO extends JPADAO<ImportDetail> implements GenericDAO<ImportDetail>{
    @Override
    public ImportDetail get(Object id) {
        return null;
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<ImportDetail> listAll() {
        return super.findWithNamedQuery("ImportDetail.findAll");
    }

    @Override
    public long count() {
        return 0;
    }
}
