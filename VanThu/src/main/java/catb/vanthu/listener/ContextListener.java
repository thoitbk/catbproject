package catb.vanthu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.Scheduler;

import catb.vanthu.cron.JobTrigger;
import catb.vanthu.mail.MailReceiver;
import catb.vanthu.util.Constants;
import catb.vanthu.util.XMLReader;

public class ContextListener implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(ContextListener.class.getCanonicalName());
	
	public void contextDestroyed(ServletContextEvent event) {
		MailReceiver.closeMailBox();
		ServletContext context = event.getServletContext();
		Scheduler scheduler = (Scheduler) context.getAttribute("scheduler");
		if (scheduler != null) {
			JobTrigger.shutdown(scheduler);
		}
		logger.info(">>> Stop web application\n");
	}

	public void contextInitialized(ServletContextEvent event) {
		PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("log4j.properties"));
		logger.info("\n>>> Start web application ...");
		logger.info("Loading resources");
		Constants.load();
		ServletContext context = event.getServletContext();
		context.setAttribute("confidentialLevels", XMLReader.readFile(context.getRealPath(Constants.CONFIDENTIAL_LEVELS_FILE), "confidentialLevel"));
		context.setAttribute("urgentLevels", XMLReader.readFile(context.getRealPath(Constants.URGENT_LEVELS_FILE), "urgentLevel"));
		Scheduler scheduler = JobTrigger.start();
		context.setAttribute("scheduler", scheduler);
		logger.info("Loading resources successfully");
	}
}
