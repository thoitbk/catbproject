package com.catb.bo.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.NewsBO;
import com.catb.dao.NewsCatalogDAO;
import com.catb.dao.NewsDAO;
import com.catb.model.News;
import com.catb.model.NewsCatalog;
import com.catb.model.NewsContent;
import com.catb.model.NewsStatus;
import com.catb.vo.RightCenterNews;
import com.catb.vo.SearchNewsVO;
import com.catb.vo.SpecialSiteInfo;
import com.catb.web.component.MenuLoader.DisplayLocation;

@Service
public class NewsBOImpl implements NewsBO {
	
	@Autowired
	private NewsDAO newsDAO;
	
	@Autowired
	private NewsCatalogDAO newsCatalogDAO;
	
	@Transactional
	public void addNews(News news, NewsContent newsContent, Integer newsCatalogId) {
		NewsCatalog newsCatalog = newsCatalogDAO.getNewsCatalogById(newsCatalogId);
		if (newsCatalog != null) {
			newsCatalog.getNewses().add(news);
			news.setNewsCatalog(newsCatalog);
			
			newsDAO.addNewsContent(newsContent);
			news.setNewsContent(newsContent);
			newsDAO.addNews(news);
		}
	}
	
	@Transactional
	public List<News> getNews(SearchNewsVO searchNewsVO, Integer page, Integer pageSize) {
		return newsDAO.getNews(searchNewsVO, page, pageSize);
	}
	
	@Transactional
	public Long countNews(SearchNewsVO searchNewsVO) {
		return newsDAO.countNews(searchNewsVO);
	}
	
	@Transactional
	public News getNewsById(Integer id) {
		return newsDAO.getNewsById(id);
	}
	
	@Transactional
	public News fetchNewsById(Integer id) {
		return newsDAO.fetchNewsById(id);
	}
	
	@Transactional
	public void updateNews(News news, String content, Integer newsCatalogId) {
		News n = newsDAO.getNewsById(news.getId());
		if (n != null) {
			n.setAuthor(news.getAuthor());
			n.setHotNews(news.getHotNews());
			n.setImage(news.getImage());
			n.setPostedDate(news.getPostedDate());
			n.setSqNumber(news.getSqNumber());
			n.setSummary(news.getSummary());
			n.setTitle(news.getTitle());
			
			NewsCatalog newsCatalog = newsCatalogDAO.getNewsCatalogById(newsCatalogId);
			n.setNewsCatalog(newsCatalog);
			
			newsDAO.updateNews(n);
			
			NewsContent newsContent = n.getNewsContent();
			if (newsContent != null) {
				newsContent.setContent(content);
				
				newsDAO.updateNewsContent(newsContent);
			}
		}
	}
	
	@Transactional
	public void deleteNewses(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				newsDAO.deleteNews(id);
			}
		}
	}
	
	@Transactional
	public void updateNewsStatus(NewsStatus newsStatus, Integer newsId) {
		News news = newsDAO.getNewsById(newsId);
		if (news != null) {
			news.setStatus(newsStatus.getStatus());
			newsDAO.updateNews(news);
		}
	}
	
	@Transactional
	public void updateNewsesStatus(NewsStatus newsStatus, Integer[] newsIds) {
		if (newsIds != null && newsIds.length > 0) {
			for (Integer newsId : newsIds) {
				updateNewsStatus(newsStatus, newsId);
			}
		}
	}
	
	@Transactional
	public void updateHotNews(Boolean hotNews, Integer newsId) {
		News news = newsDAO.getNewsById(newsId);
		if (news != null) {
			news.setHotNews(hotNews);
			newsDAO.updateNews(news);
		}
	}
	
	@Transactional
	public List<SpecialSiteInfo> getSpecialSiteInfos(Integer size) {
		List<SpecialSiteInfo> specialSiteInfos = new LinkedList<SpecialSiteInfo>();
		List<NewsCatalog> newsCatalogs = newsCatalogDAO.getNewsCatalogs(true, true);
		if (newsCatalogs != null && newsCatalogs.size() > 0) {
			for (NewsCatalog newsCatalog : newsCatalogs) {
				List<News> newses = newsDAO.getNewsesByNewsCatalogId(newsCatalog.getId(), size);
				if (newses != null) {
					specialSiteInfos.add(new SpecialSiteInfo(newsCatalog, newses));
				}
			}
		}
		
		return specialSiteInfos;
	}
	
	@Transactional
	public List<News> getHotNewses(Integer size) {
		return newsDAO.getHotNewses(size);
	}
	
	@Transactional
	public RightCenterNews getRightCenterNews(Integer size) {
		List<NewsCatalog> newsCatalogs = newsCatalogDAO.getNewsCatalogs(DisplayLocation.RIGHT_CENTER.getPosition(), null, 0, true, 1);
		if (newsCatalogs != null && newsCatalogs.size() == 1) {
			NewsCatalog newsCatalog = newsCatalogs.get(0);
			List<News> newses = newsDAO.getNewsesByNewsCatalogId(newsCatalog.getId(), size);
			return new RightCenterNews(newsCatalog, newses);
		}
		
		return null;
	}
	
	@Transactional
	public List<News> getNewsesByNewsCatalogUrl(String newsCatalogUrl,
			Integer page, Integer pageSize) {
		return newsDAO.getNewsesByNewsCatalogUrl(newsCatalogUrl, page, pageSize);
	}
	
	@Transactional
	public Long countNewsesByNewsCatalogUrl(String newsCatalogUrl) {
		return newsDAO.countNewsesByNewsCatalogUrl(newsCatalogUrl);
	}
	
	@Transactional
	public News fetchNewsByNewsId(Integer id) {
		News news = newsDAO.getNewsById(id);
		if (news != null) {
			Hibernate.initialize(news.getNewsContent());
			Hibernate.initialize(news.getNewsCatalog());
		}
		
		return news;
	}
	
	@Transactional
	public List<News> getNewsesByUrlButId(String newsCatalogUrl, Integer id, Integer pageSize) {
		return newsDAO.getNewsesByUrlButId(newsCatalogUrl, id, pageSize);
	}
}
