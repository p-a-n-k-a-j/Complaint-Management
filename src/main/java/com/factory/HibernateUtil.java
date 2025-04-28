package com.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private HibernateUtil() {
		throw new IllegalAccessError("Trying to break Signleton pattern");
	}
	
	private static SessionFactory factory;
	
	
	public static SessionFactory getSessionFactory() {
	    if (factory == null) {
	        try {
	        	factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	        } catch (Exception e) {
	            throw new RuntimeException("Failed to create SessionFactory", e);
	        }
	    }
	    return factory;
	}

	 public static void close() {
	        if (factory != null && !factory.isClosed()) {
	            factory.close();
	        }
	    }
	


}
