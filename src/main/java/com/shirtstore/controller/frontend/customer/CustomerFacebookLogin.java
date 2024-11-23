package com.shirtstore.controller.frontend.customer;

import com.google.gson.Gson;
import com.shirtstore.entity.Customer;
import com.shirtstore.entity.FacebookUser;
import com.shirtstore.entity.GoogleUser;
import com.shirtstore.service.HashSha_256Service;
import constant.Iconstant;
import constant.IconstantFacebook;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;

import java.io.IOException;

public class CustomerFacebookLogin {
    public String getToken(String code) throws ClientProtocolException, IOException {

        Content content = Request.Post(IconstantFacebook.FACEBOOK_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", IconstantFacebook.FACEBOOK_CLIENT_ID)
                                .add("client_secret", IconstantFacebook.FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", IconstantFacebook.FACEBOOK_REDIRECT_URI)
                                .add("code", code)
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

        String link = IconstantFacebook.FACEBOOK_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        // Ánh xạ JSON từ Google vào lớp tạm thời GoogleUser
        FacebookUser facebookUser = new Gson().fromJson(response, FacebookUser.class);

        // Chuyển đổi dữ liệu từ GoogleUser sang Customer
        Customer customer = convertFacebookUserToCustomer(facebookUser);

        return customer;
    }

    private static Customer convertFacebookUserToCustomer(FacebookUser facebookUser) {
        Customer customer = new Customer();

        System.out.println(facebookUser.getEmail());
        customer.setEmail(facebookUser.getEmail());

        System.out.println(facebookUser.getName());
        if (facebookUser.getName() == null){
            customer.setFirstname("");
            customer.setLastname("");
        }else{
            customer.setFirstname("");
            customer.setLastname(facebookUser.getName());
        }
        String passwordHash = new HashSha_256Service().hashWithSHA256(customer.getEmail());
        customer.setPassword(passwordHash);
//        /* customer.setCountry("VN"); */
//        // Đặt các giá trị mặc định hoặc từ GoogleUser vào các trường khác nếu cần thiết
       customer.setSignUpDate(new java.util.Date());  // Example: setting current date as sign up date
        return customer;
    }
}
