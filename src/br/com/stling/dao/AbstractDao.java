package br.com.stling.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.stling.util.HibernateFactory;

public abstract class AbstractDao {
    private Session session;
    private Transaction tx;

    public AbstractDao() {
        HibernateFactory.buildIfNeeded();
    }
    
    protected void persist(Object obj) {
    	try {
            startOperation();
            session.persist(obj);
            tx.commit();
        } catch (HibernateException e) {
        	e.printStackTrace();
        	tx.rollback();
        } catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback();
        } finally {
            HibernateFactory.close(session);
        }
    }

    protected Object merge(Object obj) {
    	Object retObj = null;
    	try {
            startOperation();
            retObj = session.merge(obj);
            tx.commit();
        } catch (HibernateException e) {
        	e.printStackTrace();
        	tx.rollback();
        } catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback();
        } finally {
            HibernateFactory.close(session);
        }
    	return retObj;
    }

    public void delete(Object obj) {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
        	tx.rollback();
        	e.printStackTrace();
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
    }
    
    public Object findById(Class<?> clazz, int id) {
		Session session = HibernateFactory.openSession();
		try {
			
			session.beginTransaction();
			Object instance =  session.get(clazz, id);
			
			return instance;
		} catch (RuntimeException re) {
			throw re;
		} finally {
            HibernateFactory.close(session);
        }
	}

    @SuppressWarnings("unchecked")
	protected List<Object> findAll(Class<?> clazz) {
        List<Object> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }
    

    protected void handleException(HibernateException e) throws HibernateException {
        HibernateFactory.rollback(tx);
        throw new HibernateException(e);
    }

    protected void startOperation() throws HibernateException {
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
    }
   
    
}

