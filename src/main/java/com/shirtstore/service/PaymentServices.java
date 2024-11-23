package com.shirtstore.service;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.shirtstore.entity.*;

public class PaymentServices {
	private static final String CLIENT_ID = "AZv2cYwNaqzx0au1TooXwNd_A9GpR1_0fzYovkVCIbMxdyMP7yA0z2iTsKgu5zeh3oVfClGnU4eJteDt";
	private static final String CLIENT_SECRET = "EJSZH7IUlO1TJdwLToG1MLl2OC8zn3jTqViYZQegL9HVSANsfFt_Z0DeZ8KhIUcLSszXaNk6iMFUT46o";
	private String mode = "sandbox";

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public PaymentServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	public void authorizePayment(ShirtOrder order) throws ServletException, IOException {
		//Get Payer's information
		//Lấy các thông tin của người trả và set lên paypal:
		//Họ, tên và email
		Payer payer = getPayerInformation(order);
		
		//Redirect URLs
		//Set các đường link khi ở trang paypal:
		//Cancel -> Thì chuyển hướng về trang view_cart
		//Return -> Thì chuyển hướng về trang review_payment
		RedirectUrls redirectUrls = getRedirectUrls();
		
		List<Transaction> transactions = getTransactionInformation(order);
		System.out.println("-----------------------------------------------");
		System.out.println(transactions);
		
		//Request payment
		Payment requestPayment = new Payment();
		requestPayment.setPayer(payer)
					  .setRedirectUrls(redirectUrls)
					  .setIntent("authorize")
					  .setTransactions(transactions);
					  
		System.out.println("=====REQUEST PAYMENT=====");
		System.out.println(requestPayment);
		
		//Set chuỗi kết nối đến server paypal
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		try {
			Payment authorizedPayment = requestPayment.create(apiContext);
			System.out.println("=====AUTHORIZED PAYMENT=====");
			System.out.println(authorizedPayment);
			
			//Lấy ra đường link để chuyển hướng sang trang paypal để tiếp tục thanh toán
			String approvalURL = getApprovalURL(authorizedPayment);
			
			response.sendRedirect(approvalURL);
			
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("Error in authorizing payment.");
		}
		
		//Get approval link
		
		//Redirect to Paypal's payment page
	}
	
	private String getApprovalURL(Payment authorizedPayment) {
		String approvalURL = null;
		
		List<Links> links = authorizedPayment.getLinks();
		
		for(Links link : links) {
			if(link.getRel().equalsIgnoreCase("approval_url")) {
				approvalURL = link.getHref();
				break;
			}
		}
		return approvalURL;
	}

	private Payer getPayerInformation(ShirtOrder order) {
		Payer payer = new Payer();
		payer.setPaymentMethod("Paypal");
				
		PayerInfo payerInfo = new PayerInfo();
		Customer customer = order.getCustomer();
		payerInfo.setFirstName(customer.getFirstname());
		payerInfo.setLastName(customer.getLastname());
		payerInfo.setEmail(customer.getEmail());
		payer.setPayerInfo(payerInfo);
	
		return payer;
	}
	
	private RedirectUrls getRedirectUrls() {
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI().toString();
		String baseURL = requestURL.replace(requestURI, "").concat(request.getContextPath());
		
		RedirectUrls redirectUrls = new RedirectUrls();
		String cancelUrl = baseURL.concat("/view_cart");
		String returnUrl = baseURL.concat("/review_payment");
		
		System.out.println("Return URL:" + returnUrl);
		System.out.println("Cancel URL:" + cancelUrl);
		
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		return redirectUrls;
	}
	
