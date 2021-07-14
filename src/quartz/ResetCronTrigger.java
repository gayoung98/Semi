package quartz;

import static org.quartz.JobBuilder.newJob;

import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.*;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;



public class ResetCronTrigger {
	public ResetCronTrigger() throws SchedulerException, InterruptedException {
		try {

			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			scheduler.start();
JobDetail job = newJob(ResetJob.class).withIdentity("job1", "group1").build();
			
			Trigger trigger = newTrigger()
							.withIdentity("trigger1", "group1")
							.startNow()
							  .withSchedule(cronSchedule("0 28 17 ? * wed"))
							.build();
			
			scheduler.scheduleJob(job, trigger);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
