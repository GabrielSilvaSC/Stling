package br.com.stling.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.stling.bean.Cliente;
import br.com.stling.bean.Produto;
import br.com.stling.util.HibernateFactory;

/**
 * Home object for domain model class ProUsuario.
 * @see mobi.liveideas.promoter.dao.ProUsuario
 * @author Hibernate Tools
 */
public class ClienteDao extends AbstractDao {

	public Cliente merge(Cliente obj) {
		return (Cliente)super.merge(obj);
	}
	
	public Cliente findClienteById(Integer id) throws Exception {
		
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Cliente u WHERE u.id = :id")
					.setParameter("id", id);
			Cliente instance = (Cliente) q.uniqueResult();
			
			return instance;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
	}
	
	public Cliente findClienteByNome(String nome) throws Exception {
		Cliente instance = null;
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Cliente u WHERE u.nome = :nome")
					.setParameter("nome", nome);
			instance = (Cliente) q.uniqueResult();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
		
		return instance;
	}
	
	public Cliente existsCpfCnpJ(String cpfCnpj) throws Exception {
		Cliente instance = null;
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Cliente u WHERE u.cpfCnpj = :cpfCnpj")
					.setParameter("cpfCnpj", cpfCnpj);
			instance = (Cliente) q.uniqueResult();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
		
		return instance;
	}
}
