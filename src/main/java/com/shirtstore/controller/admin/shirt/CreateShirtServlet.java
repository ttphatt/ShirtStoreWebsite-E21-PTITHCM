package com.shirtstore.controller.admin.shirt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.csv.CSVReaderUtility;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.service.ShirtServices;

@MultipartConfig(
		fileSizeThreshold = 1024 * 10,	//10 KB
		maxFileSize = 1024 * 300,		//300 KB
		maxRequestSize = 1024 * 1024 	//1 MB
)
@WebServlet("/admin/create_shirt")
public class CreateShirtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public CreateShirtServlet() {
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShirtServices shirtServices = new ShirtServices(request, response);
		ShirtDAO shirtDAO = new ShirtDAO();
		String shirtName = request.getParameter("shirtName");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		if(shirtDAO.findByName(shirtName) == null){
			out.print("{\"valid\": " + true + "}");
			shirtServices.createShirt();
		}else{
			out.print("{\"valid\": " + false + "}");
		}

		out.flush();
		out.close();
	}

}
