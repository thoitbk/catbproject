package com.catb.dao.statics;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.catb.common.exception.AppException;
import com.catb.model.CommonInfo;

public class ResWriter {
	
	static Logger logger = Logger.getLogger(ResReader.class);
	
	public static void writeCommonInfo(CommonInfo commonInfo, String fileName) {
		SAXBuilder builder = new SAXBuilder();
		File file = new File(fileName);
		
		try {
			Document document = (Document) builder.build(file);
			Element rootNode = document.getRootElement();
			
			rootNode.getChild("webTitle").setText(commonInfo.getWebTitle());
			rootNode.getChild("marqueeTitle").setText(commonInfo.getMarqueeTitle());
			rootNode.getChild("recentNews").setText(commonInfo.getRecentNews().toString());
			rootNode.getChild("questionAnswer").setText(commonInfo.getQuestionAnswer().toString());
			rootNode.getChild("tcCatalogs").setText(commonInfo.getTcCatalogs().toString());
			rootNode.getChild("sameSubjects").setText(commonInfo.getSameSubjects().toString());
			rootNode.getChild("headlines").setText(commonInfo.getHeadlines().toString());
			rootNode.getChild("headlineCaption").setText(commonInfo.getHeadlineCaption());
			rootNode.getChild("imageCaption").setText(commonInfo.getImageCaption());
			rootNode.getChild("videoCaption").setText(commonInfo.getVideoCaption());
			rootNode.getChild("audioCaption").setText(commonInfo.getAudioCaption());
			rootNode.getChild("detailsCaption").setText(commonInfo.getDetailsCaption());
			rootNode.getChild("administrativeProcedures").setText(commonInfo.getAdministrativeProcedures());
			rootNode.getChild("administrativeProceduresInstruction").setText(commonInfo.getAdministrativeProceduresInstruction());
			rootNode.getChild("views").setText(commonInfo.getViews());
			rootNode.getChild("introduction").setText(commonInfo.getIntroduction());
			rootNode.getChild("organizationalStructure").setText(commonInfo.getOrganizationalStructure());
			rootNode.getChild("mostViewed").setText(commonInfo.getMostViewed().toString());
			rootNode.getChild("adAmount").setText(commonInfo.getAdAmount().toString());
			rootNode.getChild("newsInSameCatalog").setText(commonInfo.getNewsInSameCatalog().toString());
			rootNode.getChild("newsInSearchResult").setText(commonInfo.getNewsInSearchResult().toString());
			rootNode.getChild("sameSubjectTitle").setText(commonInfo.getSameSubjectTitle());
			rootNode.getChild("today").setText(commonInfo.getToday());
			rootNode.getChild("postedDate").setText(commonInfo.getPostedDate());
			rootNode.getChild("author").setText(commonInfo.getAuthor());
			rootNode.getChild("print").setText(commonInfo.getPrint());
			rootNode.getChild("homePage").setText(commonInfo.getHomePage());
			rootNode.getChild("document").setText(commonInfo.getDocument());
			rootNode.getChild("legalDocument").setText(commonInfo.getLegalDocument());
			rootNode.getChild("goTop").setText(commonInfo.getGoTop());
			rootNode.getChild("duty").setText(commonInfo.getDuty());
			rootNode.getChild("achievement").setText(commonInfo.getAchievement());
			rootNode.getChild("pageSize").setText(commonInfo.getPageSize().toString());
			rootNode.getChild("rightTopSize").setText(commonInfo.getRightTopSize().toString());
			rootNode.getChild("rightCenterSize").setText(commonInfo.getRightCenterSize().toString());
			
			XMLOutputter xmlOutput = new XMLOutputter();
			 
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, new FileOutputStream(fileName));
		} catch (Exception ex) {
			logger.error("Updating configuration failed: ", ex);
			throw new AppException(ex);
		}
	}
}
