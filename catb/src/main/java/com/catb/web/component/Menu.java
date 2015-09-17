package com.catb.web.component;

import java.util.LinkedList;
import java.util.List;

public class Menu {
	
	private Integer id;
	private String title;
	private String url;
	private Integer level;
	private Integer parentId;
	private List<Menu> childMenus = new LinkedList<Menu>();
	
	public Menu() {
		
	}

	public Menu(String title, String url, Integer level, Integer parentId) {
		this.title = title;
		this.url = url;
		this.level = level;
		this.parentId = parentId;
	}

	public Menu(Integer id, String title, String url, Integer level,
			Integer parentId) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.level = level;
		this.parentId = parentId;
	}
	
	public Menu(Integer id, String title, String url, Integer level,
			Integer parentId, List<Menu> childMenus) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.level = level;
		this.parentId = parentId;
		this.childMenus = childMenus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}
}
