package com.shirtstore.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shirtstore.controller.frontend.cart.ShoppingCart;
import com.shirtstore.controller.frontend.customer.CustomerFacebookLogin;
import com.shirtstore.controller.frontend.customer.CustomerGoogleLogin;
import com.shirtstore.dao.*;
import com.shirtstore.entity.Cart;
import com.shirtstore.entity.CartDTO;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.Shirt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;

public class CustomerServices {
	private CustomerDAO customerDAO;
	private ShirtDAO shirtDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private CartDAO cartDAO;
	private CartService cartService;

	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;

		this.customerDAO = new CustomerDAO();
		this.cartDAO = new CartDAO();
		this.shirtDAO = new ShirtDAO();
		this.cartService = new CartService(request);
	}


	public void listCustomer() throws ServletException, IOException {
		listCustomer(null);
	}

	public void listCustomer(String message) throws ServletException, IOException {
		List<Customer> listCustomer = customerDAO.listAll();

		if (message != null) {
			request.setAttribute("message", message);
		}

		request.setAttribute("listCustomer", listCustomer);

		String path = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");

		Customer existCustomer = customerDAO.findByEmail(email);

		if (existCustomer != null) {
			String message = "The email: " + email + " already existed";
			listCustomer(message);
		} else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			customerDAO.create(newCustomer);

			String message = "A new customer has been created successfully";
			listCustomer(message);
		}

	}

	private void updateCustomerFieldsFromForm(Customer customer) {
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phone");
		String addressLine1 = request.getParameter("address1");
		String addressLine2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String country = request.getParameter("country");


		if (email != null && !email.equals("")) {
			customer.setEmail(email);
		}


		customer.setFirstname(firstname);
		customer.setLastname(lastname);

		if (password != null && !password.equals("")) {
			// ma hoa mat khau khach hang
			String passwordHash = new HashSha_256Service().hashWithSHA256(password);
			customer.setPassword(passwordHash);
		}
		customer.setPhoneNumber(phoneNumber);
		customer.setAddressLine1(addressLine1);
		customer.setAddressLine2(addressLine2);
		customer.setCity(city);
		customer.setState(state);
		customer.setZipcode(zip);
		customer.setCountry(country);

		sendEmailToCustomer(email, firstname + " " + lastname);
	}

	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));

		Customer customer = customerDAO.get(customerId);

		request.setAttribute("customer", customer);
		CommonUtility.generateCountriesList(request);

		String path = "customer_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}


	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");

		Customer customerByEmail = customerDAO.findByEmail(email);
		String message = null;

		if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Could not update the customer ID: " + customerId + " since there is another user with the email address: " + email;

		} else {
			Customer customerById = customerDAO.get(customerId);
			updateCustomerFieldsFromForm(customerById);

			customerDAO.update(customerById);

			message = "The customer has been updated successfully";
		}

		listCustomer(message);
	}

	public void updateCustomerFromGoogle(Customer customer) throws ServletException, IOException {
		// Cập nhật thông tin khách hàng trong cơ sở dữ liệu
		customerDAO.update(customer);

		// Cập nhật thông tin khách hàng trong phiên
		HttpSession session = request.getSession();
		session.setAttribute("loggedCustomer", customer);

		// Chuyển hướng đến trang hồ sơ
		showCustomerProfile();
	}

	public void completeRegistration() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		Customer customer = customerDAO.get(customerId);
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);

		// Cập nhật thông tin khách hàng trong phiên
		HttpSession session = request.getSession();
		session.setAttribute("loggedCustomer", customer);

		// Chuyển hướng đến trang hồ sơ
		showCustomerProfile();
	}


	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));

		RateDAO rateDAO = new RateDAO();
		long numberOfRate = rateDAO.countByCustomer(customerId);
		System.out.println("Number of rates: " + numberOfRate);


		OrderDAO orderDAO = new OrderDAO();
		long numberOfOrder = orderDAO.countByCustomer(customerId);
		System.out.println("Number of orders: " + numberOfOrder);

		String message = "";

		if (numberOfRate > 0) {
			message = "Unable to delete customer with id: " + customerId + ". Because this customer has already rated some shirts";
		} else if (numberOfOrder > 0) {
			message = "Unable to delete customer with id: " + customerId + ". Because this customer has ordered some shirts";
		} else {
			customerDAO.delete(customerId);
			message = "The customer with id: " + customerId + " has been deleted successfully";
		}
		listCustomer(message);

	}

	public void registerAsCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		String message = "";
		Customer existCustomer = customerDAO.findByEmail(email);


		if (existCustomer != null) {
			message = "Could not sign up. The email: " + email + " is already registered by another customer";
		} else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			Customer createdCustomer = customerDAO.create(newCustomer);
			message = "You have signed up successfully.\n" + "<a href='login'>Click here</a> to login";
		}


		String path = "frontend/message.jsp";
		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}


	public void showLogin() throws ServletException, IOException {
		String loginPage = "frontend/login.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginPage);
		requestDispatcher.forward(request, response);
	}


	public void doLogin() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordHash = new HashSha_256Service().hashWithSHA256(password);
		String message = "";

		Customer customer = customerDAO.checkLogin(email, passwordHash);

		if (customer != null) {
			HttpSession session = request.getSession();
			request.getSession().setAttribute("loggedCustomer", customer);
			Object objRedirectURL = session.getAttribute("redirectURL");

			cartService.saveCart(customer.getCustomerId());

			if (objRedirectURL != null) {
				String redirectURL = (String) objRedirectURL;
				session.removeAttribute("redirectURL");
				response.sendRedirect(redirectURL);
			} else {
				showCustomerProfile();
			}
		} else {
			message = "Login failed. Please check your email and password again";
			request.setAttribute("message", message);
			showLogin();
		}
	}

	private void sendEmailToCustomer(String email, String name) {
		if (email == null || email.isEmpty()) {
			System.out.println("Email is null or empty, cannot send email.");
			return;
		}
		//lấy email hiện tại ra
		String title = "Register Confirmation from PHK SHIRT STORE";
		String body = formEmail(name, email);
		//gửi mail
		MailServices.SendMail(email, title, body);
	}

	public String formEmail(String name, String email) {
		String form = "PHK SHIRT STORE\r\n"
				+ "\r\n"
				+ "Registration Confirmation and Thank You\r\n"
				+ "\r\n"
				+ "Dear " + name + ",\r\n"
				+ "\r\n"
				+ "Thank you for registering with PHK SHIRT STORE. We are thrilled to have you join our community.\r\n"
				+ "\r\n"
				+ "Registration Details:\r\n"
				+ "\r\n"
				+ "Name: " + name + "\r\n"
				+ "Email: " + email + "\r\n"
				+ "Registration Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\r\n"
				+ "We are committed to providing you with the best experience possible. As a registered member, you will enjoy exclusive benefits such as:\r\n"
				+ "\r\n"
				+ "Early access to new products and promotions\r\n"
				+ "Special discounts and offers\r\n"
				+ "Personalized recommendations and updates\r\n"
				+ "Next Steps:\r\n"
				+ "\r\n"
				+ "Stay tuned for our upcoming newsletters and updates.\r\n"
				+ "Explore our website to discover our latest products and services.\r\n"
				+ "Customer Support:\r\n"
				+ "If you have any questions or need assistance, our customer service team is here to help. You can reach us at phkshirtstore@gmail.com or call us at 0123456789.\r\n"
				+ "\r\n"
				+ "Thank you once again for registering with us. We look forward to serving you and providing you with the best products and services.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "\r\n"
				+ "PHK SHIRT STORE\r\n"
				+ "\r\n"
				+ "";
		return form;
	}

	public void showCustomerProfile() throws ServletException, IOException {
		String profilePath = "frontend/customer_profile.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(profilePath);
		requestDispatcher.forward(request, response);
	}


	public void showCustomerProfileFormEdit() throws ServletException, IOException {
		CommonUtility.generateCountriesList(request);
		String profilePath = "frontend/edit_profile.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(profilePath);
		requestDispatcher.forward(request, response);
	}


	public void updateCustomerProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);

		showCustomerProfile();
	}

	public void newCustomer() throws ServletException, IOException {
		CommonUtility.generateCountriesList(request);

		String customerForm = "customer_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(customerForm);
		requestDispatcher.forward(request, response);
	}

	public void showCustomerRegistrationForm() throws ServletException, IOException {
		CommonUtility.generateCountriesList(request);

		String path = "frontend/customer_registration.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void loginGoogle() throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String code = request.getParameter("code");
		CustomerGoogleLogin gg = new CustomerGoogleLogin();
		try {
			String accessToken = gg.getToken(code);
			System.out.println("Access Token: " + accessToken);
			if (accessToken != null) {
				Customer customer = CustomerGoogleLogin.getUserInfo(accessToken);
				System.out.println("Customer Info: " + customer.getEmail());

				// Kiểm tra xem khách hàng đã tồn tại trong cơ sở dữ liệu chưa
				Customer existingCustomer = customerDAO.findByEmail(customer.getEmail());
				if (existingCustomer == null) {
					// Nếu khách hàng chưa tồn tại, tạo mới
					customerDAO.create(customer);
					request.setAttribute("customer", customer);
					CommonUtility.generateCountriesList(request);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontend/complete_registration.jsp");
					requestDispatcher.forward(request, response);
				} else {
					// Nếu khách hàng đã tồn tại, đăng nhập và hiển thị hồ sơ
					HttpSession session = request.getSession();
					session.setAttribute("loggedCustomer", existingCustomer);

					// Chép cart mới của khách hàng.
					cartService.saveCart(existingCustomer.getCustomerId());
					showCustomerProfile();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loginFacebook() throws ServletException, IOException {
		String code = request.getParameter("code");
		CustomerFacebookLogin fb = new CustomerFacebookLogin();
		try {
			String accessToken = fb.getToken(code);
			System.out.println("Access Token: " + accessToken);
			if (accessToken != null) {
				Customer customer = CustomerFacebookLogin.getUserInfo(accessToken);
				System.out.println("Customer Info: " + customer.getEmail());
				// Kiểm tra xem khách hàng đã tồn tại trong cơ sở dữ liệu chưa
				Customer existingCustomer = customerDAO.findByEmail(customer.getEmail());
				if (existingCustomer == null) {
					// Nếu khách hàng chưa tồn tại, tạo mới
					customerDAO.create(customer);
					request.setAttribute("customer", customer);
					CommonUtility.generateCountriesList(request);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("frontend/complete_registration.jsp");
					requestDispatcher.forward(request, response);
				} else {
					// Nếu khách hàng đã tồn tại, đăng nhập và hiển thị hồ sơ
					HttpSession session = request.getSession();
					session.setAttribute("loggedCustomer", existingCustomer);

					// Chép cart mới của khách hàng.
					cartService.saveCart(existingCustomer.getCustomerId());
					showCustomerProfile();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String resetCustomerPassword(String email) throws ServletException, IOException {
		Customer existingCustomer = customerDAO.findByEmail(email);
		String randomPassword = RandomStringUtils.randomAlphanumeric(10);
		String passwordHass = new HashSha_256Service().hashWithSHA256(randomPassword);
		existingCustomer.setPassword(passwordHass);
		customerDAO.update(existingCustomer);
		return randomPassword;
    }

}
