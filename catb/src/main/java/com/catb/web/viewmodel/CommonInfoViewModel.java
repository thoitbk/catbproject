package com.catb.web.viewmodel;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CommonInfoViewModel {
	
	private String webTitle;
	
	private String marqueeTitle;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String recentNews;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String questionAnswer;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String tcCatalogs;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String sameSubjects;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String headlines;
	
	private String headlineCaption;
	
	private String imageCaption;
	
	private String videoCaption;
	
	private String audioCaption;
	
	private String detailsCaption;
	
	private String administrativeProcedures;
	
	private String administrativeProceduresInstruction;
	
	private String views;
	
	private String introduction;

	private String organizationalStructure;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String mostViewed;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String adAmount;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String newsInSameCatalog;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String newsInSearchResult;
	
	private String sameSubjectTitle;
	
	private String today;
	
	private String postedDate;
	
	private String author;

	private String print;
	
	private String homePage;
	
	private String document;
	
	private String legalDocument;
	
	private String goTop;
	
	private String duty;
	
	private String achievement;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String pageSize;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String rightTopSize;
	
	@NotBlank
	@Range(min = 1, max = 100)
	private String rightCenterSize;

	public CommonInfoViewModel() {
		
	}

	public CommonInfoViewModel(String webTitle, String marqueeTitle, String recentNews,
			String questionAnswer, String tcCatalogs, String sameSubjects,
			String headlines, String headlineCaption, String imageCaption,
			String videoCaption, String audioCaption, String detailsCaption,
			String administrativeProcedures,
			String administrativeProceduresInstruction, String views,
			String introduction, String organizationalStructure,
			String mostViewed, String adAmount, String newsInSameCatalog,
			String newsInSearchResult, String sameSubjectTitle, String today,
			String postedDate, String author, String print, String homePage,
			String document, String legalDocument, String goTop, String duty,
			String achievement, String pageSize, String rightTopSize, String rightCenterSize) {
		this.webTitle = webTitle;
		this.marqueeTitle = marqueeTitle;
		this.recentNews = recentNews;
		this.questionAnswer = questionAnswer;
		this.tcCatalogs = tcCatalogs;
		this.sameSubjects = sameSubjects;
		this.headlines = headlines;
		this.headlineCaption = headlineCaption;
		this.imageCaption = imageCaption;
		this.videoCaption = videoCaption;
		this.audioCaption = audioCaption;
		this.detailsCaption = detailsCaption;
		this.administrativeProcedures = administrativeProcedures;
		this.administrativeProceduresInstruction = administrativeProceduresInstruction;
		this.views = views;
		this.introduction = introduction;
		this.organizationalStructure = organizationalStructure;
		this.mostViewed = mostViewed;
		this.adAmount = adAmount;
		this.newsInSameCatalog = newsInSameCatalog;
		this.newsInSearchResult = newsInSearchResult;
		this.sameSubjectTitle = sameSubjectTitle;
		this.today = today;
		this.postedDate = postedDate;
		this.author = author;
		this.print = print;
		this.homePage = homePage;
		this.document = document;
		this.legalDocument = legalDocument;
		this.goTop = goTop;
		this.duty = duty;
		this.achievement = achievement;
		this.pageSize = pageSize;
		this.rightTopSize = rightCenterSize;
		this.rightCenterSize = rightCenterSize;
	}

	public String getWebTitle() {
		return webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}

	public String getMarqueeTitle() {
		return marqueeTitle;
	}

	public void setMarqueeTitle(String marqueeTitle) {
		this.marqueeTitle = marqueeTitle;
	}

	public String getRecentNews() {
		return recentNews;
	}

	public void setRecentNews(String recentNews) {
		this.recentNews = recentNews;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getTcCatalogs() {
		return tcCatalogs;
	}

	public void setTcCatalogs(String tcCatalogs) {
		this.tcCatalogs = tcCatalogs;
	}

	public String getSameSubjects() {
		return sameSubjects;
	}

	public void setSameSubjects(String sameSubjects) {
		this.sameSubjects = sameSubjects;
	}

	public String getHeadlines() {
		return headlines;
	}

	public void setHeadlines(String headlines) {
		this.headlines = headlines;
	}

	public String getHeadlineCaption() {
		return headlineCaption;
	}

	public void setHeadlineCaption(String headlineCaption) {
		this.headlineCaption = headlineCaption;
	}

	public String getImageCaption() {
		return imageCaption;
	}

	public void setImageCaption(String imageCaption) {
		this.imageCaption = imageCaption;
	}

	public String getVideoCaption() {
		return videoCaption;
	}

	public void setVideoCaption(String videoCaption) {
		this.videoCaption = videoCaption;
	}

	public String getAudioCaption() {
		return audioCaption;
	}

	public void setAudioCaption(String audioCaption) {
		this.audioCaption = audioCaption;
	}

	public String getDetailsCaption() {
		return detailsCaption;
	}

	public void setDetailsCaption(String detailsCaption) {
		this.detailsCaption = detailsCaption;
	}

	public String getAdministrativeProcedures() {
		return administrativeProcedures;
	}

	public void setAdministrativeProcedures(String administrativeProcedures) {
		this.administrativeProcedures = administrativeProcedures;
	}

	public String getAdministrativeProceduresInstruction() {
		return administrativeProceduresInstruction;
	}

	public void setAdministrativeProceduresInstruction(
			String administrativeProceduresInstruction) {
		this.administrativeProceduresInstruction = administrativeProceduresInstruction;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOrganizationalStructure() {
		return organizationalStructure;
	}

	public void setOrganizationalStructure(String organizationalStructure) {
		this.organizationalStructure = organizationalStructure;
	}

	public String getMostViewed() {
		return mostViewed;
	}

	public void setMostViewed(String mostViewed) {
		this.mostViewed = mostViewed;
	}

	public String getAdAmount() {
		return adAmount;
	}

	public void setAdAmount(String adAmount) {
		this.adAmount = adAmount;
	}

	public String getNewsInSameCatalog() {
		return newsInSameCatalog;
	}

	public void setNewsInSameCatalog(String newsInSameCatalog) {
		this.newsInSameCatalog = newsInSameCatalog;
	}

	public String getNewsInSearchResult() {
		return newsInSearchResult;
	}

	public void setNewsInSearchResult(String newsInSearchResult) {
		this.newsInSearchResult = newsInSearchResult;
	}

	public String getSameSubjectTitle() {
		return sameSubjectTitle;
	}

	public void setSameSubjectTitle(String sameSubjectTitle) {
		this.sameSubjectTitle = sameSubjectTitle;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getLegalDocument() {
		return legalDocument;
	}

	public void setLegalDocument(String legalDocument) {
		this.legalDocument = legalDocument;
	}

	public String getGoTop() {
		return goTop;
	}

	public void setGoTop(String goTop) {
		this.goTop = goTop;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getRightTopSize() {
		return rightTopSize;
	}

	public void setRightTopSize(String rightTopSize) {
		this.rightTopSize = rightTopSize;
	}

	public String getRightCenterSize() {
		return rightCenterSize;
	}

	public void setRightCenterSize(String rightCenterSize) {
		this.rightCenterSize = rightCenterSize;
	}
}
