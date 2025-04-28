package com.sendresponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ResponseMethod {

	public void setCorsHeaders(HttpServletResponse response) {
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // Not "*"
	    response.setHeader("Access-Control-Allow-Credentials", "true"); // 🔥 Required for cookies/session
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	}

    public  String getJsonValue(JsonObject json, String key) {
        return json.has(key) ? json.get(key).getAsString() : null;
    }

    public  void sendErrorResponse(HttpServletResponse response, String message, Gson gson) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(new ResponseMessage(message)));
            out.flush();
        }
    }

    public void sendSuccessResponse(HttpServletResponse response, String message, Gson gson) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(new ResponseMessage(message)));
            out.flush();
        }
    }
}
