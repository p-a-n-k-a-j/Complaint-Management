package com.dashboard.controllers;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
 * Servlet implementation class ComplaintRegisterController
 */
@WebServlet("/recentData")
public class RecentData extends HttpServlet {
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
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			String token =HandleCookie.getTokenFromCookies(request);
			if(token == null) {
				rsMethod.sendErrorResponse(response, "token not found", gson);
				return;
			}
			
			List<Map<String, Object>> log = userServices.getRecentComplaints(token);
			
			
			Map<String, Object> status = userServices.getStatus(token);
			

			if ((log == null || log.isEmpty()) && status == null) {
			   status = Collections.emptyMap();
			   log = Collections.emptyList();
			}

			

			// Combine both into a single response
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("status", status);
			responseMap.put("recentLog", log);
			
			// Send as JSON
			String json = gson.toJson(responseMap);
			out.write(json);
			out.flush();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			rsMethod.sendErrorResponse(response, "Error processing Request: "+ e.getMessage(), gson);
			
		}
	}

}
