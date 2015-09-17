package com.catb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "news_catalog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NewsCatalog implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "display")
	private Boolean display;
	
	@Column(name = "special_site")
	private Boolean specialSite;
	
	@Column(name = "display_location")
	private String displayLocation;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
	@Column(name = "child_level")
	private Integer childLevel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "newsCatalog")
	private Set<News> newses = new HashSet<News>();

	public NewsCatalog() {
		
	}

	public NewsCatalog(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public NewsCatalog(Integer id, String name, String url, Integer sqNumber,
			Boolean display, Boolean specialSite, String displayLocation,
			Integer parentId, Integer childLevel) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.sqNumber = sqNumber;
		this.display = display;
		this.specialSite = specialSite;
		this.displayLocation = displayLocation;
		this.parentId = parentId;
		this.childLevel = childLevel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Boolean getSpecialSite() {
		return specialSite;
	}

	public void setSpecialSite(Boolean specialSite) {
		this.specialSite = specialSite;
	}

	public String getDisplayLocation() {
		return displayLocation;
	}

	public void setDisplayLocation(String displayLocation) {
		this.displayLocation = displayLocation;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getChildLevel() {
		return childLevel;
	}

	public void setChildLevel(Integer childLevel) {
		this.childLevel = childLevel;
	}

	public Set<News> getNewses() {
		return newses;
	}

	public void setNewses(Set<News> newses) {
		this.newses = newses;
	}
}
