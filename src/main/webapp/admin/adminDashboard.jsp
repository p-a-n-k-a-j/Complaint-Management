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
<title>Admin Dashboard</title>
<link rel="styleSheet" type="text/css" href="./css/adminDashboard.css">
<link rel="styleSheet" type="text/css" href="./css/adminDashboard-design.css">
<script src="./js/adminDashboardMain.js"></script>
</head>
<body>


<div class="container">
<jsp:include page="admin-sidebar.jsp" />
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
<h2 class="greet">Welcome <span id="AdminUsername"></span></h2>
</div>

<div class="recent-table">
  <table class="table">
    <thead>
      <tr>
      	<th>UserName</th>
        <th>Title</th>
        <th>Status</th>
        <th>Created At</th>
        <th>Updated At</th>
        <th>Updated By</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody id="recent-data">
    <!-- data is rendered here -->
    </tbody>
  </table>
</div>


<div class="user-details" id="userDetails">
  <!-- Example content -->
  <h3>Complaint Details <span class="icon" id="close" title="close">❌</span></h3>
  <div class="detail-row"><strong>Username:</strong> <span id="user-username"></span></div>
  <div class="detail-row"><strong>Title:</strong> <span id="title"></span></div>
  <div class="detail-row"><strong>Status:</strong> <span id="status"></span></div>
  <div class="detail-row"><strong>Priority:</strong> <span id="priority"></span></div>
  <!-- <div class="detail-row"><strong>Remark:</strong> Technician assigned</div> -->
  <div class="detail-row"><strong>Created At:</strong> <span id="createdAt"></span></div>
  <div class="detail-row"><strong>Updated At:</strong> <span id="updatedAt"></span></div>
  <div class="detail-row"><strong>Updated By:</strong> <span id="updatedBy"></span></div>
  <div class="detail-row"><strong>Description:</strong> <span id="description"></span></div>
</div>


</div>
</div>



<script src="./js/adminDashboard.js"></script>
</body>
</html>