//package com.shirtstore.controller.frontend.cart;
//
//import static org.junit.Assert.*;
//
//import java.util.Map;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.shirtstore.entity.Shirt;
//
//public class ShoppingCartTest {
//	private static ShoppingCart cart;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		cart = new ShoppingCart();
//		Shirt shirt = new Shirt(1);
//		shirt.setShirtPrice(10);
//
//		cart.addItem(shirt);
//		cart.addItem(shirt);
//	}
//
//
//	@Test
//	public void testAddItem() {
//		Map<Shirt, Integer> items = cart.getItems();
//		int quantity = items.get(new Shirt(1));
//
//		assertEquals(quantity, 2);
//	}
//
//	@Test
//	public void testRemoveItem() {
//		cart.removeItem(new Shirt(1));
//
//		assertTrue(cart.getItems().isEmpty());
//	}
//
//	@Test
//	public void testRemoveItem2() {
//		Shirt shirt2 = new Shirt(2);
//		cart.addItem(shirt2);
//
//		cart.removeItem(new Shirt(2));
//
//		assertNull(cart.getItems().get(shirt2));
//	}
//
//	@Test
//	public void testGetTotalQuantity() {
//		Shirt shirt3 = new Shirt(3);
//
//		cart.addItem(shirt3);
//		cart.addItem(shirt3);
//		cart.addItem(shirt3);
//
//		assertEquals(5, cart.getTotalQuantity());
//	}
//
//	@Test
//	public void testGetTotalAmount() {
//		ShoppingCart cart = new ShoppingCart();
//		assertEquals(0.0f, cart.getTotalAmount(), 0.0f);
//	}
//
//	@Test
//	public void testGetTotalAmount2() {
//		assertEquals(20.0f, cart.getTotalAmount(), 0.0f);
//	}
//
//	@Test
//	public void testClearCart() {
//		cart.clearCart();
//
//		assertEquals(0, cart.getTotalQuantity());
//	}
//
//	@Test
//	public void testUpdateCart() {
//		ShoppingCart cart = new ShoppingCart();
//		Shirt shirt1 = new Shirt(1);
//		Shirt shirt2 = new Shirt(2);
//
//		cart.addItem(shirt1);
//		cart.addItem(shirt2);
//
//		int[] shirtIds = {1, 2};
//		int[] quantities = {2, 3};
//
//		cart.updateCart(shirtIds, quantities);
//
//		assertEquals(5, cart.getTotalQuantity());
//	}
//}
