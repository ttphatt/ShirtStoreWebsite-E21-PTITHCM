package com.shirtstore.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.shirtstore.dao.*;
import com.shirtstore.entity.Shirt;
import com.shirtstore.entity.Size;
import com.shirtstore.entity.Type;
import com.shirtstore.entity.Warehouse;

public class ShirtServices {
	private ShirtDAO shirtDAO;
	private TypeDAO typeDAO;
	private WarehouseDAO warehouseDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final String UPLOAD_DIRECTORY = "images/shirt";
	
	public ShirtServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		
		shirtDAO = new ShirtDAO();
		typeDAO = new TypeDAO();
		warehouseDAO = new WarehouseDAO();
	}
	
	//Back-end modules
	
	//Liệt kê danh sách các loại áo không kèm theo thông báo
	public void listShirts() throws ServletException, IOException {
		listShirts(null);
	}
	
	//Liệt kê danh sách các loại áo có kèm theo thông báo
	public void listShirts(String message) throws ServletException, IOException {
		List<Shirt> listShirts = shirtDAO.listAll();
		request.setAttribute("listShirts", listShirts);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String path = "shirt_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
	
	//Show ra form để thêm áo mới
	public void showShirtNewForm() throws ServletException, IOException {
		//Lấy ra danh sách các loại áo
		List<Type> listType = typeDAO.listAll();
		
		//Đẩy danh sách các loại áo qua view
		request.setAttribute("listType", listType);
		
		String path = "shirt_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}	

	public void createShirt() throws ServletException, IOException {
		String shirtName = request.getParameter("shirtName");
		Shirt existShirt = shirtDAO.findByName(shirtName);
		
		//Kiểm tra trùng tên áo vì tên áo là unique key
		if(existShirt != null) {
			String message = "Could not create new shirt since there is already a a shirt with the name: " + shirtName;
			listShirts(message);
			return;
		} 
		
		Shirt newShirt = new Shirt();
		
		//Lấy dữ liệu từ view và lưu 1 object shirt = newShirt
		readFields(newShirt);
		
		//Đẩy object shirt vừa tạo xuống model để thêm vào database
		Shirt createdShirt = shirtDAO.create(newShirt);
		
		//Đẩy ra view thông báo thành công
		if(createdShirt.getShirtId() > 0) {
			// Tạo thông tin về size của shirt
			List<Size> sizeList = new ArrayList<>();
			String[] arrSize = {"S", "L", "M", "XL", "XXL"};
			for (String s : arrSize) {
				sizeList.add(new Size(createdShirt.getShirtId(), s, 0));
			}

			new JPADAO<>().createSize(sizeList);

			// Tạo thông tin của shirt trong warehouse
			Warehouse warehouse = new Warehouse(createdShirt.getShirtId(), 0, createdShirt.getReleasedDate(), createdShirt.getReleasedDate());
			warehouseDAO.create(warehouse);
			String message = "A new shirt has been created successfully";
			
			//Refresh lại bằng cách liệt kê lại các đôi áo
			listShirts(message);
		}
	}

	public void editShirt() throws ServletException, IOException {
		//Đổ dữ liệu vào combo box Type
		List<Type> listType = typeDAO.listAll();
		request.setAttribute("listType", listType);
		
		Integer shirtId = Integer.parseInt(request.getParameter("id"));
		Shirt shirt = shirtDAO.get(shirtId);
		
		//Đẩy ra view object shirt đã có dữ liệu để edit
		request.setAttribute("shirt", shirt);
		
		String path = "shirt_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
	
	//Đọc dữ liệu từ view và lưu vào object shirt (tham số)
	public void readFields(Shirt shirt) throws ServletException, IOException {
		Integer typeId = Integer.parseInt(request.getParameter("type"));
		String shirtName = request.getParameter("shirtName");
		String brand = request.getParameter("brand");
		String description = request.getParameter("description");
		Float shirtPrice = Float.parseFloat(request.getParameter("shirtPrice"));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date releasedDate;

		try {
			releasedDate = df.parse(request.getParameter("releasedDate"));
		}
		catch(ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing released date (format is: yyyy-MM-dd");
		}


		Type type = typeDAO.get(typeId);

		shirt.setType(type);
		shirt.setShirtName(shirtName);
		shirt.setBrand(brand);
		shirt.setDescription(description);
		shirt.setShirtPrice(shirtPrice);
		shirt.setReleasedDate(releasedDate);

		String imageUrl = imageProcessing();
		shirt.setShirtImage(imageUrl);
	}

	public String fileNameProcessing(Part part) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
		String formattedDate = dateTimeFormatter.format(LocalDateTime.now());
		return formattedDate + "-" + getFileName(part);
	}

	public String imageProcessing() throws ServletException, IOException {
		String applicationPath = request.getServletContext().getRealPath("");
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIRECTORY;

		File uploadDir = new File(uploadFilePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}

		Part part = request.getPart("shirtImage");
		String fileName = fileNameProcessing(part);
		File file = new File(uploadFilePath, fileName);
		Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

		part.write(uploadFilePath + File.separator + fileName);

		String imageUrl= request.getContextPath() + "/" + UPLOAD_DIRECTORY + "/" + fileName;
		return imageUrl;
	}

	private String getFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		System.out.println("Content disposition" + contentDisposition);
		String[] tokens = contentDisposition.split(";");
		System.out.println("tokens" + tokens);

		for(String token : tokens){
			if(token.trim().startsWith("filename")){
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}

	//Cập nhật dữ liệu của shirt
	public void updateShirt() throws ServletException, IOException {
		Integer shirtId = Integer.parseInt(request.getParameter("shirtId"));
		String shirtName = request.getParameter("shirtName");
		
		Shirt existShirt = shirtDAO.get(shirtId);
		Shirt shirtByName = shirtDAO.findByName(shirtName);
		
		//Tồn tại 1 chiếc áo khác cùng tên
		if(shirtByName != null && !existShirt.equals(shirtByName)) {
			String message = "Unable to update this shirt because there is another shirt has the name: " + shirtName;
			listShirts(message);
			return;
		}
		
		//Đọc dữ liệu từ view vào object existShirt
		readFields(existShirt);
		
		//Cập nhật dữ liệu xuống database
		shirtDAO.update(existShirt);
		
		String message = "The pair of shirt has been updated successfully";
		listShirts(message);
	}

	public void deleteShirt() throws ServletException, IOException {
		Integer shirtId = Integer.parseInt(request.getParameter("id"));
		
		RateDAO rateDAO = new RateDAO();
		long numberOfRate = rateDAO.countByShirt(shirtId);
		
		long numberOfOrder = shirtDAO.countByOrderDetail(shirtId);
		
		String message = "";
		
		//Nếu tồn tại đánh giá của áo chọn để xóa -> Không xóa
		if(numberOfRate > 0) {
			message = "Unable to delete the shirt with id " + shirtId + ". Because it has been rated";
		}
		
		//Nếu tồn tại đơn hàng của áo chọn để xóa -> Không xóa
		else if(numberOfOrder > 0) {
			message = "Unable to delete the shirt with id " + shirtId + ". Because it has been ordered";
		}
		else {
			shirtDAO.delete(shirtId);
			message = "The shirt with id: " + shirtId + " has been deleted successfully";
		}
		listShirts(message);
	}
	
	//Front-end modules:
	//Liệt kê áo theo loại
	public void listShirtsByType() throws ServletException, IOException {
		int typeId = Integer.parseInt(request.getParameter("id"));
		List<Shirt> listShirts = shirtDAO.listByType(typeId);
		Type type = typeDAO.get(typeId);

		request.setAttribute("type", type);
		request.setAttribute("listShirts", listShirts);

		String path = "frontend/shirts_list_by_type.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
	
	//Xem chi tiết 1 cái áo
	public void viewShirtDetail() throws ServletException, IOException {
		int shirtId = Integer.parseInt(request.getParameter("id"));
		Shirt shirt = shirtDAO.get(shirtId);

		request.setAttribute("shirt", shirt);
	
		String path = "frontend/shirt_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
	
	//Tìm kiếm áo theo từ khóa
	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Shirt>resList = null;
		
		if(keyword.equals("")) {
			resList = shirtDAO.listAll();
		}
		else {
			resList = shirtDAO.search(keyword);
		}
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", resList);
		
		String path = "frontend/search_result.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
}
