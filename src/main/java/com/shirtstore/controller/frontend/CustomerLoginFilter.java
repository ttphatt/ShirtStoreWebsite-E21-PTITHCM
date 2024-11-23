package com.shoestore.controller.frontend;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;


@WebFilter("/*")
public class CustomerLoginFilter extends HttpFilter implements Filter {
    private static final String[] loginRequiredURLs = {
    		"/view_profile", "/edit_profile", "/update_profile"	, "/write_rate",
    		"/checkout", "/place_order", "/view_orders", "/show_order_detail"
    };
	
    public CustomerLoginFilter() {
        super();    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession(false);
		
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if(path.startsWith("/admin/")) {
			chain.doFilter(request, response);
			return;
		}
		
		String requestURL = httpRequest.getRequestURL().toString();
		boolean loggedIn = session != null && session.getAttribute("loggedCustomer") != null;
		
		System.out.println("Path: " + path);
		System.out.println("Logged in: " + loggedIn);
		
		if(!loggedIn && isLoginRequired(requestURL)) {
			String queryString = httpRequest.getQueryString();
			String redirectURL = requestURL;
			
			System.out.println("Made 1");
			
			if(queryString != null) {
				redirectURL = redirectURL.concat("?").concat(queryString);
			}
			
			session.setAttribute("redirectURL", redirectURL);
			
			String loginPath = "frontend/login.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginPath);
			requestDispatcher.forward(request, response);
		}
		else {
			chain.doFilter(request, response);	
			System.out.println("Made 2");
		}
	}

	private boolean isLoginRequired(String requestURL) {
		for(String loginRequiredURL : loginRequiredURLs) {
			if(requestURL.contains(loginRequiredURL)) {
				return true;
			}
		}
		return false;
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
