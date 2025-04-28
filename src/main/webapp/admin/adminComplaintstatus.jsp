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
<link rel="styleSheet" type="text/css" href="./css/adminComplaintStatus.css">

<script src="./js/adminComplaintStatus.js"></script>
</head>
<body>
<div class="container">

<jsp:include page="admin-sidebar.jsp" />

<div class="header">
<div class="complaint-container">
 <table class="table">
    <thead>
      <tr>
        <th>Title</th>
        <th>Status</th>
        <th>Updated At</th>
        <th>Updated By</th>
        <th>Remark</th>
      </tr>
    </thead>
<tbody id="complaint-data">
<!-- data render here -->
</tbody>

</table>
</div>
</div>
</div>

<script src="./js/adminDashboard.js"></script>
</body>
</html>