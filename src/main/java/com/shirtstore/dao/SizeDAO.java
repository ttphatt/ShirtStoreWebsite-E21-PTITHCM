package com.shirtstore.dao;

import com.shirtstore.entity.Size;

import java.util.List;

public class SizeDAO extends JPADAO<Size> implements GenericDAO<Size>{
    @Override
    public Size get(Object id) {
        return super.find(Size.class, id);
    }

    @Override
    public void delete(Object id) {
        super.delete(Size.class, id);
    }

    @Override
    public List<Size> listAll() {
        return super.findWithNamedQuery("Size.listAll");
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Size create(Size entity) {
        return super.create(entity);
    }
}
