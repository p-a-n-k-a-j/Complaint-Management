package com.dashboard.controllers;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth.services.UserServices;
import com.factory.HandleCookie;
import com.google.gson.Gson;

import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class ProfileInfo
 */
@WebServlet("/ProfileInfo")
public class ProfileInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResponseMethod rsMethod = new ResponseMethod();
	private Gson gson = new Gson();
	private UserServices userServices = new UserServices();

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.print("enter in profile info");
		try {
			String token =HandleCookie.getTokenFromCookies(request);
			System.out.println("token "+ token);
			if(token == null) {
				System.out.println("token is null");
				return;
			}
			
			Map<String, Object> profileInfo = userServices.getProfileInfo(token);
			
			try(PrintWriter out = response.getWriter()){
				out.write(gson.toJson(profileInfo));
				out.flush();
			}
			
		}catch(Exception e) {
			rsMethod.sendErrorResponse(response, "Server faliuer", gson);
		}
		
	}

}
