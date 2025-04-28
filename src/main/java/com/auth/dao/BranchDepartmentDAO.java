package com.auth.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.auth.entity.BranchDepartment;
import com.factory.HibernateUtil;



public class BranchDepartmentDAO {
	public BranchDepartment getBranchByName(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Query<BranchDepartment> query = session.createQuery("FROM BranchDepartment WHERE name =:name", BranchDepartment.class);
        query.setParameter("name", name);
        BranchDepartment branch = query.uniqueResult();
        session.close();
        
        return branch;
    }
	
	public void saveBranchDepartment(BranchDepartment branchDepartment) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.persist(branchDepartment);
		tx.commit();
		session.close();
	
	}
}
