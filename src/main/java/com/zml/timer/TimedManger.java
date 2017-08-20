package com.zml.timer;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TimedManger {
	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		
		// define the job and tie it to our HelloJob class
		  JobDetail job = JobBuilder.newJob(TimedJob.class)
		      .withIdentity("job1", "group1")
		      .build();

		  // Trigger the job to run now, and then repeat every 40 seconds
		  Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group3")  
			        .withSchedule(CronScheduleBuilder.cronSchedule("* 2 * * * * ?"))  
			        .build();  


		  scheduler.scheduleJob(job, trigger);

	}
}
