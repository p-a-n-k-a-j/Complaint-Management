package com.auth.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.auth.entity.User;
import com.factory.HibernateUtil;
import com.factory.JwtUtil;

public class UserDAO {
	
	public void saveUser(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(user);
		tx.commit();
		session.close();
		
	}
	public User getUserById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = (User) session.get(User.class, id);
		
		return user;
	}
	
	
	public boolean updateProfile(String token, String fname, String lname, String phone) {
	    boolean valid = JwtUtil.isValid(token);
	    UserDAO userDao = new UserDAO();
	    Transaction tx = null;

	    if (valid) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            String username = JwtUtil.getUsername(token);
	            User user = userDao.getUserByEmail(username);

	            if (user != null) {
	                tx = session.beginTransaction();

	                user.setFirstName(fname);
	                user.setLastName(lname);
	                user.setPhone(phone);

	                session.merge(user);
	                tx.commit();

	                return true;
	            }
	        } catch (Exception e) {
	            if (tx != null) tx.rollback();
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	    }

	    return false;
	}

	
	
	public User getUserByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query<User> query = session.createQuery("FROM User WHERE email =:email", User.class);
		query.setParameter("email", email);
		User user = query.uniqueResult();
		session.close();
		
		return user;
	}
	
	public String getUserPassword(String token) {
		boolean valid = JwtUtil.isValid(token);
		String password = "";
		if(valid) {
			String username = JwtUtil.getUsername(token);
			User user = getUserByEmail(username);
			password= user.getPassword();
		}
		return password;
	}
	
	public String getUserName(String token) {
		String name=" ";
		boolean isValid = JwtUtil.isValid(token);
		if(isValid) {
			String username = JwtUtil.getUsername(token);
			User user = getUserByEmail(username);
			name= user.getFirstName();
		}
		return name;
	}
	
	public String getUserRole(String token) {
		String role=" ";
		boolean isValid = JwtUtil.isValid(token);
		if(isValid) {
			String username = JwtUtil.getUsername(token);
			User user = getUserByEmail(username);
			role= user.getRole().getRole().getRoleName();
		}
		return role;
	}
	
	public boolean updatePassword(String token, String oldPassword, String newPassword) {
	    boolean isValid = JwtUtil.isValid(token);
	    if (!isValid) return false;

	    String email = JwtUtil.getUsername(token);

	    String hql = "FROM User WHERE email = :email";
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction transaction = null;

	    try {
	        transaction = session.beginTransaction();

	        Query<User> query = session.createQuery(hql, User.class);
	        query.setParameter("email", email);
	        User user = query.uniqueResult();

	        if (user != null && user.getPassword().equals(oldPassword)) {
	            user.setPassword(newPassword);  
	            session.merge(user);           
	            transaction.commit();
	            return true;
	        }

	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    return false;
	}


}
