package com.shirtstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.Type;

public class ShirtDAOTest {
	private static ShirtDAO shirtDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		shirtDAO = new ShirtDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		shirtDAO.close();
	}

	@Test
	public void testCreateShirt() throws ParseException, IOException {
		Shirt newShirt = new Shirt();
		
		Type type = new Type("Slip-on");
		type.setTypeId(11);
		
		newShirt.setType(type);
		newShirt.setShirtName("Slip-on checkerboard");
		newShirt.setBrand("Vans");
		newShirt.setDescription("Buy this if you want to play chess");
		newShirt.setShirtPrice(69.99f);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date releasedDate = df.parse("24/04/2024");
		newShirt.setReleasedDate(releasedDate);
		
//		String imagePath = "C:\\Users\\Admin\\Downloads\\LTW\\Images for the Final Project\\VansSlipOn-Checkerboard.jpg";
//		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
//		newShirt.setShirtImage(imageBytes);
		
		Shirt createdShirt = shirtDAO.create(newShirt);
		assertTrue(createdShirt.getShirtId() > 0);
	}
	
	@Test
	public void testUpdateShirt() throws ParseException, IOException {
			Shirt existShirt = new Shirt();
			existShirt.setShirtId(4);
			
			Type type = new Type("Slip-on");
			type.setTypeId(11);
			
			existShirt.setType(type);
			existShirt.setShirtName("Slip-on checkerboard");
			existShirt.setBrand("Vans");
			existShirt.setDescription("Buy this if you want to play chess");
			existShirt.setShirtPrice(59.99f);
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date releasedDate = df.parse("24/04/2024");
			existShirt.setReleasedDate(releasedDate);
			
//			String imagePath = "C:\\Users\\Admin\\Downloads\\LTW\\Images for the Final Project\\vans-slip-on-checkerboard-black.jpg";
//			byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
//			existShirt.setShirtImage(imageBytes);
			
			Shirt updatedShirt = shirtDAO.update(existShirt);
			assertEquals(updatedShirt.getShirtName(), "Slip-on checkerboard");
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDeleteShirtFail() {
		Integer shirtId = 100;
		shirtDAO.delete(shirtId);
		
		assertTrue(true);
	}
	
	@Test
	public void testDeleteShirtSuccess() {
		Integer shirtId = 1;
		shirtDAO.delete(shirtId);
		
		assertTrue(true);
	}
	
	@Test
	public void testGetShirtFail() {
		Integer shirtId = 99;
		Shirt shirt = shirtDAO.get(shirtId);
		assertNull(shirt);
	}
	
	@Test
	public void testGetShirtSuccess() {
		Integer shirtId = 2;
		Shirt shirt = shirtDAO.get(shirtId);
		assertNotNull(shirt);
	}
	
	@Test
	public void testListAll() {
		List<Shirt> listShirts = shirtDAO.listAll();
		
		for(Shirt temp: listShirts) {
			System.out.println(temp.getShirtName() + " - " + temp.getShirtPrice());
		}
		
		assertFalse(listShirts.isEmpty());
	}
	
	@Test
	public void testFindByShirtNameNonExist() {
		String shirtName = "Classic old-skool";
		Shirt shirt = shirtDAO.findByName(shirtName);
		
		assertNull(shirt);
	}
	
	@Test
	public void testFindByShirtNameExist() {
		String shirtName = "Classic shirt V.1";
		Shirt shirt =  shirtDAO.findByName(shirtName);
		
		/* System.out.println("Shirt's id: " + shirt.getShirtId()); */
		assertNotNull(shirt);
	}
	
	@Test
	public void testCountAll() {
		long totalShirts = shirtDAO.count();
		
		assertEquals(3, totalShirts);
	}
	
	@Test
	public void testFindByType() {
		int typeId = 1;
		
		List<Shirt> listShirts = shirtDAO.listByType(typeId);
		
		assertTrue(listShirts.size() > 0);
	}
	
	@Test
	public void testFindNewShirts() {
		List<Shirt> listNewShirts = shirtDAO.listNewShirts();
		
		for(Shirt shirt: listNewShirts) {
			System.out.println(shirt.getShirtName());
			System.out.println(shirt.getReleasedDate());
		}
		
		assertEquals(listNewShirts.size(), 4);
	}
	
	@Test
	public void testSearchShirts() {
		String keyWord = "Classic";
		List<Shirt> listShirts = shirtDAO.search(keyWord);
		
		System.out.println("There are " + listShirts.size() + " results for " + keyWord);
		
		assertTrue(listShirts.size() > 0);
	}
	
	@Test
	public void testSearchShirtsByBrand() {
		String keyWord = "Vans";
		List<Shirt> listShirts = shirtDAO.search(keyWord);
		
		assertEquals(7, listShirts.size());
	}
	
	@Test
	public void testSearchShirtsByDescription() {
		String keyWord = "Durable";
		List<Shirt> listShirts = shirtDAO.search(keyWord);
		
		assertEquals(7, listShirts.size());
	}
	
	@Test
	public void testCountByType() {
		int typeId = 1;
		long shirtNumb = shirtDAO.countByType(typeId);
		
		assertTrue(shirtNumb == 7);
	}
	
	@Test
	public void testListBestSellingShirts() {
		List<Shirt> listBestSellingShirts = shirtDAO.listBestSellingShirts();
		
		for(Shirt shirt : listBestSellingShirts) {
			System.out.println(shirt.getShirtId() + " " + shirt.getShirtName());
		}
		
		assertEquals(4, listBestSellingShirts.size());
	}
	
	@Test
	public void testListMostFavoredShirts() {
		List<Shirt> listMostFavoredShirts = shirtDAO.listMostFavoredShirts();
		
		for(Shirt shirt : listMostFavoredShirts) {
			System.out.println(shirt.getShirtId() + " " + shirt.getShirtName());
		}
		
		assertEquals(4, listMostFavoredShirts.size());
	}
}
