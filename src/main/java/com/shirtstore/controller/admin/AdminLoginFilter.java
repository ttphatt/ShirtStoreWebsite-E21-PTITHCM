package com.shirtstore.controller.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter("/admin/*")
public class AdminLoginFilter extends HttpServlet implements Filter {

    public AdminLoginFilter() {
        
    }


	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		boolean loggedIn = session != null && session.getAttribute("userEmail") != null;
		String loginURI = httpRequest.getContextPath() + "/admin/login";
		
		
		System.out.println(session != null);
		System.out.println(session.getAttribute("userEmail") != null);
		System.out.println("First string: " + loggedIn);
		System.out.println("Second string: " + loginURI);
		System.out.println("Third string: " + httpRequest.getRequestURI());
		
		boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
		boolean loginPage = httpRequest.getRequestURI().endsWith("login.jsp");
		
		//Kiểm tra xem user có đăng nhập hay chưa
		if(loggedIn && (loginRequest || loginPage)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/");
			requestDispatcher.forward(request, response);
			System.out.println("Reach 1");
		}
		
		else if(loggedIn || loginRequest) {
			chain.doFilter(request, response);
			System.out.println("Reach 2");
		}
		//User chưa đang nhập thì điều hướng sang trang login
		else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/login.jsp");
			requestDispatcher.forward(request, response);
			System.out.println("Reach 3");
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
