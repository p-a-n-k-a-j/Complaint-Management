package com.auth.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth.services.UserServices;
import com.factory.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    private final ResponseMethod responseMethod = new ResponseMethod();

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        responseMethod.setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        responseMethod.setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        	
        try (PrintWriter out = response.getWriter()) {
            BufferedReader reader = request.getReader();
            JsonObject jsonData = gson.fromJson(reader, JsonObject.class);

            String username = responseMethod.getJsonValue(jsonData, "username");
            String password = responseMethod.getJsonValue(jsonData, "password");

           if(username == null || password == null) {
        	   responseMethod.sendErrorResponse(response, "fill all field", gson);
           }

            UserServices userServices = new UserServices();
            //it validate user
            String message = userServices.validate(username, password);

            if (!"valid".equals(message)) {
                responseMethod.sendErrorResponse(response, message, gson);
            } else {
                String token = JwtUtil.generateToken(username);
               
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true); // Prevents JavaScript access to the cookie
                cookie.setSecure(true);    // Ensures the cookie is sent over HTTPS
                cookie.setPath("/complaint-management/"); // ✅ So it's valid for all routes
                cookie.setMaxAge(30 * 24 * 60 * 60); // ✅ 30 days
                response.addCookie(cookie);

                

                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("message", "valid");
//                jsonResponse.addProperty("token", token);
                out.write(gson.toJson(jsonResponse));
                out.flush();
            }

        } catch (Exception e) {
            responseMethod.sendErrorResponse(response, "Error occurred during request: " + e.getMessage(), gson);
        }
    }
}