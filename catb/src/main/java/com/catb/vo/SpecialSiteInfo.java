package com.catb.vo;

import java.util.List;

import com.catb.model.News;
import com.catb.model.NewsCatalog;

public class SpecialSiteInfo {
	
	private NewsCatalog newsCatalog;
	private List<News> newses;
	
	public SpecialSiteInfo() {
		
	}

	public SpecialSiteInfo(NewsCatalog newsCatalog, List<News> newses) {
		this.newsCatalog = newsCatalog;
		this.newses = newses;
	}

	public NewsCatalog getNewsCatalog() {
		return newsCatalog;
	}

	public void setNewsCatalog(NewsCatalog newsCatalog) {
		this.newsCatalog = newsCatalog;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}
}
