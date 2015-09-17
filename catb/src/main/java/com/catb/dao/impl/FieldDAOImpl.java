package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.FieldDAO;
import com.catb.model.Field;

@Repository
public class FieldDAOImpl implements FieldDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addField(Field field) {
		Session session = sessionFactory.getCurrentSession();
		session.save(field);
	}

	@SuppressWarnings("unchecked")
	public List<Field> getFields() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM Field";
		
		return (List<Field>) session.createQuery(select).list();
	}

	public Field getFieldById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Field) session.get(Field.class, id);
	}

	public void updateField(Field field) {
		Session session = sessionFactory.getCurrentSession();
		String update = "UPDATE Field " + 
						"SET code = :code, name = :name, sqNumber = :sqNumber, display = :display, description = :description " + 
						"WHERE id = :id";
		Query query = session.createQuery(update);
		query.setParameter("code", field.getCode());
		query.setParameter("name", field.getName());
		query.setParameter("sqNumber", field.getSqNumber());
		query.setParameter("display", field.getDisplay());
		query.setParameter("description", field.getDescription());
		query.setParameter("id", field.getId());
		
		query.executeUpdate();
	}

	public void deleteField(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Field field = (Field) session.get(Field.class, id);
		if (field != null) {
			session.delete(field);
		}
	}
}
