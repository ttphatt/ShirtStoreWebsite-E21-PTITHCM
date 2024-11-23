package com.shirtstore.service;

import com.shirtstore.csv.CSVReaderUtility;
import com.shirtstore.dao.PromotionDAO;
import com.shirtstore.entity.Promotion;
import com.shirtstore.entity.PromotionDTO;
import com.shirtstore.entity.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PromotionServices {
    private PromotionDAO promotionDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private static final String orderPromotionId = "orderPromotionId";
    private static final String shippingPromotionId = "shippingPromotionId";

    private static final String orderPromotionTypeName = "Order Discount";
    private static final String shippingPromotionTypeName = "Shipping Discount";


    public PromotionServices(HttpServletRequest request, HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;

        promotionDAO = new PromotionDAO();
    }

    //Lấy danh sách các khuyen mai không kèm theo thông báo
    public void listPromotion() throws ServletException, IOException {
        listPromotion(null);
//        List<Integer> listUsedPromotion = promotionDAO.countUsedPromotion();
//        System.out.println(listUsedPromotion);

    }

    //Lấy danh sách các khuyến mãi kèm theo thông báo
    public void listPromotion(String message) throws ServletException, IOException {
        List<Promotion> infos = promotionDAO.listAll();
        List<Long> listUsedPromotion = promotionDAO.countUsedAllPromotion();
        List<PromotionDTO> listPromotion = new ArrayList<>();

        for (int i = 0; i < infos.size(); i++) {
            Promotion promotion = infos.get(i);
            listPromotion.add(new PromotionDTO(promotion.getDescription(), promotion.getPromotionId(), promotion.getType(), promotion.getStatus(),
                    promotion.getQuantityInStock(), promotion.getStartDate(), promotion.getEndDate(), listUsedPromotion.get(i), promotion.isDoDisplay()));
        }

//        System.out.println(listUsedPromotion);

        //Đẩy danh sách qua view bằng
        request.setAttribute("listPromotion", listPromotion);

        if(message != null) {
            request.setAttribute("message", message);
        }

        String path = "/admin/promotion_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);

        requestDispatcher.forward(request, response);
    }

    //Tạo 1 ma khuyen mai moi
    public void createPromotion() throws ServletException, IOException {
        String promotionID = request.getParameter("promotionId");
        System.out.println("first " + promotionID);

        //Kiểm tra có trùng loại khuyen mai có sẵn hay không
        Promotion existPromotion = promotionDAO.findByName(promotionID);

        //Trùng loại áo có sẵn
        if( existPromotion != null) {
            String message = "Could not create new promotion. Promotion already exists with the name: " + existPromotion;
            request.setAttribute("message", message);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");

            requestDispatcher.forward(request, response);
        }

        //Không trùng loại khuyen mãi có san
        else {
            //Tạo 1 object type mới
            Promotion newPromotion = new Promotion();

            //Lấy dữ liệu từ view và lưu 1 object promotion = newPromotion
            newPromotion = readFields(newPromotion);

            if(newPromotion ==null){
                String message = "Start date cannot be after end date";
                request.setAttribute("message", message);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
                requestDispatcher.forward(request, response);
            }else{
                //Đẩy object type mới vừa tạo xuống model để thêm vào database
                Promotion createdPromotion = promotionDAO.create(newPromotion);
                //Đẩy ra view thông báo thành công
                if(createdPromotion.getPromotionId() != null) {
                    String message = "New promotion has been created successfully";
                    //Refresh lại bằng cách liệt kê lại các khuyen mai
                    listPromotion(message);
            }
            }
        }
    }

    private Promotion readFields(Promotion promotion) throws ServletException, IOException{
        String promotionId = request.getParameter("promotionId");
        String description = request.getParameter("description");
        String promotionType = request.getParameter("type");
        String status = request.getParameter("status");
        Float max_discount = Float.parseFloat(request.getParameter("maxDiscount"));
        Integer quantity_in_stock = Integer.parseInt(request.getParameter("quantityInStock"));
        Float price_limit = Float.parseFloat(request.getParameter("priceLimit"));
        Float percent = Float.parseFloat(request.getParameter("percent"));


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            startDate = df.parse(request.getParameter("startDate"));
        }
        catch(ParseException ex) {
            ex.printStackTrace();
            throw new ServletException("Error parsing released date (format is: yyyy-MM-dd");
        }

        try {
            endDate = df.parse(request.getParameter("endDate"));
        }
        catch(ParseException ex) {
            ex.printStackTrace();
            throw new ServletException("Error parsing released date (format is: yyyy-MM-dd");
        }

        // Check if startDate is after endDate
        if (startDate.after(endDate) || startDate.equals(endDate)) {
            promotion = null;
        }
        else {
            //set fields
            promotion.setPromotionId(promotionId);
            promotion.setDescription(description);
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotion.setType(promotionType);
            promotion.setPercent(percent);
            promotion.setMaxDiscount(max_discount);
            promotion.setPriceLimit(price_limit);
            promotion.setQuantityInStock(quantity_in_stock);
            promotion.setStatus(status);
        }
        return promotion;
    }

    public void editPromotion() throws ServletException, IOException {
        //Lấy ra id của loại promotion từ view
        String promotionId = request.getParameter("promotionId");
        System.out.println(promotionId);

        //Tạo 1 object promotion để lưu dữ liệu của promotion được edit theo id vừa lấy
        Promotion promotion = promotionDAO.get(promotionId);

        //Đẩy promotion qua view
        request.setAttribute("promotion", promotion);

        String path = "promotion_form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public void updatePromotion() throws ServletException, IOException {
        String promotionId = request.getParameter("promotionId");
        System.out.println(promotionId);
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        Integer quantity_in_stock = Integer.parseInt(request.getParameter("quantityInStock"));
        Long usedPromotion = promotionDAO.countUsedPromotion(promotionId);

        if(quantity_in_stock < usedPromotion ) {
            String message = "Cannot change the quantity of promotion " + promotionId + " lower than the used one";
            listPromotion(message);
            return;
        }


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            startDate = df.parse(request.getParameter("startDate"));
        }
        catch(ParseException ex) {
            ex.printStackTrace();
            throw new ServletException("Error parsing released date (format is: yyyy-MM-dd");
        }
        try {
            endDate = df.parse(request.getParameter("endDate"));
        }
        catch(ParseException ex) {
            ex.printStackTrace();
            throw new ServletException("Error parsing released date (format is: yyyy-MM-dd");
        }

        // Check if startDate is after endDate
        if (startDate.after(endDate) || startDate.equals(endDate)) {
            String message = "Start date cannot be after end date";
            listPromotion(message);
            return;
        }

        Promotion existedPromotion = promotionDAO.get(promotionId);

        existedPromotion.setDescription(description);
        existedPromotion.setStartDate(startDate);
        existedPromotion.setEndDate(endDate);
        existedPromotion.setStatus(status);
        existedPromotion.setQuantityInStock(quantity_in_stock);

        promotionDAO.update(existedPromotion);

        String message = "The promotion has been updated successfully";
        //Refresh lại bằng cách liệt kê lại các khuyen mai
        listPromotion(message);

    }

    public boolean checkExistPromotion(Promotion promotion, String promotionType){
        if (promotion != null && promotion.getType().equals(promotionType)) {
            return true;
        }
        return false;
    }

    public boolean checkValidDate(Promotion promotion) throws ParseException {
        Date current_date = new Date();
        boolean isBetween = !current_date.before(promotion.getStartDate()) && !current_date.after(promotion.getEndDate());

        return isBetween;
    }

    public boolean checkPriceLimit(Promotion promotion) {
        float subTotal = Float.parseFloat(request.getParameter("subTotal"));

        if(subTotal >= promotion.getPriceLimit()) {
            return true;
        }
        return false;
    }

    //Modified
    public boolean checkQuantityInStock(Promotion promotion) {
        long usedPromotion = promotionDAO.countUsedPromotion(promotion.getPromotionId());
        System.out.println(usedPromotion);
        if(usedPromotion < promotion.getQuantityInStock()) {
            return true;
        }
        return false;
    }

    public boolean checkStatus(Promotion promotion) {
        if(promotion.getStatus().equals("Active")){
            return true;
        }
        return false;
    }

    public int checkValid(Promotion promotion, String promotionType) throws ParseException {
        if(!checkExistPromotion(promotion, promotionType)) return 1;
        if(!checkValidDate(promotion))   return 2;
        if(!checkPriceLimit(promotion))   return 3;
        if(!checkQuantityInStock(promotion))   return 4;
        if(!checkStatus(promotion))   return 5;

        return 0;
    }

    public String checkValidation(int validation) throws IOException {
        String message = "";
        switch (validation){
            case 1:
                message = CSVReaderUtility.loadCSVData().get("INVALID_PROMOTION_ID");
                break;
            case 2:
                message = CSVReaderUtility.loadCSVData().get("INVALID_PROMOTION_DATE");
                break;
            case 3:
                message = CSVReaderUtility.loadCSVData().get("INVALID_PROMOTION_PRICE_LIMIT");
                break;
            case 4:
                message = CSVReaderUtility.loadCSVData().get("INVALID_PROMOTION_QUANTITY");
                break;
            case 5:
                message = CSVReaderUtility.loadCSVData().get("INVALID_PROMOTION_STATUS");
                break;
            case 0:
                message = null;
                break;
        }
        return message;
    }

    //Modified totalPrice -> subTotal
    public float getPriceDiscount(Promotion promotion) {
        float percent = (float) (promotion.getPercent() * 0.01);
        float priceDiscount = 0;
        float maxDiscount = promotion.getMaxDiscount();

        if(promotion.getType().equals("Order Discount"))    priceDiscount = Float.parseFloat(request.getParameter("subTotal")) * percent;
        else    priceDiscount = (float) request.getSession().getAttribute("shippingFee") * percent;

        if(priceDiscount >= maxDiscount) {
            priceDiscount = maxDiscount;
        }

        return priceDiscount;
    }

    public float getPriceDiscount2(Promotion promotion) {
        float percent = (float) (promotion.getPercent() * 0.01);
        float priceDiscount = 0;
        float maxDiscount = promotion.getMaxDiscount();

        if(promotion.getType().equals(orderPromotionTypeName))    priceDiscount = Float.parseFloat(request.getParameter("subTotal")) * percent;
        else    priceDiscount = (float) request.getSession().getAttribute("shippingFee") * percent;

        if(priceDiscount >= maxDiscount) {
            priceDiscount = maxDiscount;
        }

        return priceDiscount;
    }

    public void promotionProcessing(Promotion promotion, String promotionIdName) throws IOException {
        float priceDiscount = getPriceDiscount(promotion);
        float newTotalPrice = Float.parseFloat(request.getParameter("totalPrice")) - priceDiscount;;

        if(promotion.getType().equals("Order Discount")){
            request.getSession().setAttribute("priceDiscount", priceDiscount);
        }
        else{
            request.getSession().setAttribute("shippingDiscount", priceDiscount);
        }

        request.getSession().setAttribute("totalPrice", newTotalPrice);
        request.getSession().setAttribute(promotionIdName, promotion.getPromotionId());
    }

    public List<Object> promotionProcessing2(Promotion promotion, String promotionIdName) throws IOException {
        float priceDiscount = getPriceDiscount2(promotion);
        float newTotalPrice = (float) request.getSession().getAttribute("totalPrice") - priceDiscount;
        List<Object> res = new ArrayList<>();

        if(promotion.getType().equals(orderPromotionTypeName)){
            res.add(orderPromotionTypeName);
            request.getSession().setAttribute("orderDiscount", priceDiscount);
        }
        else{
            res.add(shippingPromotionTypeName);
            request.getSession().setAttribute("shippingDiscount", priceDiscount);
        }


        request.getSession().setAttribute(promotionIdName, promotion.getPromotionId());

        res.add(priceDiscount);
        res.add(promotion.getPromotionId());
        res.add(newTotalPrice);

        return res;
    }

    public void redirectToCheckoutPage(String messageName, String message) throws ServletException, IOException {
        CommonUtility.generateCountriesList(request);
        String path = "frontend/checkout.jsp";

        if(message != null) request.setAttribute(messageName, message);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public void responseToCheckOutPage(boolean isPromotionValid, String message, List<Object> promoInfo) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        if(isPromotionValid) {
            out.print("{\"valid\": " + true + ", \"newTotalPrice\": " + promoInfo.get(3) + ", \"discountPrice\": " + promoInfo.get(1) + "}");
        }
        else{
            out.print("{\"valid\": " + false + ", \"message\": \"" + message + "\"}");
        }

        out.flush();
        out.close();
    }

    public Promotion getPromotionFromView(String promotionIdName) {
        String promotionId = request.getParameter(promotionIdName);
        Promotion promotion = promotionDAO.get(promotionId);

        return promotion;
    }

    public void checkOrderPromotion(String promotionType) throws ParseException, IOException, ServletException {
        String promotionIdName = "promotionId";
        Promotion promotion = getPromotionFromView(promotionIdName);
        int validation = checkValid(promotion, promotionType);
        String message = checkValidation(validation);

        if(message == null) promotionProcessing(promotion, promotionIdName);
        redirectToCheckoutPage("message_order_promotion", message);
    }

    public void checkOrderPromotion2() throws ParseException, IOException {
        Promotion promotion = getPromotionFromView(orderPromotionId);
        int validation = checkValid(promotion, orderPromotionTypeName);
        String message = checkValidation(validation);

        if(message == null){
            List<Object> promoInfo = promotionProcessing2(promotion, orderPromotionId);
            responseToCheckOutPage(true, null, promoInfo);
        }
        else{
            responseToCheckOutPage(false, message, null);
        }
    }

    public void checkShippingPromotion(String promotionType) throws ParseException, IOException, ServletException {
        String promotionIdName = "shippingPromotionId";
        Promotion promotion = getPromotionFromView(promotionIdName);
        int validation = checkValid(promotion, promotionType);
        String message = checkValidation(validation);

        if(message == null) promotionProcessing(promotion, promotionIdName);
        redirectToCheckoutPage("message_shipping_promotion", message);
    }

    public void checkShippingPromotion2() throws ParseException, IOException, ServletException {
        Promotion promotion = getPromotionFromView(shippingPromotionId);
        int validation = checkValid(promotion, shippingPromotionTypeName);
        String message = checkValidation(validation);

        if(message == null){
            List<Object> promoInfo = promotionProcessing2(promotion, shippingPromotionId);
            responseToCheckOutPage(true, null, promoInfo);
        }
        else{
            responseToCheckOutPage(false, message, null);
        }
    }

    public void checkPromotion(String promotionType) throws ParseException, IOException, ServletException {
        if(promotionType.equals("Order Discount"))  checkOrderPromotion(promotionType);
        else  checkShippingPromotion(promotionType);
    }

    public void checkPromotion2(String promotionType) throws ParseException, IOException, ServletException {
        if(promotionType.equals(orderPromotionTypeName))  checkOrderPromotion2();
        else checkShippingPromotion2();
    }

    //Update the display status of promotion
    public void updatePromotionDisplay() {
        String promotionId = request.getParameter("promotionId");
        boolean doDisplay = Boolean.parseBoolean(request.getParameter("doDisplay"));
        Promotion promotion = promotionDAO.get(promotionId);

        promotion.setDoDisplay(doDisplay);
        promotionDAO.update(promotion);
    }

}
