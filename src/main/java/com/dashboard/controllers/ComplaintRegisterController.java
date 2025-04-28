package com.dashboard.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.services.UserServices;
import com.factory.HandleCookie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class ComplaintRegisterController
 */
@WebServlet("/registerComplaint")
public class ComplaintRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResponseMethod rsMethod = new ResponseMethod();
	Gson gson = new Gson();
	UserServices userServices = new UserServices();
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try(PrintWriter out = response.getWriter()) {
			BufferedReader reader = request.getReader();
			JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
			String token =HandleCookie.getTokenFromCookies(request);
			String title = rsMethod.getJsonValue(jsonData, "title");
			String description = rsMethod.getJsonValue(jsonData, "description");
			String priority = rsMethod.getJsonValue(jsonData, "priority");
			
//			System.out.println("token: " +token);
//			System.out.println("title: " +title);
//			System.out.println("description: " +description);
//			System.out.println("priority: " +priority);
			
			JsonObject jsonResponse = new JsonObject();
			if(token == null && title== null && description == null && priority == null) {
				jsonResponse.addProperty("error", "fill all fields");
				out.write(gson.toJson(jsonResponse));
				out.flush();
				return;
			}
			
			boolean registerDone=userServices.registerComplaint(token, title, description, priority);
			if(registerDone) {
				jsonResponse.addProperty("message", "success");
			}else {
				jsonResponse.addProperty("message", "fail");
			}
			
			out.write(gson.toJson(jsonResponse));
			out.flush();
		}catch(Exception e)
		{
			e.printStackTrace();
			rsMethod.sendErrorResponse(response, "Error processing Request: "+ e.getMessage(), gson);
			
		}
	}

}
