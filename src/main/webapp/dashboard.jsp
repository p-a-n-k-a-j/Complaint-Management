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
<title>Dashboard</title>
<link rel="styleSheet" type="text/css" href="./css/dashboard.css">
<link rel="styleSheet" type="text/css" href="./css/dashboard-design.css">
<script src="./js/dashboardMain.js"></script>
</head>
<body>


<div class="container">
<%@include file="side-bar.jsp" %>
<div class="header">

<div class="status-card">

  <div class="card" data-status="totalComplaint">
    <h2 class="card-heading">Total Complaints</h2>
    <h2 class="card-number">0</h2>
    <img src="./images/total.png" class="card-icon">
  </div>

  <div class="card" data-status="pending">
    <h2 class="card-heading">Pending</h2>
    <h2 class="card-number">0</h2>
    <img src="./images/pending.png" class="card-icon">
  </div>

  <div class="card" data-status="inProgress">
    <h2 class="card-heading">In Progress</h2>
    <h2 class="card-number">0</h2>
    <img src="./images/inprogress.png" class="card-icon">
  </div>

  <div class="card" data-status="resolved">
    <h2 class="card-heading">Resolved</h2>
    <h2 class="card-number">0</h2>
    <img src="./images/resolved.png" class="card-icon">
  </div>

</div>
<!-- status card end here -->

<div class="greeting" id="greetuser">
<h2 class="greet">Welcome <span id="username"></span></h2>
</div>

<div class="recent-table">
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
    <tbody id="recent-data">
    <!-- data is rendered here -->
    </tbody>
  </table>
</div>
</div>
</div>

<script src="./js/dashboard.js"></script>
</body>
</html>