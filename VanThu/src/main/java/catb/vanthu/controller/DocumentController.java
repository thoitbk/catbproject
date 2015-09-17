package catb.vanthu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.bo.BureauBO;
import catb.vanthu.bo.DepartmentBO;
import catb.vanthu.bo.DocumentBO;
import catb.vanthu.bo.DocumentFileBO;
import catb.vanthu.bo.DocumentNumberTrackerBO;
import catb.vanthu.bo.DocumentTypeBO;
import catb.vanthu.mail.MailReceiver;
import catb.vanthu.mail.MailSender;
import catb.vanthu.model.Bureau;
import catb.vanthu.model.Department;
import catb.vanthu.model.DepartmentDocument;
import catb.vanthu.model.Document;
import catb.vanthu.model.DocumentFile;
import catb.vanthu.model.DocumentType;
import catb.vanthu.model.UserInfo;
import catb.vanthu.tags.PageInfo;
import catb.vanthu.tags.TagFunctions;
import catb.vanthu.util.Constants;
import catb.vanthu.util.PropertiesUtil;
import catb.vanthu.util.Util;
import catb.vanthu.valueobject.ComplexSearchDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchInboundDocumentsVO;
import catb.vanthu.valueobject.ComplexSearchOutboundDocumentsVO;
import catb.vanthu.valueobject.FileMeta;
import catb.vanthu.valueobject.MailAttachment;
import catb.vanthu.valueobject.MailContentVO;
import catb.vanthu.valueobject.SimpleSearchDocumentVO;
import catb.vanthu.viewmodel.ComplexSearchDocumentsViewModel;
import catb.vanthu.viewmodel.ComplexSearchInboundDocumentsViewModel;
import catb.vanthu.viewmodel.ComplexSearchOutboundDocumentsViewModel;
import catb.vanthu.viewmodel.ResponseCode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class DocumentController {
	
	@Autowired
	private BureauBO bureauBO;
	
	@Autowired
	private DocumentTypeBO documentTypeBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private DocumentBO documentBO;
	
	@Autowired
	private DocumentFileBO documentFileBO;
	
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
	
	@ModelAttribute("pDepartments")
	public Map<Integer, String> populateDepartmentsInBureau() {
		List<Department> departments = departmentBO.getDepartmentsInBureau();
		Map<Integer, String> pDepartments = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			pDepartments.put(department.getId(), department.getCode());
		}
		
		return pDepartments;
	}
	
	@ModelAttribute("departments")
	public Map<Integer, String> populateDepartments() {
		List<Department> departments = departmentBO.getDepartments();
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		for (Department department : departments) {
			map.put(department.getId(), department.getCode());
		}
		
		return map;
	}
	
	@ModelAttribute("cDepartments")
	public Map<String, Map<Integer, String>> populateDepartmentsCategorized() {
		List<Bureau> bureaus = bureauBO.getBureaus();
		Map<String, Map<Integer, String>> cDepartments = new LinkedHashMap<String, Map<Integer, String>>();
		for (Bureau bureau : bureaus) {
			SortedMap<Integer, String> departments = new TreeMap<Integer, String>();
			for (Department d : bureau.getDepartments()) {
				departments.put(d.getId(), d.getCode());
			}
			cDepartments.put(bureau.getName(), departments);
		}
		
		return cDepartments;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/createDocument", method = RequestMethod.GET)
	public String initCreateDocumentForm(HttpServletRequest request, ModelMap model) {
		request.getSession().removeAttribute("files");
		Integer documentNumber = documentNumberTrackerBO.getDocumentNumber();
		model.addAttribute("documentNumber", documentNumber);
		return "document/createDocument";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/importDocument", method = RequestMethod.GET)
	public String importDocumentFromMail(@RequestParam(value = "uid", required = true) Long uid, HttpServletRequest request, ModelMap model) throws FileNotFoundException {
		List<MailAttachment> attachments = MailReceiver.getAttachments(uid);
		List<FileMeta> fileMetas = new LinkedList<FileMeta>();
		if (attachments != null && attachments.size() > 0) {
			for (MailAttachment attachment : attachments) {
				String fileName = attachment.getFileName();
				String id = getGenId();
				File file = createFile(id, fileName);
				FileOutputStream fos = new FileOutputStream(file);
				MailReceiver.getAttachment(uid, attachment.getPart(), fos);
				fileMetas.add(new FileMeta(id,
										   fileName, 
										   String.valueOf(file.length() / 1024), 
										   attachment.getContentType(), 
										   id + File.separator + fileName));
			}
		}
		request.getSession().removeAttribute("files");
		request.getSession().setAttribute("files", fileMetas);
		Integer documentNumber = documentNumberTrackerBO.getDocumentNumber();
		model.addAttribute("documentNumber", documentNumber);
		model.addAttribute("files", fileMetas);
		
		return "document/createDocument";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/deleteDocument/{t}/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDocument(@PathVariable("t") Integer t, @PathVariable("id") Integer id, 
			HttpServletRequest request, HttpSession session) {
		documentBO.deleteDocument(id);
		int newPage = 1;
		Integer p = 1;
		String page = request.getParameter("p");
		if (page != null && !"".equals(page)) {
			p = Integer.parseInt(page);
		}
		newPage = Util.evaluatePageNumAfterDelete(p, t, Constants.PAGE_SIZE, 1);
		
		session.setAttribute("msg", PropertiesUtil.getProperty("document.delete.success"));
		
		if (newPage == p) {
			return new ModelAndView(new RedirectView("/document/simpleSearchDocuments.html" + (request.getQueryString() != null ? "?" + request.getQueryString() : "")));
		} else {
			Map<String, String> modifiedParams = new HashMap<String, String>();
			modifiedParams.put("p", String.valueOf(newPage));
			String q = Util.modifyQueryString(request, modifiedParams);
			return new ModelAndView(new RedirectView("/document/simpleSearchDocuments.html" + (q != null ? "?" + q : "")));
		}
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/uploadDocument", method = RequestMethod.POST)
	public void uploadDocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FileMeta> files = getUploadFiles(request);
		HttpSession session = request.getSession();
		if (files.size() > 0) {
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("files");
			if (fileMetas != null) {
				fileMetas.addAll(files);
			} else {
				session.setAttribute("files", files);
			}
		}
		
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(session.getAttribute("files")));
		out.close();
    }
	
	private File createFile(String id, String fileName) {
		File f = new File(Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + id);
		if (!f.exists()) {
			f.mkdir();
		}
		
		String path = Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + id + File.separator + fileName;
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
	@RequestMapping(value = "/document/deleteDocument", method = RequestMethod.GET)
	public void deleteDocument(@RequestParam(value = "id", required = true) String id, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		List<FileMeta> files = (List<FileMeta>) session.getAttribute("files");
		if (files != null && files.size() > 0) {
			FileMeta f = null;
			for (FileMeta file : files) {
				if (file.getId().equals(id)) {
					f = file;
					break;
				}
			}
			if (f != null) {
				File t = new File(Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + f.getPath());
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
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/createDocument", method = RequestMethod.POST)
	public void processCreateDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String sign = request.getParameter("sign");
		Integer type = Integer.parseInt(request.getParameter("type"));
		String date = request.getParameter("publishDate");
		Integer confidentialLevel = Integer.parseInt(request.getParameter("confidentialLevel"));
		Integer urgentLevel = Integer.parseInt(request.getParameter("urgentLevel"));
		String signer = request.getParameter("signer");
		String abs = request.getParameter("abs");
		String _senders = request.getParameter("senders");
		String _receivers = request.getParameter("receivers");
		String _sendMail = request.getParameter("sendMail");
		
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
				if (documentBO.checkSignExistInYear(sign, Calendar.getInstance().get(Calendar.YEAR))) {
					errors.add(PropertiesUtil.getProperty("sign.existed"));
				} else {
					String number = matcher.group(1);
					Integer n = Integer.parseInt(number);
					if (documentBO.checkNumberExistInYear(n, Calendar.getInstance().get(Calendar.YEAR))) {
						errors.add(PropertiesUtil.getProperty("number.existed"));
					}
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
		List<Integer> receivers = null;
		if (_receivers == null || "".equals(receivers)) {
			errors.add(PropertiesUtil.getProperty("receivers.required"));
		} else {
			receivers = new ArrayList<Integer>(Arrays.asList(gson.fromJson(_receivers, Integer[].class)));
			if (receivers.size() == 0) {
				errors.add(PropertiesUtil.getProperty("receivers.required"));
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
			Document document = new Document();
			document.setSign(sign);
			document.setPublishDate(publishDate);
			if (confidentialLevel != -1) {
				document.setConfidentialLevel(confidentialLevel);
			}
			if (urgentLevel != -1) {
				document.setUrgentLevel(urgentLevel);
			}
			document.setSendDate(new Date());
			document.setSigner(signer);
			document.setAbs(abs);
			document.setIsMailSent("true".equalsIgnoreCase(_sendMail) ? true : false);
			
			HttpSession session = request.getSession();
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("files");
			List<String> filePaths = new ArrayList<String>();
			if (fileMetas != null && fileMetas.size() > 0) {
				Set<DocumentFile> files = new HashSet<DocumentFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new DocumentFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType(), document));
					filePaths.add(Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + fileMeta.getPath());
				}
				document.setDocumentFiles(files);
			}
			session.removeAttribute("files");
			documentBO.saveDocument(document, (type == -1 ? null : type), senders, receivers);
			Integer newDocumentNumber = documentNumberTrackerBO.getDocumentNumber() + 1;
			documentNumberTrackerBO.updateDocumentNumber(newDocumentNumber);
			if ("true".equalsIgnoreCase(_sendMail)) {
				List<Department> departments = departmentBO.getDepartments(senders);
				List<String> senderCodes = new ArrayList<String>();
				for (Department d : departments) {
					senderCodes.add(d.getCode());
				}
				MailContentVO mailContentVO = new MailContentVO(departmentBO.getEmailsOfDepartments(receivers), sign, publishDate, signer, senderCodes, abs, filePaths);
				MailSender.send(mailContentVO);
			}
			code = new ResponseCode(1, PropertiesUtil.getProperty("create.document.success"));
			session.setAttribute("msg", PropertiesUtil.getProperty("create.document.success"));
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(code));
		out.close();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/updateDocument/{id}", method = RequestMethod.GET)
	public String initUpdateDocumentForm(@PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {
		Document document = documentBO.getDocumentById(id);
		
		Map<Integer, String> sDepartments = populateDepartmentsInBureau();
		Map<Integer, String> sentDepartments = new LinkedHashMap<Integer, String>();
		Set<Department> sender = document.getDepartments();
		for (Department d : sender) {
			sentDepartments.put(d.getId(), d.getCode());
		}
		
		Iterator<Entry<Integer, String>> iter = sDepartments.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, String> e = iter.next();
			if (sentDepartments.containsKey(e.getKey())) {
				iter.remove();
			}
		}
		
		Map<Integer, String> rDepartments = populateDepartments();
		Map<Integer, String> receivedDepartments = new LinkedHashMap<Integer, String>();
		Set<DepartmentDocument> receivers = document.getDepartmentDocuments();
		for (DepartmentDocument d : receivers) {
			receivedDepartments.put(d.getDepartment().getId(), d.getDepartment().getCode());
		}
		
		iter = rDepartments.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, String> e = iter.next();
			if (receivedDepartments.containsKey(e.getKey())) {
				iter.remove();
			}
		}
		
		model.addAttribute("document", document);
		model.addAttribute("sDepartments", sDepartments);
		model.addAttribute("sentDepartments", sentDepartments);
		model.addAttribute("rDepartments", rDepartments);
		model.addAttribute("receivedDepartments", receivedDepartments);
		
		request.getSession().removeAttribute("files");
		Set<DocumentFile> files = document.getDocumentFiles();
		List<FileMeta> fileMetas = new LinkedList<FileMeta>();
		for (DocumentFile file : files) {
			fileMetas.add(new FileMeta(String.valueOf(file.getId()), file.getName(), "0", file.getMime(), file.getPath()));
		}
		request.getSession().setAttribute("files", fileMetas);
		
		return "document/updateDocument";
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/updateDocument", method = RequestMethod.POST)
	public void processUpdateDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Integer documentId = Integer.parseInt(request.getParameter("documentId"));
		String documentSign = request.getParameter("documentSign");
		String sign = request.getParameter("sign");
		Integer type = Integer.parseInt(request.getParameter("type"));
		String date = request.getParameter("publishDate");
		Integer confidentialLevel = Integer.parseInt(request.getParameter("confidentialLevel"));
		Integer urgentLevel = Integer.parseInt(request.getParameter("urgentLevel"));
		String signer = request.getParameter("signer");
		String abs = request.getParameter("abs");
		String _senders = request.getParameter("senders");
		String _receivers = request.getParameter("receivers");
		
		// Validate input data
		List<String> errors = new LinkedList<String>();
		if (sign == null || "".equals(sign)) {
			errors.add(PropertiesUtil.getProperty("sign.required"));
		} else {
			sign = sign.trim();
			Pattern signPattern = Pattern.compile(Constants.DOCUMENT_SIGN_PATTERN);
			Matcher matcher = signPattern.matcher(sign);
			documentSign = documentSign.trim();
			Matcher oldMatcher = signPattern.matcher(documentSign);
			if (!matcher.matches()) {
				errors.add(PropertiesUtil.getProperty("sign.malformed"));
			} else {
				if (documentBO.checkSignExistInYear(sign, Calendar.getInstance().get(Calendar.YEAR), documentSign)) {
					errors.add(PropertiesUtil.getProperty("sign.existed"));
				} else {
					String number = matcher.group(1);
					Integer n = Integer.parseInt(number);
					oldMatcher.matches();
					Integer n1 = Integer.parseInt(oldMatcher.group(1));
					if (documentBO.checkNumberExistInYear(n, Calendar.getInstance().get(Calendar.YEAR), n1)) {
						errors.add(PropertiesUtil.getProperty("number.existed"));
					}
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
		List<Integer> receivers = null;
		if (_receivers == null || "".equals(receivers)) {
			errors.add(PropertiesUtil.getProperty("receivers.required"));
		} else {
			receivers = new ArrayList<Integer>(Arrays.asList(gson.fromJson(_receivers, Integer[].class)));
			if (receivers.size() == 0) {
				errors.add(PropertiesUtil.getProperty("receivers.required"));
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
			Document document = new Document();
			document.setId(documentId);
			document.setSign(sign);
			document.setPublishDate(publishDate);
			if (confidentialLevel != -1) {
				document.setConfidentialLevel(confidentialLevel);
			}
			if (urgentLevel != -1) {
				document.setUrgentLevel(urgentLevel);
			}
			document.setSendDate(new Date());
			document.setSigner(signer);
			document.setAbs(abs);
			
			HttpSession session = request.getSession();
			List<FileMeta> fileMetas = (List<FileMeta>) session.getAttribute("files");
			if (fileMetas != null && fileMetas.size() > 0) {
				Set<DocumentFile> files = new HashSet<DocumentFile>();
				for (FileMeta fileMeta : fileMetas) {
					files.add(new DocumentFile(fileMeta.getFileName(), fileMeta.getPath(), fileMeta.getFileType(), document));
				}
				document.setDocumentFiles(files);
			}
			session.removeAttribute("files");
			
			documentBO.updateDocument(document, (type == -1 ? null : type), senders, receivers);
			code = new ResponseCode(1, PropertiesUtil.getProperty("update.document.success"));
			session.setAttribute("msg", PropertiesUtil.getProperty("update.document.success"));
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(code));
		out.close();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/simpleSearchDocuments", method = RequestMethod.GET)
	public String simpleSearchDocuments(
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
		
		model.addAttribute("documents", documentBO.getDocuments(simpleSearchDocumentVO, p, Constants.PAGE_SIZE));
		model.addAttribute("pageInfo", new PageInfo(documentBO.countDocuments(simpleSearchDocumentVO), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "document/simpleSearchDocuments";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/report", method = RequestMethod.GET)
	public String reportDocumentsMonthly(
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
		
		model.addAttribute("documents", documentBO.getDocuments(simpleSearchDocumentVO));
		
		return "document/report";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/complexReport", method = RequestMethod.GET)
	public String complexReportDocumentsMonthly(ComplexSearchDocumentsViewModel cModel, ModelMap model) {
		ComplexSearchDocumentsVO complexSearchDocumentsVO = paramToComplexSearchDocumentVO(cModel, new HashMap<String, String>());
		List<Document> documents = documentBO.getDocuments(complexSearchDocumentsVO);
		
		model.addAttribute("documents", documents);
		
		return "document/complexReport";
	}
	
	private ComplexSearchDocumentsVO paramToComplexSearchDocumentVO(ComplexSearchDocumentsViewModel cModel, Map<String, String> params) {
		ComplexSearchDocumentsVO complexSearchDocumentsVO = new ComplexSearchDocumentsVO();
		
		Date fromPublishDate = null, toPublishDate = null, fromSendDate = null, toSendDate = null;
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
			if (cModel.getSfromSendDate() != null && !"".equals(cModel.getSfromSendDate())) {
				fromSendDate = f.parse(cModel.getSfromSendDate());
				params.put("sfromSendDate", cModel.getSfromSendDate());
			}
			if (cModel.getStoSendDate() != null && !"".equals(cModel.getStoSendDate())) {
				toSendDate = f.parse(cModel.getStoSendDate());
				params.put("stoSendDate", cModel.getStoSendDate());
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		if (cModel.getSsign() != null && !"".equals(cModel.getSsign())) {
			complexSearchDocumentsVO.setSign(cModel.getSsign());
			params.put("ssign", cModel.getSsign());
		}
		if (cModel.getStype() != null && !cModel.getStype().equals(-1)) {
			complexSearchDocumentsVO.setType(cModel.getStype());
			params.put("sstype", String.valueOf(cModel.getStype()));
		}
		if (cModel.getSconfidentialLevel() != null && !cModel.getSconfidentialLevel().equals(-1)) {
			complexSearchDocumentsVO.setConfidentialLevel(cModel.getSconfidentialLevel());
			params.put("sconfidentialLevel", String.valueOf(cModel.getSconfidentialLevel()));
		}
		if (cModel.getSurgentLevel() != null && !cModel.getSurgentLevel().equals(-1)) {
			complexSearchDocumentsVO.setUrgentLevel(cModel.getSurgentLevel());
			params.put("surgentLevel", String.valueOf(cModel.getSurgentLevel()));
		}
		if (cModel.getSsender() != null && !cModel.getSsender().equals(-1)) {
			complexSearchDocumentsVO.setSender(cModel.getSsender());
			params.put("ssender", String.valueOf(cModel.getSsender()));
		}
		if (cModel.getSreceiver() != null && !cModel.getSreceiver().equals(-1)) {
			complexSearchDocumentsVO.setReceiver(cModel.getSreceiver());
			params.put("sreceiver", String.valueOf(cModel.getSreceiver()));
		}
		if (cModel.getSsigner() != null && !"".equals(cModel.getSsigner())) {
			complexSearchDocumentsVO.setSigner(cModel.getSsigner());
			params.put("ssigner", cModel.getSsigner());
		}
		complexSearchDocumentsVO.setFromPublishDate(fromPublishDate);
		complexSearchDocumentsVO.setToPublishDate(toPublishDate);
		complexSearchDocumentsVO.setFromSendDate(fromSendDate);
		complexSearchDocumentsVO.setToSendDate(toSendDate);
		if (cModel.getSabs() != null && !"".equals(cModel.getSabs())) {
			complexSearchDocumentsVO.setAbs(cModel.getSabs());
			params.put("sabs", cModel.getSabs());
		}
		
		return complexSearchDocumentsVO;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/complexSearchDocuments", method = RequestMethod.GET)
	public String complexSearchDocuments(
			ComplexSearchDocumentsViewModel cModel,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		Map<String, String> params = new HashMap<String, String>();
		ComplexSearchDocumentsVO complexSearchDocumentsVO = paramToComplexSearchDocumentVO(cModel, params);
		
		List<Document> documents = documentBO.getDocuments(complexSearchDocumentsVO, p, Constants.PAGE_SIZE);
		model.addAttribute("documents", documents);
		model.addAttribute("pageInfo", new PageInfo(documentBO.countDocuments(complexSearchDocumentsVO), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "document/complexSearchDocuments";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/downloadDocument", method = RequestMethod.GET)
	public void downloadDocument(
			@RequestParam(value = "f", required = true, defaultValue = "") Integer fileId,
			HttpServletResponse response) throws IOException {
		
		DocumentFile file = documentFileBO.getDocumentFileById(fileId);
		if (file != null) {
			responseFile(file, response);
		}
	}
	
	private void responseFile(DocumentFile file, HttpServletResponse response) throws IOException {
		response.setContentType(file.getMime() + "; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8").replace("+", " "));
		File f = new File(Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + file.getPath());
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
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/downloadOutboundDocument", method = RequestMethod.GET)
	public void downloadOutboundDocument(
			@RequestParam(value = "f", required = true, defaultValue = "") Integer fileId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserInfo userInfo = AuthUtil.getUserInfo(request.getSession());
		Integer departmentId = userInfo.getDepartmentId();
		if (!documentBO.checkUserHavePermissionToOutboundFile(departmentId, fileId)) {
			response.sendRedirect("/accessDenied.html");
		} else {
			DocumentFile file = documentFileBO.getDocumentFileById(fileId);
			responseFile(file, response);
		}
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/downloadInboundDocument", method = RequestMethod.GET)
	public void downloadInboundDocument(
			@RequestParam(value = "f", required = true, defaultValue = "") Integer fileId,
			@RequestParam(value = "d", required = true, defaultValue = "") Integer documentId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer departmentId = AuthUtil.getUserInfo(request.getSession()).getDepartmentId();
		if (!documentBO.checkUserHavePermissionToInboundFile(departmentId, fileId)) {
			response.sendRedirect("/accessDenied.html");
		} else {
			Document document = documentBO.getDocumentById(documentId);
			if (!TagFunctions.isRead(document, departmentId)) {
				documentBO.updateReadStatusOfDocument(departmentId, documentId, new Date(), true);
			}
			DocumentFile file = documentFileBO.getDocumentFileById(fileId);
			responseFile(file, response);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/documentDetails", method = RequestMethod.GET)
	public String viewDocumentDetails(
			@RequestParam(value = "d", required = true) Integer documentId,
			ModelMap model) {
		Document document = documentBO.getDocumentById(documentId);
		model.addAttribute("document", document);
		model.addAttribute("t", 0);
		
		return "document/documentDetails";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/outboundDocumentDetails", method = RequestMethod.GET)
	public ModelAndView viewOutboundDocumentDetails(
			@RequestParam(value = "d", required = true) Integer documentId,
			ModelMap model, HttpSession session) {
		UserInfo userInfo = AuthUtil.getUserInfo(session);
		Integer departmentId = userInfo.getDepartmentId();
		
		if (!documentBO.checkUserHavePermissionToOutboundDocument(departmentId, documentId)) {
			return new ModelAndView(new RedirectView("/accessDenied.html"));
		} else {
			Document document = documentBO.getDocumentById(documentId);
			model.addAttribute("document", document);
			model.addAttribute("t", 1);
			
			return new ModelAndView("document/documentDetails");
		}
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/inboundDocumentDetails", method = RequestMethod.GET)
	public ModelAndView viewInboundDocumentDetails(
			@RequestParam(value = "d", required = true) Integer documentId,
			ModelMap model, HttpSession session) {
		UserInfo userInfo = AuthUtil.getUserInfo(session);
		Integer departmentId = userInfo.getDepartmentId();
		
		if (!documentBO.checkUserHavePermissionToInboundDocument(departmentId, documentId)) {
			return new ModelAndView(new RedirectView("/accessDenied.html"));
		} else {
			Document document = documentBO.getDocumentById(documentId);
			model.addAttribute("document", document);
			model.addAttribute("t", 2);
			
			if (!TagFunctions.isRead(document, departmentId)) {
				documentBO.updateReadStatusOfDocument(departmentId, documentId, new Date(), true);
			}
			
			return new ModelAndView("document/documentDetails");
		}
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/simpleSearchOutboundDocuments", method = RequestMethod.GET)
	public String simpleSearchOutboundDocuments(
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
		
		UserInfo userInfo = AuthUtil.getUserInfo(request.getSession());
		Integer departmentId = userInfo.getDepartmentId();
		
		model.addAttribute("documents", documentBO.getOutboundDocuments(simpleSearchDocumentVO, departmentId, p, Constants.PAGE_SIZE));
		model.addAttribute("pageInfo", new PageInfo(documentBO.countOutboundDocuments(simpleSearchDocumentVO, departmentId), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "document/simpleSearchOutboundDocuments";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/reportOutbound", method = RequestMethod.GET)
	public String reportOutboundDocumentsMonthly(
			@RequestParam(value = "smonth", required = true) Integer month,
			@RequestParam(value = "syear", required = true) Integer year,
			ModelMap model, HttpServletRequest request) {
		SimpleSearchDocumentVO simpleSearchDocumentVO = new SimpleSearchDocumentVO();
		
		if (month != null && !month.equals(-1)) {
			simpleSearchDocumentVO.setMonth(month);
		}
		if (year != null && !year.equals(-1)) {
			simpleSearchDocumentVO.setYear(year);
		}
		
		UserInfo userInfo = AuthUtil.getUserInfo(request.getSession());
		Integer departmentId = userInfo.getDepartmentId();
		
		model.addAttribute("documents", documentBO.getOutboundDocuments(simpleSearchDocumentVO, departmentId));
		
		return "document/reportOutbound";
	}
	
	private ComplexSearchOutboundDocumentsVO paramToComplexSearchOutboundDocumentsVO(ComplexSearchOutboundDocumentsViewModel cModel, Map<String, String> params) {
		ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO = new ComplexSearchOutboundDocumentsVO();
		
		Date fromPublishDate = null, toPublishDate = null, fromSendDate = null, toSendDate = null;
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
			if (cModel.getSfromSendDate() != null && !"".equals(cModel.getSfromSendDate())) {
				fromSendDate = f.parse(cModel.getSfromSendDate());
				params.put("sfromSendDate", cModel.getSfromSendDate());
			}
			if (cModel.getStoSendDate() != null && !"".equals(cModel.getStoSendDate())) {
				toSendDate = f.parse(cModel.getStoSendDate());
				params.put("stoSendDate", cModel.getStoSendDate());
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		if (cModel.getSsign() != null && !"".equals(cModel.getSsign())) {
			complexSearchOutboundDocumentsVO.setSign(cModel.getSsign());
			params.put("ssign", cModel.getSsign());
		}
		if (cModel.getStype() != null && !cModel.getStype().equals(-1)) {
			complexSearchOutboundDocumentsVO.setType(cModel.getStype());
			params.put("sstype", String.valueOf(cModel.getStype()));
		}
		if (cModel.getSconfidentialLevel() != null && !cModel.getSconfidentialLevel().equals(-1)) {
			complexSearchOutboundDocumentsVO.setConfidentialLevel(cModel.getSconfidentialLevel());
			params.put("sconfidentialLevel", String.valueOf(cModel.getSconfidentialLevel()));
		}
		if (cModel.getSurgentLevel() != null && !cModel.getSurgentLevel().equals(-1)) {
			complexSearchOutboundDocumentsVO.setUrgentLevel(cModel.getSurgentLevel());
			params.put("surgentLevel", String.valueOf(cModel.getSurgentLevel()));
		}
		if (cModel.getSreceiver() != null && !cModel.getSreceiver().equals(-1)) {
			complexSearchOutboundDocumentsVO.setReceiver(cModel.getSreceiver());
			params.put("sreceiver", String.valueOf(cModel.getSreceiver()));
		}
		if (cModel.getSsigner() != null && !"".equals(cModel.getSsigner())) {
			complexSearchOutboundDocumentsVO.setSigner(cModel.getSsigner());
			params.put("ssigner", cModel.getSsigner());
		}
		complexSearchOutboundDocumentsVO.setFromPublishDate(fromPublishDate);
		complexSearchOutboundDocumentsVO.setToPublishDate(toPublishDate);
		complexSearchOutboundDocumentsVO.setFromSendDate(fromSendDate);
		complexSearchOutboundDocumentsVO.setToSendDate(toSendDate);
		if (cModel.getSabs() != null && !"".equals(cModel.getSabs())) {
			complexSearchOutboundDocumentsVO.setAbs(cModel.getSabs());
			params.put("sabs", cModel.getSabs());
		}
		
		return complexSearchOutboundDocumentsVO;
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/complexSearchOutboundDocuments", method = RequestMethod.GET)
	public String complexSearchOutboundDocuments(
			ComplexSearchOutboundDocumentsViewModel cModel,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		Map<String, String> params = new HashMap<String, String>();
		ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO = paramToComplexSearchOutboundDocumentsVO(cModel, params);
		Integer departmentId = AuthUtil.getUserInfo(request.getSession()).getDepartmentId();
		
		List<Document> documents = documentBO.getOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId, p, Constants.PAGE_SIZE);
		model.addAttribute("documents", documents);
		model.addAttribute("pageInfo", new PageInfo(documentBO.countOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "document/complexSearchOutboundDocuments";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/complexReportOutbound", method = RequestMethod.GET)
	public String complexReportOutboundDocuments(
			ComplexSearchOutboundDocumentsViewModel cModel,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		ComplexSearchOutboundDocumentsVO complexSearchOutboundDocumentsVO = paramToComplexSearchOutboundDocumentsVO(cModel, new HashMap<String, String>());
		Integer departmentId = AuthUtil.getUserInfo(request.getSession()).getDepartmentId();
		
		List<Document> documents = documentBO.getOutboundDocuments(complexSearchOutboundDocumentsVO, departmentId);
		model.addAttribute("documents", documents);
		
		return "document/complexReportOutbound";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/simpleSearchInboundDocuments", method = RequestMethod.GET)
	public String simpleSearchInboundDocuments(
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
		
		UserInfo userInfo = AuthUtil.getUserInfo(request.getSession());
		Integer departmentId = userInfo.getDepartmentId();
		
		model.addAttribute("documents", documentBO.getInboundDocuments(simpleSearchDocumentVO, departmentId, p, Constants.PAGE_SIZE));
		model.addAttribute("pageInfo", new PageInfo(documentBO.countInboundDocuments(simpleSearchDocumentVO, departmentId), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		model.addAttribute("departmentId", departmentId);
		
		return "document/simpleSearchInboundDocuments";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/reportInbound", method = RequestMethod.GET)
	public String reportInboundDocumentsMonthly(
			@RequestParam(value = "smonth", required = true) Integer month,
			@RequestParam(value = "syear", required = true) Integer year,
			ModelMap model, HttpServletRequest request) {
		SimpleSearchDocumentVO simpleSearchDocumentVO = new SimpleSearchDocumentVO();
		
		if (month != null && !month.equals(-1)) {
			simpleSearchDocumentVO.setMonth(month);
		}
		if (year != null && !year.equals(-1)) {
			simpleSearchDocumentVO.setYear(year);
		}
		
		UserInfo userInfo = AuthUtil.getUserInfo(request.getSession());
		Integer departmentId = userInfo.getDepartmentId();
		
		model.addAttribute("documents", documentBO.getInboundDocuments(simpleSearchDocumentVO, departmentId));
		
		return "document/reportInbound";
	}
	
	private ComplexSearchInboundDocumentsVO paramToComplexSearchInboundDocumentsVO(ComplexSearchInboundDocumentsViewModel cModel, Map<String, String> params) {
		ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO = new ComplexSearchInboundDocumentsVO();
		
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
			complexSearchInboundDocumentsVO.setSign(cModel.getSsign());
			params.put("ssign", cModel.getSsign());
		}
		if (cModel.getStype() != null && !cModel.getStype().equals(-1)) {
			complexSearchInboundDocumentsVO.setType(cModel.getStype());
			params.put("sstype", String.valueOf(cModel.getStype()));
		}
		if (cModel.getSconfidentialLevel() != null && !cModel.getSconfidentialLevel().equals(-1)) {
			complexSearchInboundDocumentsVO.setConfidentialLevel(cModel.getSconfidentialLevel());
			params.put("sconfidentialLevel", String.valueOf(cModel.getSconfidentialLevel()));
		}
		if (cModel.getSurgentLevel() != null && !cModel.getSurgentLevel().equals(-1)) {
			complexSearchInboundDocumentsVO.setUrgentLevel(cModel.getSurgentLevel());
			params.put("surgentLevel", String.valueOf(cModel.getSurgentLevel()));
		}
		if (cModel.getSsender() != null && !cModel.getSsender().equals(-1)) {
			complexSearchInboundDocumentsVO.setSender(cModel.getSsender());
			params.put("ssender", String.valueOf(cModel.getSsender()));
		}
		if (cModel.getSsigner() != null && !"".equals(cModel.getSsigner())) {
			complexSearchInboundDocumentsVO.setSigner(cModel.getSsigner());
			params.put("ssigner", cModel.getSsigner());
		}
		complexSearchInboundDocumentsVO.setFromPublishDate(fromPublishDate);
		complexSearchInboundDocumentsVO.setToPublishDate(toPublishDate);
		complexSearchInboundDocumentsVO.setFromReceiveDate(fromReceiveDate);
		complexSearchInboundDocumentsVO.setToReceiveDate(toReceiveDate);
		if (cModel.getSabs() != null && !"".equals(cModel.getSabs())) {
			complexSearchInboundDocumentsVO.setAbs(cModel.getSabs());
			params.put("sabs", cModel.getSabs());
		}
		
		return complexSearchInboundDocumentsVO;
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/complexSearchInboundDocuments", method = RequestMethod.GET)
	public String complexSearchInboundDocuments(
			ComplexSearchInboundDocumentsViewModel cModel,
			@RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
			ModelMap model, HttpServletRequest request) {
		
		Map<String, String> params = new HashMap<String, String>();
		ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO = paramToComplexSearchInboundDocumentsVO(cModel, params);
		
		Integer departmentId = AuthUtil.getUserInfo(request.getSession()).getDepartmentId();
		
		List<Document> documents = documentBO.getInboundDocuments(complexSearchInboundDocumentsVO, departmentId, p, Constants.PAGE_SIZE);
		model.addAttribute("documents", documents);
		model.addAttribute("pageInfo", new PageInfo(documentBO.countInboundDocuments(complexSearchInboundDocumentsVO, departmentId), p, Constants.PAGE_SIZE));
		model.addAttribute("params", params);
		
		return "document/complexSearchInboundDocuments";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/complexReportInbound", method = RequestMethod.GET)
	public String complexReportInboundDocuments(ComplexSearchInboundDocumentsViewModel cModel, ModelMap model, HttpServletRequest request) {
		ComplexSearchInboundDocumentsVO complexSearchInboundDocumentsVO = paramToComplexSearchInboundDocumentsVO(cModel, new HashMap<String, String>());
		
		Integer departmentId = AuthUtil.getUserInfo(request.getSession()).getDepartmentId();
		model.addAttribute("documents", documentBO.getInboundDocuments(complexSearchInboundDocumentsVO, departmentId));
		
		return "document/complexReportInbound";
	}
	
	@PreAuthorize("hasRole('NORMAL_USER')")
	@RequestMapping(value = "/document/unreadDocuments", method = RequestMethod.GET)
	public String viewUnreadDocuments(ModelMap model, HttpSession session) {
		Integer departmentId = AuthUtil.getUserInfo(session).getDepartmentId();
		List<Document> documents = documentBO.getUnreadDocuments(departmentId);
		
		model.addAttribute("documents", documents);
		
		return "document/unreadDocuments";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/document/sendMail", method = RequestMethod.GET)
	public void sendMail(
			@RequestParam(value = "id", required = true, defaultValue = "") Integer documentId, HttpServletResponse response) throws IOException {
		Document document = documentBO.getDocumentById(documentId);
		ResponseCode code = null;
		if (document != null) {
			List<Integer> receiverIds = new ArrayList<Integer>();
			for (DepartmentDocument d : document.getDepartmentDocuments()) {
				receiverIds.add(d.getDepartment().getId());
			}
			List<String> destinationAddresses = departmentBO.getEmailsOfDepartments(receiverIds);
			
			List<String> senders = new ArrayList<String>();
			for (Department d : document.getDepartments()) {
				senders.add(d.getCode());
			}
			
			List<String> files = new ArrayList<String>();
			for (DocumentFile f : document.getDocumentFiles()) {
				files.add(Constants.GOING_DOCUMENT_UPLOAD_DIR + File.separator + f.getPath());
			}
			
			try {
				if (destinationAddresses.size() > 0) {
					MailContentVO mailContentVO = new MailContentVO(
							destinationAddresses, document.getSign(), 
							document.getPublishDate(), document.getSigner(), 
							senders, document.getAbs(), files);
					MailSender.send(mailContentVO);
				}
				
				documentBO.updateSendStatusOfDocument(documentId, true);
				code = new ResponseCode(2, PropertiesUtil.getProperty("sendMail.success"));
			} catch (Exception ex) {
				ex.printStackTrace();
				code = new ResponseCode(1, PropertiesUtil.getProperty("sendMail.failed"));
			}
		} else {
			code = new ResponseCode(1, PropertiesUtil.getProperty("sendMail.failed"));
		}
		response.setContentType("application/json");
		Gson gson = new GsonBuilder().create();
		response.getWriter().print(gson.toJson(code));
	}
	
	private static String getGenId() {
		Date d = new Date();
		return String.valueOf(d.getTime());
	}
}
