package com.catb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "author")
	private String author;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "posted_date")
	private Date postedDate;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "sq_number")
	private Integer sqNumber;
	
	@Column(name = "hot_news")
	private Boolean hotNews;
	
	@Column(name = "status")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "news_catalog_id")
	private NewsCatalog newsCatalog;
	
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "news_content_id")
	private NewsContent newsContent;

	public News() {
		
	}

	public News(Integer id, String title, String summary, String author,
			Date postedDate, String image, Integer sqNumber, Boolean hotNews,
			Integer status, NewsCatalog newsCatalog, NewsContent newsContent) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.author = author;
		this.postedDate = postedDate;
		this.image = image;
		this.sqNumber = sqNumber;
		this.hotNews = hotNews;
		this.status = status;
		this.newsCatalog = newsCatalog;
		this.newsContent = newsContent;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSqNumber() {
		return sqNumber;
	}

	public void setSqNumber(Integer sqNumber) {
		this.sqNumber = sqNumber;
	}

	public Boolean getHotNews() {
		return hotNews;
	}

	public void setHotNews(Boolean hotNews) {
		this.hotNews = hotNews;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public NewsCatalog getNewsCatalog() {
		return newsCatalog;
	}

	public void setNewsCatalog(NewsCatalog newsCatalog) {
		this.newsCatalog = newsCatalog;
	}

	public NewsContent getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(NewsContent newsContent) {
		this.newsContent = newsContent;
	}
}
