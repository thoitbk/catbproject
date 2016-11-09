package catb.vanthu.cron;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import catb.vanthu.bo.DocumentNumberTrackerBO;
import catb.vanthu.bo.impl.DocumentNumberTrackerBOImpl;

public class DocumentNumberResetJob implements Job {
	
	static Logger logger = Logger.getLogger(DocumentNumberResetJob.class.getCanonicalName());
	
	private DocumentNumberTrackerBO documentNumberTrackerBO = new DocumentNumberTrackerBOImpl(); 
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			documentNumberTrackerBO.updateDocumentNumber(0);
			documentNumberTrackerBO.updateComingDocumentNumber(0);
		} catch (Exception ex) {
			logger.error("Exception", ex);
		}
	}
}
