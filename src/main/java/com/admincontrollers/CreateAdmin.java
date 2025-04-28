package com.admincontrollers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.services.UserServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.sendresponse.ResponseMethod;

@WebServlet("/CreateAdmin")
public class CreateAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final Gson gson = new Gson();
	 private final ResponseMethod rsMethod = new ResponseMethod();
	    @Override
	    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	rsMethod.setCorsHeaders(response);
	        response.setStatus(HttpServletResponse.SC_OK);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	ResponseMethod rsMethod = new ResponseMethod();
	    	rsMethod.setCorsHeaders(response);
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        System.out.println("Enter into create admin");
	        try (PrintWriter out = response.getWriter()) {
	            BufferedReader reader = request.getReader();
	            JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
	            
	            if (jsonData == null) {
	            	rsMethod.sendErrorResponse(response, "Invalid JSON received", gson);
	                return;
	            }
	           
	            String firstName = rsMethod.getJsonValue(jsonData, "firstname");
	            String lastName = rsMethod.getJsonValue(jsonData, "lastname");
	            String email = rsMethod.getJsonValue(jsonData, "email");
	            String phone = rsMethod.getJsonValue(jsonData, "phone");
	            String password = rsMethod.getJsonValue(jsonData, "pass");
	            
	            String dept = rsMethod.getJsonValue(jsonData, "dept");
	           
	            
	            if (firstName == null || email == null || password == null) {
	            	rsMethod.sendErrorResponse(response, "Missing required fields", gson);
	                return;
	            }
    
	            UserServices userService = new UserServices();
	            
	           
				//here i save data in database
	            String result = userService.registerUser(firstName, lastName, email, phone, password, "admin", dept);
	  
	            rsMethod.sendSuccessResponse(response, result, gson);
	           
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            rsMethod.sendErrorResponse(response, "Error processing request: " + e.getMessage(), gson);
	        }
	    }


	   
	}
