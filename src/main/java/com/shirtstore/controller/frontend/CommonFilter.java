package com.shirtstore.controller.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.shirtstore.dao.TypeDAO;
import com.shirtstore.entity.Type;


@WebFilter("/*")
public class CommonFilter extends HttpFilter implements Filter {
   
	private final TypeDAO typeDAO;
	
	public CommonFilter() {
		typeDAO = new TypeDAO();
    }


	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if(!path.startsWith("/admin/")) {
			List<Type>listType = typeDAO.listAll();
			request.setAttribute("listType", listType);
			System.out.println("Common Filter ");
		}	
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
