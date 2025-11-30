package fr.cytech.projetjeejakarta.model;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class QuartzListener implements ServletContextListener {

    private Scheduler scheduler = new StdSchedulerFactory().getScheduler("MonthlyScheduler");

    public QuartzListener() throws SchedulerException {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Create a scheduler
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Pass ServletContext to the scheduler context
            scheduler.getContext().put("servletContext", sce.getServletContext());

            // Define the job
            JobDetail job = JobBuilder.newJob(fr.cytech.projetjeejakarta.model.MonthlyTaskJob.class)
                    .withIdentity("monthlyTaskJob", "group1")
                    .build();

            // Trigger: first day of every month at 00:00
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("monthlyTaskTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 * ?").withMisfireHandlingInstructionFireAndProceed()).build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (scheduler != null) scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

