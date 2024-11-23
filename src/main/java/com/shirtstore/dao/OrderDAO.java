package com.shirtstore.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shirtstore.entity.ShirtOrder;

public class OrderDAO extends JPADAO<ShirtOrder> implements GenericDAO<ShirtOrder> {
	@Override
	public ShirtOrder create(ShirtOrder order) {
		order.setOrderDate(new Date());
		order.setStatus("Processing");
		
		return super.create(order);
	}
	
	@Override
	public ShirtOrder update(ShirtOrder order) {
		return super.update(order);
	}

	@Override
	public ShirtOrder get(Object orderId) {
		return super.find(ShirtOrder.class, orderId);
	}
	
	public ShirtOrder get(Object orderId, Integer customerId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("customerId", customerId);
		
		List<ShirtOrder>res = super.findWithNamedQuery("ShirtOrder.findByIdAndCustomer", params);
		
		if(!res.isEmpty()) {
			return res.get(0);
		}
		
		return null;
	}
	
	@Override
	public void delete(Object orderId) {
		super.delete(ShirtOrder.class, orderId);
	}

	@Override
	public List<ShirtOrder> listAll() {
		return super.findWithNamedQuery("ShirtOrder.listAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("ShirtOrder.countAll");
	}

	public List<ShirtOrder> listByCustomer(Integer customerId){
		return super.findWithNamedQuery("ShirtOrder.findByCustomer", "customerId", customerId);
	}
	
	public List<ShirtOrder> listMostRecentSales(){
		return super.findWithNamedQuery("ShirtOrder.listAll", 0, 3);
	}
	
	public long countByCustomer(int customerId) {
		return super.countWithNamedQuery("ShirtOrder.countByCustomer", "customerId", customerId);
	}
}
