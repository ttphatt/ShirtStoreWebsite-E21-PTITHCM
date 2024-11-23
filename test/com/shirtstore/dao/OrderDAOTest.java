package com.shirtstore.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shirtstore.entity.Customer;
import com.shirtstore.entity.OrderDetail;
import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.ShirtOrder;

public class OrderDAOTest {
	private static OrderDAO orderDAO;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		orderDAO = new OrderDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		orderDAO.close();
	}

	@Test
	public void testCreateShirtOrder() {
		ShirtOrder order = new ShirtOrder();
		Customer customer = new Customer();

		customer.setCustomerId(3);

		order.setCustomer(customer);
		order.setFirstname("Bart");
		order.setLastname("Williamson");
		order.setPhone("981234567");
		order.setAddressLine1("84 North Avenue");
		order.setAddressLine2("765 South-west street");
		order.setCity("New York");
		order.setState("New York");
		order.setCountry("US");
		order.setPayment("Paypal");
		order.setZipcode("123456");

		Set<OrderDetail>orderDetails = new HashSet<OrderDetail>();
		OrderDetail orderDetail = new OrderDetail();

		Shirt shirt = new Shirt(17);
		orderDetail.setShirt(shirt);
		orderDetail.setQuantity(2);
		orderDetail.setSubTotal(111.98f);
		orderDetail.setShirtOrder(order);

		orderDetails.add(orderDetail);

		order.setOrderDetails(orderDetails);
		order.setTax(11.198f);
		order.setShippingFee(1.0f);
//		order.setOrderSum(124.178f);
		order.setSubtotal(111.98f);

		ShirtOrder savedOrder = orderDAO.create(order);

		assertTrue(order.getOrderId() > 0);
	}

	@Test
	public void testCreateShirtOrder2() {
		ShirtOrder order = new ShirtOrder();
		Customer customer = new Customer();

		customer.setCustomerId(2);

		order.setCustomer(customer);
		order.setFirstname("Bart Homer");
		order.setPhone("981234567");
		order.setAddressLine1("84 North Avenue, New Orleans, America");

		Set<OrderDetail>orderDetails = new HashSet<OrderDetail>();
		OrderDetail orderDetail1 = new OrderDetail();

		Shirt shirt = new Shirt(7);
		orderDetail1.setShirt(shirt);
		orderDetail1.setQuantity(2);
		orderDetail1.setSubTotal(50.0f);
		orderDetail1.setShirtOrder(order);

		orderDetails.add(orderDetail1);


		Shirt shirt2 = new Shirt(9);
		OrderDetail orderDetail2 = new OrderDetail();

		orderDetail2.setShirt(shirt2);
		orderDetail2.setQuantity(3);
		orderDetail2.setSubTotal(42.00f);
		orderDetail2.setShirtOrder(order);

		orderDetails.add(orderDetail2);


		order.setOrderDetails(orderDetails);

		ShirtOrder savedOrder = orderDAO.create(order);

		assertTrue(order.getOrderId() > 0 && order.getOrderDetails().size() == 2);
	}

	@Test
	public void testGet() {
		Integer orderId = 19;
		ShirtOrder order = orderDAO.get(orderId);

		System.out.println(order.getFirstname());
		System.out.println(order.getLastname());
		System.out.println(order.getPhone());
		System.out.println(order.getAddressLine1());
		System.out.println(order.getAddressLine2());
		System.out.println(order.getCity());
		System.out.println(order.getState());
		System.out.println(order.getCountry());
		System.out.println(order.getZipcode());

		System.out.println(order.getStatus());
		System.out.println(order.getSubtotal());
		System.out.println(order.getShippingFee());
		System.out.println(order.getTax());
//		System.out.println(order.getOrderSum());
		System.out.println(order.getPayment());

		assertEquals(1, order.getOrderDetails().size());
	}

	@Test
	public void testGetByIdAndCustomerNull() {
		Integer orderId = 10;
		Integer customerId = 99;

		ShirtOrder order = orderDAO.get(orderId, customerId);
		assertNull(order);
	}

	@Test
	public void testGetByIdAndCustomerNotNull() {
		Integer orderId = 4;
		Integer customerId = 2;

		ShirtOrder order = orderDAO.get(orderId, customerId);
		assertNotNull(order);
	}

	@Test
	public void testDeleteObject() {
		Integer orderId = 3;
		orderDAO.delete(orderId);

		ShirtOrder order = orderDAO.get(orderId);

		assertNull(order);
	}

	@Test
	public void testListAll() {
		List<ShirtOrder>listOrders = orderDAO.listAll();

		for (ShirtOrder shirtOrder : listOrders) {
			System.out.println(shirtOrder.getOrderId() + " " + shirtOrder.getCustomer().getFirstname() + " " + shirtOrder.getCustomer().getLastname() + " " + shirtOrder.getStatus());

			for(OrderDetail detail : shirtOrder.getOrderDetails()) {
				Shirt shirt = detail.getShirt();
				int quantity = detail.getQuantity();
				float subtotal = detail.getSubTotal();
				System.out.println(shirt.getShirtName() + " " + quantity + " " + subtotal);
			}

		}

		assertTrue(listOrders.size() > 0);
	}

	@Test
	public void testUpdateShirtOrderToAddress() {
		Integer orderId = 2;
		ShirtOrder order = orderDAO.get(orderId);

		order.setAddressLine1("New Shipping Address");

		orderDAO.update(order);

		ShirtOrder updatedOrder = orderDAO.get(orderId);

		assertEquals(order.getAddressLine1(), updatedOrder.getAddressLine1());
	}

	@Test
	public void testUpdateShirtOrderDetail() {
		Integer orderId = 19;
		ShirtOrder order = orderDAO.get(orderId);

		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if(orderDetail.getShirt().getShirtId() == 17) {
				 orderDetail.setQuantity(5);
				 orderDetail.setSubTotal(254.95f);
			}
		}

		orderDAO.update(order);

		iterator = order.getOrderDetails().iterator();

		int expectedQuantity = 5;
		float expectedSubTotal = 254.95f;

		int actualQuantity = 0;
		float actualSubTotal = 0;

		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if(orderDetail.getShirt().getShirtId() == 17) {
				 actualQuantity = orderDetail.getQuantity();
				 actualSubTotal = orderDetail.getSubTotal();
			}
		}

		assertEquals(expectedQuantity, actualQuantity);
		assertEquals(expectedSubTotal, actualSubTotal, 0.0f);
	}

	@Test
	public void testCount() {
		long totalOrders = orderDAO.count();

		assertEquals(4, totalOrders);
	}

	@Test
	public void testFindByCustomerWithoutOrders() {
		Integer customerId = 99;

		List<ShirtOrder>listOrders = orderDAO.listByCustomer(customerId);

		assertTrue(listOrders.isEmpty());
	}

	@Test
	public void testFindByCustomerWithOrders() {
		Integer customerId = 2;

		List<ShirtOrder>listOrders = orderDAO.listByCustomer(customerId);

		assertTrue(listOrders.size() > 0);
	}

	@Test
	public void testMostRecentSales() {
		List<ShirtOrder> listMostRecentOrders = orderDAO.listMostRecentSales();


		assertEquals(3, listMostRecentOrders.size());
	}
}
