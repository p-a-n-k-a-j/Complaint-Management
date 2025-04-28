package com.admincontrollers;

import java.io.BufferedReader;
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
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class ProfileUpdate
 */
@WebServlet("/AdminUpdate")
public class AdminUpdate extends HttpServlet {
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
		
		try {
			BufferedReader reader = request.getReader();
			JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
			
			String token =HandleCookie.getAdminTokenFromCookies(request);
			if(token == null) {
				System.out.println("token is null");
				return;
			}
			String fname = rsMethod.getJsonValue(jsonData, "firstName");
			String lname = rsMethod.getJsonValue(jsonData, "lastName");
			String mobile = rsMethod.getJsonValue(jsonData, "phoneNumber");
		
			if(fname == null || lname == null || mobile == null ||
					   fname.isEmpty() || lname.isEmpty() || mobile.isEmpty()) {
				
					    rsMethod.sendErrorResponse(response, "all field required", gson);
					    return;
					}
			
			
			boolean update = userServices.updateProfile(token, fname, lname, mobile);
			
			if(update) {
				rsMethod.sendSuccessResponse(response, "success", gson);
			}else {
				rsMethod.sendSuccessResponse(response, "fail", gson);
			}
			
			
			
		}catch(Exception e) {
			rsMethod.sendErrorResponse(response, "Server faliuer", gson);
		}
		
	}

}