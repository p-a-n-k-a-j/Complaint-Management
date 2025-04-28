package com.admincontrollers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminLogout
 */
@WebServlet("/AdminLogout")
public class AdminLogout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminLogout() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate admin token cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("adminToken".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0); // Expire immediately
                    cookie.setPath("/complaint-management/"); 
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        // Redirect to admin login
        response.sendRedirect(request.getContextPath() + "/admin/vipLogin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Handle both GET and POST
    }
}

