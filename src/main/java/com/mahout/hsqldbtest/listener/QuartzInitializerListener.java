package com.mahout.hsqldbtest.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.mahout.hsqldbtest.job.RestoreOrphanedSongJob;
import com.mahout.hsqldbtest.job.SongExistCheckJob;
import com.mahout.hsqldbtest.job.SongUpdateMD5Job;

@WebListener
public class QuartzInitializerListener implements ServletContextListener {

	private Scheduler scheduler;
	private Map<JobDetail, List<Trigger>> jobDetailMap = new HashMap<JobDetail, List<Trigger>>();

	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println("QuartzInitializerListener  - Initializing Application successfully");

		registerSongExistCheckJob();
		registerUpdateMD5Job();
		registerRestoreOrphanedSongJob();

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			scheduler.scheduleJobs(jobDetailMap, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {

		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		System.out.println("QuartzInitializerListener  - Stopping Application successfully");
	}

	private void registerSongExistCheckJob() {

		JobDetail songExistCheckJob = JobBuilder.newJob(SongExistCheckJob.class).withIdentity("SongExistCheckJob")
				.build();

		Trigger songExistCheckJobTrigger = TriggerBuilder.newTrigger().withIdentity("SongExistCheckJobTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();

		List<Trigger> songExistCheckJobTriggerList = new ArrayList<Trigger>();
		songExistCheckJobTriggerList.add(songExistCheckJobTrigger);

		jobDetailMap.put(songExistCheckJob, songExistCheckJobTriggerList);
	}

	private void registerUpdateMD5Job() {

		JobDetail updateMD5Job = JobBuilder.newJob(SongUpdateMD5Job.class).withIdentity("UpdateMD5Job").build();

		Trigger updateMD5JobTrigger = TriggerBuilder.newTrigger().withIdentity("UpdateMD5JobTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();

		List<Trigger> updateMD5JobTriggerList = new ArrayList<Trigger>();
		updateMD5JobTriggerList.add(updateMD5JobTrigger);

		jobDetailMap.put(updateMD5Job, updateMD5JobTriggerList);

	}

	private void registerRestoreOrphanedSongJob() {

		JobDetail restoreOrphanedSongJob = JobBuilder.newJob(RestoreOrphanedSongJob.class)
				.withIdentity("RestoreOrphanedSongJob").build();

		Trigger restoreOrphanedSongJobTrigger = TriggerBuilder.newTrigger()
				.withIdentity("RestoreOrphanedSongJobTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

		List<Trigger> restoreOrphanedSongJobTriggerList = new ArrayList<Trigger>();
		restoreOrphanedSongJobTriggerList.add(restoreOrphanedSongJobTrigger);

		jobDetailMap.put(restoreOrphanedSongJob, restoreOrphanedSongJobTriggerList);
	}

}
