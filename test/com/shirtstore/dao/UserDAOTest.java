package com.shirtstore.dao;

import static org.junit.Assert.*;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import com.shirtstore.entity.Users;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOTest {
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		userDAO = new UserDAO();
	}
	
	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("roundround@gmail.com");
		user1.setFullName("The ball");
		user1.setPassword("1487965");
		
		user1 = userDAO.create(user1);
		
		assertTrue(user1.getUserId() > 0);
	}
	
	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(1);
		user.setEmail("homer123@gmail.com");
		user.setFullName("Dracker");
		user.setPassword("1404");
		
		user = userDAO.update(user);
		String expected = "1404";
		String actual = user.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();
		user1 = userDAO.create(user1);	
		
		assertNotNull(user1);
	}
	
	@Test
	public void testGetUsersFound() {
		Integer userId = 1;
		Users user = userDAO.get(userId);
		
		if(user != null) {
			System.out.println(user.getEmail());
		}			
		assertNotNull(user);
	}
	
	@Test
	public void testGetUsersNotFound() {
		Integer userId = 99;
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test
	public void testDeleteUsers() {
		Integer userId = 1;
		userDAO.delete(userId);
		
		Users user = userDAO.get(userId);
		assertNull(user);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteNonExistUsers() {
		Integer userId = 44;
		userDAO.delete(userId);
	}
	
	@Test
	public void testListAll() {
		List<Users>listUsers = userDAO.listAll();
		
		for(Users user : listUsers) {
			System.out.println(user.getEmail());
		}
		
		assertTrue(listUsers.size() > 0);
	}
		
	@Test
	public void testCount() {
		long totalUsers = userDAO.count();
		
		System.out.println("There are: " + totalUsers + " users in the database");
		
		assertTrue(totalUsers > 0);
	}
	
	@Test
	public void testFindByEmail() {
		String email = "drk14042003@gmail.com";
		
		Users user = userDAO.findByEmail(email);
		assertNotNull(user);
	}
	
	@Test
	public void testCheckLoginSuccess() {
		String email = "homer@gmail.com";
		String password = "1235743";
		
		boolean loginRes = userDAO.checkLogin(email, password);
		assertTrue(loginRes);
	}
	
	@Test
	public void testCheckLoginFail() {
		String email = "homer@gmail.com";
		String password = "123574789543";
		
		boolean loginRes = userDAO.checkLogin(email, password);
		assertFalse(loginRes);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		userDAO.close();
	}
}
