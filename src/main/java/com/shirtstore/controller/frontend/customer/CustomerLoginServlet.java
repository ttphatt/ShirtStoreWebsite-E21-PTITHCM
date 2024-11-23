package com.shirtstore.controller.frontend.customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.shirtstore.service.CustomerServices;

@WebServlet("/login")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CustomerLoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        System.out.println("Output code: " + code);
        System.out.println("Output state: " + state);
        if(code != null && !code.isEmpty() && state != null && state.equals("provider=google")) {
            processRequestGoogle(request, response);
        }
        else if(code != null && !code.isEmpty() && state != null && state.equals("provider=facebook")) {
            processRequestFacebook(request, response);
        } else {
            CustomerServices customerService = new CustomerServices(request, response);
            customerService.showLogin();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CustomerServices customerService = new CustomerServices(request, response);
        String captchaResponse = request.getParameter("h-captcha-response");
        
        if(verifyHCaptcha(captchaResponse)) {
            customerService.doLogin();
        } else {
            request.setAttribute("message", "Captcha verification failed");
            customerService.showLogin();
        }
    }

    //Captcha verification
    private boolean verifyHCaptcha(String response) {
        String secret = "ES_0daeb1d187dc4080a94c9dc233ba33ed"; // Replace with your hCaptcha secret key
        try {
            URL url = new URL("https://hcaptcha.com/siteverify");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String data = "secret=" + URLEncoder.encode(secret, "UTF-8") + "&response=" + URLEncoder.encode(response, "UTF-8");

            OutputStream outStream = con.getOutputStream();
            outStream.write(data.getBytes());
            outStream.flush();
            outStream.close();

            InputStream inStream = con.getInputStream();
            InputStreamReader reader = new InputStreamReader(inStream);
            BufferedReader br = new BufferedReader(reader);

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseBuilder.append(line);
            }

            br.close();
            
            // Assuming you have a method to parse JSON
            JSONObject json = new JSONObject(responseBuilder.toString());
            return json.getBoolean("success");
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Google login
    protected void processRequestGoogle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CustomerServices customerService = new CustomerServices(request, response);
    	customerService.loginGoogle();
    }

    //Google login
    protected void processRequestFacebook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerServices customerService = new CustomerServices(request, response);
        customerService.loginFacebook();
    }
}


