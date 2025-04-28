package com.auth.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.auth.entity.UserRole;
import com.factory.HibernateUtil;

public class UserRoleDAO {
	
	public void saveUSerRole(UserRole userRole) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(userRole);
		tx.commit();
		session.close();
		
	}

}
