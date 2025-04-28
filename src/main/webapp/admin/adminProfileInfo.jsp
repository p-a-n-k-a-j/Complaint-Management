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
<link rel="styleSheet" type="text/css" href="./css/adminProfileInfo.css">
</head>
<body>
<div class="container">
<jsp:include page="admin-sidebar.jsp" />
  <div class="header">
  <!-- Profile view -->
<div class="form-container" id="profile-view">
  <h2 class="profile-heading">👤 My Profile</h2>

  <div class="group-field">
    <label class="labels">First Name:</label>
    <span id="firstname"></span>
  </div>

  <div class="group-field">
    <label class="labels">Last Name:</label>
    <span id="lastname"></span>
  </div>

  <div class="group-field">
    <label class="labels">Email:</label>
    <span id="emailid"></span>
  </div>

  <div class="group-field">
    <label class="labels">Mobile:</label>
    <span id="mobile"></span>
  </div>

  <div class="group-field">
    <label class="labels">Role:</label>
    <span id="role"></span>
  </div>

 <!--  <div class="group-field">
    <label class="labels">Department:</label>
    <span id="dept"></span>
  </div> -->

  <div class="button-wrap">
    <button id="edit" class="edit">Edit</button>
  </div>
</div>
<!-- from container ends here -->


<!--  here I create a form where user can update own details-->
<div class="form-fields" id="edit-form" style="display: none;">
  <form id="update-form">
        <div class="combine-group-of-field">
            <div class="name-grouping-two">
                <label for="fname" class="labela">Name</label>
                <input type="text" class="name-input" id="fname" name="firstName" placeholder=" " required>
                <label class="sub-label">First Name</label>
                
            </div>
            <div class="name-grouping-two">
                <label class="labels" style="visibility: hidden">Placeholder</label>
                <input type="text" class="name-input" id="lname" name="lastName" placeholder=" " required>
                <label class="sub-label">Last Name</label>
                
                
            </div>
        </div>
        <!-- first name and last name error div for maintain structure -->
         <div class="errors">
         <div class="error-div"><span class="error-message" id="fname-error"></span></div>
         <div class="error-div"><span class="error-message" id="lname-error"></span></div>
         </div>

        <div class="combine-group-of-field">
           
            <div class="grouping-two">
                <label for="phone" class="labels">Phone No.</label>
                <input type="tel" id="phone" name="phoneNumber" class="inputs" pattern="[0-9+]+" placeholder="+91 1234567890" required>
            	
            </div>
        </div>

       
           
        

          <div class="combine-group-of-field">
      <div class="grouping-two">
        <button type="submit" class="submit btn" id="btn">Save Changes</button>
      </div>
      <div class="grouping-two">
        <button  class="close btn" id="close">Close</button>
      </div>
            
        </div>

 </form>
 </div>


  </div><!--header ends here  -->
</div><!-- container ends here -->






<script type="text/javascript" src="./js/adminProfileInfo.js"></script>
<script src="./js/adminDashboard.js"></script>
</body>
</html>