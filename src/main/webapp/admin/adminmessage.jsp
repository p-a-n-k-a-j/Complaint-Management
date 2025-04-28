<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@page import="com.factory.HandleCookie"%>
    <% String token = HandleCookie.getAdminTokenFromCookies(request);
    if(token==null){
    	response.sendRedirect(request.getContextPath() + "/admin/vipLogin.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Complaint Status</title>
<link rel="styleSheet" type="text/css" href="./css/adminDashboard.css">



</head>
<body>
<div class="container">
<jsp:include page="admin-sidebar.jsp" />

<div class="header">
<h2>admin message</h2>
</div>
</div>

<script src="./js/adminDashboard.js"></script>
</body>
</html>