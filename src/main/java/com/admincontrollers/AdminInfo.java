package com.admincontrollers;


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
@WebServlet("/AdminInfo")
public class AdminInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResponseMethod rsMethod = new ResponseMethod();
	private Gson gson = new Gson();
	private UserServices userServices = new UserServices();

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // 405
	    response.getWriter().write("GET method not supported");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rsMethod.setCorsHeaders(response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		/* System.out.print("enter in profile info"); */
		try(PrintWriter out = response.getWriter()){
			String token =HandleCookie.getAdminTokenFromCookies(request);
			/* System.out.println("token "+ token); */
			
			
			if(token == null) {
				System.out.println("token is null");
				return;
			}
			/* System.out.println("after token"); */
			
			
			Map<String, Object> profileInfo = userServices.getAdminInfo(token);

			if (profileInfo.isEmpty()) {
				/*
				 * System.out.
				 * println("Empty profile info — maybe invalid token or user not found");
				 */
			    rsMethod.sendErrorResponse(response, "No profile info found", gson);
			    return;
			}
			
				out.write(gson.toJson(profileInfo));
				out.flush();
			
			
		}catch(Exception e) {
			rsMethod.sendErrorResponse(response, "Server faliuer", gson);
		}
		
	}

}