	private Amount getAmountDetails(ShirtOrder order) {
		Details details = new Details();
		details.setShipping(String.format(Locale.US, "%.2f", order.getShippingFee()));
		details.setTax(String.format(Locale.US, "%.2f", order.getTax()));
		details.setSubtotal(String.format(Locale.US, "%.2f", order.getSubtotal()));

		Set<OrderPromotion> orderPromotions = order.getOrderPromotions();
		if(!orderPromotions.isEmpty()) {
			float totalDiscount = 0;
			for(OrderPromotion orderPromotion : orderPromotions) {
				totalDiscount += orderPromotion.getDiscountPrice();
			}
			details.setShippingDiscount(String.format(Locale.US, "%.2f", totalDiscount));
		}

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setDetails(details);
		amount.setTotal(String.format(Locale.US, "%.1f", order.getOrderSum()));
		
		return amount;
	}
	
	private	ShippingAddress getRecipientInformation(ShirtOrder order) {
		ShippingAddress shippingAddress = new ShippingAddress();
		
		String recipientName = order.getFirstname() + " " + order.getLastname();
		shippingAddress.setRecipientName(recipientName)
					   .setPhone(order.getPhone())
					   .setLine1(order.getAddressLine1())
					   .setLine2(order.getAddressLine2())
					   .setCity(order.getCity())
					   .setState(order.getState())
					   .setCountryCode(order.getCountry())
					   .setPostalCode(order.getZipcode());
		
		return shippingAddress;
	}
	
	private List<Transaction> getTransactionInformation(ShirtOrder order) {
		Transaction transaction = new Transaction();
		transaction.setDescription("Shirt order");	//Thông tin mô tả của giao dịch trên pay pal
		
		//Set các thông tin liên quan đến: Tiền ship, thuế, tổng số tiền trước thuế và phí ship, đơn vị tiền tệ và tổng số tiền sau thuế và phí ship
		Amount amount = getAmountDetails(order);
		transaction.setAmount(amount);
		
		ItemList itemList = new ItemList();
		
		//Set các thông tin liên quan đến: Tên người nhận, số điện thoại người nhận, địa chỉ 1, địa chỉ 2, thành phố, bang, đất nước, mã ZIP
		ShippingAddress shippingAddress = getRecipientInformation(order);
		itemList.setShippingAddress(shippingAddress);
		
		
		List<Item>  paypalItems = new ArrayList<Item>();
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		//Duyệt để xử lý thông tin của từng sản pham trong đơn hàng
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			Shirt shirt = orderDetail.getShirt();
			Integer quantity = orderDetail.getQuantity();
			
			//Set các thông tin liên quan đến áo: Đơn vị tiền tệ, tên áo, số lượng và giá
			Item paypalItem = new Item();
			paypalItem.setCurrency("USD")
					  .setName(shirt.getShirtName())
					  .setQuantity(String.valueOf(quantity))
					  .setPrice(String.format(Locale.US, "%.2f", shirt.getShirtPrice()));
			
			paypalItems.add(paypalItem);
		}
		
		itemList.setItems(paypalItems);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransaction = new ArrayList<Transaction>();
		listTransaction.add(transaction);
		
		return listTransaction;
	}
	
	//Trả về trang review_payment
	public void reviewPayment() throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		System.out.println("Payment ID: " + paymentId);
		System.out.println("Payer ID: " + payerId);
		
		if(paymentId == null || payerId == null) {
			throw new ServletException("Error in displaying payment");
		}
		
		//Connect to Paypal server
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			//Lấy thông tin giao dịch thông payment id
			Payment payment = Payment.get(apiContext, paymentId);
			
			//Lấy các thông tin để hiển thị ra view
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			request.setAttribute("payer", payerInfo);
			request.setAttribute("transaction", transaction);
			request.setAttribute("recipient", shippingAddress);
			
			String reviewPage = "frontend/review_payment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
			request.getRequestDispatcher(reviewPage).forward(request, response);
			
		} catch (PayPalRESTException | IOException e) {
			e.printStackTrace();
			throw new ServletException("Error in getting payment details from Paypal");
		}
	}
	
	//Thực hiện giao dịch paypal
	public Payment executePayment() throws PayPalRESTException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		System.out.println("Payment id:" + paymentId);
		System.out.println("Payer id:" + payerId);
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		return payment.execute(apiContext, paymentExecution);
	}
}
