package com.shirtstore.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.shirtstore.controller.frontend.cart.ShoppingCart;
import com.shirtstore.dao.*;
import com.shirtstore.entity.*;

public class OrderServices {
	private OrderDAO orderDAO;
	private ShirtDAO shirtDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.orderDAO = new OrderDAO();
		shirtDAO = new ShirtDAO();
	}
	
	public void listAllOrders() throws ServletException, IOException {
		listAllOrders(null);
	}
	
	public void listAllOrders(String message) throws ServletException, IOException {
		List<ShirtOrder> listOrders = orderDAO.listAll();
		OrderPromotionDAO orderPromotionDAO = new OrderPromotionDAO();
		float totalDiscount = 0, orderSum = 0;

		//Check promotion
		for(ShirtOrder order : listOrders){
			List<OrderPromotion> orderPromotions = orderPromotionDAO.getOrderPromotions(order.getOrderId());
			if(!orderPromotions.isEmpty()){
				for(OrderPromotion orderPromotion : orderPromotions){
					totalDiscount += orderPromotion.getDiscountPrice();
				}
			}

			orderSum = order.getSubtotal() + order.getTax() + order.getShippingFee() - totalDiscount;
			order.setOrderSum(orderSum);
		}
		//----------------------------------------------------------------------------------------------------//

		if(message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("listOrder", listOrders);
		
		String path = "order_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		
		ShirtOrder order = orderDAO.get(orderId);
		
		request.setAttribute("order", order);
		
		String path = "order_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void showCheckOutForm() throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		PromotionDAO promotionDAO = new PromotionDAO();

		Map<String, String> orderPromotions = promotionDAO.findOrderPromotionsBeingDisplayed();
		Map<String, String> shippingPromotions = promotionDAO.findShippingPromotionsBeingDisplayed();

		request.setAttribute("orderPromotions", orderPromotions);
		request.setAttribute("shippingPromotions", shippingPromotions);

		//Tax = Subtotal * 10%
		float tax = shoppingCart.getTotalAmount() * 0.1f;
		// shipping fee
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		String startAddress = "11, Duong Nguyen Dinh Chieu, Phuong Da Kao, Quan 1, Thanh Pho Ho Chi Minh, Viet Nam";
		String endAddress = customer.getAddressLine1() + ", " + customer.getState() + ", " + customer.getCity() + ", " + customer.getCountry();

		double distance = 1000000000;

		try {
			distance = new DistanceService().calculateDistance(startAddress, endAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}

		float shippingFee = 0.0f;

		if(distance <= 1000){
			//Shipping fee
			shippingFee = (float) calculateDomesticShippingFee(distance);
		}
		else{
			shippingFee = (float) calculateInternationalShippingFee(distance);
		}

		float totalPrice = shoppingCart.getTotalAmount() + tax + shippingFee;

		session.setAttribute("orderDiscount", null);
		session.setAttribute("orderPromotionId", null);
		session.setAttribute("shippingDiscount", null);
		session.setAttribute("shippingPromotionId", null);

		session.setAttribute("tax", tax);
		session.setAttribute("shippingFee", shippingFee);
		session.setAttribute("totalPrice", totalPrice);

		CommonUtility.generateCountriesList(request);
		String path = "frontend/checkout.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	private double calculateDomesticShippingFee(double distance) {
		System.out.println(distance);
		double threeKmRate = 0.5; // <= 3km
		double fromThreeKmRate = 0.07; // adding fee/km when distance > 3km
		double fromFiftyKmRate = 0.02; // add fee/km when distance > 50 km
		double fromOneHundredKmRate = 0.01; // add fee/km when distance > 100 km

		double totalFee;

		if (distance <= 3) {
			totalFee = threeKmRate * distance;
		} else if (distance <= 50) {
			totalFee = threeKmRate*3 + (distance - 3) * fromThreeKmRate;
		} else if (distance <= 100) {
			totalFee = threeKmRate*3 + fromThreeKmRate*50 + (distance - 50) * fromFiftyKmRate;
		} else {
			totalFee = threeKmRate*3 + fromThreeKmRate*50 + fromFiftyKmRate *100 + (distance-100)*fromOneHundredKmRate ;
		}

		return totalFee;
	}

	private double calculateInternationalShippingFee(double distance) {
		System.out.println(distance);
		double baseOne = 0.01; // <= 3km
		double baseTwo = 0.0091;
		double totalFee;

		if (distance <= 1800) {
			totalFee = 17;
		} else if (distance <= 5000) {
			totalFee = baseOne*distance;
		} else if (distance <= 7000) {
			totalFee = baseTwo*distance;
		} else {
			totalFee = 0.005 * distance;
		}
		return totalFee;
	}

	public void placeOrder() throws ServletException, IOException {
		String payment = request.getParameter("payment");
		ShirtOrder order = readOrderInfo();
		
		System.out.println("Order's id: " + order.getOrderId());
		
		if(payment.equals("Paypal")) {
			PaymentServices paymentService = new PaymentServices(request, response);
			request.getSession().setAttribute("order4Paypal", order);
			System.out.println("Paypal Checkout");
			paymentService.authorizePayment(order);
		}
		else {
			placeOrderCOD(order);
		}
	}
	
	private void sendEmailToCustomer(int orderId){
		//lấy email hiện tại ra
        HttpSession session = request.getSession();
        Customer loggedCustomer = (Customer) session.getAttribute("loggedCustomer");
        String email = loggedCustomer.getEmail();
        String name = loggedCustomer.getFirstname();
        String title = "Order Confirmation from PHK SHIRT STORE";
        String body = formEmail(name, orderId);
        //gửi mail
        MailServices.SendMail(email,title,body);
	}
	
	public String formEmail(String name, int orderId) {
		String form = "PHK SHIRT" +
				" STORE\r\n"
				+ "\r\n"
				+ "Order Confirmation and Thank You\r\n"
				+ "\r\n"
				+ "Dear " + name + ",\r\n"
				+ "\r\n"
				+ "Thank you for placing an order with PHK SHIRT STORE. We are excited to process your order and ensure you receive your items as soon as possible.\r\n"
				+ "\r\n"
				+ "Order Details:\r\n"
				+ "\r\n"
				+ "Order Number: " + orderId + "\r\n"
				+ "Order Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\r\n"
				+ "We appreciate your trust in us and strive to deliver the highest quality products and services. Your satisfaction is our top priority.\r\n"
				+ "\r\n"
				+ "Next Steps:\r\n"
				+ "\r\n"
				+ "You will receive a notification once your order is shipped, including tracking information.\r\n"
				+ "If you have any special instructions or need to make changes to your order, please contact us promptly.\r\n"
				+ "Customer Support:\r\n"
				+ "If you have any questions or require further assistance, our customer service team is here to help. You can reach us at phkshirtstore@gmail.com or call us at 0123456789.\r\n"
				+ "\r\n"
				+ "Thank you once again for your purchase. We look forward to serving you and hope you enjoy your new items!\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "\r\n"
				+ "PHK SHIRT STORE\r\n"
				+ "\r\n"
				+ "";
		return form;
	}
	
	public Integer placeOrderPaypal(Payment payment) {
		ShirtOrder order =  (ShirtOrder) request.getSession().getAttribute("order4Paypal");
		ItemList itemList = payment.getTransactions().get(0).getItemList();
		System.out.println("Itemlist" + itemList);
		ShippingAddress shippingAddress = itemList.getShippingAddress();
		String shippingPhonenumber = itemList.getShippingPhoneNumber();
		
		String recipientName = shippingAddress.getRecipientName();
		String[] names = recipientName.split(" ");
		
		order.setFirstname(names[0]);
		order.setLastname(names[1]);
		order.setAddressLine1(shippingAddress.getLine1());
		order.setAddressLine2(shippingAddress.getLine2());
		order.setCity(shippingAddress.getCity());
		order.setState(shippingAddress.getState());
		order.setCountry(shippingAddress.getCountryCode());
		order.setPhone(shippingPhonenumber);
		
		int orderId = saveOrder(order);

		//Please don't comment this line
		sendEmailToCustomer(orderId);
		//---------------------------//

		return orderId;
	}
	
	private Integer saveOrder(ShirtOrder order) {
		System.out.println("+++++++++++++++++++++++++++++++++");
		for(OrderDetail orderDetail : order.getOrderDetails()) {
			System.out.println(orderDetail.getShirt().getShirtId());
			System.out.println(orderDetail.getSize());
		}
		ShirtOrder savedOrder = orderDAO.create(order);
		System.out.println(savedOrder.getOrderId());
		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");

		for(CartDTO cartDTO : shoppingCart.getCarts()){
			new JPADAO<>().updateSize(cartDTO.getShirt().getShirtId(), cartDTO.getSize(), -cartDTO.getQuantity());
		}

		shoppingCart.deleteCart(order.getCustomer().getCustomerId());
		shoppingCart.clearCart();
		
		return savedOrder.getOrderId();
	}
	
	private ShirtOrder readOrderInfo() {
		String payment = request.getParameter("payment");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phoneNumber = request.getParameter("phoneNumber");
		String addressLine1 = request.getParameter("addressLine1");
		String addressLine2 = request.getParameter("addressLine2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String country = request.getParameter("country");


		ShirtOrder order = new ShirtOrder();
		
		order.setFirstname(firstname);
		order.setLastname(lastname);
		order.setPhone(phoneNumber);
		order.setAddressLine1(addressLine1);
		order.setAddressLine2(addressLine2);
		order.setCity(city);
		order.setState(state);
		order.setZipcode(zip);
		order.setCountry(country);
		order.setPayment(payment);
		
		//Lấy thông tin khách hàng từ session khi khách hàng đăng nhập tài khoản
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		order.setCustomer(customer);
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		List<CartDTO> listCards = shoppingCart.getCarts();
		
		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();

		for (CartDTO cartDTO : listCards) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setShirt(cartDTO.getShirt());
			orderDetail.setShirtOrder(order);
			orderDetail.setSize(cartDTO.getSize());
			orderDetail.setQuantity(cartDTO.getQuantity());
			orderDetail.setSubTotal(cartDTO.getQuantity() * cartDTO.getShirt().getShirtPrice());
			orderDetails.add(orderDetail);
		}

		System.out.println("-----------------------------");
		for(OrderDetail orderDetail : orderDetails){
			System.out.println(orderDetail.getShirt().getShirtId());
			System.out.println(orderDetail.getSize());
		}

		order.setOrderDetails(orderDetails);

		float tax = (float) session.getAttribute("tax");
		float shippingFee = (float) session.getAttribute("shippingFee");
		float total = (float) session.getAttribute("totalPrice");

		System.out.println("Tax: " + tax);
		System.out.println("Shipping fee: " + shippingFee);
		System.out.println("Total price: " + total);

		order.setSubtotal(shoppingCart.getTotalAmount());
		order.setTax(tax);
		order.setShippingFee(shippingFee);
		order.setOrderSum(total);

		Set<OrderPromotion> orderPromotions = new HashSet<OrderPromotion>();
		PromotionDAO promotionDAO = new PromotionDAO();
		String idPromotion = (String) request.getSession().getAttribute("orderPromotionId");
		String shippingPromotionId = (String) request.getSession().getAttribute("shippingPromotionId");
		System.out.println(idPromotion);
		System.out.println(shippingPromotionId);

		if(idPromotion != null) {
			System.out.println("In id promotion");
			OrderPromotion orderPromotion = new OrderPromotion();
			Promotion promotion = promotionDAO.get(idPromotion);

			orderPromotion.setShirtOrder(order);
			orderPromotion.setPromotion(promotion);
			orderPromotion.setDiscountPrice((float) request.getSession().getAttribute("orderDiscount"));

			orderPromotions.add(orderPromotion);
			order.setOrderPromotions(orderPromotions);
		}

		if(shippingPromotionId != null){
			OrderPromotion orderPromotion = new OrderPromotion();
			Promotion promotion = promotionDAO.get(shippingPromotionId);

			orderPromotion.setShirtOrder(order);
			orderPromotion.setPromotion(promotion);
			orderPromotion.setDiscountPrice((float) request.getSession().getAttribute("shippingDiscount"));

			orderPromotions.add(orderPromotion);
			order.setOrderPromotions(orderPromotions);
		}

		return order;
	}
	
	private void placeOrderCOD(ShirtOrder order) throws ServletException, IOException {
//		sendEmailToCustomer(saveOrder(order));
		saveOrder(order);
		String message = "Thank you for choosing our products. Your order has been received. Your shirts will arrive within a few days.";
		request.setAttribute("message", message);
		
		String path = "frontend/message.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
		
	}

	public void listOrderByCustomer() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		OrderPromotionDAO orderPromotionDAO = new OrderPromotionDAO();
		List<ShirtOrder> listOrders = orderDAO.listByCustomer(customer.getCustomerId());
		float totalDiscount = 0;
		float orderSum = 0;

		//Check promotion
		for(ShirtOrder order : listOrders){
			totalDiscount = 0;
			orderSum = 0;
			List<OrderPromotion>orderPromotions = orderPromotionDAO.getOrderPromotions(order.getOrderId());
			if(!orderPromotions.isEmpty()){
				for(OrderPromotion orderPromotion : orderPromotions){
					totalDiscount += orderPromotion.getDiscountPrice();
				}
			}

			orderSum = order.getSubtotal() + order.getTax() + order.getShippingFee() - totalDiscount;
			System.out.println("Order's id: " + order.getOrderId());
			System.out.println("Subtotal: " + order.getSubtotal());
			System.out.println("Tax: " + order.getTax());
			System.out.println("ShippingFee: " + order.getShippingFee());
			System.out.println("Total discount: " + totalDiscount);
			System.out.println("Order sum: " + orderSum);
			order.setOrderSum(orderSum);
		}
		//----------------------------------------------------------------------------------------------------//
		request.setAttribute("listOrders", listOrders);
	
		String path = "frontend/order_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void showOrderDetailForCustomer() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		ShirtOrder order = orderDAO.get(orderId, customer.getCustomerId());
		OrderPromotionDAO orderPromotionDAO = new OrderPromotionDAO();
		float totalDiscount = 0;
		float orderSum = 0;

		List<OrderPromotion>orderPromotions = orderPromotionDAO.getOrderPromotions(order.getOrderId());
		if(!orderPromotions.isEmpty()){
			Set<OrderPromotion> listPromotions = new HashSet<OrderPromotion>();
			for(OrderPromotion orderPromotion : orderPromotions){
				totalDiscount += orderPromotion.getDiscountPrice();
				listPromotions.add(orderPromotion);
			}
			order.setOrderPromotions(listPromotions);
		}

		orderSum = order.getSubtotal() + order.getTax() + order.getShippingFee() - totalDiscount;
		order.setOrderSum(orderSum);

		request.setAttribute("order", order);
		CommonUtility.generateCountriesList(request);
		
		String path = "frontend/order_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void showEditOrderForm() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));

		ShirtOrder order = orderDAO.get(orderId);
		OrderPromotionDAO orderPromotionDAO = new OrderPromotionDAO();
		float totalDiscount = 0;
		float orderSum = 0;

		List<OrderPromotion>orderPromotions = orderPromotionDAO.getOrderPromotions(order.getOrderId());
		if(!orderPromotions.isEmpty()){
			Set<OrderPromotion> listPromotions = new HashSet<OrderPromotion>();
			for(OrderPromotion orderPromotion : orderPromotions){
				totalDiscount += orderPromotion.getDiscountPrice();
				listPromotions.add(orderPromotion);
			}
			order.setOrderPromotions(listPromotions);
		}

		orderSum = order.getSubtotal() + order.getTax() + order.getShippingFee() - totalDiscount;
		order.setOrderSum(orderSum);

		request.setAttribute("order", order);
		
		String path = "order_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
		
	}

	public void updateOrder() throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShirtOrder order = (ShirtOrder) session.getAttribute("order");
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String addressLine1 = request.getParameter("addressLine1");
		String addressLine2 = request.getParameter("addressLine2");
		
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		
		float shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		float tax = Float.parseFloat(request.getParameter("tax"));
		
		
		String payment = request.getParameter("payment");
		String orderStatus = request.getParameter("orderStatus");
		
		
		order.setFirstname(firstname);
		order.setLastname(lastname);
		order.setPhone(phone);
		order.setAddressLine1(addressLine1);
		order.setAddressLine2(addressLine2);
		order.setCity(city);
		order.setState(state);
		order.setCountry(country);
		order.setZipcode(zipcode);
		order.setPayment(payment);
		order.setStatus(orderStatus);
	
		order.setShippingFee(shippingFee);
		order.setTax(tax);
		
		String[] arrShirtId = request.getParameterValues("shirtId");
		String[] arrShirtPrice = request.getParameterValues("shirtPrice");
		String[] arrQuantity = new String[arrShirtId.length];
		
		for(int i = 1; i <= arrQuantity.length; i++) {
			arrQuantity[i - 1] = request.getParameter("quantity" + i);
		}
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		orderDetails.clear();
		
		float orderSum = 0.0f;
		
		for(int i = 0; i < arrShirtId.length; i++) {
			int shirtId = Integer.parseInt(arrShirtId[i]);
			int quantity = Integer.parseInt(arrQuantity[i]);
			float shirtPrice = Float.parseFloat(arrShirtPrice[i]);
			float subTotal = quantity * shirtPrice;
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setShirt(new Shirt(shirtId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubTotal(subTotal);
			orderDetail.setShirtOrder(order);
			
			orderDetails.add(orderDetail);
			
			orderSum += subTotal;
		}
		
		order.setSubtotal(orderSum);
		orderSum += shippingFee;
		orderSum += tax;
		
//		order.setOrderSum(orderSum);
		
		orderDAO.update(order);
		
		String message = "The order with id: " + order.getOrderId() + " has been updated successfully";
		listAllOrders(message);
	}

	public void updateOrderCustomer() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		ShirtOrder order = orderDAO.get(orderId);

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String addressLine1 = request.getParameter("addressLine1");
		String addressLine2 = request.getParameter("addressLine2");

		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");

		order.setFirstname(firstname);
		order.setLastname(lastname);
		order.setPhone(phone);
		order.setAddressLine1(addressLine1);
		order.setAddressLine2(addressLine2);
		order.setCity(city);
		order.setState(state);
		order.setZipcode(zipcode);
		order.setCountry(country);

		orderDAO.update(order);

		String message = "Your order has been updated successfully";
		String path = "frontend/message.jsp";

		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void deleteOrder() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		orderDAO.delete(orderId);
		
		
		String message = "The order with id: " + orderId + " has been deleted successfully";
		listAllOrders(message);
		
	}

	public void cancelOrder() throws ServletException, IOException {
		String message = "Your order has been cancelled successfully";
		changeOrderStatusCustomer("Cancelled", message);
	}

	public void returnOrder() throws ServletException, IOException {
		String message = "Your order has been returned successfully";
		changeOrderStatusCustomer("Returned", message);
	}

	public void completeOrder() throws ServletException, IOException {
		String message = "Your order has been set to completed status successfully";
		changeOrderStatusCustomer("Completed", message);
	}

	public void shippingOrder() throws ServletException, IOException {
		String message = "The order with id: " + Integer.parseInt(request.getParameter("orderId")) + " has been set to shipping status successfully";
		changeOrderStatusAdmin("Shipping", message);
	}

	public void deliveredOrder() throws ServletException, IOException {
		String message = "The order with id: " + Integer.parseInt(request.getParameter("orderId")) + " has been set to delivered status successfully";
		changeOrderStatusAdmin("Delivered", message);
	}

	public void changeOrderStatusCustomer(String status, String message) throws ServletException, IOException {
		String path = "frontend/message.jsp";
		changeOrderStatus(status, path, message);
	}

	public void changeOrderStatusAdmin(String status, String message) throws ServletException, IOException {
		String path = "message.jsp";
		changeOrderStatus(status, path, message);
	}

	public void changeOrderStatus(String status, String path, String message) throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		ShirtOrder order = orderDAO.get(orderId);

		order.setStatus(status);
		orderDAO.update(order);

		for(OrderDetail orderDetail : order.getOrderDetails()) {
			new JPADAO<>().updateSize(orderDetail.getShirt().getShirtId(), orderDetail.getSize(), orderDetail.getQuantity());
		}

		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
}
