package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.PositionDAO;
import com.catb.model.Position;

@Repository
public class PositionDAOImpl implements PositionDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addPosition(Position position) {
		Session session = sessionFactory.getCurrentSession();
		session.save(position);
	}

	@SuppressWarnings("unchecked")
	public List<Position> getPositions() {
		Session session = sessionFactory.getCurrentSession();
		String query = "FROM Position";
		
		return session.createQuery(query).list();
	}

	public Position getPositionById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Position position = (Position) session.get(Position.class, id);
		
		return position;
	}

	public void updatePosition(Position position) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE Position " + 
					    "SET name = :name, code = :code, description = :description " + 
					    "WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("name", position.getName());
		query.setParameter("code", position.getCode());
		query.setParameter("description", position.getDescription());
		query.setParameter("id", position.getId());
		
		query.executeUpdate();
	}

	public void deletePosition(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String delete = "DELETE FROM Position " + 
						"WHERE id = :id";
		Query query = session.createQuery(delete);
		query.setParameter("id", id);
		
		query.executeUpdate();
	}
}
