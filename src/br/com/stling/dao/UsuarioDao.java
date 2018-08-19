package br.com.stling.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.stling.bean.Usuario;
import br.com.stling.util.HibernateFactory;

/**
 * Home object for domain model class ProUsuario.
 * @see mobi.liveideas.promoter.dao.ProUsuario
 * @author Hibernate Tools
 */
public class UsuarioDao extends AbstractDao {

	public Usuario findByEmailPassword(Usuario usuario) throws Exception {
		
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Usuario u WHERE u.email = :email and u.senha = :senha")
					.setParameter("email", usuario.getEmail()).setParameter("senha", usuario.getSenha());
			Usuario instance = (Usuario) q.uniqueResult();
			
			if (instance != null) {
				Hibernate.initialize(instance.getTipo());
			}
			
			return instance;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
	}	
}
