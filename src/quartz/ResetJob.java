package quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import dao.ManagerDAO;

public class ResetJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Logger l = Logger.getLogger(ResetJob.class);
		ManagerDAO managerDao = ManagerDAO.getInstance();    
		try {
		int result1 = managerDao.deleteChat();
		int result2 = managerDao.deleteSeat();
		if(result1>0 && result2>0) {
			l.warn("자리신청, 채팅 데이터 삭제 성공");
			managerDao.resetChatMessage();
		}else if(result1>0 && result2<1) {
			l.warn("채팅 데이터 삭제 성공");
			managerDao.resetChatMessage();
		}else if(result1<1 && result2>0) {
			l.warn("자리신청 데이터 삭제 성공");
		}else {
			l.warn("데이터 삭제 실패");
		}
		
		}catch(Exception e) {
			
		}
	}
}
