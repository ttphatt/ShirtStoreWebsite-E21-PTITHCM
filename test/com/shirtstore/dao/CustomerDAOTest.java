package com.shirtstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shirtstore.entity.Customer;

public class CustomerDAOTest {
	private static CustomerDAO customerDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		customerDAO = new CustomerDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		customerDAO.close();
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmail("customer123@gmail.com");
		customer.setFirstname("Bart");
		customer.setLastname("Docker");
		customer.setCity("New Orleans");
		customer.setState("New Orleans");
		customer.setCountry("America");
		customer.setAddressLine1("14 South Avenue");
		customer.setAddressLine2("345 Red Valley St");	
		customer.setPassword("qwerty");
		customer.setPhoneNumber("7657123");
		customer.setZipcode("70117");
		
		Customer res = customerDAO.create(customer);
		
		assertTrue(res.getCustomerId() > 0);
	}

	@Test
	public void testGet() {
		Integer customerId = 10;
		
		Customer customer = customerDAO.get(customerId);
		
		assertNotNull(customer);
	}

	@Test
	public void testUpdate() {
		Customer customer = customerDAO.get(10);
		String firstNameUpdated = "Joe Hana";
		
		customer.setFirstname("Joe Hana");
		customer.setPassword("123456");		
		Customer updatedCustomer = customerDAO.update(customer);
		
		assertTrue(updatedCustomer.getFirstname().equals(firstNameUpdated));
	}
	
	@Test
	public void testDeleteObject() {
		Integer customerId = 10;
		customerDAO.delete(customerId);
		
		Customer customer =  customerDAO.get(customerId);
		
		assertNull(customer);
	}
	
	@Test
	public void testFindAll() {
		List<Customer> listCustomers = customerDAO.listAll();
		
		for(Customer customer : listCustomers) {
			System.out.println(customer.getFirstname() + " " + customer.getLastname());
		}
		
		assertFalse(listCustomers.isEmpty());
	}

	@Test
	public void testCountAll() {
		long totalCustomer = customerDAO.count();
		
		assertEquals(8, totalCustomer);
	}
	
	@Test
	public void testFindByEmail() {
		String email = "temp@gmail.com";
		
		Customer customer = customerDAO.findByEmail(email);
		
		assertNotNull(customer);
	}
	
	@Test
	public void testCheckLoginSuccessful() {
		String email = "temp@gmail.com";
		String password = "qwerty";
		
		Customer customer = customerDAO.checkLogin(email, password);
		
		assertNotNull(customer);
	}
	
	@Test
	public void testCheckLoginFail() {
		String email = "temp@gmail.com";
		String password = "123456";
		
		Customer customer = customerDAO.checkLogin(email, password);
		
		assertNull(customer);
	}
}
