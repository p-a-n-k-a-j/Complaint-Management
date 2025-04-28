<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.factory.HandleCookie"%>
    <% String token = HandleCookie.getAdminTokenFromCookies(request);
    if(token!=null){
    	response.sendRedirect(request.getContextPath() + "/admin/adminDashboard.jsp");
    	return;
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login | Admin Portal</title>
<link rel="styleSheet" type="text/css" href= "./css/vipLogin.css">
</head>
<body>
<div class="form-wrapper">
 <div class="form-fields">
<form id="register-form">
<div class="error">
<span class="error-message" id="email-error"></span>
</div>
<div class="group-field">

<select class="input" name="role" id="role">
<option value="">Choose Role</option>
<option value="admin">Admin</option>
<option value="super-admin">Super Admin</option>
</select>
</div>

<div class="group-field">
<label for="email" class="label">Email</label>
<input type="email" name="username" id="email" class="input" placeholder="example@gmail.com" required>
</div>

<div class="group-field">
<label>Password</label>
<input type="password" name="password" id="password" class="input" placeholder="Enter password" required>
</div>
<div class="group-field">
<button id="login" class="login"> Login</button>
</div>

</form>
</div> 
<div class="form-header">

<h2>Login to Access the Complaint System</h2>
<p>Enter your email and password to log in and track your complaints. If you face any issues, please contact support.</p>



</div>

</div>
<script src="./js/vipLogin.js"></script>
</body>
</html>