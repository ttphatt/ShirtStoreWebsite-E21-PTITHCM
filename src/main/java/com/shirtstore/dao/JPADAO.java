package com.shirtstore.dao;

import com.shirtstore.entity.ProductSales;
import com.shirtstore.entity.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPADAO<E> {
	private static EntityManagerFactory entityManagerFactory;
	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("ShirtStoreWebsite");
	}

	public JPADAO() {
	}
	
	//Thêm dữ liệu vào database
	public E create(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(entity);
		entityManager.flush();
		entityManager.refresh(entity);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return entity;
	}
	
	//Cập nhật dữ liệu trong database
	public E update(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		
		entityManager.close();
		return entity;
	}
	
	//Tìm dữ liệu trong database
	public E find(Class<E>type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		E entity = entityManager.find(type, id);
		if(entity != null) {
			entityManager.refresh(entity);
		}
		
		entityManager.close();
		return entity;
	}
	
	//Xóa dữ liệu trong database
	public void delete(Class<E>type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Object reference = entityManager.getReference(type, id);
		entityManager.remove(reference);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
	}
	
	//Trả về tập dữ liệu trong database bằng query
	public List<E>findWithNamedQuery(String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		List<E>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	public List<E>findWithNamedQuery(String queryName, int firstResult, int maxResult){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<E>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	public List<Object[]>findWithNamedQueryObjects(String queryName, int firstResult, int maxResult){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		
		List<Object[]>res = query.getResultList();
		
		entityManager.close();
		return res;
	}

	public List<Object[]>findWithNamedQueryObjects(String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);

		List<Object[]>res = query.getResultList();
		entityManager.close();

		return res;
	}

	public List<E>findWithNamedQuery(String queryName, String paramName, Object paramValue){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);
		
		query.setParameter(paramName, paramValue);
		List<E>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	public List<E>findWithNamedQuery(String queryName, Map<String, Object>parameters){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		Set<java.util.Map.Entry<String, Object>>setParameters = parameters.entrySet();
	
		for(java.util.Map.Entry<String, Object>temp : setParameters) {
			query.setParameter(temp.getKey(), temp.getValue());
		}
		
		List<E>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	//Các function thêm vào
	public List<String>listWithNamedQuery(String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		List<String>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	public List<Integer>countListWithNamedQuery(String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		List<Integer>res = query.getResultList();
		
		entityManager.close();
		return res;
	}

	public List<Long> countListWithNamedQuery3 (String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createNamedQuery(queryName);
		List<Long>res = query.getResultList();

		entityManager.close();
		return res;
	}
	
	public List<Double>countListWithNamedQuery2(String queryName){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		List<Double>res = query.getResultList();
		
		entityManager.close();
		return res;
	}
	
	//Trả về sồ lượng dữ liệu có trong database bằng query
	public long countWithNamedQuery(String queryName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		
		long res = (long)query.getSingleResult();
		entityManager.close();
		return res;
	}

	public long countWithNamedQuery(String queryName, Map<String, Object> parameters) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Query query = entityManager.createNamedQuery(queryName);
		Set<java.util.Map.Entry<String, Object>>setParameters = parameters.entrySet();

		for(java.util.Map.Entry<String, Object>temp : setParameters) {
			query.setParameter(temp.getKey(), temp.getValue());
		}

		long res = (long)query.getSingleResult();
		entityManager.close();
		return res;
	}

	public long countWithNamedQuery(String queryName, String parameterName, Object parameterValue) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);
		query.setParameter(parameterName, parameterValue);
		
		long res = (long)query.getSingleResult();
		entityManager.close();
		return res;
	}
	
	public void close() {
		if(entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

	// ------------------------------------ Custom JPADAO ------------------------------------
	public List<ProductSales> getProductSales() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String sql = "SELECT product_id, SUM(quantity) AS quantity_sold " +
				"FROM order_detail " +
				"WHERE order_id NOT IN (" +
				"    SELECT order_id " +
				"    FROM `order` " +
				"    WHERE status IN ('Cancelled', 'Returned')" +
				") " +
				"GROUP BY product_id";

		Query query = entityManager.createNativeQuery(sql);

		List<Object[]> results = query.getResultList();

		List<ProductSales> productSalesList = new ArrayList<>();

		for (Object[] row : results) {
			int productId = (int) row[0];
			int quantitySold = ((Number) row[1]).intValue();

			productSalesList.add(new ProductSales(productId, quantitySold));
		}

		entityManager.close();
		return productSalesList;
	}

	public ProductSales getProductSalesByProductId(int productId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String sql = "SELECT product_id, SUM(quantity) AS quantity_sold FROM order_detail WHERE product_id = ?1";

		Query query = entityManager.createNativeQuery(sql);

		query.setParameter(1, productId);

		Object[] result = (Object[]) query.getSingleResult();

		ProductSales productSales = null;
		if (result != null) {
			int id = (int) result[0];
			int quantitySold = ((Number) result[1]).intValue();

			productSales = new ProductSales(id, quantitySold);
		}

		entityManager.close();
		return productSales;
	}

	public void createSize(List<Size> sizes) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			for (Size size : sizes) {
				entityManager.persist(size);
			}

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public void updateSize(int productId, String type, int addQuantity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			String jpql = "UPDATE Size s SET s.quantity = s.quantity + ?1 WHERE s.product_id = ?2 AND s.type = ?3";
			Query query = entityManager.createQuery(jpql);

			query.setParameter(1, addQuantity);
			query.setParameter(2, productId);
			query.setParameter(3, type);

			int rowsUpdated = query.executeUpdate();

			entityManager.getTransaction().commit();

			if (rowsUpdated == 0) {
				System.out.println("Không có bản ghi nào được cập nhật");
			} else {
				System.out.println("Đã cập nhật số lượng thành công");
			}

		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public void deleteCart(int customer_id){
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			String jpql = "DELETE FROM Cart c WHERE c.customer_id = ?1";
			Query query = entityManager.createQuery(jpql);

			query.setParameter(1, customer_id);

			query.executeUpdate();

			entityManager.getTransaction().commit();

		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}
}
