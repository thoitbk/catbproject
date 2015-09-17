package com.catb.web.controller;

import java.io.File;
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

import com.catb.bo.ImageBO;
import com.catb.bo.ImageCatalogBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.common.exception.AppException;
import com.catb.model.Image;
import com.catb.model.ImageCatalog;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.FileMeta;
import com.catb.web.viewmodel.ImageGallery;
import com.catb.web.viewmodel.ImageInfo;
import com.catb.web.viewmodel.ImageViewModel;
import com.catb.web.viewmodel.Status;

@Controller
public class ImageController {
	
	@Autowired
	private ImageBO imageBO;
	
	@Autowired
	private ImageCatalogBO imageCatalogBO;
	
	private Map<Integer, String> populateImageCatalogs() {
		List<ImageCatalog> imageCatalogs = imageCatalogBO.getImageCatalogs();
		Map<Integer, String> imageCatalogMap = new LinkedHashMap<Integer, String>();
		for (ImageCatalog imageCatalog : imageCatalogs) {
			imageCatalogMap.put(imageCatalog.getId(), imageCatalog.getName());
		}
		
		return imageCatalogMap;
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/add", method = RequestMethod.GET)
	public ModelAndView showCreateImage(
			@RequestParam(value = "cId", required = false) Integer imageCatalogId, 
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer page, 
			ModelMap model, HttpServletRequest request) {
		model.addAttribute("imageCatalogMap", populateImageCatalogs());
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		ImageViewModel imageViewModel = new ImageViewModel();
		if (imageCatalogId != null && imageCatalogId >= 0) {
			imageViewModel.setImageCatalogId(imageCatalogId);
			params.put("cId", String.valueOf(imageCatalogId));
		}
		model.addAttribute("imageViewModel", imageViewModel);
		model.addAttribute("params", params);
		
		Integer pageSize = Util.getPageSize(request);
		List<Image> images = imageBO.getImages(imageCatalogId, page, pageSize);
		model.addAttribute("images", images);
		model.addAttribute("pageInfo", new PageInfo(imageBO.countImages(imageCatalogId), page, pageSize));
		
		request.getSession().removeAttribute("imageFile");
		
		return new ModelAndView("cm/image/add");
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/add", method = RequestMethod.POST)
	public ModelAndView processCreateImage(
								@RequestParam(value = "cId", required = false) Integer imageCatalogId,
								@RequestParam(value = "p", required = false, defaultValue = "1") Integer page,
								@Valid @ModelAttribute("imageViewModel") ImageViewModel imageViewModel, 
								BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("imageCatalogMap", populateImageCatalogs());
			
			Map<String, String> params = new LinkedHashMap<String, String>();
			if (imageCatalogId != null && imageCatalogId >= 0) {
				imageViewModel.setImageCatalogId(imageCatalogId);
				params.put("cId", String.valueOf(imageCatalogId));
			}
			model.addAttribute("params", params);
			Integer pageSize = Util.getPageSize(request);
			model.addAttribute("images", imageBO.getImages(imageCatalogId, page, pageSize));
			model.addAttribute("pageInfo", new PageInfo(imageBO.countImages(imageCatalogId), page, pageSize));
			
			return new ModelAndView("cm/image/add");
		} else {
			Image image = new Image();
			image.setCaption(imageViewModel.getCaption());
			image.setDisplay(imageViewModel.getDisplay());
			
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("imageFile");
			if (fileMeta != null) {
				image.setFile(fileMeta.getPath());
				request.getSession().removeAttribute("imageFile");
			}
			
			imageBO.addImage(image, imageCatalogId);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("image.created.successfully"));
			
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/image/add" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateImage(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (catalogId != null && catalogId >= 0) {
			params.put("cId", String.valueOf(catalogId));
		}
		model.addAttribute("params", params);
		ImageViewModel imageViewModel = new ImageViewModel();
		Image image = imageBO.fetchImageById(id);
		request.getSession().removeAttribute("imageFile");
		if (image != null) {
			Integer imageCatalogId = image.getImageCatalog() != null ? image.getImageCatalog().getId() : null;
			imageViewModel.setImageCatalogId(imageCatalogId);
			imageViewModel.setCaption(image.getCaption());
			imageViewModel.setDisplay(image.getDisplay());
			
			if (image.getFile() != null) {
				request.getSession().setAttribute("imageFile", new FileMeta(String.valueOf(image.getId()), null, null, null, image.getFile()));
			}
		}
		
		model.addAttribute("imageCatalogMap", populateImageCatalogs());
		model.addAttribute("imageViewModel", imageViewModel);
		Integer pageSize = Util.getPageSize(request);
		model.addAttribute("images", imageBO.getImages(catalogId, 1, pageSize));
		model.addAttribute("pageInfo", new PageInfo(imageBO.countImages(catalogId), 1, pageSize));
		
		return new ModelAndView("cm/image/update");
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateImage(
			@RequestParam(value = "cId", required = false) Integer catalogId,
			@PathVariable("id") Integer id, @Valid @ModelAttribute("imageViewModel") ImageViewModel imageViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			Map<String, String> params = new LinkedHashMap<String, String>();
			if (catalogId != null && catalogId >= 0) {
				params.put("cId", String.valueOf(catalogId));
			}
			model.addAttribute("params", params);
			model.addAttribute("imageCatalogMap", populateImageCatalogs());
			Integer pageSize = Util.getPageSize(request);
			model.addAttribute("images", imageBO.getImages(catalogId, 1, pageSize));
			model.addAttribute("pageInfo", new PageInfo(imageBO.countImages(catalogId), 1, pageSize));
			
			return new ModelAndView("cm/image/update");
		} else {
			Image image = new Image();
			image.setId(id);
			image.setCaption(imageViewModel.getCaption());
			image.setDisplay(imageViewModel.getDisplay());
			
			FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("imageFile");
			if (fileMeta != null) {
				image.setFile(fileMeta.getPath());
			}
			
			imageBO.updateImage(image, imageViewModel.getImageCatalogId());
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("image.updated.successfully"));
			
			String queryString = request.getQueryString() != null && !"".equals(request.getQueryString()) ? "?" + request.getQueryString() : "";
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/image/add" + queryString));
		}
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteImage(@RequestParam("ids") Integer[] ids, HttpSession session) {
		imageBO.deleteImages(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("image.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/upload", method = RequestMethod.POST)
	@ResponseBody
	public FileMeta uploadImage(HttpServletRequest request, HttpServletResponse response) {
		List<FileMeta> files = getUploadFiles(request);
		
		if (files != null && files.size() > 0) {
			request.getSession().setAttribute("imageFile", files.get(0));
			return files.get(0);
		}
		
		return null;
	}
	
	@RequiresPermissions(value = {"image:manage"})
	@RequestMapping(value = "/cm/image/remove", method = RequestMethod.POST)
	@ResponseBody
	public Status removeImage(HttpServletRequest request) {
		FileMeta fileMeta = (FileMeta) request.getSession().getAttribute("imageFile");
		if (fileMeta != null) {
			String path = String.format("%s\\%s", Constants.IMAGE_LOCATION, fileMeta.getPath());
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			
			request.getSession().removeAttribute("imageFile");
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
						String imageUrl = String.format("%s/%s/%s", Constants.IMAGE_URL, dirName, newFileName);
						
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
		String directory = Constants.IMAGE_LOCATION + File.separator + dirName;
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		String absolutePath = directory + File.separator + fileName;
		File file = new File(absolutePath);
		
		return file;
	}
	
	@RequestMapping(value = "/thu-vien-anh", method = RequestMethod.GET)
	public ModelAndView showImageGalleries(ModelMap model) {
		List<ImageGallery> imageGalleries = imageCatalogBO.fetchImageCatalogsHavingImage();
		model.addAttribute("imageGalleries", imageGalleries);
		
		return new ModelAndView("imageGalleries");
	}
	
	@RequestMapping(value = {"/thu-vien-anh/{id}/{s}", "/thu-vien-anh/{id}"}, method = RequestMethod.GET)
	@ResponseBody
	public List<ImageInfo> showImageGallery(@PathVariable("id") Integer id) {
		List<Image> images = imageBO.getImagesByCatalogId(id);
		List<ImageInfo> imageInfos = new LinkedList<ImageInfo>();
		
		if (images != null && images.size() > 0) {
			for (Image image : images) {
				imageInfos.add(new ImageInfo(image.getCaption(), image.getFile()));
			}
		}
		
		return imageInfos;
	}
}
