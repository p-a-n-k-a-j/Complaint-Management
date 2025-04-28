package com.dashboard.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth.services.*;
import com.factory.HandleCookie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendresponse.ResponseMethod;
@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserServices userService = new UserServices();
	private final Gson gson = new Gson();
	private final ResponseMethod responseMethod = new ResponseMethod();

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		responseMethod.setCorsHeaders(response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		responseMethod.setCorsHeaders(response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		try {
			BufferedReader reader = request.getReader();
			JsonObject jsonData = gson.fromJson(reader, JsonObject.class);

			String token =HandleCookie.getTokenFromCookies(request);
			String oldPassword = responseMethod.getJsonValue(jsonData, "oldPassword");
			String newPassword = responseMethod.getJsonValue(jsonData, "newPassword");

	

			boolean updateDone = userService.updatePassword(token, oldPassword, newPassword);
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", updateDone ? "success" : "failed");

			// ✅ Set status OK only after logic passes
			response.setStatus(HttpServletResponse.SC_OK);

			try (PrintWriter out = response.getWriter()) {
				out.print(gson.toJson(jsonResponse));
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace(); // ✅ Log full error
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseMethod.sendErrorResponse(response, "server failure", gson);
		}
	}
}
