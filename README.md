 Complaint Management System

A secure and dynamic web application that allows users to register complaints, track their status, and manage updates through role-based access (User, Admin, Super-Admin). Built using Java, JSP, Servlets, Hibernate, PostgreSQL, and JWT authentication.

---

🚀 Features

- User Registration with Email Verification  
- Secure Login Authentication using JWT Tokens
- Role-Based Access Control (User / Admin / Super-Admin)
- Dynamic Dashboard with Complaint Statistics  
- Create, View, Search, and Track Complaints 
- Complaint Status Updates and Full Activity Log  
- Profile Management (View and Edit Profile Info) 
- Change Password and Logout Safely  
- Secure Session Management via Cookies and Filters

---

🛠️ Technologies Used

- Java (Core + Advanced)
- JSP and Servlets
- Hibernate (ORM Framework)
- PostgreSQL (Database)
- JWT (JSON Web Tokens) (Authentication)
- HTML, CSS, JavaScript (Frontend)

---

 ⚙️ How It Works

1. User Registration: 
   Users sign up with valid email (verified through email verification key).

2. Login and Token Generation:  
   Upon successful login, a JWT token is generated and stored in browser cookies.

3. Middleware Token Validation: 
   Every request checks token validity before accessing protected resources.

4. Dashboard:  
   Users/Admins/Super-Admins can view complaint status cards and latest updates.

5. Complaint Management:  
   - Users can create, search, and track their complaints.  
   - Admins/Super-Admins can update complaint statuses and manage complaints across users.

6. Profile and Security:  
   Users can edit their profile, change password, and securely logout (token deletion).

---

 📂 Setup Instructions

1. Clone this repository
   ```bash
   git clone https://github.com/p-a-n-k-a-j/Complaint-Management.git
   ```

2. Import project into IDE** (Eclipse or IntelliJ IDEA).

3. Configure Database:
   - Install PostgreSQL.
   - Create a database named `complaint_management`.
   - Update Hibernate configuration file (`hibernate.cfg.xml`) with your database credentials.

4. Add Libraries:
   - Include Hibernate, JWT, Servlet API jars.

5. Run Project:
   - Deploy on Apache Tomcat Server.
   - Access the application via browser.

---

📌 Folder Structure (Example)

```
src/
 ├── com.cms.controller
 ├── com.cms.dao
 ├── com.cms.model
 ├── com.cms.filter
 ├── com.cms.utility
WebContent/
 ├── jsp/
 ├── css/
 ├── js/
 └── images/
hibernate.cfg.xml
web.xml
```

---
👨‍💻 Developed by
   Pankaj Tirdiya  
- [LinkedIn](https://www.linkedin.com/in/pankaj-tirdiya-787543227)  
- [GitHub](https://github.com/p-a-n-k-a-j)

