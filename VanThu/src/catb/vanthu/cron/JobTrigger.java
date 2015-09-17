package catb.vanthu.cron;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import catb.vanthu.util.Constants;

public class JobTrigger {
	
	static Logger logger = Logger.getLogger(JobTrigger.class.getCanonicalName());
	
	public static Scheduler start() {
		JobDetail job = JobBuilder.newJob(DocumentNumberResetJob.class)
				.withIdentity("documentNumberReset", "documentNumberReset").build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("documentNumberReset", "documentNumberReset")
				.withSchedule(CronScheduleBuilder.cronSchedule(Constants.NUMBER_RESET_INTERVAL))
				.build();

		Scheduler scheduler = null;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			return scheduler;
		} catch (SchedulerException ex) {
			logger.error("Exception", ex);
			return null;
		}
	}
	
	public static void shutdown(Scheduler scheduler) {
		try {
			scheduler.shutdown();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception: ", ex);
		}
	}
}
