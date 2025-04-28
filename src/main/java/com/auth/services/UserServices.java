package com.auth.services;

import java.time.LocalDate;
import java.util.Collections;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;

import com.auth.dao.BranchDepartmentDAO;
import com.auth.dao.RoleDAO;
import com.auth.dao.UserDAO;
import com.auth.dao.UserRoleDAO;
import com.auth.entity.BranchDepartment;
import com.auth.entity.Role;
import com.auth.entity.User;
import com.auth.entity.UserRole;
import com.dashboard.dao.ComplaintDAO;
import com.dashboard.entity.ComplaintLog;
import com.dashboard.entity.Complaints;
import com.factory.HibernateUtil;
import com.factory.JwtUtil;



public class UserServices {
	private RoleDAO roleDao = new RoleDAO();
	private UserRoleDAO userRoleDao = new UserRoleDAO();
	private BranchDepartmentDAO branchDeptDao = new BranchDepartmentDAO();
	private UserDAO userDao = new UserDAO();
	private ComplaintDAO complaintDao = new ComplaintDAO();
	
	public String registerUser(String firstName, String lastName, String email, String phone, String password, String roleName, String branchName) {
	    // Check if the user already exists
	    User existing = userDao.getUserByEmail(email);
	    if (existing != null) {
	        return "User with this email already exists!";
	    }

	    // Create and save the user
	    User user = new User();
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setEmail(email);
	    user.setPhone(phone);
	    user.setPassword(password);

	    userDao.saveUser(user); // Save user first to generate ID

	    // Fetch or create role, manage before saving
	    Role role = roleDao.getRoleByName(roleName);
	    if (role == null) {
	        role = new Role();
	        role.setRoleName(roleName);
	    }
	    
	    roleDao.saveRole(role); // Save role to database

	    // Fetch or create BranchDepartment, ensure it's managed
	    BranchDepartment branch = branchDeptDao.getBranchByName(branchName);
	    if (branch == null) {
	        branch = new BranchDepartment();
	        branch.setName(branchName);
	        branchDeptDao.saveBranchDepartment(branch);
	    }

	    // Create and save UserRole
	    UserRole userRole = new UserRole();
	    userRole.setBranchDepartment(branch);
	    userRole.setRole(role);
	    userRole.setUser(user);
	    userRoleDao.saveUSerRole(userRole);

	    return "success";
	}


	public  String validate(String username, String password) {
		
		
		User user =userDao.getUserByEmail(username);
		if(user != null && user.getPassword().trim().equals(password.trim())) {
			return "valid";
		}
		
		return "Invalid username & password";
		
	}
	public String validate(String role, String username, String password) {
	    User user = userDao.getUserByEmail(username);
	    
	    if (user != null 
	        && user.getPassword().trim().equals(password.trim()) 
	        && user.getRole().getRole().getRoleName().equalsIgnoreCase(role)) {
	        
	        return "valid";
	    }

	    return "Invalid username & password";
	}

	public boolean registerComplaint(String token, String title, String description, String priority) {
		boolean test = false;
		 boolean tokenValid = JwtUtil.isValid(token);
		 
		 if(tokenValid) {
			 String username = JwtUtil.getUsername(token); 
			 System.out.println("extrated username: "+  username);
				User user=userDao.getUserByEmail(username);
			    if (user == null) {
			        throw new RuntimeException("User not found with username: " + username);
			    }
				Complaints complaint = new Complaints();
				complaint.setUser(user);
				complaint.setTitle(title);
				complaint.setStatus("Pending");
				complaint.setUpdatedBy("system");
				complaint.setDescription(description);
				complaint.setCreatedAt(Date.valueOf(LocalDate.now()));
				complaint.setPriority(priority);
				complaint.setUpdatedAt(Date.valueOf(LocalDate.now()));
				complaintDao.saveComplaint(complaint);
				test=true;
		 }else {
			 return test;
		 }
		 
		
		return test;
	}
	
