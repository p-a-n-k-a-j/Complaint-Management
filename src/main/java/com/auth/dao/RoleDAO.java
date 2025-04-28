package com.auth.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.auth.entity.Role;
import com.factory.HibernateUtil;

public class RoleDAO {
	
	public Role getRoleByName(String name) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Role role = null;
	    try {
	        session.beginTransaction(); // Start transaction
	        Query<Role> query = session.createQuery("FROM Role WHERE roleName = :roleName", Role.class);
	        query.setParameter("roleName", name);
	        role = query.uniqueResult();
	        session.getTransaction().commit(); // Commit transaction
	    } catch (Exception e) {
	        if (session.getTransaction() != null) session.getTransaction().rollback(); // Rollback on error
	        e.printStackTrace();
	    } finally {
	        session.close(); // Ensure session is closed
	    }
	    return role;
	}

	
	public void saveRole(Role role) {
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Transaction tx = session.beginTransaction();
	    session.merge(role);  
	    tx.commit();
	    session.close();
	}



}
