package br.com.stling.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.stling.bean.Produto;
import br.com.stling.util.HibernateFactory;

/**
 * Home object for domain model class ProProduto.
 * @see mobi.liveideas.promoter.dao.ProProduto
 * @author Hibernate Tools
 */
public class ProdutoDao extends AbstractDao {

	public Produto findByDescricao(String descricao) throws Exception {
		
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Produto u WHERE u.descricao = :descricao")
					.setParameter("descricao", descricao);
			Produto instance = (Produto) q.uniqueResult();
			
			return instance;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
	}
	
	public Produto findById(Integer id) throws Exception {
		
		Session session = HibernateFactory.openSession();
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("Select u FROM Produto u WHERE u.id = :id")
					.setParameter("id", id);
			Produto instance = (Produto) q.uniqueResult();
			
			return instance;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			HibernateFactory.close(session);
		}
	}
}