	public boolean updateComplaint(int complaintId, String newStatus, String updatedByToken) {
		boolean taskdone = false;
		 boolean tokenvalid = JwtUtil.isValid(updatedByToken);
		 if(tokenvalid) {
			 String UpdatedBy = JwtUtil.getUsername(updatedByToken);//here I get updatedBy from token only need to pass admin or super admin token
			 User user =userDao.getUserByEmail(UpdatedBy);
			 String roleName=user.getRole().getRole().getRoleName();
			 complaintDao.updateComplaintStatus(complaintId, newStatus, roleName);
			taskdone= true;
		 }
		 return taskdone;
		 
	}
	
	
	public List<Map<String, Object>> getComplaintLogAsMap(String token){

		List<ComplaintLog> logs = complaintDao.getComplaintLogsByToken(token);
		if(logs == null) {
			return Collections.emptyList();
		}
		return logs.stream().map(log -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("title", log.getComplaint().getTitle()); // 👈 Title added here
	        map.put("status", log.getStatus());
	        map.put("remark", log.getRemark());
	        map.put("updatedAt", log.getUpdatedAt().toString());
	        map.put("updatedBy", log.getUpdatedBy());
	        return map;
	    }).collect(Collectors.toList());
	}
	
	
	//this is provide only todays complaint and user for admin 
	public List<Map<String, Object>> getRecentComplaints(){
		
		List<Complaints> complaints = complaintDao.getRecentComplaint();
		if(complaints == null) {
			return Collections.emptyList();
		}
		return complaints.stream().map(complaint -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", complaint.getComplaintId());
	        map.put("title", complaint.getTitle()); // 👈 Title added here
	        map.put("status", complaint.getStatus());
	        map.put("createdAt", complaint.getCreatedAt());
	        map.put("updatedAt", complaint.getUpdatedAt());
	        map.put("updatedBy", complaint.getUpdatedBy());
	        map.put("username", complaint.getUser().getFirstName());
	        return map;
		}).collect(Collectors.toList());
	}
	
	
	//this is for user. this table see in dashboard latest table
