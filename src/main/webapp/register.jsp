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
<title>Register New Complaint</title>
<link rel="styleSheet" type="text/css" href="./css/dashboard.css">
<link rel="styleSheet" type="text/css" href="./css/register.css">
</head>
<body>
<div class="container">
  <jsp:include page="side-bar.jsp" />

  <div class="header">
    <div class="complaint-from">
      <form id="register-form">
        <div class="group-field">
          <label for="title" class="labels">Title</label>
          <input type="text" class="inputs" id="title" name="title" placeholder="Enter title">
        </div>

        <div class="group-field">
          <label for="priority" class="labels">Priority</label>
          <select class="inputs" name="priority" id="priority">
          <option value="Normal">Normal</option>
          <option value="Medium">Medium</option>
          <option value="High">High</option>
          </select>
        </div>

        <div class="group-field">
          <label for="description" class="labels">Description</label>
          <textarea rows="4" class="inputs textarea" id="description" name="description" placeholder="Enter description"></textarea>
        </div>

        <div class="button-wrap">
          <button type="submit" class="submit-btn" id="btn">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script type="text/javascript" src="./js/register.js"></script>
<script src="./js/dashboard.js"></script>
</body>
</html>