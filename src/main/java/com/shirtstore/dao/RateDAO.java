package com.shirtstore.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shirtstore.entity.Rate;

public class RateDAO extends JPADAO<Rate> implements GenericDAO<Rate> {

	@Override
	public Rate create(Rate rate) {
		rate.setRateTime(new Date());
		return super.create(rate);
	}
	
	@Override
	public Rate get(Object rateId) {
		return super.find(Rate.class, rateId);
	}

	@Override
	public void delete(Object rateId) {
		super.delete(Rate.class, rateId);
		
	}

	@Override
	public List<Rate> listAll() {
		return super.findWithNamedQuery("Rate.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Rate.countAll");
	}

	public Rate findByCustomerAndShirt(Integer customerId, Integer shirtId) {
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("shirtId", shirtId);
		
		List<Rate>res = super.findWithNamedQuery("Rate.findByCustomerAndShirt", params);
	
		if(!res.isEmpty()) {
			return res.get(0);
		}
		return null;
	}
	
	public List<Rate> listMostRecentRates(){
		return super.findWithNamedQuery("Rate.findAll", 0, 3);
	}
	
	public long countByShirt(int shirtId) {
		return super.countWithNamedQuery("Rate.countByShirt", "shirtId", shirtId);
	}
	
	public long countByCustomer(int customerId) {
		return super.countWithNamedQuery("Rate.countByCustomer", "customerId", customerId);
	}
	
	public List<String> listRatingStars(){
		return super.listWithNamedQuery("Rate.listRatingStars");
	}
	
	public List<Integer> countRatingStars(){
		return super.countListWithNamedQuery("Rate.countRatingStars");
	}
}
