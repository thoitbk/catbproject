package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.CriminalDenouncementBO;
import com.catb.dao.CriminalDenouncementDAO;
import com.catb.model.CriminalDenouncement;

@Service
public class CriminalDenouncementBOImpl implements CriminalDenouncementBO {
	
	@Autowired
	private CriminalDenouncementDAO criminalDenouncementDAO;
	
	@Transactional
	public void addCriminalDenouncement(CriminalDenouncement criminalDenouncement) {
		criminalDenouncementDAO.addCriminalDenouncement(criminalDenouncement);
	}
	
	@Transactional
	public List<CriminalDenouncement> getCriminalDenouncements(String title, Integer page, Integer pageSize) {
		return criminalDenouncementDAO.getCriminalDenouncements(title, page, pageSize);
	}
	
	@Transactional
	public Long countCriminalDenouncements(String title) {
		return criminalDenouncementDAO.countCriminalDenouncements(title);
	}
	
	@Transactional
	public CriminalDenouncement getCriminalDenouncement(Integer id) {
		return criminalDenouncementDAO.getCriminalDenouncement(id);
	}
	
	@Transactional
	public void updateCriminalDenouncement(CriminalDenouncement criminalDenouncement) {
		CriminalDenouncement c = criminalDenouncementDAO.getCriminalDenouncement(criminalDenouncement.getId());
		if (c != null) {
			c.setReplyContent(criminalDenouncement.getReplyContent());
			c.setStatus(criminalDenouncement.getStatus());
			criminalDenouncementDAO.updateCriminalDenouncement(c);
		}
	}
	
	@Transactional
	public void updateCriminalDenouncementStatus(CriminalDenouncement criminalDenouncement) {
		CriminalDenouncement c = criminalDenouncementDAO.getCriminalDenouncement(criminalDenouncement.getId());
		if (c != null) {
			c.setStatus(criminalDenouncement.getStatus());
			criminalDenouncementDAO.updateCriminalDenouncement(c);
		}
	}
	
	@Transactional
	public void deleteCriminalDenouncements(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				criminalDenouncementDAO.deleteCriminalDenouncement(id);
			}
		}
	}
}
