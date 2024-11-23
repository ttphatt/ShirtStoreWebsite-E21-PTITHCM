package com.shirtstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shirtstore.entity.Customer;
import com.shirtstore.entity.Rate;
import com.shirtstore.entity.Shirt;

public class RateDAOTest {
	private static RateDAO rateDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rateDAO = new RateDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		rateDAO.close();
	}

	@Test
	public void testCreateRate() {
		Rate rate = new Rate();

		Shirt shirt = new Shirt();
		shirt.setShirtId(13);

		Customer customer = new Customer();
		customer.setCustomerId(9);

		rate.setShirt(shirt);
		rate.setCustomer(customer);

		rate.setHeadline("A nice shirt");
		rate.setRatingStars(3);
		rate.setRatingDetail("They are so comfortable when I put it on");

		Rate createdRate = rateDAO.create(rate);

		assertTrue(createdRate.getRateId() > 0);
	}

	@Test
	public void testGet() {
		Integer rateId = 1;
		Rate rate = rateDAO.get(rateId);

		assertNotNull(rate);
	}

	@Test
	public void testUpdate() {
		Integer rateId = 1;
		Rate rate = rateDAO.get(rateId);

		rate.setHeadline("A great shirt for my outfit");

		Rate updatedRate = rateDAO.update(rate);

		assertEquals(rate.getHeadline(), updatedRate.getHeadline());
	}

	@Test
	public void testDeleteObject() {
		int rateId = 2;
		rateDAO.delete(rateId);

		Rate rate = rateDAO.get(rateId);

		assertNull(rate);
	}

	@Test
	public void testListAll() {
		List<Rate> listRates = rateDAO.listAll();

		for(Rate rate : listRates) {
			System.out.println(rate.getRateId() + " " + rate.getHeadline() + " " + rate.getShirt().getShirtName() + " " + rate.getCustomer().getFirstname() + " " + rate.getCustomer().getLastname());
		}

		assertTrue(listRates.size() > 0);
	}

	@Test
	public void testCount() {
		long numberOfRates = rateDAO.count();

//		System.out.println("Total number of rates: " + numberOfRates);
		assertTrue(numberOfRates > 0);
	}

	@Test
	public void testFindByCustomerAndShirtNotFound() {
		Integer customerId = 99;
		Integer shirtId = 99;

		Rate res = rateDAO.findByCustomerAndShirt(customerId, shirtId);

		assertNull(res);
	}

	@Test
	public void testFindByCustomerAndShirtFound() {
		Integer customerId = 7;
		Integer shirtId = 3;

		Rate res = rateDAO.findByCustomerAndShirt(customerId, shirtId);

		assertNotNull(res);
	}

	@Test
	public void testListMostRecentRates() {
		List<Rate> recentRates = rateDAO.listMostRecentRates();

		assertEquals(3, recentRates.size());
	}
}
