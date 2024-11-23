package com.shirtstore.controller.frontend.customer;

import java.io.IOException;

import com.shirtstore.service.HashSha_256Service;
import constant.Iconstant;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Content;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.GoogleUser;

public class CustomerGoogleLogin {
    public String getToken(String code) throws ClientProtocolException, IOException {

        Content content = Request.Post(Iconstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", Iconstant.GOOGLE_CLIENT_ID)
                                .add("client_secret", Iconstant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", Iconstant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", Iconstant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute()
                .returnContent();

        String responseString = content.asString();
        System.out.println("Response: " + responseString);

        // Chuyển đổi phản hồi JSON sử dụng org.json
        JSONObject jsonObj = new JSONObject(responseString);

        // In toàn bộ JSON object để kiểm tra cấu trúc của nó
        System.out.println("Parsed JSON: " + jsonObj.toString());

        if (jsonObj.has("access_token")) {
            String accessToken = jsonObj.getString("access_token");
            return accessToken;
        } else {
            System.out.println("No access_token found in response.");
            return null;
        }
    }
    
    public static Customer getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

    	String link = Iconstant.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        // Ánh xạ JSON từ Google vào lớp tạm thời GoogleUser
        GoogleUser googleUser = new Gson().fromJson(response, GoogleUser.class);

        // Chuyển đổi dữ liệu từ GoogleUser sang Customer
        Customer customer = convertGoogleUserToCustomer(googleUser);

        return customer;
    }
    
    private static Customer convertGoogleUserToCustomer(GoogleUser googleUser) {
        Customer customer = new Customer();
        customer.setEmail(googleUser.getEmail());
        System.out.println(googleUser.getEmail());

        System.out.println(googleUser.getGiven_name());
        if (googleUser.getGiven_name() == null){
            customer.setFirstname("");
        }else{
            customer.setFirstname(googleUser.getGiven_name());
        }

        System.out.println(googleUser.getFamily_name());
        if (googleUser.getFamily_name() == null){
            customer.setLastname("");
        }else{
            customer.setLastname(googleUser.getFamily_name());
        }
        String passwordHash = new HashSha_256Service().hashWithSHA256(customer.getEmail());
        customer.setPassword(passwordHash);
		/* customer.setCountry("VN"); */
        // Đặt các giá trị mặc định hoặc từ GoogleUser vào các trường khác nếu cần thiết
        customer.setSignUpDate(new java.util.Date());  // Example: setting current date as sign up date
        return customer;
    }
}
