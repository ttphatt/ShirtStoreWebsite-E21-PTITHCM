package com.shirtstore.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shirtstore.entity.Customer;

public class CustomerDAO extends JPADAO<Customer> implements GenericDAO<Customer> {
	@Override
	public Customer create(Customer customer) {
		customer.setSignUpDate(new Date());
		return super.create(customer);
	}

	@Override
	public Customer update(Customer entity) {
		// TODO Auto-generated method stub
		return super.update(entity);
	}

	@Override
	public Customer get(Object customerId) {
		return super.find(Customer.class, customerId);
	}

	@Override
	public void delete(Object customerId) {
		super.delete(Customer.class, customerId);
	}

	@Override
	public List<Customer> listAll() {
		return super.findWithNamedQuery("Customer.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Customer.countAll");
	}
	
	public Customer findByEmail(String email) {
		List<Customer> res = super.findWithNamedQuery("Customer.findByEmail", "email", email);
		
		if(!res.isEmpty()) {
			return res.get(0);
		}
		return null;
	}
	
	
	//Kiem tra email va mat khau dang nhap co trung khop trong db hay ko	
	public Customer checkLogin(String email, String password) {
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("password", password);
		
		List<Customer> res = super.findWithNamedQuery("Customer.checkLogin", params);
		
		if(!res.isEmpty()) {
			return res.get(0);
		}
		
		return null;
	}

	public long checkCompletedOrder(int customerId, int shirtId){
		Map<String, Object>params = new HashMap<>();
		params.put("customerId", customerId);
		params.put("shirtId", shirtId);
		return super.countWithNamedQuery("Customer.checkCompletedOrder", params);
	}
}
