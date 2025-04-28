package com.admincontrollers;


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
 * Servlet implementation class MyComplaints
 */
@WebServlet("/UserComplaints")
public class AdminMyComplaints extends HttpServlet {
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

            if (token == null) {
                
                rsMethod.sendErrorResponse(response, "Token not found", gson);
                return;
            }

            
            List<Map<String, Object>> complaints = userServices.getComplaints();
            
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
