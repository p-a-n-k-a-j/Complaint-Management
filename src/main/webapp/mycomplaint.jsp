<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@page import="com.factory.HandleCookie"%>
    <% String token = HandleCookie.getTokenFromCookies(request);
    if(token==null){
    	response.sendRedirect(request.getContextPath() + "/login.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View All Complaints</title>
<link rel="styleSheet" type="text/css" href="./css/dashboard.css">
<link rel="styleSheet" type="text/css" href="./css/myComplaints.css">

<script src="./js/myComplaints.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="side-bar.jsp" />

<div class="header">
<div class="complaint-container">
 <table class="table">
    <thead>
      <tr>
        <th>Title</th>
        <th>Status</th>
        <th>Updated At</th>
        <th>Updated By</th>
        <th>Priority</th>
      </tr>
    </thead>
<tbody id="complaint-data">
<!-- data render here -->
</tbody>

</table>
</div>

</div>
</div>

<script src="./js/dashboard.js"></script>
</body>
</html>