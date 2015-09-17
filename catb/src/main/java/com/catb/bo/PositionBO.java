package com.catb.bo;

import java.util.List;

import com.catb.model.Position;

public interface PositionBO {
	
	public void addPosition(Position position);
	public List<Position> getPositions();
	public Position getPositionById(Integer id);
	public void updatePosition(Position position);
	public void deletePositions(Integer[] ids);
}
