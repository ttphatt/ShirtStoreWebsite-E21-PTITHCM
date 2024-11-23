package com.shirtstore.dao;

import java.util.ArrayList;
import java.util.List;

import com.shirtstore.entity.Shirt;

public class ShirtDAO extends JPADAO<Shirt> implements GenericDAO<Shirt> {

	public ShirtDAO() {

	}

 
	@Override
	public Shirt create(Shirt shirt) {
		return super.create(shirt);
	}
	
	@Override
	public Shirt update(Shirt existShirt) {
		return super.update(existShirt);
	}
	
	@Override
	public Shirt get(Object shirtId) {
		return super.find(Shirt.class, shirtId);
	}

	@Override
	public void delete(Object shirtId) {
		super.delete(Shirt.class, shirtId);
	}

	@Override
	public List<Shirt> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Shirt.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Shirt.countAll");
	}
	
	public Shirt findByName(String shirtName) {
		List<Shirt> res = super.findWithNamedQuery("Shirt.findByShirtName", "shirtName", shirtName);
		
		if(!res.isEmpty()) {
			return res.get(0);
		}
		return null;
	}
	
	public List<Shirt>listByType(int typeId){
		return super.findWithNamedQuery("Shirt.findByType", "typeId", typeId);
	}
	
	public List<Shirt> listNewShirts(){
		return super.findWithNamedQuery("Shirt.findNew", 0, 6);
	}
	
	public List<Shirt>search(String keyword){
		return super.findWithNamedQuery("Shirt.search", "keyword", keyword);
	}
	
	public long countByType(int typeId) {
		return super.countWithNamedQuery("Shirt.countByType", "typeId", typeId);
	}
	
	public List<Shirt> listBestSellingShirts(){
		return super.findWithNamedQuery("OrderDetail.listBestSelling", 0, 3);
	}
	
	public List<Shirt> listMostFavoredShirts(){
		List<Shirt> mostFavoredShirts = new ArrayList<Shirt>();
		List<Object[]> result = super.findWithNamedQueryObjects("Rate.listMostFavoredShirts", 0, 3);
		
		if(!result.isEmpty()) {
			for(Object[] elements : result) {
				Shirt shirt = (Shirt) elements[0];
				mostFavoredShirts.add(shirt);
			}
		}
		
		return mostFavoredShirts;
	}
	
	public long countByOrderDetail(int shirtId) {
		return super.countWithNamedQuery("OrderDetail.countByShirt", "shirtId", shirtId);
	}
	
	public List<Integer> countShirtsByTypes(){
		return super.countListWithNamedQuery("Shirt.countByType2");
	}
	
	public List<String> listSoldShirtName(){
		return super.listWithNamedQuery("Shirt.listSoldShirtName");
	}
	
	public List<Integer> listEachShirtRevenue(){
		return super.countListWithNamedQuery("Shirt.listEachShirtRevenue");
	}
}
