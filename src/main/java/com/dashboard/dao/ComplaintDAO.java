package com.dashboard.dao;

import java.time.LocalDate;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.auth.dao.UserDAO;
import com.auth.entity.User;
import com.dashboard.entity.ComplaintLog;
import com.dashboard.entity.Complaints;
import com.factory.HibernateUtil;
import com.factory.JwtUtil;
public class ComplaintDAO {

    public void saveComplaint(Complaints complaint) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Create log
            ComplaintLog log = new ComplaintLog();
            log.setComplaint(complaint);
            log.setStatus(complaint.getStatus());
            log.setUpdatedAt(Date.valueOf(LocalDate.now()));
            log.setUpdatedBy(complaint.getUpdatedBy());
            log.setRemark("Initial complaint registered");

            // Add log to complaint
            complaint.getLogs().add(log);

            // Save complaint (along with logs due to cascade)
            session.persist(complaint);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<ComplaintLog> getComplaintLogsByToken(String token) {
        boolean isvalid = JwtUtil.isValid(token);
        if(isvalid) {
        	String username = JwtUtil.getUsername(token);
        	UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(username);

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                String hql = "SELECT log FROM ComplaintLog log WHERE log.complaint.user.id = :userId ORDER BY log.updatedAt ASC";
                Query<ComplaintLog> query = session.createQuery(hql, ComplaintLog.class);
                query.setParameter("userId", user.getId());
                return query.list(); // return logs of that user's complaints
        }
        
        }
        return Collections.emptyList();
    }
    public void updateComplaintStatus(int complaintId, String newStatus, String updatedBy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            
            Complaints complaint = session.get(Complaints.class, complaintId);
            if (complaint != null) {
                complaint.setStatus(newStatus);
                complaint.setUpdatedBy(updatedBy);
                complaint.setUpdatedAt(Date.valueOf(LocalDate.now()));

                // Create log
                ComplaintLog log = new ComplaintLog();
                log.setComplaint(complaint);
                log.setStatus(newStatus);
                log.setUpdatedAt(Date.valueOf(LocalDate.now()));
                log.setUpdatedBy(updatedBy);
                log.setRemark("Status changed to " + newStatus);

                // Add to list
                complaint.getLogs().add(log);

                session.merge(complaint); // save both complaint + log
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    
    
    public long getTotalComplaints(String token) {
        int userId = getUserIdFromToken(token);
        if (userId == -1) return -1;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(c) from Complaints c where c.user.id = :userId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        }
    }

    public long getPendingComplaints(String token) {
        return getCountByStatus("Pending", token);
    }

    public long getInProgressComplaints(String token) {
        return getCountByStatus("InProgress", token);
    }

    public long getResolvedComplaints(String token) {
        return getCountByStatus("Resolved", token);
    }

    private long getCountByStatus(String status, String token) {
        int userId = getUserIdFromToken(token);
        if (userId == -1) return -1;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(c) from Complaints c where c.status = :status AND c.user.id = :userId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("status", status);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        }
        
        
        
    }


    public List<ComplaintLog> getLogsByComplaintId(int complaintId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM ComplaintLog cl WHERE cl.complaint.complaintId = :id ORDER BY cl.updatedAt DESC";
            Query<ComplaintLog> query = session.createQuery(hql, ComplaintLog.class);
            query.setParameter("id", complaintId);
            return query.list();
        }
    }
    public List<ComplaintLog> getRecentUpdateByUsingToken(String token) {
        int userId = getUserIdFromToken(token);
        if (userId == -1) return Collections.emptyList(); // safer than null

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT cl FROM ComplaintLog cl WHERE cl.complaint.user.id = :userId AND DATE(cl.updatedAt) = :today";
            Query<ComplaintLog> query = session.createQuery(hql, ComplaintLog.class);
            query.setParameter("userId", userId);

            java.sql.Date today = Date.valueOf(LocalDate.now());
            query.setParameter("today", today);

            return query.list();
        }
    }
    public List<Complaints> getRecentComplaint() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT cl FROM Complaints cl WHERE  DATE(cl.updatedAt) = :today";
            Query<Complaints> query = session.createQuery(hql, Complaints.class);
           

            java.sql.Date today = Date.valueOf(LocalDate.now());
            query.setParameter("today", today);

            return query.list();
        }
    }
    // this is for user there user track there status
    public List<ComplaintLog> getComplaintStatus(String token) {
        int userId = getUserIdFromToken(token);
        if (userId == -1) return Collections.emptyList(); // safer than null

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT cl FROM ComplaintLog cl WHERE cl.complaint.user.id = :userId";
            Query<ComplaintLog> query = session.createQuery(hql, ComplaintLog.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
    
    
    // this status for admin dashboard:
    public List<Complaints> getComplaintStatus() {
        

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT cl FROM Complaints cl";
            Query<Complaints> query = session.createQuery(hql, Complaints.class);
            
            return query.list();
        }
    }
    //this method I made for show MyComplaints to user in front
    //this is the main table
    public List<Complaints> getComplaint(String token) {
        int userId = getUserIdFromToken(token);
        if (userId == -1) return Collections.emptyList(); // safer than null

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	String hql = "FROM Complaints c WHERE c.user.id = :userId";
            Query<Complaints> query = session.createQuery(hql, Complaints.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
    
    
 
    
    // this method for admin to all user complaints:
    public List<Complaints> getComplaint() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	String hql = "select c FROM Complaints c";
            Query<Complaints> query = session.createQuery(hql, Complaints.class);
            return query.list();
        }
    }

    public int getUserIdFromToken(String token) {
    	boolean valid = JwtUtil.isValid(token);
    	int id=-1;
    	if(valid) {
    		String username = JwtUtil.getUsername(token);
    		UserDAO userDao = new UserDAO();
            User user = userDao.getUserByEmail(username);
            id =user.getId();
    	}
    	return id;
    }

    




//these status count for admin dashboard
public long getTotalComplaints() {

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        String hql = "select count(c) from Complaints c ";
        Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult();
    }
}

public long getPendingComplaints() {
    return getCountByStatus("Pending");
}

public long getInProgressComplaints() {
    return getCountByStatus("InProgress");
}

public long getResolvedComplaints() {
    return getCountByStatus("Resolved");
}

private long getCountByStatus(String status) {
   
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        String hql = "select count(c) from Complaints c where c.status = :status ";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("status", status);
       
        return query.uniqueResult();
    }
}




//it will return a complaint object
public Complaints getComplaintById(int id) {
	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	String hql = "From Complaints c WHERE c.complaint_id =: id";
	Query<Complaints> query = session.createQuery(hql, Complaints.class);
	query.setParameter("id", id);
	return query.uniqueResult();
	}
}

//it for authentication purpose
public Complaints getComplaintById(String token, int id) {
	boolean isValid = JwtUtil.isValid(token);
	if(isValid) {
	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	String hql = "From Complaints c WHERE c.complaintId =: id";
	Query<Complaints> query = session.createQuery(hql, Complaints.class);
	query.setParameter("id", id);
	return query.uniqueResult();
	}
	}
	return null;
}

}





