package com.shirtstore.controller.admin.shirt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shirtstore.csv.CSVReaderUtility;
import com.shirtstore.service.ShirtServices;


@WebServlet("/admin/update_shirt")
@MultipartConfig(
		fileSizeThreshold = 1024 * 10,	//10 KB
		maxFileSize = 1024 * 300,		//300 KB
		maxRequestSize = 1024 * 1024 	//1 MB
)
public class UpdateShirtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateShirtServlet() {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ShirtServices shirtServices = new ShirtServices(request, response);
			shirtServices.updateShirt();
		}
		catch (Exception e){
			String message = CSVReaderUtility.loadCSVData().get("OVER_SIZE_IMAGE");
			String path = "message.jsp";

			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
			requestDispatcher.forward(request, response);
		}
	}

}
