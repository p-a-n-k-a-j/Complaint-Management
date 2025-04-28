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
import com.factory.HandleCookie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class UpdateStatus
 */
@WebServlet("/UpdateStatus")
public class UpdateStatus extends HttpServlet {
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
		System.out.println("Enter into update status");
		try(PrintWriter out = response.getWriter()) {
			BufferedReader reader = request.getReader();
			JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
			String updateBy =HandleCookie.getAdminTokenFromCookies(request);
			int complaintId = Integer.parseInt(rsMethod.getJsonValue(jsonData, "id"));
			String newStatus = rsMethod.getJsonValue(jsonData, "status");
			
			
			/*
			 * System.out.println("id: " +complaintId); System.out.println("status: "
			 * +newStatus);
			 */
//			System.out.println("description: " +description);
//			System.out.println("priority: " +priority);
			
			JsonObject jsonResponse = new JsonObject();
			if(updateBy == null && complaintId== 0 && newStatus == null ) {
				jsonResponse.addProperty("success", false);
				out.write(gson.toJson(jsonResponse));
				out.flush();
				return;
			}
			
			boolean updateDone=userServices.updateComplaint(complaintId, newStatus, updateBy);
			if(updateDone) {
				jsonResponse.addProperty("success", true);
			}else {
				jsonResponse.addProperty("success", false);
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