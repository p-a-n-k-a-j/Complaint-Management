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
<title>Change Password</title>
<link rel="styleSheet" type="text/css" href="./css/adminDashboard.css">
<link rel="styleSheet" type="text/css" href="./css/adminChangePassword.css">
</head>
<body>
<div class="container">
 <jsp:include page="admin-sidebar.jsp" />

  <div class="header">
    <div class="complaint-from">
      <form id="register-form">
      
 <div class="group-field">
          <label for="oldPassword" class="labels">Enter Old Password</label>
          <input type="password" class="inputs" id="oldPassword" name ="oldPassword"placeholder="Enter password">
        </div>

        <div class="group-field">
          <label for="newPassword" class="labels">Enter New Password</label>
          <input type="password" class="inputs" id="newPassword" name="newPassword" placeholder="Enter password">
        </div>
        
         <div class="group-field">
          <label for="confirmPass" class="labels">Confirm Password</label>
          <input type="password" class="inputs" id="confirmPass"  placeholder="Enter confirm password">
        </div>
        <div class="errors">
        <span class="errorMessage" id="errorMessage"></span>
        </div>
        
        <div class="group-field">
       <label class="labels">
  		<input type="checkbox" id="show-password" class="inputs"> Show Password
		</label>
        </div>

        <div class="button-wrap">
          <button type="submit" class="submit-btn" id="btn">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script type="text/javascript" src="./js/adminChangePassword.js"></script>
<script src="./js/adminDashboard.js"></script>
</body>
</html>