public List<Map<String, Object>> getRecentComplaints(String token){
		
		List<ComplaintLog> logs = complaintDao.getRecentUpdateByUsingToken(token);
		if(logs == null) {
			return Collections.emptyList();
		}
		return logs.stream().map(log -> {
			Map<String, Object> map = new HashMap<>();
			
	        map.put("title", log.getComplaint().getTitle()); // 👈 Title added here
	        map.put("status", log.getStatus());
	        map.put("updatedAt", log.getUpdatedAt());
	        map.put("updatedBy", log.getUpdatedBy());
	        map.put("remark", log.getRemark());
	        
	        return map;
		}).collect(Collectors.toList());
	}
	//this is for provide all status details
	public Map<String, Object> getStatus(String token) {
	    Map<String, Object> map = new HashMap<>();
	    try {
	        long totalComplaint = complaintDao.getTotalComplaints(token);
	        long pending = complaintDao.getPendingComplaints(token);
	        long inProgress = complaintDao.getInProgressComplaints(token);
	        long resolved = complaintDao.getResolvedComplaints(token);
	       
	        map.put("totalComplaint", totalComplaint);
	        map.put("pending", pending);
	        map.put("inProgress", inProgress);
	        map.put("resolved", resolved);
	        map.put("username", userDao.getUserName(token));
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("error", "Unable to fetch complaint status");
	    }
	    return map;
	}
	
	//this is for admin dashboard or admin can see and track all complaints
	public Map<String, Object> getAdminStatus(String token) {
	    Map<String, Object> map = new HashMap<>();
	    try {
	        long totalComplaint = complaintDao.getTotalComplaints();
	        long pending = complaintDao.getPendingComplaints();
	        long inProgress = complaintDao.getInProgressComplaints();
	        long resolved = complaintDao.getResolvedComplaints();

	        map.put("totalComplaint", totalComplaint);
	        map.put("pending", pending);
	        map.put("inProgress", inProgress);
	        map.put("resolved", resolved);
	        map.put("username", userDao.getUserName(token));
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("error", "Unable to fetch complaint status");
	    }
	    return map;
	}


	//here i make a method that prints all logs that related to this user as a Complaint status
	//basically it prints the log table of particular  user
	public List<Map<String, Object>> getComplaintStatus(String token){
		List<ComplaintLog> logs =complaintDao.getComplaintStatus(token);
		if(logs == null) {
			return Collections.emptyList();
		}
			return logs.stream().map(log -> {
				Map<String, Object> map = new HashMap<>();
		        map.put("title", log.getComplaint().getTitle()); // 👈 Title added here
		        map.put("status", log.getStatus());
		        map.put("remark", log.getRemark());
		        map.put("updatedAt", log.getUpdatedAt().toString());
		        map.put("updatedBy", log.getUpdatedBy());
		        return map;
			}).collect(Collectors.toList());
		
	}
	
	
	// this will return cards value for admin
	
	
	//this method is made for my Complaints functionality so that user can see the complaint
	//instead of logs
	public List<Map<String, Object>> getComplaints(String token){
		List<Complaints> complaints =complaintDao.getComplaint(token);
		if(complaints == null) {
			return Collections.emptyList();
		}
			return complaints.stream().map(complaint -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", complaint.getComplaintId());
		        map.put("title", complaint.getTitle()); // 👈 Title added here
		        map.put("status", complaint.getStatus());
		        map.put("priority", complaint.getPriority());
		        map.put("updatedAt", complaint.getUpdatedAt());
		        map.put("updatedBy", complaint.getUpdatedBy());
		        return map;
			}).collect(Collectors.toList());
		
			
	}
	// this method execute when someone want all user data or complaint details.
	//but right now I made for admin
	public Map<String, Object> getComplaintDetails(String token, int complaintId){
		
		Complaints complaint =complaintDao.getComplaintById(token, complaintId);
		if(complaint == null) {
			return Collections.emptyMap();
		}
			 
				Map<String, Object> map = new HashMap<>();
				map.put("username", complaint.getUser().getFirstName());
		        map.put("title", complaint.getTitle()); // 👈 Title added here
		        map.put("status", complaint.getStatus());
		        map.put("priority", complaint.getPriority());
		        map.put("updatedAt", complaint.getUpdatedAt());
		        map.put("updatedBy", complaint.getUpdatedBy());
		        map.put("createdAt", complaint.getCreatedAt());
		        map.put("description", complaint.getDescription());
		        return map;
			
		
			
	}
	
	// this method for admin to get all user complaints
	public List<Map<String, Object>> getComplaints(){
		List<Complaints> complaints =complaintDao.getComplaint();
		if(complaints == null) {
			return Collections.emptyList();
		}
			return complaints.stream().map(complaint -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", complaint.getComplaintId());
		        map.put("title", complaint.getTitle()); // 👈 Title added here
		        map.put("status", complaint.getStatus());
		        map.put("priority", complaint.getPriority());
		        map.put("updatedAt", complaint.getUpdatedAt());
		        map.put("updatedBy", complaint.getUpdatedBy());
		        return map;
			}).collect(Collectors.toList());
		
			
	}
	public String getUserName(String token) {
		return userDao.getUserName(token);
	}
	public boolean updatePassword(String token, String oldPassword, String newPassword) {
		return userDao.updatePassword(token, oldPassword, newPassword);
	}

	public Map<String, Object> getProfileInfo(String token){
		boolean valid = JwtUtil.isValid(token);
		if(valid) {
			String userName = JwtUtil.getUsername(token);
			User user = userDao.getUserByEmail(userName);
			Map<String, Object> map = new HashMap<>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("email", user.getEmail());
			map.put("phone", user.getPhone());
			map.put("role", user.getRole().getRole().getRoleName());
			map.put("depart", user.getRole().getBranchDepartment().getName());
			return map;
		}
		return Collections.emptyMap();
		
	}
	

	public Map<String, Object> getAdminInfo(String token){
		boolean valid = JwtUtil.isValid(token);
		if(valid) {
			String userName = JwtUtil.getUsername(token);
			User user = userDao.getUserByEmail(userName);
			Map<String, Object> map = new HashMap<>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("email", user.getEmail());
			map.put("phone", user.getPhone());
			map.put("role", user.getRole().getRole().getRoleName());
			
			return map;
		}
		return Collections.emptyMap();
		
	}
	
	public boolean updateProfile(String token, String fname, String lname, String phone) {
		return userDao.updateProfile(token, fname, lname, phone);
	}
}
