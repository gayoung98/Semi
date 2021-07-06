package quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dao.ManagerDAO;

public class ResetJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ManagerDAO managerDao = ManagerDAO.getInstance();    
		try {
		int result1 = managerDao.deleteChat();
		int result2 = managerDao.deleteSeat();
		if(result1>0 && result2>0) {
			System.out.println("데이터 삭제 성공");
		}else {
			System.out.println("데이터 삭제 실패");
		}
		}catch(Exception e) {
			
		}
	}
}
