
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
<title>Create Admin</title>
<link rel="styleSheet" type="text/css" href="./css/adminDashboard.css">
<link rel="styleSheet" type="text/css" href="./css/adminRegister.css">
</head>
<body>
<div class="container">
  <jsp:include page="admin-sidebar.jsp" />

  <div class="header">
    <div class="complaint-from">
      <form id="register-form" method="post">
        <div class="group-field">
          <label for="fname" class="labels">First Name</label>
          <input type="text" class="inputs" id="fname" name="firstname" placeholder="Enter Firstname">
        </div>
         <div class="group-field">
          <label for="lname" class="labels">Last Name</label>
          <input type="text" class="inputs" id="lname" name="lastname" placeholder="Enter Lastname">
        </div>
         <div class="group-field">
          <label for="email" class="labels">Email</label>
          <input type="email" class="inputs" id="email" name="email" placeholder="Enter email">
        </div>
		 <div class="group-field">
          <label for="title" class="labels">Phone</label>
          <input type="tel" class="inputs" id="phone" name="phone" placeholder="Enter contact">
        </div>
        
         
        <div class="group-field">
        <label for="dept"  class="labels">Department</label>
                <select id="dept" name="dept" class="inputs" required>
                    <option value="CSE">CSE</option>
                    <option value="CE">CE</option>
                    <option value="ME">ME</option>
                    <option value="EE">EE</option>
                </select>
        </div>

       	 <div class="group-field">
          <label for="password" class="labels">Password</label>
          <input type="password" class="inputs" id="password" name="password" placeholder="Enter password">
        </div>

        <div class="button-wrap">
          <button type="submit" class="submit-btn" id="btn">Create Admin</button>
          <button type="reset" class="submit-btn">Reset</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script type="text/javascript" src="./js/adminRegister.js"></script>
<script src="./js/adminDashboard.js"></script>
</body>
</html>