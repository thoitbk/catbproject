package catb.vanthu.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import catb.vanthu.mail.MailReceiver;
import catb.vanthu.tags.PageInfo;
import catb.vanthu.util.Constants;
import catb.vanthu.valueobject.MailDetails;
import catb.vanthu.valueobject.MailList;

@Controller
public class MailController {
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/mail/showInbox", method = RequestMethod.GET)
	public String showInbox(@RequestParam(value = "p", required = false, defaultValue = "1") Integer p, ModelMap model) {
		MailList mailList = MailReceiver.showInbox(p, Constants.PAGE_SIZE);
		model.addAttribute("mails", mailList.getMails());
		model.addAttribute("pageInfo", new PageInfo(mailList.getInboxSize(), p, Constants.PAGE_SIZE));
		
		return "mail/showInbox";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/mail/showDetails", method = RequestMethod.GET)
	public String showDetails(@RequestParam(value = "uid", required = true) Long uid, ModelMap model) {
		MailDetails mailDetails = MailReceiver.showDetails(uid);
		model.addAttribute("uid", uid);
		model.addAttribute("mailDetails", mailDetails);
		
		return "mail/showDetails";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'VAN_THU')")
	@RequestMapping(value = "/mail/download", method = RequestMethod.GET)
	public void downloadAttachment(@RequestParam(value = "uid", required = true) Long uid,
			@RequestParam(value = "part", required = true) Integer part, 
			@RequestParam(value = "fileName", required = true) String fileName, 
			@RequestParam(value = "ct", required = false) String contentType,
			HttpServletResponse response) throws IOException {
		
		response.setContentType(contentType + "; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", " "));
		MailReceiver.getAttachment(uid, part, response.getOutputStream());
	}
}
