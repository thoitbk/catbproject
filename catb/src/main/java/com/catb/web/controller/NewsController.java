package com.catb.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.bo.NewsBO;
import com.catb.bo.NewsCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.common.exception.AppException;
import com.catb.model.News;
import com.catb.model.NewsCatalog;
import com.catb.model.NewsContent;
import com.catb.model.NewsStatus;
import com.catb.vo.SearchNewsVO;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.FileMeta;
import com.catb.web.viewmodel.NewsViewModel;
import com.catb.web.viewmodel.SearchNewsViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class NewsController {
	
	@Autowired
	private NewsCatalogBO newsCatalogBO;
	
	@Autowired
	private NewsBO newsBO;
	
	@ModelAttribute("newsCatalogs")
	public Map<Integer, String> populateNewsCatalogs() {
		List<NewsCatalog> newsCatalogs = newsCatalogBO.getNewsCatalog(null, null);
		Map<Integer, String> newsCatalogsMap = new LinkedHashMap<Integer, String>();
		for (NewsCatalog newsCatalog : newsCatalogs) {
			newsCatalogsMap.put(newsCatalog.getId(), newsCatalog.getName());
		}
		
		return newsCatalogsMap;
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("newsStatuses")
	public Map<Integer, String> populateNewsStatus(HttpServletRequest request) {
		Map<Integer, String> newsStatuses = (Map<Integer, String>) request.getServletContext().getAttribute("NEWS_STATUSES");
		return newsStatuses;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequiresPermissions(value = {"news:create"})
	@RequestMapping(value = "/cm/news/create", method = RequestMethod.GET)
	public ModelAndView showCreateNews(ModelMap model, HttpServletRequest request) {
		NewsViewModel newsViewModel = new NewsViewModel();
		newsViewModel.setPostedDate(new Date());
		model.addAttribute("newsViewModel", newsViewModel);
		request.getSession().removeAttribute("newsImage");
		
		return new ModelAndView("cm/news/create");
	}
	
	@RequiresPermissions(value = {"news:create"})
	@RequestMapping(value = "/cm/news/create", method = RequestMethod.POST)
	public ModelAndView processCreateNews(
			@Valid @ModelAttribute("newsViewModel") NewsViewModel newsViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("cm/news/create");
		} else {
			News news = new News();
			if (newsViewModel.getSqNumber() != null && !"".equals(newsViewModel.getSqNumber().trim())) {
				news.setSqNumber(Integer.parseInt(newsViewModel.getSqNumber().trim()));
			} else {
				news.setSqNumber(Constants.MAX_SQ_NUMBER);
			}
			news.setTitle(newsViewModel.getTitle());
			news.setSummary(newsViewModel.getSummary());
			news.setAuthor(newsViewModel.getAuthor());
			news.setPostedDate(newsViewModel.getPostedDate());
			news.setHotNews(newsViewModel.getHotNews());
			news.setStatus(NewsStatus.PENDING.getStatus());
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("newsImage");
			if (fileMeta != null) {
				news.setImage(fileMeta.getPath());
				request.getSession().removeAttribute("newsImage");
			}
			
			NewsContent newsContent = new NewsContent();
			newsContent.setContent(newsViewModel.getContent());
			
			newsBO.addNews(news, newsContent, Integer.parseInt(newsViewModel.getNewsCatalogId()));
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("news.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/news/create"));
		}
	}
	
	@RequiresPermissions(value = {"news:create"})
	@RequestMapping(value = "/cm/news/uploadNewsImage", method = RequestMethod.POST)
	@ResponseBody
	public FileMeta uploadNewsImage(HttpServletRequest request, HttpServletResponse response) {
		List<FileMeta> files = getUploadFiles(request);
		
		if (files != null && files.size() > 0) {
			request.getSession().setAttribute("newsImage", files.get(0));
			return files.get(0);
		}
		
		return null;
	}
	
	@RequiresPermissions(value = {"news:create"})
	@RequestMapping(value = "/cm/news/removeNewsImage", method = RequestMethod.POST)
	@ResponseBody
	public Status removeNewsImage(HttpServletRequest request) {
		FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("newsImage");
		if (fileMeta != null) {
			String relativePath = fileMeta.getPath();
			String absolutePath = request.getServletContext().getRealPath(relativePath);
			File file = new File(absolutePath);
			if (file.exists()) {
				file.delete();
			}
			
			request.getSession().removeAttribute("newsImage");
		}
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@SuppressWarnings("unchecked")
	private List<FileMeta> getUploadFiles(HttpServletRequest request) {
		List<FileMeta> files = new LinkedList<FileMeta>();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		FileMeta temp = null;
		
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			try {
				List<FileItem> items = upload.parseRequest(request);
				
				for (FileItem item : items) {
					if (!item.isFormField()) {
						String dirName = Util.getCurrentDateString();
						String fileName = item.getName();
						String randomString = Util.getRandomString();
						String newFileName = randomString + "." + FilenameUtils.getExtension(fileName);
						String imageUrl = String.format("%s/%s/%s", Constants.NEWS_IMAGE_PATH, dirName, newFileName);
						
						File file = createFile(dirName, newFileName);
						if (!file.exists()) {
							item.write(file);
							temp = new FileMeta(
									randomString, fileName, String.valueOf(item.getSize() / 1024), 
									item.getContentType(), imageUrl);
						}
						files.add(temp);
					}
				}
			} catch (Exception ex) {
				throw new AppException(ex);
			}
		}
		
		return files;
	}
	
	private File createFile(String dirName, String fileName) {
		String directory = Constants.NEWS_IMAGE_LOCATION + File.separator + dirName;
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		String absolutePath = directory + File.separator + fileName;
		File file = new File(absolutePath);
		
		return file;
	}
	
	@RequiresPermissions(value = {"news:manage"})
	@RequestMapping(value = "/cm/news/manage", method = RequestMethod.GET)
	public ModelAndView manageNews(
						SearchNewsViewModel searchNewsViewModel,
						@RequestParam(value = "p", defaultValue = "1", required = false) Integer page,
						ModelMap model, HttpServletRequest request) {
		handleSearchNews(searchNewsViewModel, model, request, page);
		
		return new ModelAndView("cm/news/manage");
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/approve", method = RequestMethod.GET)
	public ModelAndView showApproveNews(
			SearchNewsViewModel searchNewsViewModel,
			@RequestParam(value = "p", defaultValue = "1", required = false) Integer page,
			ModelMap model, HttpServletRequest request) {
		handleSearchNews(searchNewsViewModel, model, request, page);
		
		return new ModelAndView("cm/news/approve");
	}
	
	private void handleSearchNews(SearchNewsViewModel searchNewsViewModel, ModelMap model, HttpServletRequest request, Integer page) {
		SearchNewsViewModel viewModel = new SearchNewsViewModel();
		SearchNewsVO searchNewsVO = new SearchNewsVO();
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (searchNewsViewModel.getsNewsCatalogId() != null && searchNewsViewModel.getsNewsCatalogId() >= 0) {
			searchNewsVO.setNewsCatalogId(searchNewsViewModel.getsNewsCatalogId());
			viewModel.setsNewsCatalogId(searchNewsViewModel.getsNewsCatalogId());
			params.put("sNewsCatalogId", String.valueOf(searchNewsViewModel.getsNewsCatalogId()));
		}
		if (searchNewsViewModel.getsNewsStatus() != null && searchNewsViewModel.getsNewsStatus() >= 0) {
			searchNewsVO.setNewsStatus(searchNewsViewModel.getsNewsStatus());
			viewModel.setsNewsStatus(searchNewsViewModel.getsNewsStatus());
			params.put("sNewsStatus", String.valueOf(searchNewsViewModel.getsNewsStatus()));
		}
		if (searchNewsViewModel.getsHotNews() != null) {
			searchNewsVO.setHotNews(searchNewsViewModel.getsHotNews());
			viewModel.setsHotNews(searchNewsViewModel.getsHotNews());
			params.put("sHotNews", String.valueOf(searchNewsViewModel.getsHotNews()));
		}
		if (searchNewsViewModel.getsAuthor() != null && !"".equals(searchNewsViewModel.getsAuthor().trim())) {
			searchNewsVO.setAuthor(searchNewsViewModel.getsAuthor().trim());
			viewModel.setsAuthor(searchNewsViewModel.getsAuthor());
			params.put("sAuthor", searchNewsViewModel.getsAuthor());
		}
		if (searchNewsViewModel.getsTitle() != null && !"".equals(searchNewsViewModel.getsTitle().trim())) {
			searchNewsVO.setTitle(searchNewsViewModel.getsTitle().trim());
			viewModel.setsTitle(searchNewsViewModel.getsTitle());
			params.put("sTitle", searchNewsViewModel.getsTitle());
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (searchNewsViewModel.getsFrom() != null && !"".equals(searchNewsViewModel.getsFrom().trim())) {
			try {
				Date from = dateFormat.parse(searchNewsViewModel.getsFrom().trim());
				searchNewsVO.setFrom(from);
				viewModel.setsFrom(searchNewsViewModel.getsFrom());
				params.put("sFrom", searchNewsViewModel.getsFrom());
			} catch (Exception ex) {
				// Ignore this exception
			}
		}
		if (searchNewsViewModel.getsTo() != null && !"".equals(searchNewsViewModel.getsTo().trim())) {
			try {
				Date to = dateFormat.parse(searchNewsViewModel.getsTo().trim());
				searchNewsVO.setTo(to);
				viewModel.setsTo(searchNewsViewModel.getsTo());
				params.put("sTo", searchNewsViewModel.getsTo());
			} catch (Exception ex) {
			}
		}
		
		Integer pageSize = Util.getPageSize(request);
		List<News> newses = newsBO.getNews(searchNewsVO, page, pageSize);
		PageInfo pageInfo = new PageInfo(newsBO.countNews(searchNewsVO), page, pageSize);
		
		model.addAttribute("searchNewsViewModel", viewModel);
		model.addAttribute("newses", newses);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("params", params);
	}
	
	@RequiresPermissions(value = {"news:manage", "news:approve"}, logical = Logical.OR)
	@RequestMapping(value = "/cm/news/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateNews(@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		NewsViewModel newsViewModel = new NewsViewModel();
		News news = newsBO.fetchNewsById(id);
		
		request.getSession().removeAttribute("newsImage");
		
		if (news != null) {
			newsViewModel.setNewsCatalogId(String.valueOf(news.getNewsCatalog().getId()));
			if (news.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(news.getSqNumber())) {
				newsViewModel.setSqNumber(String.valueOf(news.getSqNumber()));
			}
			newsViewModel.setTitle(news.getTitle());
			newsViewModel.setSummary(news.getSummary());
			newsViewModel.setAuthor(news.getAuthor());
			newsViewModel.setHotNews(news.getHotNews());
			newsViewModel.setPostedDate(news.getPostedDate());
			newsViewModel.setContent(news.getNewsContent().getContent());
			if (news.getImage() != null) {
				request.getSession().setAttribute("newsImage", new FileMeta(null, null, null, null, news.getImage()));
			}
		}
		model.addAttribute("newsViewModel", newsViewModel);
		
		return new ModelAndView("cm/news/update");
	}
	
	@RequiresPermissions(value = {"news:manage", "news:approve"}, logical = Logical.OR)
	@RequestMapping(value = "/cm/news/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateNews(@PathVariable("id") Integer id, @Valid @ModelAttribute("newsViewModel") NewsViewModel newsViewModel, 
										BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("cm/news/update");
		} else {
			News news = new News();
			news.setId(id);
			news.setTitle(newsViewModel.getTitle());
			news.setSummary(newsViewModel.getSummary());
			news.setAuthor(newsViewModel.getAuthor());
			news.setPostedDate(newsViewModel.getPostedDate());
			news.setHotNews(newsViewModel.getHotNews());
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("newsImage");
			if (fileMeta != null) {
				news.setImage(fileMeta.getPath());
			}
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (newsViewModel.getSqNumber() != null && !"".equals(newsViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(newsViewModel.getSqNumber().trim());
			}
			news.setSqNumber(sqNumber);
			
			newsBO.updateNews(news, newsViewModel.getContent(), Integer.parseInt(newsViewModel.getNewsCatalogId()));
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("news.updated.successfully"));
			
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			
			String ap = request.getParameter("ap");
			String redirectUrl = ap != null && "1".equals(ap) ? "/cm/news/approve" : "/cm/news/manage";
			
			return new ModelAndView(new RedirectView(request.getContextPath() + redirectUrl + queryString));
		}
	}
	
	@RequiresPermissions(value = {"news:manage"})
	@RequestMapping(value = "/cm/news/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteNews(@RequestParam("ids") Integer[] ids, HttpSession session) {
		newsBO.deleteNewses(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("news.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"news:manage", "news:approve"}, logical = Logical.OR)
	@RequestMapping(value = "/cm/news/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewNews(@PathVariable("id") Integer id, ModelMap model) {
		News news = newsBO.fetchNewsById(id);
		model.addAttribute("news", news);
		
		return new ModelAndView("cm/news/view");
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/approve/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Status approveNews(@PathVariable("id") Integer id) {
		newsBO.updateNewsStatus(NewsStatus.APPROVED, id);
		
		Status status = new Status(Status.OK, PropertiesUtil.getProperty("news.approved.successfully"));
		return status;
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/deny/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Status denyNews(@PathVariable("id") Integer id) {
		newsBO.updateNewsStatus(NewsStatus.DENIED, id);
		
		Status status = new Status(Status.OK, PropertiesUtil.getProperty("news.denied.successfully"));
		return status;
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/approveSelected", method = RequestMethod.POST)
	@ResponseBody
	public Status approveNewses(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {
		newsBO.updateNewsesStatus(NewsStatus.APPROVED, ids);
		
		request.getSession().setAttribute("msg", PropertiesUtil.getProperty("newses.approved.successfully"));
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/denySelected", method = RequestMethod.POST)
	@ResponseBody
	public Status denyNewses(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {
		newsBO.updateNewsesStatus(NewsStatus.DENIED, ids);
		
		request.getSession().setAttribute("msg", PropertiesUtil.getProperty("newses.denied.successfully"));
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"news:approve"})
	@RequestMapping(value = "/cm/news/updateHotNews", method = RequestMethod.POST)
	@ResponseBody
	public Status updateHotNews(
			@RequestParam(value = "hot", required = false, defaultValue = "false") Boolean isHotNews, 
			@RequestParam(value = "id", required = true) Integer newsId, HttpServletRequest request) {
		newsBO.updateHotNews(isHotNews, newsId);
		
		request.getSession().setAttribute("msg", PropertiesUtil.getProperty("hotNews.updated.successfully"));
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequestMapping(value = Constants.NEWS_PREFIX + "/{url}", method = RequestMethod.GET)
	public ModelAndView showNewsesByNewsCatalogUrl(
			@PathVariable("url") String url, ModelMap model, HttpServletRequest request, 
			@RequestParam(value = "p", defaultValue = "1", required = false) Integer page) {
		NewsCatalog newsCatalog = newsCatalogBO.getNewsCatalogByUrl(url);
		if (newsCatalog == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/notFound"));
		}
		Integer pageSize = Util.getNewsListSize(request);
		List<News> newses = newsBO.getNewsesByNewsCatalogUrl(url, page, pageSize);
		PageInfo pageInfo = new PageInfo(newsBO.countNewsesByNewsCatalogUrl(url), page, pageSize);
		
		model.addAttribute("newsCatalog", newsCatalog);
		model.addAttribute("newses", newses);
		model.addAttribute("pageInfo", pageInfo);
		
		return new ModelAndView("showNewsesByNewsCatalog");
	}
	
	@RequestMapping(value = {Constants.NEWS_PREFIX + "/{url}/{id}/{s}", Constants.NEWS_PREFIX + "/{url}/{id}"}, method = RequestMethod.GET)
	public ModelAndView showNewsDetails(@PathVariable("url") String url, 
			@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		News news = newsBO.fetchNewsByNewsId(id);
		if (news == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/notFound"));
		}
		NewsContent content = news.getNewsContent();
		NewsCatalog newsCatalog = news.getNewsCatalog();
		List<News> newses = newsBO.getNewsesByUrlButId(newsCatalog.getUrl(), id, Util.getNewsAmountInSameSubject(request));
		
		model.addAttribute("news", news);
		model.addAttribute("newsCatalog", newsCatalog);
		model.addAttribute("content", content);
		model.addAttribute("newses", newses);
		
		return new ModelAndView("showNewsDetails");
	}
	
	@RequestMapping(value = "/thong-bao/{id}", method = RequestMethod.GET)
	public ModelAndView viewFull(@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		News news = newsBO.fetchNewsByNewsId(id);
		if (news == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/notFound"));
		}
		
		NewsContent content = news.getNewsContent();
		
		model.addAttribute("news", news);
		model.addAttribute("content", content);
		
		return new ModelAndView("viewFull");
	}
}
