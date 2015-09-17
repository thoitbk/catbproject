package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.catb.dao.NewsDAO;
import com.catb.model.News;
import com.catb.model.NewsContent;
import com.catb.model.NewsStatus;
import com.catb.vo.SearchNewsVO;

@Repository
public class NewsDAOImpl implements NewsDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addNews(News news) {
		Session session = sessionFactory.getCurrentSession();
		session.save(news);
	}

	public void addNewsContent(NewsContent newsContent) {
		Session session = sessionFactory.getCurrentSession();
		session.save(newsContent);
	}

	@SuppressWarnings("unchecked")
	public List<News> getNews(SearchNewsVO searchNewsVO, Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, searchNewsVO);
		
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		
		criteria.addOrder(Order.asc("sqNumber"));
		criteria.addOrder(Order.desc("id"));
		
		return (List<News>) criteria.list();
	}

	public Long countNews(SearchNewsVO searchNewsVO) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, searchNewsVO);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private Criteria buildCriteria(Session session, SearchNewsVO searchNewsVO) {
		Criteria criteria = session.createCriteria(News.class, "news");
		criteria.createAlias("news.newsCatalog", "newsCatalog");
		criteria.setFetchMode("newsCatalog", FetchMode.JOIN);
		
		if (searchNewsVO.getNewsCatalogId() != null) {
			criteria.add(Restrictions.eq("newsCatalog.id", searchNewsVO.getNewsCatalogId()));
		}
		if (searchNewsVO.getNewsStatus() != null) {
			criteria.add(Restrictions.eq("status", searchNewsVO.getNewsStatus()));
		}
		if (searchNewsVO.getHotNews() != null) {
			criteria.add(Restrictions.eq("hotNews", searchNewsVO.getHotNews()));
		}
		if (searchNewsVO.getAuthor() != null) {
			criteria.add(Restrictions.like("author", "%" + searchNewsVO.getAuthor() + "%"));
		}
		if (searchNewsVO.getTitle() != null) {
			criteria.add(Restrictions.like("title", "%" + searchNewsVO.getTitle() + "%"));
		}
		if (searchNewsVO.getFrom() != null) {
			criteria.add(Restrictions.ge("postedDate", searchNewsVO.getFrom()));
		}
		if (searchNewsVO.getTo() != null) {
			criteria.add(Restrictions.le("postedDate", searchNewsVO.getTo()));
		}
		
		return criteria;
	}

	public News getNewsById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (News) session.get(News.class, id);
	}

	@SuppressWarnings("unchecked")
	public News fetchNewsById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT n " + 
						"FROM News n INNER JOIN FETCH n.newsCatalog INNER JOIN FETCH n.newsContent " + 
						"WHERE n.id = :id";
		Query query = session.createQuery(select);
		query.setParameter("id", id);
		
		List<News> newses = (List<News>) query.list();
		return newses != null && newses.size() > 0 ? newses.get(0) : null;
	}

	public void updateNews(News news) {
		Session session = sessionFactory.getCurrentSession();
		session.update(news);
	}

	public void updateNewsContent(NewsContent newsContent) {
		Session session = sessionFactory.getCurrentSession();
		session.update(newsContent);
	}

	public void deleteNews(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		News news = getNewsById(id);
		if (news != null) {
			session.delete(news);
		}
	}

	@SuppressWarnings("unchecked")
	public List<News> getNewsesByNewsCatalogId(Integer newsCatalogId, Integer size) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT n " + 
					    "FROM News n INNER JOIN n.newsCatalog c " + 
					    "WHERE n.status = :status AND c.id = :newsCatalogId " + 
					    "ORDER BY n.sqNumber ASC, n.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("status", NewsStatus.APPROVED.getStatus());
		query.setParameter("newsCatalogId", newsCatalogId);
		query.setMaxResults(size);
		
		query.setCacheable(true);
		query.setCacheRegion("query.newsesByNewsCatalogId");
		
		return (List<News>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<News> getHotNewses(Integer size) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT n " + 
						"FROM News n INNER JOIN FETCH n.newsCatalog " + 
						"WHERE n.status = :status AND n.hotNews = :hotNews " + 
						"ORDER BY n.sqNumber ASC, n.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("status", NewsStatus.APPROVED.getStatus());
		query.setParameter("hotNews", true);
		query.setMaxResults(size);
		
//		query.setCacheable(true);
//		query.setCacheRegion("query.hotNewses");
		
		return (List<News>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<News> getNewsesByNewsCatalogUrl(String newsCatalogUrl, Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT n " + 
						"FROM News n INNER JOIN FETCH n.newsCatalog c " + 
						"WHERE n.status = :status AND c.url = :url " + 
						"ORDER BY n.sqNumber ASC, n.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("status", NewsStatus.APPROVED.getStatus());
		query.setParameter("url", newsCatalogUrl);
		
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		return (List<News>) query.list();
	}

	public Long countNewsesByNewsCatalogUrl(String newsCatalogUrl) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT COUNT(n) " + 
						"FROM News n INNER JOIN n.newsCatalog c " + 
						"WHERE n.status = :status AND c.url = :url";
		Query query = session.createQuery(select);
		query.setParameter("status", NewsStatus.APPROVED.getStatus());
		query.setParameter("url", newsCatalogUrl);
		
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<News> getNewsesByUrlButId(String newsCatalogUrl, Integer id, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT n " + 
						"FROM News n INNER JOIN FETCH n.newsCatalog c " + 
						"WHERE n.id != :id AND n.status = :status AND c.url = :url " + 
						"ORDER BY n.sqNumber ASC, n.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("id", id);
		query.setParameter("status", NewsStatus.APPROVED.getStatus());
		query.setParameter("url", newsCatalogUrl);
		
		query.setMaxResults(pageSize);
		
		return (List<News>) query.list();
	}
}
