package com.catb.web.viewmodel;

public class SearchNewsViewModel {
	
	private Integer sNewsCatalogId;
	private Integer sNewsStatus;
	private Boolean sHotNews;
	private String sAuthor;
	private String sTitle;
	private String sFrom;
	private String sTo;
	
	public SearchNewsViewModel() {
		
	}

	public SearchNewsViewModel(Integer sNewsCatalogId, Integer sNewsStatus,
			Boolean sHotNews, String sAuthor, String sTitle, String sFrom,
			String sTo) {
		this.sNewsCatalogId = sNewsCatalogId;
		this.sNewsStatus = sNewsStatus;
		this.sHotNews = sHotNews;
		this.sAuthor = sAuthor;
		this.sTitle = sTitle;
		this.sFrom = sFrom;
		this.sTo = sTo;
	}

	public Integer getsNewsCatalogId() {
		return sNewsCatalogId;
	}

	public void setsNewsCatalogId(Integer sNewsCatalogId) {
		this.sNewsCatalogId = sNewsCatalogId;
	}

	public Integer getsNewsStatus() {
		return sNewsStatus;
	}

	public void setsNewsStatus(Integer sNewsStatus) {
		this.sNewsStatus = sNewsStatus;
	}

	public Boolean getsHotNews() {
		return sHotNews;
	}

	public void setsHotNews(Boolean sHotNews) {
		this.sHotNews = sHotNews;
	}

	public String getsAuthor() {
		return sAuthor;
	}

	public void setsAuthor(String sAuthor) {
		this.sAuthor = sAuthor;
	}

	public String getsTitle() {
		return sTitle;
	}

	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getsFrom() {
		return sFrom;
	}

	public void setsFrom(String sFrom) {
		this.sFrom = sFrom;
	}

	public String getsTo() {
		return sTo;
	}

	public void setsTo(String sTo) {
		this.sTo = sTo;
	}
}
