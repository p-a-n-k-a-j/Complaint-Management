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

import com.auth.services.UserServices;
import com.factory.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/vipLogin")
public class AdminLogin extends HttpServlet {
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
	            String role = responseMethod.getJsonValue(jsonData, "role");
	            String username = responseMethod.getJsonValue(jsonData, "username");
	            String password = responseMethod.getJsonValue(jsonData, "password");

	            
	           
	           if(username == null || password == null) {
	        	   responseMethod.sendErrorResponse(response, "fill all field", gson);
	           }

	            UserServices userServices = new UserServices();
	            //it validate user
	            String message = userServices.validate(role, username, password);

	            if (!"valid".equals(message)) {
	            	System.out.println("response is not valid");
	                responseMethod.sendErrorResponse(response, message, gson);
	            } else {
	                String token = JwtUtil.generateToken(username);
	               
	                Cookie cookie = new Cookie("adminToken", token);
	                cookie.setHttpOnly(true);
	                cookie.setSecure(true);  // Only if using HTTPS
	                cookie.setPath("/complaint-management/");
	                cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
	                response.addCookie(cookie);


	                

	                JsonObject jsonResponse = new JsonObject();
	                jsonResponse.addProperty("message", "valid");
//	                jsonResponse.addProperty("token", token);
	                out.write(gson.toJson(jsonResponse));
	                out.flush();
	            }

	        } catch (Exception e) {
	            responseMethod.sendErrorResponse(response, "Error occurred during request: " + e.getMessage(), gson);
	        }
	    }
	}