package com.shirtstore.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.dao.UserDAO;
import com.shirtstore.entity.Users;

public class UserServices {
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	public UserServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		userDAO = new UserDAO();
	}
	
	public void listUser() throws ServletException, IOException {
		listUser(null);
	}
	
	//Lấy ra danh sách các user có trong database và đẩy lên view
	public void listUser(String message) throws ServletException, IOException  {
		//Xuống model lấy danh sách các user
		List<Users>listUsers = userDAO.listAll();
		
		//Đẩy danh sách các user qua view
		request.setAttribute("listUsers", listUsers);
		
		//Hiển thị thông báo bên view
		request.setAttribute("message", message);
		
		String path = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		
		requestDispatcher.forward(request, response);
	}
	
	//Thêm 1 user mới vào database
	public void createUser() throws ServletException, IOException {
		//Lấy ra các thông tin: email, họ và tên và password từ view
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		String role = "staff";

		//Kiểm tra user này có trong database chưa bằng email (Email: Unique Key)
		Users existUser = userDAO.findByEmail(email);
		
		//Trường hợp đã tồn tại user
		if(existUser != null) {
			String message = "Could not create user. A user with email " + email + " already exists";
			request.setAttribute("message", message);
			
			//Điều hướng đến view cần hiển thị
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			
			//Đi đến view đó
			requestDispatcher.forward(request, response);
		}
		//Trường hợp chưa có user trong database
		else {
			//Tạo 1 object user mới
			String passwordHash = new HashSha_256Service().hashWithSHA256(password);
			Users user = new Users(email, fullName, passwordHash, role);
			
			//Đẩy object user xuống Model để thêm vào trong database
			userDAO.create(user);
			listUser("Create a new user successfully");
		}
	}
	
	//Điều hướng đến trang edit
	public void editUser() throws ServletException, IOException {
		//Lấy id của user trên view 
		int userId = Integer.parseInt(request.getParameter("id"));
		
		//Lấy thông tin cùa user bằng id để đẩy qua trang edit
		Users user = userDAO.get(userId);
		
		String path = "user_form.jsp";
		
		//Đẩy thông tin của user qua view
		request.setAttribute("user", user);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}

	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		String passwordHash = new HashSha_256Service().hashWithSHA256(password);
			
		Users userById = userDAO.get(userId);
		Users userByEmail = userDAO.findByEmail(email);
		
		//Kiểm tra xem email có tồn tại và thuộc quyền sở hữu của user khác không
		//Cũng là kiểm tra trường hợp user edit email mà email này đã có user khác sử dụng
		if(userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "Could not update the user. User with email " + email + " already exists";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
		}
		else {
			//Tạo 1 object user mới để lưu dữ liệu vừa edit
			Users user = new Users(userId, email, fullName, passwordHash, userByEmail.getRole());
			
			//Đẩy object xuống Model để thêm vào database
			userDAO.update(user);
			
			String message = "User has been updated successfully";
			
			//Refresh lại danh sách user bằng cách liệt kê lại danh sách các user
			listUser(message);
		}
	}

	public void deleteUser() throws ServletException, IOException {
		//Lấy id của user từ view xuống
		int userId = Integer.parseInt(request.getParameter("id"));
		
		//Xóa user trong database dựa theo id
		userDAO.delete(userId);
		
		String message = "User has been deleted successfully";
		listUser(message);
	}
	
	//Kiểm tra đăng nhập của user
	public void login() throws ServletException, IOException{
		//Lấy ra các thông tin: user và password từ view
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordHash = new HashSha_256Service().hashWithSHA256(password);
		//Thực hiện kiểm tra xem user này có tài khoản trong database hay chưa
		//Cũng là để kiểm tra tài khoản và mật khẩu khi đăng nhập vào trang admin
		boolean loginRes = userDAO.checkLogin(email, passwordHash);
		
		//Đăng nhập vào trang admin thành công
		if(loginRes) {
			//Tạo 1 object user từ việc tìm user theo email 
			Users user = userDAO.findByEmail(email);
			
			request.getSession().setAttribute("userFullName", user.getFullName());

			request.getSession().setAttribute("userRole", user.getRole());

			request.getSession().setAttribute("userEmail", email);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/");
			requestDispatcher.forward(request, response);
		}
		
		//Đăng nhập vào trang admin không thành công
		else {
			String message = "Login failed, please check your email and password again";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
			
		}
	}
}
