package br.com.stling.util;

import org.hibernate.SessionFactory;
 


public class SessionFactoryUtil {

	 private static SessionFactory sessionFactory;

	    static {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	           // sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
	            
	        } catch (Throwable ex) {
	        	ex.printStackTrace();
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }

	    public static SessionFactory getSessionFactory() {
	    	
//	    	if (sessionFactory.isClosed()) {
//	    		sessionFactory.openSession();
//	    	}
	    	
	        return sessionFactory;
	    }
	    

}
