package com.catb.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.bo.VideoBO;
import com.catb.bo.VideoCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.common.exception.AppException;
import com.catb.model.Video;
import com.catb.model.VideoCatalog;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.FileMeta;
import com.catb.web.viewmodel.Status;
import com.catb.web.viewmodel.VideoInfo;
import com.catb.web.viewmodel.VideoViewModel;

@Controller
public class VideoController {
	
	@Autowired
	private VideoBO videoBO;
	
	@Autowired
	private VideoCatalogBO videoCatalogBO;
	
	private Map<Integer, String> populateVideoCatalogs() {
		List<VideoCatalog> videoCatalogs = videoCatalogBO.getVideoCatalogs();
		Map<Integer, String> videoCatalogMap = new LinkedHashMap<Integer, String>();
		for (VideoCatalog videoCatalog : videoCatalogs) {
			videoCatalogMap.put(videoCatalog.getId(), videoCatalog.getName());
		}
		
		return videoCatalogMap;
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/add", method = RequestMethod.GET)
	public ModelAndView showCreateVideo(
			@RequestParam(value = "cId", required = false) Integer videoCatalogId, 
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, 
			ModelMap model, HttpServletRequest request) {
		model.addAttribute("videoCatalogMap", populateVideoCatalogs());
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		VideoViewModel videoViewModel = new VideoViewModel();
		if (videoCatalogId != null && videoCatalogId >= 0) {
			videoViewModel.setVideoCatalogId(videoCatalogId);
			params.put("cId", String.valueOf(videoCatalogId));
		}
		model.addAttribute("videoViewModel", videoViewModel);
		model.addAttribute("params", params);
		
		Integer pageSize = Util.getPageSize(request);
		List<Video> videos = videoBO.getVideos(videoCatalogId, page, pageSize);
		model.addAttribute("videos", videos);
		model.addAttribute("pageInfo", new PageInfo(videoBO.countVideos(videoCatalogId), page, pageSize));
		
		request.getSession().removeAttribute("videoFile");
		
		return new ModelAndView("cm/video/add");
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/add", method = RequestMethod.POST)
	public ModelAndView processCreateVideo(
								@RequestParam(value = "cId", required = false) Integer videoCatalogId,
								@RequestParam(value = "p", required = false, defaultValue = "1") Integer page,
								@Valid @ModelAttribute("videoViewModel") VideoViewModel videoViewModel, BindingResult bindingResult, 
								HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("videoCatalogMap", populateVideoCatalogs());
			
			Map<String, String> params = new LinkedHashMap<String, String>();
			if (videoCatalogId != null && videoCatalogId >= 0) {
				videoViewModel.setVideoCatalogId(videoCatalogId);
				params.put("cId", String.valueOf(videoCatalogId));
			}
			model.addAttribute("params", params);
			
			Integer pageSize = Util.getPageSize(request);
			model.addAttribute("videos", videoBO.getVideos(videoCatalogId, page, pageSize));
			model.addAttribute("pageInfo", new PageInfo(videoBO.countVideos(videoCatalogId), page, pageSize));
			
			return new ModelAndView("cm/video/add");
		} else {
			Video video = new Video();
			video.setCaption(videoViewModel.getCaption());
			video.setDisplay(videoViewModel.getDisplay());
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (videoViewModel.getSqNumber() != null && !"".equals(videoViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(videoViewModel.getSqNumber().trim());
			}
			video.setSqNumber(sqNumber);
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("videoFile");
			if (fileMeta != null) {
				video.setFile(fileMeta.getPath());
				request.getSession().removeAttribute("videoFile");
			}
			
			videoBO.addVideo(video, videoCatalogId);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("video.created.successfully"));
			
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/video/add" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateVideo(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		model.addAttribute("videoCatalogMap", populateVideoCatalogs());
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (catalogId != null && catalogId >= 0) {
			params.put("cId", String.valueOf(catalogId));
		}
		model.addAttribute("params", params);
		VideoViewModel videoViewModel = new VideoViewModel();
		Video video = videoBO.fetchVideoById(id);
		request.getSession().removeAttribute("videoFile");
		if (video != null) {
			Integer videoCatalogId = video.getVideoCatalog() != null ? video.getVideoCatalog().getId() : null;
			videoViewModel.setVideoCatalogId(videoCatalogId);
			videoViewModel.setCaption(video.getCaption());
			videoViewModel.setDisplay(video.getDisplay());
			if (video.getFile() != null) {
				request.getSession().setAttribute("videoFile", new FileMeta(String.valueOf(video.getId()), null, null, null, video.getFile()));
			}
			if (video.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(video.getSqNumber())) {
				videoViewModel.setSqNumber(String.valueOf(video.getSqNumber()));
			}
		}
		model.addAttribute("videoViewModel", videoViewModel);
		
		Integer pageSize = Util.getPageSize(request);
		model.addAttribute("videos", videoBO.getVideos(catalogId, 1, pageSize));
		model.addAttribute("pageInfo", new PageInfo(videoBO.countVideos(catalogId), 1, pageSize));
		
		return new ModelAndView("cm/video/update");
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateVideo(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@PathVariable("id") Integer id, @Valid @ModelAttribute("videoViewModel") VideoViewModel videoViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			Map<String, String> params = new LinkedHashMap<String, String>();
			if (catalogId != null && catalogId >= 0) {
				params.put("cId", String.valueOf(catalogId));
			}
			model.addAttribute("params", params);
			model.addAttribute("videoCatalogMap", populateVideoCatalogs());
			Integer pageSize = Util.getPageSize(request);
			model.addAttribute("videos", videoBO.getVideos(catalogId, 1, pageSize));
			model.addAttribute("pageInfo", new PageInfo(videoBO.countVideos(catalogId), 1, pageSize));
			
			return new ModelAndView("cm/video/update");
		} else {
			Video video = new Video();
			video.setId(id);
			video.setCaption(videoViewModel.getCaption());
			video.setDisplay(videoViewModel.getDisplay());
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("videoFile");
			if (fileMeta != null) {
				video.setFile(fileMeta.getPath());
			}
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (videoViewModel.getSqNumber() != null && !"".equals(videoViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(videoViewModel.getSqNumber().trim());
			}
			video.setSqNumber(sqNumber);
			
			videoBO.updateVideo(video, videoViewModel.getVideoCatalogId());
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("video.updated.successfully"));
			
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/video/add" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteVideo(@RequestParam("ids") Integer[] ids, HttpSession session) {
		videoBO.deleteVideos(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("video.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/upload", method = RequestMethod.POST)
	@ResponseBody
	public FileMeta uploadVideo(HttpServletRequest request, HttpServletResponse response) {
		List<FileMeta> files = getUploadFiles(request);
		request.getSession().removeAttribute("videoFile");
		if (files != null && files.size() > 0) {
			request.getSession().setAttribute("videoFile", files.get(0));
			return files.get(0);
		}
		
		return null;
	}
	
	@RequiresPermissions(value = {"video:manage"})
	@RequestMapping(value = "/cm/video/remove", method = RequestMethod.POST)
	@ResponseBody
	public Status removeVideo(HttpServletRequest request) {
		FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("videoFile");
		if (fileMeta != null) {
			String path = String.format("%s\\%s", Constants.VIDEO_LOCATION, fileMeta.getPath());
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			
			request.getSession().removeAttribute("videoFile");
		}
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequestMapping(value = {"/videoGallery/video/{id}", "/videoGallery/video/{id}/{s}"}, method = RequestMethod.GET)
	public void viewVideo(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
		Video video = videoBO.fetchVideoById(id);
		if (video != null) {
			String path = Constants.VIDEO_LOCATION + File.separator + video.getFile();
			File file = new File(path);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] buffer = new byte[1024 * 1024];
				int readByte = -1;
				
				while ((readByte = fis.read(buffer)) != -1) {
					os.write(buffer, 0, readByte);
				}
				
				os.close();
				fis.close();
			}
		}
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
						String path = String.format("%s/%s/%s", Constants.VIDEO_URL, dirName, newFileName);
						
						File file = createFile(dirName, newFileName);
						if (!file.exists()) {
							item.write(file);
							temp = new FileMeta(
									randomString, fileName, String.valueOf(item.getSize() / 1024), 
									item.getContentType(), path);
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
		String directory = Constants.VIDEO_LOCATION + File.separator + dirName;
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		String absolutePath = directory + File.separator + fileName;
		File file = new File(absolutePath);
		
		return file;
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	@ResponseBody
	public List<VideoInfo> getVideos() {
		List<Video> videos = videoBO.getVideos(Constants.VIDEO_AMOUNT);
		List<VideoInfo> videoInfos = new LinkedList<VideoInfo>();
		
		if (videos != null && videos.size() > 0) {
			for (Video video : videos) {
				videoInfos.add(new VideoInfo(video.getId(), video.getCaption(), video.getFile()));
			}
		}
		
		return videoInfos;
	}
	
	@RequestMapping(value = "/thu-vien-video", method = RequestMethod.GET)
	public ModelAndView showVideoGalleries(
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, ModelMap model) {
		Integer pageSize = Constants.VIDEO_PAGE_SIZE;
		List<Video> videos = videoBO.getVideos(page, pageSize);
		Long count = videoBO.countVideos();
		
		model.addAttribute("videos", videos);
		model.addAttribute("pageInfo", new PageInfo(count, page, pageSize));
		
		return new ModelAndView("videoGalleries");
	}
}
