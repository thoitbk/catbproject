package com.catb.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class Constants {
	
	static Logger logger = Logger.getLogger(Constants.class);
	
	// Location of resources config files
	public static String CM_MENU_CONFIG_FILE;
	public static String COMMONINFO_CONFIG_FILE;
	public static String DISPLAY_LOCATION_CONFIG_FILE;
	public static String NEWS_STATUSES_CONFIG_FILE;
	// Logical path of directory storing uploaded images
	public static String NEWS_IMAGE_LOCATION;
	public static String SCHEDULE_LOCATION;
	// Relative path of uploaded images
	public static String NEWS_IMAGE_PATH;
	// Number of page links generated
	public static Integer NUMBER_OF_PAGE_LINK;
	public static String ADMINISTRATIVE_PROCEDURE_LOCATION;
	public static String DOCUMENT_LOCATION;
	public static String IMAGE_LOCATION;
	public static String IMAGE_URL;
	public static String VIDEO_LOCATION;
	public static String VIDEO_URL;
	public static String CONTACT_LOCATION;
	public static String CONTACT_URL;
	
	public static String SMTP_HOST;
	public static String SMTP_PORT;
	public static String EMAIL_SENDER;
	public static String EMAIL_SENDER_PASSWORD;
	
	public static final Integer MAX_SQ_NUMBER = 2000000000;
	public static final String NEWS_PREFIX = "/tin-tuc";
	public static final String HOMEPAGE = "Trang chủ";
	public static final String COMMENT_URL = "hoi-dap";
	public static final String ADMINISTRATIVE_PROCEDURE_URL = "thu-tuc-hanh-chinh";
	public static final String DOCUMENT_URL = "van-ban-phap-quy";
	public static final String CRIMINAL_DENOUNCEMENT_URL = "to-giac-toi-pham";
	public static final Integer IMAGE_AMOUNT = 10;
	public static final Integer VIDEO_AMOUNT = 5;
	public static final Integer VIDEO_PAGE_SIZE = 10;
	public static final Integer QUESTION_PAGE_SIZE = 20;
	public static final Integer COMMENT_IN_SAME_CATALOG = 10;
	public static final String QA_CAPTCHA_KEY = "QA_CAPTCHA_KEY";
	public static final String CD_CAPTCHA_KEY = "CD_CAPTCHA_KEY";
	public static final Integer MAX_ADS_NUM = 5;
	
	public static void load() {
		try {
			Configuration config = new PropertiesConfiguration("conf.properties");
			CM_MENU_CONFIG_FILE = config.getString("CM_MENU_CONFIG_FILE");
			COMMONINFO_CONFIG_FILE = config.getString("COMMONINFO_CONFIG_FILE");
			DISPLAY_LOCATION_CONFIG_FILE = config.getString("DISPLAY_LOCATION_CONFIG_FILE");
			NEWS_STATUSES_CONFIG_FILE = config.getString("NEWS_STATUSES_CONFIG_FILE");
			NEWS_IMAGE_LOCATION = config.getString("NEWS_IMAGE_LOCATION");
			NEWS_IMAGE_PATH = config.getString("NEWS_IMAGE_PATH");
			NUMBER_OF_PAGE_LINK = config.getInt("NUMBER_OF_PAGE_LINK");
			ADMINISTRATIVE_PROCEDURE_LOCATION = config.getString("ADMINISTRATIVE_PROCEDURE_LOCATION");
			DOCUMENT_LOCATION = config.getString("DOCUMENT_LOCATION");
			SCHEDULE_LOCATION = config.getString("SCHEDULE_LOCATION");
			IMAGE_LOCATION = config.getString("IMAGE_LOCATION");
			IMAGE_URL = config.getString("IMAGE_URL");
			VIDEO_LOCATION = config.getString("VIDEO_LOCATION");
			VIDEO_URL = config.getString("VIDEO_URL");
			SMTP_HOST = config.getString("SMTP_HOST");
			SMTP_PORT = config.getString("SMTP_PORT");
			EMAIL_SENDER = config.getString("EMAIL_SENDER");
			EMAIL_SENDER_PASSWORD = config.getString("EMAIL_SENDER_PASSWORD");
			CONTACT_LOCATION = config.getString("CONTACT_LOCATION");
			CONTACT_URL = config.getString("CONTACT_URL");
		} catch (ConfigurationException ex) {
			logger.error("Loading configuration failed: ", ex);
		}
	}
}
