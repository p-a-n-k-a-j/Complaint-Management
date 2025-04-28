package com.admincontrollers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;

/**
 * Servlet implementation class ViewComplaintDetails
 */
@WebServlet("/ViewComplaintDetails")
public class ViewComplaintDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 ResponseMethod rsMethod = new ResponseMethod();
	    Gson gson = new Gson();
	    UserServices userServices = new UserServices();

	    @Override
	    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        rsMethod.setCorsHeaders(response);
	        response.setStatus(HttpServletResponse.SC_OK);
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        rsMethod.setCorsHeaders(response);
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	       
	       

	        try (PrintWriter out = response.getWriter()) {
	            String token =HandleCookie.getAdminTokenFromCookies(request);
	            BufferedReader reader = request.getReader();
	            JsonObject jsonData = gson.fromJson(reader, JsonObject.class);
	            
	            int complaintId = Integer.parseInt(rsMethod.getJsonValue(jsonData, "id"));
	            if (token == null) {
	                
	                rsMethod.sendErrorResponse(response, "Token is null ", gson);
	                return;
	            }
	            if( complaintId == 0) {
	            	 rsMethod.sendErrorResponse(response, "ComplaintId is null ", gson);
		             return;
	            }
	            
	            Map<String, Object> complaints = userServices.getComplaintDetails(token, complaintId);
	            
	            // ✅ Always return 200 with data or empty list
	            String json = gson.toJson(complaints);
	            out.write(json);
	            out.flush();

	        } catch (Exception e) {
	            e.printStackTrace();
	            rsMethod.sendErrorResponse(response, "Error processing request: " + e.getMessage(), gson);
	        }
	    }
	}
