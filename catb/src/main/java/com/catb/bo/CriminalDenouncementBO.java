package com.catb.bo;

import java.util.List;

import com.catb.model.CriminalDenouncement;

public interface CriminalDenouncementBO {
	
	public void addCriminalDenouncement(CriminalDenouncement criminalDenouncement);
	public List<CriminalDenouncement> getCriminalDenouncements(String title, Integer page, Integer pageSize);
	public Long countCriminalDenouncements(String title);
	public CriminalDenouncement getCriminalDenouncement(Integer id);
	public void updateCriminalDenouncement(CriminalDenouncement criminalDenouncement);
	public void updateCriminalDenouncementStatus(CriminalDenouncement criminalDenouncement);
	public void deleteCriminalDenouncements(Integer[] ids);
}
