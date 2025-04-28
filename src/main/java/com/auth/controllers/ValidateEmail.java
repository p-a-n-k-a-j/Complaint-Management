package com.auth.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mailprocess.EmailProcess;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class ValidateEmail
 */
@WebServlet("/varify")
public class ValidateEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
       ResponseMethod method = new ResponseMethod();
       EmailProcess sendEmail = new EmailProcess();
       Gson gson = new Gson();

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	method.setCorsHeaders(response);
    	response.setStatus(HttpServletResponse.SC_OK);
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		method.setCorsHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try(PrintWriter out = response.getWriter()){
		BufferedReader reader = request.getReader();
    	JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
    	 String firstName = method.getJsonValue(jsonData, "fname");
    	String email = method.getJsonValue(jsonData, "email");
    	String varificationKey =sendEmail.generateVarificationKey();
    	
    	 String subject = "Account Activation - Complaint Management System";
			String emailContent = "<html><body>"
			        + "<h2>Dear " + firstName + ",</h2>"
			        + "<p>Thank you for registering with the <strong>Complaint Management System</strong>.</p>"
			        + "<p style=\"color:black; \">Your Varification key: <strong>" + varificationKey + "</strong></p>"
			        + "<p>If you did not register, please ignore this email.</p>"
			        + "<br>"
			        + "<p>Best regards,</p>"
			        + "<p><strong>Complaint Management Team</strong></p>"
			        + "</body></html>";
			
		boolean test=sendEmail.sendEmail(email, subject, emailContent);
		if(test) {
			
			method.sendSuccessResponse(response, varificationKey, gson);
			return;
		}
		method.sendErrorResponse(response, "fail", gson);
			
		}catch(Exception e) {
			e.printStackTrace();
			method.sendErrorResponse(response, "Error processing request :"+e.getMessage(), gson);
		}
	}

}
