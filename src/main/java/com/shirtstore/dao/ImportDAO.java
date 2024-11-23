package com.shirtstore.dao;

import com.shirtstore.entity.Import;

import java.util.Date;
import java.util.List;

public class ImportDAO extends JPADAO<Import> implements GenericDAO<Import>{
    @Override
    public Import create(Import newImport){
        return super.create(newImport);
    }

    @Override
    public Import get(Object id) {
        return super.find(Import.class, id);
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<Import> listAll() {
        return super.findWithNamedQuery("Import.findAll");
    }

    @Override
    public long count() {
        return super.countWithNamedQuery("Import.count");
    }
}
