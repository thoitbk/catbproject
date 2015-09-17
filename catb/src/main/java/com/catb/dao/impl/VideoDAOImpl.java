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

import com.catb.dao.VideoDAO;
import com.catb.model.Video;

@Repository
public class VideoDAOImpl implements VideoDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Video> getVideos(Integer videoCatalogId, Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, videoCatalogId);
		
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		
		criteria.addOrder(Order.asc("sqNumber"));
		criteria.addOrder(Order.desc("id"));
		
		return (List<Video>) criteria.list();
	}
	
	private Criteria buildCriteria(Session session, Integer videoCatalogId) {
		Criteria criteria = session.createCriteria(Video.class, "video");
		
		if (videoCatalogId != null && videoCatalogId >= 0) {
			criteria.createAlias("video.videoCatalog", "videoCatalog");
			criteria.setFetchMode("videoCatalog", FetchMode.JOIN);
			criteria.add(Restrictions.eq("videoCatalog.id", videoCatalogId));
		}
		
		return criteria;
	}

	public Long countVideos(Integer videoCatalogId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = buildCriteria(session, videoCatalogId);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	public void addVideo(Video video) {
		Session session = sessionFactory.getCurrentSession();
		session.save(video);
	}

	public Video getVideoById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Video) session.get(Video.class, id);
	}

	public void updateVideo(Video video) {
		Session session = sessionFactory.getCurrentSession();
		session.update(video);
	}

	public void deleteVideo(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Video video = (Video) session.get(Video.class, id);
		if (video != null) {
			session.delete(video);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Video> getVideos(Integer amount) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT v FROM Video v WHERE v.display = :display ORDER BY sqNumber ASC, id DESC";
		Query query = session.createQuery(select);
		query.setParameter("display", true);
		query.setMaxResults(amount);
		
		return (List<Video>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Video> getVideosByCatalogId(Integer catalogId) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT v FROM Video v INNER JOIN v.videoCatalog c WHERE c.id = :id AND v.display = :display";
		Query query = session.createQuery(select);
		query.setParameter("id", catalogId);
		query.setParameter("display", true);
		
		return (List<Video>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Video> getVideos(Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT v FROM Video v WHERE display = :display ORDER BY sqNumber ASC, id DESC";
		Query query = session.createQuery(select);
		query.setParameter("display", true);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		return (List<Video>) query.list();
	}

	public Long countVideos() {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT COUNT(v) FROM Video v WHERE display = :display";
		Query query = session.createQuery(select);
		query.setParameter("display", true);
		
		return (Long) query.uniqueResult();
	}
}
