package catb.vanthu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import catb.vanthu.bo.DocumentTypeBO;
import catb.vanthu.model.DocumentType;
import catb.vanthu.util.PropertiesUtil;
import catb.vanthu.viewmodel.ResponseCode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class DocumentTypeController {
	
	@Autowired
	private DocumentTypeBO documentTypeBO;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/documentType/listDocumentTypes", method = RequestMethod.GET)
	public String listDocumentTypes(ModelMap model) {
		List<DocumentType> documentTypes = documentTypeBO.getDocumentTypes();
		model.addAttribute("documentTypes", documentTypes);
		
		return "documentType/listDocumentTypes";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/documentType/createDocumentType", method = RequestMethod.GET)
	public void createDocumentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String typeName = request.getParameter("typeName");
		DocumentType documentType = documentTypeBO.getDocumentTypeByName(typeName);
		
		Gson gson = new GsonBuilder().create();
		ResponseCode responseCode = null;
		
		if (documentType != null) {
			responseCode = new ResponseCode(1, PropertiesUtil.getProperty("documentType.existed"));
		} else {
			DocumentType t = new DocumentType(typeName);
			documentTypeBO.saveDocumentType(t);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("documentType.create.success"));			
			responseCode = new ResponseCode(2, PropertiesUtil.getProperty("documentType.create.success"));
		}
		
		String jsonStr = gson.toJson(responseCode);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.close();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/documentType/updateDocumentType", method = RequestMethod.GET)
	public void updateDocumentType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String oldName = request.getParameter("oldName");
		String newName = request.getParameter("newName");
		
		Gson gson = new GsonBuilder().create();
		ResponseCode responseCode = null;
		
		if (oldName != null && newName != null && !newName.equalsIgnoreCase(oldName)
				&& documentTypeBO.getDocumentTypeByName(newName) != null) {
			responseCode = new ResponseCode(1, PropertiesUtil.getProperty("documentType.existed"));
		} else {
			documentTypeBO.updateDocumentType(Integer.parseInt(id), newName);
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("documentType.update.success"));			
			responseCode = new ResponseCode(2, PropertiesUtil.getProperty("documentType.update.success"));
		}
		
		String jsonStr = gson.toJson(responseCode);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.close();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/documentType/deleteDocumentType/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDocumentType(@PathVariable("id") Integer id, HttpSession session) {
		documentTypeBO.deleteDocumentType(id);
		session.setAttribute("msg", PropertiesUtil.getProperty("documentType.delete.success"));
		
		return new ModelAndView(new RedirectView("/documentType/listDocumentTypes.html"));
	}
}
