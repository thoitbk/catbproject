package com.catb.dao;

import java.util.List;

import com.catb.model.Position;

public interface PositionDAO {
	
	public void addPosition(Position position);
	public List<Position> getPositions();
	public Position getPositionById(Integer id);
	public void updatePosition(Position position);
	public void deletePosition(Integer id);
}
