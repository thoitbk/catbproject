package com.catb.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

import com.catb.bo.AdministrativeProcedureBO;
import com.catb.bo.DepartmentBO;
import com.catb.bo.FieldBO;
import com.catb.common.Constants;
import com.catb.common.PropertiesUtil;
import com.catb.common.exception.AppException;
import com.catb.model.AdministrativeProcedure;
import com.catb.model.AdministrativeProcedureFile;
import com.catb.model.Department;
import com.catb.model.Field;
import com.catb.web.util.DiacriticsRemover;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.AdministrativeProcedureViewModel;
import com.catb.web.viewmodel.FileMeta;
import com.catb.web.viewmodel.Status;

@Controller
public class AdministrativeProcedureController {
	
	@Autowired
	private AdministrativeProcedureBO administrativeProcedureBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private FieldBO fieldBO;
	
	private Map<Integer, String> populateDepartments() {
		List<Department> departments = departmentBO.getDepartments();
		Map<Integer, String> departmentMap = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			departmentMap.put(department.getId(), department.getName());
		}
		
		return departmentMap;
	}
	
	private Map<Integer, String> populateFields() {
		List<Field> fields = fieldBO.getFields();
		Map<Integer, String> fieldMap = new LinkedHashMap<Integer, String>();
		for (Field field : fields) {
			fieldMap.put(field.getId(), field.getName());
		}
		
		return fieldMap;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/add", method = RequestMethod.GET)
	public ModelAndView showCreateAdministrativeProcedure(ModelMap model, HttpServletRequest request) {
		AdministrativeProcedureViewModel administrativeProcedureViewModel = new AdministrativeProcedureViewModel();
		administrativeProcedureViewModel.setPublishedDate(new Date());
		model.addAttribute("administrativeProcedureViewModel", administrativeProcedureViewModel);
		model.addAttribute("departmentMap", populateDepartments());
		model.addAttribute("fieldMap", populateFields());
		model.addAttribute("administrativeProcedures", administrativeProcedureBO.getAdministrativeProcedures());
		request.getSession().removeAttribute("administrativeProcedureFiles");
		
		return new ModelAndView("cm/administrativeProcedure/add");
	}
	
	@SuppressWarnings("unchecked")
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/add", method = RequestMethod.POST)
	public ModelAndView processCreateAdministrativeProcedure(
			@Valid @ModelAttribute("administrativeProcedureViewModel") AdministrativeProcedureViewModel administrativeProcedureViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departmentMap", populateDepartments());
			model.addAttribute("fieldMap", populateFields());
			model.addAttribute("administrativeProcedures", administrativeProcedureBO.getAdministrativeProcedures());
			return new ModelAndView("cm/administrativeProcedure/add");
		} else {
			AdministrativeProcedure administrativeProcedure = new AdministrativeProcedure();
			administrativeProcedure.setCode(administrativeProcedureViewModel.getCode());
			administrativeProcedure.setContent(administrativeProcedureViewModel.getContent());
			administrativeProcedure.setDescription(administrativeProcedureViewModel.getDescription());
			administrativeProcedure.setName(administrativeProcedureViewModel.getName());
			administrativeProcedure.setPublishedDate(administrativeProcedureViewModel.getPublishedDate());
			administrativeProcedure.setValidDuration(administrativeProcedureViewModel.getValidDuration());
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (administrativeProcedureViewModel.getSqNumber() != null && !"".equals(administrativeProcedureViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(administrativeProcedureViewModel.getSqNumber().trim());
			}
			administrativeProcedure.setSqNumber(sqNumber);
			
			List<FileMeta> fileMetas = (List<FileMeta>) request.getSession().getAttribute("administrativeProcedureFiles");
			List<AdministrativeProcedureFile> files = null;
			if (fileMetas != null && fileMetas.size() > 0) {
				files = new LinkedList<AdministrativeProcedureFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new AdministrativeProcedureFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType()));
				}
			}
			
			Integer departmentId = administrativeProcedureViewModel.getDepartmentId();
			Integer fieldId = administrativeProcedureViewModel.getFieldId();
			System.out.println("department id: " + departmentId);
			
			administrativeProcedureBO.addAdministrativeProcedure(administrativeProcedure, departmentId, fieldId, files);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("administrativeProcedure.created.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/administrativeProcedure/add"));
		}
	}
	
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/update/{id}", method = RequestMethod.GET)
	public ModelAndView showUpdateAdministrativeProcedure(@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		AdministrativeProcedureViewModel administrativeProcedureViewModel = new AdministrativeProcedureViewModel();
		AdministrativeProcedure administrativeProcedure = administrativeProcedureBO.fetchAdministrativeProcedureById(id);
		
		request.getSession().removeAttribute("administrativeProcedureFiles");
		if (administrativeProcedure != null) {
			administrativeProcedureViewModel.setCode(administrativeProcedure.getCode());
			administrativeProcedureViewModel.setContent(administrativeProcedure.getContent());
			Integer departmentId = administrativeProcedure.getDepartment() != null ? administrativeProcedure.getDepartment().getId() : null;
			administrativeProcedureViewModel.setDepartmentId(departmentId);
			administrativeProcedureViewModel.setDescription(administrativeProcedure.getDescription());
			Integer fieldId = administrativeProcedure.getField() != null ? administrativeProcedure.getField().getId() : null;
			administrativeProcedureViewModel.setFieldId(fieldId);
			administrativeProcedureViewModel.setName(administrativeProcedure.getName());
			administrativeProcedureViewModel.setPublishedDate(administrativeProcedure.getPublishedDate());
			if (administrativeProcedure.getSqNumber() != null && !Constants.MAX_SQ_NUMBER.equals(administrativeProcedure.getSqNumber())) {
				administrativeProcedureViewModel.setSqNumber(String.valueOf(administrativeProcedure.getSqNumber()));
			}
			administrativeProcedureViewModel.setValidDuration(administrativeProcedure.getValidDuration());
			if (administrativeProcedure.getAdministrativeProcedureFiles() != null && administrativeProcedure.getAdministrativeProcedureFiles().size() > 0) {
				List<FileMeta> fileMetas = new LinkedList<FileMeta>();
				for (AdministrativeProcedureFile file : administrativeProcedure.getAdministrativeProcedureFiles()) {
					fileMetas.add(new FileMeta(String.valueOf(file.getId()), file.getName(), null, file.getMime(), file.getPath()));
				}
				request.getSession().setAttribute("administrativeProcedureFiles", fileMetas);
			}
		}
		
		model.addAttribute("departmentMap", populateDepartments());
		model.addAttribute("fieldMap", populateFields());
		model.addAttribute("administrativeProcedureViewModel", administrativeProcedureViewModel);
		model.addAttribute("administrativeProcedures", administrativeProcedureBO.getAdministrativeProcedures());
		
		return new ModelAndView("cm/administrativeProcedure/update");
	}
	
	@SuppressWarnings("unchecked")
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/update/{id}", method = RequestMethod.POST)
	public ModelAndView processUpdateAdministrativeProcedure(
			@PathVariable("id") Integer id, 
			@Valid @ModelAttribute("administrativeProcedureViewModel") AdministrativeProcedureViewModel administrativeProcedureViewModel, 
			BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departmentMap", populateDepartments());
			model.addAttribute("fieldMap", populateFields());
			model.addAttribute("administrativeProcedures", administrativeProcedureBO.getAdministrativeProcedures());
			return new ModelAndView("cm/administrativeProcedure/update");
		} else {
			AdministrativeProcedure administrativeProcedure = new AdministrativeProcedure();
			administrativeProcedure.setId(id);
			administrativeProcedure.setCode(administrativeProcedureViewModel.getCode());
			administrativeProcedure.setContent(administrativeProcedureViewModel.getContent());
			administrativeProcedure.setDescription(administrativeProcedureViewModel.getDescription());
			administrativeProcedure.setName(administrativeProcedureViewModel.getName());
			administrativeProcedure.setPublishedDate(administrativeProcedureViewModel.getPublishedDate());
			Integer sqNumber = Constants.MAX_SQ_NUMBER;
			if (administrativeProcedureViewModel.getSqNumber() != null && !"".equals(administrativeProcedureViewModel.getSqNumber().trim())) {
				sqNumber = Integer.parseInt(administrativeProcedureViewModel.getSqNumber().trim());
			}
			administrativeProcedure.setSqNumber(sqNumber);
			administrativeProcedure.setValidDuration(administrativeProcedureViewModel.getValidDuration());
			
			List<FileMeta> fileMetas = (List<FileMeta>) request.getSession().getAttribute("administrativeProcedureFiles");
			List<AdministrativeProcedureFile> files = null;
			if (fileMetas != null && fileMetas.size() > 0) {
				files = new LinkedList<AdministrativeProcedureFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new AdministrativeProcedureFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType()));
				}
			}
			
			Integer departmentId = administrativeProcedureViewModel.getDepartmentId();
			Integer fieldId = administrativeProcedureViewModel.getFieldId();
			
			administrativeProcedureBO.updateAdministrativeProcedure(administrativeProcedure, departmentId, fieldId, files);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("administrativeProcedure.updated.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/administrativeProcedure/add"));
		}
	}
	
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/delete", method = RequestMethod.POST)
	@ResponseBody
	public Status deleteAdministrativeProcedure(@RequestParam("ids") Integer[] ids, HttpSession session) {
		administrativeProcedureBO.deleteAdministrativeProcedures(ids);
		session.setAttribute("msg", PropertiesUtil.getProperty("administrativeProcedure.deleted.successfully"));
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/uploadFiles", method = RequestMethod.POST)
	@ResponseBody
	public List<FileMeta> uploadFiles(HttpServletRequest request, HttpServletResponse response) {
		List<FileMeta> files = getUploadFiles(request);
		
		if (files != null && files.size() > 0) {
			List<FileMeta> storedFiles = (List<FileMeta>) request.getSession().getAttribute("administrativeProcedureFiles");
			if (storedFiles != null) {
				storedFiles.addAll(files);
			} else {
				request.getSession().setAttribute("administrativeProcedureFiles", files);
			}
		}
		
		return files;
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
						String path = String.format("\\%s\\%s", dirName, newFileName);
						
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
		String directory = Constants.ADMINISTRATIVE_PROCEDURE_LOCATION + File.separator + dirName;
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		String absolutePath = directory + File.separator + fileName;
		File file = new File(absolutePath);
		
		return file;
	}
	
	@SuppressWarnings("unchecked")
	@RequiresPermissions(value = {"administrativeProcedure:manage"})
	@RequestMapping(value = "/cm/administrativeProcedure/removeFiles", method = RequestMethod.POST)
	@ResponseBody
	public Status removeAdministrativeProcedureFiles(
			@RequestParam(value = "fileIds", required = true) String[] fileIds, HttpServletRequest request) {
		List<FileMeta> fileMetas = (List<FileMeta>) request.getSession().getAttribute("administrativeProcedureFiles");
		if (fileMetas != null && fileMetas.size() > 0) {
			Iterator<FileMeta> fileMetaIterator = fileMetas.iterator();
			FileMeta fileMeta = null;
			while (fileMetaIterator.hasNext()) {
				fileMeta = fileMetaIterator.next();
				for (String fileId : fileIds) {
					if (fileId != null && fileId.equals(fileMeta.getId())) {
						String path = String.format("%s\\%s", Constants.ADMINISTRATIVE_PROCEDURE_LOCATION, fileMeta.getPath());
						File file = new File(path);
						if (file.exists()) {
							file.delete();
						}
						fileMetaIterator.remove();
						break;
					}
				}
			}
		}
		
		Status status = new Status(Status.OK, "ok");
		return status;
	}
	
	@RequestMapping(value = Constants.ADMINISTRATIVE_PROCEDURE_URL, method = RequestMethod.GET)
	public ModelAndView listAdministrativeProcedures(ModelMap model) {
		List<AdministrativeProcedure> administrativeProcedures = administrativeProcedureBO.listAdministrativeProcedures();
		model.addAttribute("administrativeProcedures", administrativeProcedures);
		return new ModelAndView("administrativeProcedure/list");
	}
	
	@RequestMapping(value = {Constants.ADMINISTRATIVE_PROCEDURE_URL + "/{id}/{s}", 
			Constants.ADMINISTRATIVE_PROCEDURE_URL + "/{id}"}, method = RequestMethod.GET)
	public ModelAndView viewAdministrativeProcedure(
			@PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) {
		AdministrativeProcedure administrativeProcedure = administrativeProcedureBO.fetchAdministrativeProcedureById(id);
		if (administrativeProcedure != null) {
			model.addAttribute("administrativeProcedure", administrativeProcedure);
			return new ModelAndView("administrativeProcedure/view");
		} else {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/notFound"));
		}
	}
	
	@RequestMapping(value = {Constants.ADMINISTRATIVE_PROCEDURE_URL + "/download/{id}/{s}", 
			Constants.ADMINISTRATIVE_PROCEDURE_URL + "/download/{id}"}, method = RequestMethod.GET)
	public void downloadAdministrativeProcedureFile(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
		AdministrativeProcedureFile administrativeProcedureFile = administrativeProcedureBO.getAdministrativeProcedureFile(id);
		if (administrativeProcedureFile != null) {
			response.setContentType(administrativeProcedureFile.getMime() + "; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			String fileName = administrativeProcedureFile.getName();
			String extension = FilenameUtils.getExtension(fileName);
			String baseName = FilenameUtils.getBaseName(fileName);
			String friendlyFileName = DiacriticsRemover.toFriendlyUrl(baseName);
			String newFileName = friendlyFileName + "." + extension;
			
			response.setHeader("Content-Disposition", "attachment;filename=\"" + newFileName + "\"");
			File f = new File(Constants.ADMINISTRATIVE_PROCEDURE_LOCATION + File.separator + administrativeProcedureFile.getPath());
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);
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
}
