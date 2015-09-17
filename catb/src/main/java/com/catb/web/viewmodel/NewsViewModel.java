package com.catb.web.viewmodel;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class NewsViewModel {
	
	@NotBlank
	private String newsCatalogId;
	
	@NotBlank
	@Size(min = 0, max = 1000)
	private String title;
	
	@Pattern(regexp = "(^$|[0-9]{0,10})")
	private String sqNumber;
	
	@NotBlank
	@Size(min = 0, max = 5000)
	private String summary;
	
	@Size(min = 0, max = 200)
	private String author;
	
	@Past
	private Date postedDate;
	
	private Boolean hotNews;
	
	@NotBlank
	private String content;

	public NewsViewModel() {
		
	}
	
	public NewsViewModel(String newsCatalogId, String title, String sqNumber,
			String summary, String author, Date postedDate, Boolean hotNews,
			String content) {
		this.newsCatalogId = newsCatalogId;
		this.title = title;
		this.sqNumber = sqNumber;
		this.summary = summary;
		this.author = author;
		this.postedDate = postedDate;
		this.hotNews = hotNews;
		this.content = content;
	}
	
	public String getNewsCatalogId() {
		return newsCatalogId;
	}

	public void setNewsCatalogId(String newsCatalogId) {
		this.newsCatalogId = newsCatalogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(String sqNumber) {
		this.sqNumber = sqNumber;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Boolean getHotNews() {
		return hotNews;
	}

	public void setHotNews(Boolean hotNews) {
		this.hotNews = hotNews;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
