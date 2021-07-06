package quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.SchedulerException;



@WebListener
public class ContextListener implements ServletContextListener{

	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			new ResetCronTrigger();
		
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			new ResetCronTrigger();
		
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
