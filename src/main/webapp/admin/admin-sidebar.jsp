
<div class="side-bar">

<ul class="links">
<li class="link"><a href="adminDashboard.jsp"><img src="./images/logo.png" style="height:auto; width:100%;"></a></li>
<li class="link active"><a href="adminDashboard.jsp"><img src="./images/menu.png" class="link-icons"> Dashboard</a></li>
<li class="link"><a href="adminRegister.jsp"><img src="./images/notes.png" class="link-icons"> Create Admin</a></li>
<li class="link dropdown-toggle" id="dropdown-toggle"><a href="#">
<img src="./images/mycomplaint.png" class="link-icons"> Complaints
<img src="./images/down.png"  class="icon-expand" id="down-arrow-complaint">
<img src="./images/expand.png"  class="icon-expand" id="up-arrow-complaint" style="display:none;">
</a>

<!--drop down for my complaint  -->
    <ul class="dropdown" id="complaints-dropdown">
      <li class="sublink"><a href="adminMycomplaint.jsp"> Manage Complaints</a></li>
     <!--  <li class="sublink"><a href="adminSearchcomplaint.jsp">Search Complaint</a></li> -->
    </ul>
    
    </li>
<!-- <li class="link"><a href="adminComplaintstatus.jsp"><img src="./images/status.png" class="link-icons"> Complaint Status</a></li>
<li class="link"><a href="adminmessage.jsp"><img src="./images/messenger.png" class="link-icons"> Admin Message</a></li> -->
<li class="link setting-toggle" id="setting-toggle"><a href="#"><img src="./images/settings.png" class="link-icons" >Settings</a>
<img src="./images/down.png"  class="icon-expand" id="down-arrow-setting">
<img src="./images/expand.png"  class="icon-expand" id="up-arrow-setting" style="display:none;">
 <!-- this dropdown for settings -->
  <ul class="sdropdown" id="setting-dropdown">
      <li class="sublink-setting"><a href="adminProfileInfo.jsp"> Profile Info</a></li>
      <li class="sublink-setting"><a href="adminChangePassword.jsp">Change Password</a></li>
      <li class="sublink-setting"><a href="<%=application.getContextPath() %>/AdminLogout"> Logout</a></li>
    </ul>
</li>
<!--< li class="link"><a href="#"></i> Admin Message</a></li>
<li class="link"><a href="#"> Admin Message</a></li>
<li class="link"><a href="#">Admin Message</a></li> -->

</ul>
</div>
