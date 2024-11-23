package com.shirtstore.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shirtstore.entity.Type;

public class TypeDAOTest{
	private static TypeDAO typeDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		typeDAO = new TypeDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		typeDAO.close();
	}

	@Test
	public void testCreateType() {
		Type newType = new Type("Vintage");
		Type type = typeDAO.create(newType);
		
		assertTrue(type != null && type.getTypeId() > 0);
	}

	@Test
	public void testUpdateType() {
		Type newType = new Type("Basic");
		newType.setTypeId(1);
		
		Type type = typeDAO.update(newType);
		
		assertEquals(newType.getTypeName(), type.getTypeName());
	}
	
	@Test
	public void testGet() {
		Integer typeId = 2;
		Type type = typeDAO.get(typeId);
		
		assertNotNull(type);
	}

	@Test
	public void testDeleteType() {
		Integer typeId = 3;
		typeDAO.delete(typeId);
		
		Type type = typeDAO.get(typeId);
		assertNull(type);
	}

	@Test
	public void testListAll() {
		List<Type> listType = typeDAO.listAll();
		
		listType.forEach(c -> System.out.println(c.getTypeName()));
		
		assertTrue(listType.size() > 0);
	}

	@Test
	public void testCount() {
		long totalType = typeDAO.count();
		
		System.out.println("There is a total of: " + totalType + " types");
		
		assertTrue(totalType > 0);
	}

	@Test
	public void testFindByName() {
		String typeName = "Basic";
		Type temp = typeDAO.findByName(typeName);
		
		assertNotNull(temp);
	}
	
	@Test
	public void testFindByNameNotFound() {
		String typeName = "Basic 213";
		Type temp = typeDAO.findByName(typeName);
		
		assertNull(temp);
	}
}
