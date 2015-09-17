package catb.vanthu.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class Constants {
	
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PHONE_NUMBER_PATTERN = "^([0-9\\(\\)\\/\\+ \\-]*)$";
	public static final String DOCUMENT_SIGN_PATTERN = "([\\d]*)/([\\S\\s]*)";
	public static Integer PAGE_SIZE;
	public static Integer NUMBER_OF_PAGE_LINK;
	public static String CONFIDENTIAL_LEVELS_FILE;
	public static String URGENT_LEVELS_FILE;
	public static String GOING_DOCUMENT_UPLOAD_DIR;
	public static String COMING_DOCUMENT_UPLOAD_DIR;
	public static String EMAIL_ADDRESS_FROM;
	public static String SMTP_HOST;
	public static String SMTP_PORT;
	public static String EMAIL_USERNAME;
	public static String EMAIL_PASSWORD;
	public static String NUMBER_RESET_INTERVAL;
	
	public static void load() {
		try {
			Configuration config = new PropertiesConfiguration("conf.properties");
			PAGE_SIZE = config.getInt("PAGE_SIZE");
			NUMBER_OF_PAGE_LINK = config.getInt("NUMBER_OF_PAGE_LINK");
			CONFIDENTIAL_LEVELS_FILE = config.getString("CONFIDENTIAL_LEVELS_FILE");
			URGENT_LEVELS_FILE = config.getString("URGENT_LEVELS_FILE");
			GOING_DOCUMENT_UPLOAD_DIR = config.getString("GOING_DOCUMENT_UPLOAD_DIR");
			COMING_DOCUMENT_UPLOAD_DIR = config.getString("COMING_DOCUMENT_UPLOAD_DIR");
			EMAIL_ADDRESS_FROM = config.getString("EMAIL_ADDRESS_FROM");
			SMTP_HOST = config.getString("SMTP_HOST");
			SMTP_PORT = config.getString("SMTP_PORT");
			EMAIL_USERNAME = config.getString("EMAIL_USERNAME");
			EMAIL_PASSWORD = config.getString("EMAIL_PASSWORD");
			NUMBER_RESET_INTERVAL = config.getString("NUMBER_RESET_INTERVAL");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		load();
		System.out.println(PAGE_SIZE);
	}
}
