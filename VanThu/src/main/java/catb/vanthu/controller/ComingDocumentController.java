package catb.vanthu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import catb.vanthu.bo.BureauBO;
import catb.vanthu.bo.ComingDocumentBO;
import catb.vanthu.bo.ComingDocumentFileBO;
import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.bo.DocumentNumberTrackerBO;
import catb.vanthu.bo.DocumentTypeBO;
import catb.vanthu.model.Bureau;
import catb.vanthu.model.ComingDocument;
import catb.vanthu.model.ComingDocumentFile;
import catb.vanthu.model.Department;
import catb.vanthu.model.DocumentType;
import catb.vanthu.tags.PageInfo;
import catb.vanthu.util.Constants;
import catb.vanthu.util.PropertiesUtil;
import catb.vanthu.util.Util;
import catb.vanthu.valueobject.ComplexSearchComingDocumentsVO;
import catb.vanthu.valueobject.FileMeta;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;
import catb.vanthu.viewmodel.ComplexSearchComingDocumentsViewModel;
import catb.vanthu.viewmodel.ResponseCode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class ComingDocumentController {
	
	@Autowired
	private BureauBO bureauBO;
	
	@Autowired
	private DocumentTypeBO documentTypeBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private ComingDocumentBO comingDocumentBO;
	
	@Autowired
	private ComingDocumentFileBO comingDocumentFileBO;
	
	@Autowired
	private DocumentNumberTrackerBO documentNumberTrackerBO;
	
	@ModelAttribute("documentTypes")
	public Map<Integer, String> populateDocumentTypes() {
		List<DocumentType> documentTypes = documentTypeBO.getDocumentTypes();
		Map<Integer, String> documentTypesMap = new LinkedHashMap<Integer, String>();
		for (DocumentType documentType : documentTypes) {
			documentTypesMap.put(documentType.getId(), documentType.getTypeName());
		}
		
		return documentTypesMap;
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("confidentialLevels")
	public Map<Integer, String> populateConfidentialLevels(HttpServletRequest request) {
		return (Map<Integer, String>) request.getServletContext().getAttribute("confidentialLevels");
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("urgentLevels")
	public Map<Integer, String> populateUrgentLevels(HttpServletRequest request) {
		return (Map<Integer, String>) request.getServletContext().getAttribute("urgentLevels");
	}
	
	@ModelAttribute("departments")
	public Map<Integer, String> populateDepartmentsNotInBureau() {
		List<Department> departments = departmentBO.getDepartmentsNotInBureau();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			map.put(department.getId(), department.getCode());
		}
		
		return map;
	}
	
	@ModelAttribute("cDepartments")
	public Map<String, Map<Integer, String>> populateDepartmentsNotInBureauCategorized() {
		List<Bureau> bureaus = bureauBO.getBureaus();
		Map<String, Map<Integer, String>> cDepartments = new LinkedHashMap<String, Map<Integer, String>>();
		for (Bureau bureau : bureaus) {
			SortedMap<Integer, String> departments = new TreeMap<Integer, String>();
			for (Department d : bureau.getDepartments()) {
				if (d.getInProvince()) break;
				departments.put(d.getId(), d.getCode());
			}
			if (departments.size() > 0) {
				cDepartments.put(bureau.getName(), departments);
			}
		}
		
		return cDepartments;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/createDocument", method = RequestMethod.GET)
	public String initCreateComingDocumentForm(HttpServletRequest request, ModelMap model) {
		request.getSession().removeAttribute("comingFiles");
		return "comingDocument/createDocument";
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/createDocument", method = RequestMethod.POST)
	public void processCreateComingDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String sign = request.getParameter("sign");
		Integer type = Integer.parseInt(request.getParameter("type"));
		String date = request.getParameter("publishDate");
		Integer confidentialLevel = Integer.parseInt(request.getParameter("confidentialLevel"));
		Integer urgentLevel = Integer.parseInt(request.getParameter("urgentLevel"));
		String abs = request.getParameter("abs");
		String _senders = request.getParameter("senders");
		
		// Validate input data
		List<String> errors = new LinkedList<String>();
		if (sign == null || "".equals(sign)) {
			errors.add(PropertiesUtil.getProperty("sign.required"));
		} else {
			sign = sign.trim();
			Pattern signPattern = Pattern.compile(Constants.DOCUMENT_SIGN_PATTERN);
			Matcher matcher = signPattern.matcher(sign);
			if (!matcher.matches()) {
				errors.add(PropertiesUtil.getProperty("sign.malformed"));
			} else {
				if (comingDocumentBO.checkSignExistInYear(sign, Calendar.getInstance().get(Calendar.YEAR))) {
					errors.add(PropertiesUtil.getProperty("sign.existed"));
				}
			}
		}
		Date publishDate = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			try {
				publishDate = f.parse(date);
			} catch (ParseException ex) {
				errors.add(PropertiesUtil.getProperty("date.malformed"));
			}
		}
		Gson gson = new GsonBuilder().create();
		List<Integer> senders = null;
		if (_senders == null || "".equals(_senders)) {
			errors.add(PropertiesUtil.getProperty("senders.required"));
		} else {
			senders = new ArrayList<Integer>(Arrays.asList(gson.fromJson(_senders, Integer[].class)));
			if (senders.size() == 0) {
				errors.add(PropertiesUtil.getProperty("senders.required"));
			}
		}
		
		ResponseCode code = null;
		
		if (errors.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (String error : errors) {
				builder.append(error).append("<br />");
			}
			code = new ResponseCode(0, builder.toString());
		} else {
			
			ComingDocument comingDocument = new ComingDocument();
			int number = documentNumberTrackerBO.getComingDocumentNumber() + 1;
			comingDocument.setNumber(number);
			comingDocument.setSign(sign);
			comingDocument.setPublishDate(publishDate);
			if (confidentialLevel != -1) {
				comingDocument.setConfidentialLevel(confidentialLevel);
			}
			if (urgentLevel != -1) {
				comingDocument.setUrgentLevel(urgentLevel);
			}
			comingDocument.setReceiveDate(new Date());
			comingDocument.setAbs(abs);
			
			HttpSession session = request.getSession();
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("comingFiles");
			if (fileMetas != null && fileMetas.size() > 0) {
				Set<ComingDocumentFile> files = new HashSet<ComingDocumentFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new ComingDocumentFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType(), comingDocument));
				}
				comingDocument.setComingDocumentFiles(files);
			}
			
			session.removeAttribute("comingFiles");
			
			comingDocumentBO.saveComingDocument(comingDocument, (type == -1 ? null : type), senders);
			documentNumberTrackerBO.updateComingDocumentNumber(number);
			
			code = new ResponseCode(1, PropertiesUtil.getProperty("create.document.success"));
			session.setAttribute("msg", PropertiesUtil.getProperty("create.document.success"));
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(code));
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/uploadDocument", method = RequestMethod.POST)
	public void uploadDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FileMeta> files = getUploadFiles(request);
		HttpSession session = request.getSession();
		if (files.size() > 0) {
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("comingFiles");
			if (fileMetas != null) {
				fileMetas.addAll(files);
			} else {
				session.setAttribute("comingFiles", files);
			}
		}
		
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(session.getAttribute("comingFiles")));
		out.close();
    }
	
	private File createFile(String id, String fileName) {
		File f = new File(Constants.COMING_DOCUMENT_UPLOAD_DIR + File.separator + id);
		if (!f.exists()) {
			f.mkdir();
		}
		
		String path = Constants.COMING_DOCUMENT_UPLOAD_DIR + File.separator + id + File.separator + fileName;
		File file = new File(path);
		
		return file;
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
						String id = getGenId();
						File file = createFile(id, item.getName());
						if (!file.exists()) {
							item.write(file);
							temp = new FileMeta(id, 
									item.getName(), 
									String.valueOf(item.getSize() / 1024), 
									item.getContentType(), 
									id + File.separator + item.getName());
						}
						files.add(temp);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return files;
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/deleteDocument", method = RequestMethod.GET)
	public void deleteDocument(@RequestParam(value = "id", required = true) String id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		List<FileMeta> files = (List<FileMeta>) session.getAttribute("comingFiles");
		if (files != null && files.size() > 0) {
			FileMeta f = null;
			for (FileMeta file : files) {
				if (file.getId().equals(id)) {
					f = file;
					break;
				}
			}
			if (f != null) {
				File t = new File(Constants.COMING_DOCUMENT_UPLOAD_DIR + File.separator + f.getPath());
				if (t.exists()) {
//					//t.delete();
				}
				files.remove(f);
			}
		}
		
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		ResponseCode code = new ResponseCode(1, "ok");
		response.getWriter().print(gson.toJson(code));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/simpleSearchDocuments", method = RequestMethod.GET)
	public String simpleSearchComingDocuments(
			@RequestParam(value = "sdocumentInfo", required = false, defaultValue = "") String documentInfo,
			@RequestParam(value = "smonth", required = false, defaultValue = "-1") Integer month,
			@RequestParam(value = "syear", required = false, defaultValue = "-1") Integer year,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		SimpleSearchDocumentVO simpleSearchDocumentVO = new SimpleSearchDocumentVO();
		Map<String, String> params = new HashMap<String, String>();
		
		if (documentInfo != null && !"".equals(documentInfo)) {
			simpleSearchDocumentVO.setDocumentInfo(documentInfo);
			params.put("sdocumentInfo", documentInfo);
		}
		if (month != null && !month.equals(-1)) {
			simpleSearchDocumentVO.setMonth(month);
			params.put("smonth", String.valueOf(month));
		}
		if (year != null && !year.equals(-1)) {
			simpleSearchDocumentVO.setYear(year);
			params.put("syear", String.valueOf(year));
		}
		
		model.addAttribute("documents", comingDocumentBO.getComingDocuments(simpleSearchDocumentVO, p, Constants.PAGE_SIZE));
		model.addAttribute("pageInfo", new PageInfo(comingDocumentBO.countComingDocuments(simpleSearchDocumentVO), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "comingDocument/simpleSearchDocuments";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/documentDetails", method = RequestMethod.GET)
	public String viewDocumentDetails(
			@RequestParam(value = "d", required = true) Integer documentId,
			ModelMap model) {
		ComingDocument document = comingDocumentBO.getComingDocumentById(documentId);
		model.addAttribute("document", document);
		
		return "comingDocument/documentDetails";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/downloadDocument", method = RequestMethod.GET)
	public void downloadDocument(
			@RequestParam(value = "f", required = true, defaultValue = "") Integer fileId,
			HttpServletResponse response) throws IOException {
		
		ComingDocumentFile file = comingDocumentFileBO.getComingDocumentFileById(fileId);
		if (file != null) {
			responseFile(file, response);
		}
	}
	
	private void responseFile(ComingDocumentFile file, HttpServletResponse response) throws IOException {
		response.setContentType(file.getMime() + "; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8").replace("+", " "));
		File f = new File(Constants.COMING_DOCUMENT_UPLOAD_DIR + File.separator + file.getPath());
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
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/deleteDocument/{t}/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDocument(@PathVariable("t") Integer t, @PathVariable("id") Integer id, 
			HttpServletRequest request, HttpSession session) {
		comingDocumentBO.deleteComingDocument(id);
		int newPage = 1;
		Integer p = 1;
		String page = request.getParameter("p");
		if (page != null && !"".equals(page)) {
			p = Integer.parseInt(page);
		}
		newPage = Util.evaluatePageNumAfterDelete(p, t, Constants.PAGE_SIZE, 1);
		
		session.setAttribute("msg", PropertiesUtil.getProperty("document.delete.success"));
		
		if (newPage == p) {
			return new ModelAndView(new RedirectView("/comingDocument/simpleSearchDocuments.html" + (request.getQueryString() != null ? "?" + request.getQueryString() : "")));
		} else {
			Map<String, String> modifiedParams = new HashMap<String, String>();
			modifiedParams.put("p", String.valueOf(newPage));
			String q = Util.modifyQueryString(request, modifiedParams);
			return new ModelAndView(new RedirectView("/comingDocument/simpleSearchDocuments.html" + (q != null ? "?" + q : "")));
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/updateDocument/{id}", method = RequestMethod.GET)
	public String initUpdateComingDocumentForm(@PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {
		ComingDocument document = comingDocumentBO.getComingDocumentById(id);
		
		Map<Integer, String> nDepartments = populateDepartmentsNotInBureau();
		Map<Integer, String> sentDepartments = new LinkedHashMap<Integer, String>();
		Set<Department> sender = document.getSentDepartments();
		for (Department d : sender) {
			sentDepartments.put(d.getId(), d.getCode());
		}
		
		Iterator<Entry<Integer, String>> iter = nDepartments.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, String> e = iter.next();
			if (sentDepartments.containsKey(e.getKey())) {
				iter.remove();
			}
		}
		
		model.addAttribute("document", document);
		model.addAttribute("nDepartments", nDepartments);
		model.addAttribute("sentDepartments", sentDepartments);
		
		request.getSession().removeAttribute("comingFiles");
		Set<ComingDocumentFile> files = document.getComingDocumentFiles();
		List<FileMeta> fileMetas = new LinkedList<FileMeta>();
		for (ComingDocumentFile file : files) {
			fileMetas.add(new FileMeta(String.valueOf(file.getId()), file.getName(), "0", file.getMime(), file.getPath()));
		}
		request.getSession().setAttribute("comingFiles", fileMetas);
		
		return "comingDocument/updateDocument";
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/updateDocument", method = RequestMethod.POST)
	public void processUpdateDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Integer documentId = Integer.parseInt(request.getParameter("documentId"));
		String documentSign = request.getParameter("documentSign");
		String sign = request.getParameter("sign");
		Integer type = Integer.parseInt(request.getParameter("type"));
		String date = request.getParameter("publishDate");
		Integer confidentialLevel = Integer.parseInt(request.getParameter("confidentialLevel"));
		Integer urgentLevel = Integer.parseInt(request.getParameter("urgentLevel"));
		String abs = request.getParameter("abs");
		String _senders = request.getParameter("senders");
		
		// Validate input data
		List<String> errors = new LinkedList<String>();
		if (sign == null || "".equals(sign)) {
			errors.add(PropertiesUtil.getProperty("sign.required"));
		} else {
			sign = sign.trim();
			Pattern signPattern = Pattern.compile(Constants.DOCUMENT_SIGN_PATTERN);
			Matcher matcher = signPattern.matcher(sign);
			documentSign = documentSign.trim();
			if (!matcher.matches()) {
				errors.add(PropertiesUtil.getProperty("sign.malformed"));
			} else {
				if (comingDocumentBO.checkSignExistInYear(sign, Calendar.getInstance().get(Calendar.YEAR), documentSign)) {
					errors.add(PropertiesUtil.getProperty("sign.existed"));
				}
			}
		}
		Date publishDate = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			try {
				publishDate = f.parse(date);
			} catch (ParseException ex) {
				errors.add(PropertiesUtil.getProperty("date.malformed"));
			}
		}
		Gson gson = new GsonBuilder().create();
		List<Integer> senders = null;
		if (_senders == null || "".equals(_senders)) {
			errors.add(PropertiesUtil.getProperty("senders.required"));
		} else {
			senders = new ArrayList<Integer>(Arrays.asList(gson.fromJson(_senders, Integer[].class)));
			if (senders.size() == 0) {
				errors.add(PropertiesUtil.getProperty("senders.required"));
			}
		}
		
		ResponseCode code = null;
		
		if (errors.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (String error : errors) {
				builder.append(error).append("<br />");
			}
			code = new ResponseCode(0, builder.toString());
		} else {
			
			ComingDocument document = new ComingDocument();
			document.setId(documentId);
			document.setSign(sign);
			document.setPublishDate(publishDate);
			if (confidentialLevel != -1) {
				document.setConfidentialLevel(confidentialLevel);
			}
			if (urgentLevel != -1) {
				document.setUrgentLevel(urgentLevel);
			}
			document.setAbs(abs);
			
			HttpSession session = request.getSession();
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("comingFiles");
			if (fileMetas != null && fileMetas.size() > 0) {
				Set<ComingDocumentFile> files = new HashSet<ComingDocumentFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new ComingDocumentFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType(), document));
				}
				document.setComingDocumentFiles(files);
			}
			session.removeAttribute("comingFiles");
			
			comingDocumentBO.updateComingDocument(document, (type == -1 ? null : type), senders);
			code = new ResponseCode(1, PropertiesUtil.getProperty("update.document.success"));
			session.setAttribute("msg", PropertiesUtil.getProperty("update.document.success"));
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(code));
		out.close();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/complexReport", method = RequestMethod.GET)
	public String complexReportComingDocumentsMonthly(ComplexSearchComingDocumentsViewModel cModel, ModelMap model, HttpServletRequest request) {
		ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO = paramToComplexSearchComingDocumentsVO(cModel, new HashMap<String, String>());
		
		List<ComingDocument> documents = comingDocumentBO.getComingDocuments(complexSearchComingDocumentsVO);
		model.addAttribute("documents", documents);
		
		return "comingDocument/complexReport";
	}
	
	private ComplexSearchComingDocumentsVO paramToComplexSearchComingDocumentsVO(ComplexSearchComingDocumentsViewModel cModel, Map<String, String> params) {
		ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO = new ComplexSearchComingDocumentsVO();
		
		Date fromPublishDate = null, toPublishDate = null, fromReceiveDate = null, toReceiveDate = null;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (cModel.getSfromPublishDate() != null && !"".equals(cModel.getSfromPublishDate())) {
				fromPublishDate = f.parse(cModel.getSfromPublishDate());
				params.put("sfromPublishDate", cModel.getSfromPublishDate());
			}
			if (cModel.getStoPublishDate() != null && !"".equals(cModel.getStoPublishDate())) {
				toPublishDate = f.parse(cModel.getStoPublishDate());
				params.put("stoPublishDate", cModel.getStoPublishDate());
			}
			if (cModel.getSfromReceiveDate() != null && !"".equals(cModel.getSfromReceiveDate())) {
				fromReceiveDate = f.parse(cModel.getSfromReceiveDate());
				params.put("sfromReceiveDate", cModel.getSfromReceiveDate());
			}
			if (cModel.getStoReceiveDate() != null && !"".equals(cModel.getStoReceiveDate())) {
				toReceiveDate = f.parse(cModel.getStoReceiveDate());
				params.put("stoReceiveDate", cModel.getStoReceiveDate());
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		if (cModel.getSsign() != null && !"".equals(cModel.getSsign())) {
			complexSearchComingDocumentsVO.setSign(cModel.getSsign());
			params.put("ssign", cModel.getSsign());
		}
		if (cModel.getStype() != null && !cModel.getStype().equals(-1)) {
			complexSearchComingDocumentsVO.setType(cModel.getStype());
			params.put("sstype", String.valueOf(cModel.getStype()));
		}
		if (cModel.getSconfidentialLevel() != null && !cModel.getSconfidentialLevel().equals(-1)) {
			complexSearchComingDocumentsVO.setConfidentialLevel(cModel.getSconfidentialLevel());
			params.put("sconfidentialLevel", String.valueOf(cModel.getSconfidentialLevel()));
		}
		if (cModel.getSurgentLevel() != null && !cModel.getSurgentLevel().equals(-1)) {
			complexSearchComingDocumentsVO.setUrgentLevel(cModel.getSurgentLevel());
			params.put("surgentLevel", String.valueOf(cModel.getSurgentLevel()));
		}
		if (cModel.getSsender() != null && !cModel.getSsender().equals(-1)) {
			complexSearchComingDocumentsVO.setSender(cModel.getSsender());
			params.put("ssender", String.valueOf(cModel.getSsender()));
		}
		complexSearchComingDocumentsVO.setFromPublishDate(fromPublishDate);
		complexSearchComingDocumentsVO.setToPublishDate(toPublishDate);
		complexSearchComingDocumentsVO.setFromReceiveDate(fromReceiveDate);
		complexSearchComingDocumentsVO.setToReceiveDate(toReceiveDate);
		if (cModel.getSabs() != null && !"".equals(cModel.getSabs())) {
			complexSearchComingDocumentsVO.setAbs(cModel.getSabs());
			params.put("sabs", cModel.getSabs());
		}
		
		return complexSearchComingDocumentsVO;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/complexSearchDocuments", method = RequestMethod.GET)
	public String complexSearchComingDocuments(
			ComplexSearchComingDocumentsViewModel cModel,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		Map<String, String> params = new HashMap<String, String>();
		ComplexSearchComingDocumentsVO complexSearchComingDocumentsVO = paramToComplexSearchComingDocumentsVO(cModel, params);
		
		List<ComingDocument> documents = comingDocumentBO.getComingDocuments(complexSearchComingDocumentsVO, p, Constants.PAGE_SIZE);
		model.addAttribute("documents", documents);
		model.addAttribute("pageInfo", new PageInfo(comingDocumentBO.countComingDocuments(complexSearchComingDocumentsVO), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "comingDocument/complexSearchDocuments";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/comingDocument/report", method = RequestMethod.GET)
	public String reportComingDocumentsMonthly(
			@RequestParam(value = "smonth", required = true) Integer month,
			@RequestParam(value = "syear", required = true) Integer year, 
			ModelMap model) {
		
		SimpleSearchDocumentVO simpleSearchDocumentVO = new SimpleSearchDocumentVO();
		
		if (month != null && !month.equals(-1)) {
			simpleSearchDocumentVO.setMonth(month);
		}
		if (year != null && !year.equals(-1)) {
			simpleSearchDocumentVO.setYear(year);
		}
		
		model.addAttribute("documents", comingDocumentBO.getComingDocuments(simpleSearchDocumentVO));
		
		return "comingDocument/report";
	}
	
	private static String getGenId() {
		Date d = new Date();
		return String.valueOf(d.getTime());
	}
}
