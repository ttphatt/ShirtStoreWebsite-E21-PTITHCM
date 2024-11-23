package com.shirtstore.dao;

import java.util.List;

import com.shirtstore.entity.Type;

public class TypeDAO extends JPADAO<Type> implements GenericDAO<Type> {
	public TypeDAO() {

	}
	
	@Override
	public Type create(Type type) {
		return super.create(type);
	}
	
	@Override
	public Type update(Type type) {
		return super.update(type);
	}
	
	@Override
	public Type get(Object id) {
		return super.find(Type.class, id);
	}

	@Override
	public void delete(Object id) {
		super.delete(Type.class, id);
	}

	@Override
	public List<Type> listAll() {
		return super.findWithNamedQuery("Type.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Type.countAll");
	}
	
	public Type findByName(String typeName) {
		List<Type>types = super.findWithNamedQuery("Type.findByName", "typeName", typeName);

		if(types != null && types.size() > 0) {
			return types.get(0);
		}
		return null;
	}
	
	public List<String> listTypeName() {
		List<String> res = super.listWithNamedQuery("Type.findAllOrderByTypeId");
		return res;
	}
}
