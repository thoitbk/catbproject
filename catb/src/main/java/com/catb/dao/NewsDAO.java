package com.catb.dao;

import java.util.List;

import com.catb.model.News;
import com.catb.model.NewsContent;
import com.catb.vo.SearchNewsVO;

public interface NewsDAO {
	
	public void addNews(News news);
	public void addNewsContent(NewsContent newsContent);
	public List<News> getNews(SearchNewsVO searchNewsVO, Integer page, Integer pageSize);
	public Long countNews(SearchNewsVO searchNewsVO);
	public News getNewsById(Integer id);
	public News fetchNewsById(Integer id);
	public void updateNews(News news);
	public void updateNewsContent(NewsContent newsContent);
	public void deleteNews(Integer id);
	public List<News> getNewsesByNewsCatalogId(Integer newsCatalogId, Integer size);
	public List<News> getHotNewses(Integer size);
	public List<News> getNewsesByNewsCatalogUrl(String newsCatalogUrl, Integer page, Integer pageSize);
	public List<News> getNewsesByUrlButId(String newsCatalogUrl, Integer id, Integer pageSize);
	public Long countNewsesByNewsCatalogUrl(String newsCatalogUrl);
}
