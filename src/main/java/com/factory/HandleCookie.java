package com.factory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HandleCookie {
	public static String getAdminTokenFromCookies(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("adminToken".equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null; // No token found
	}



public static String getTokenFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }
    return null; // No token found
}

}
