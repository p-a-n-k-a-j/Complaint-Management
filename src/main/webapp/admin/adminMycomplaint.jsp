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
<title>Manage All Complaints</title>
<link rel="styleSheet" type="text/css" href="./css/adminDashboard.css">
<link rel="styleSheet" type="text/css" href="./css/adminMyComplaints.css">

<script src="./js/adminMyComplaints.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="admin-sidebar.jsp" />

<div class="header">
<div class="table-heading">
  <h2>Manage User Complaints & Update Status</h2>

  <div class="search-bar" id="search-bar">
    <img src="./images/search.png" alt="Search" class="search-icon">
    <input type="text" placeholder="Search complaints..." class="search-input" id="searchInput">
  </div>
</div>

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

<script src="./js/adminDashboard.js"></script>
</body>
</html>