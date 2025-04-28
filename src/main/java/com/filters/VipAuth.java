package com.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.factory.JwtUtil;
import com.sendresponse.ResponseMethod;

import java.io.IOException;
@WebFilter(urlPatterns = {"/admin/adminDashboard.jsp"})
public class VipAuth implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // CORS Headers
        ResponseMethod rsMethod = new ResponseMethod();
        rsMethod.setCorsHeaders(res);

        // Get token from cookie
        String token = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("adminToken".equals(cookie.getName())) {
                    token = cookie.getValue();
					 
                    break;
                }
            }
        }
       
        boolean isValid = token != null && JwtUtil.isValid(token);
		 
        if (isValid) {
            chain.doFilter(request, response); // ✅ continue
        } else {
            res.sendRedirect(req.getContextPath() + "/admin/vipLogin.jsp"); // ❌ redirect to login
        }
    }
}
