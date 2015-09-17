package catb.vanthu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import catb.vanthu.dao.BureauDAO;
import catb.vanthu.dao.util.HibernateUtil;
import catb.vanthu.model.Bureau;

@Repository
public class BureauDAOImpl implements BureauDAO {
	
	static Logger logger = Logger.getLogger(BureauDAOImpl.class.getCanonicalName());
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bureau> getBureaus() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			String queryStr = "FROM Bureau";
			Query query = session.createQuery(queryStr);
			
			List<Bureau> bureaus = (List<Bureau>) query.list();
			
			if (bureaus != null && bureaus.size() != 0) {
				for (Bureau bureau : bureaus) {
					Hibernate.initialize(bureau.getDepartments());
				}
			}
			
			session.getTransaction().commit();
			
			return bureaus;
		} catch (Exception ex) {
			logger.error("Exception : ", ex);
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
