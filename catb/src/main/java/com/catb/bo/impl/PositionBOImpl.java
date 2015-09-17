package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.PositionBO;
import com.catb.dao.PositionDAO;
import com.catb.model.Position;

@Service
public class PositionBOImpl implements PositionBO {
	
	@Autowired
	private PositionDAO positionDAO;
	
	@Transactional
	public void addPosition(Position position) {
		positionDAO.addPosition(position);
	}
	
	@Transactional
	public List<Position> getPositions() {
		return positionDAO.getPositions();
	}
	
	@Transactional
	public Position getPositionById(Integer id) {
		return positionDAO.getPositionById(id);
	}
	
	@Transactional
	public void updatePosition(Position position) {
		positionDAO.updatePosition(position);
	}
	
	@Transactional
	public void deletePositions(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				positionDAO.deletePosition(id);
			}
		}
	}
}
