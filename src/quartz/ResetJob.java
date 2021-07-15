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
		int result3 = managerDao.deleteFreePolice();
		
		if(result2>0 && result3>0) {
			
			l.warn("채팅, 자리신청, 신고 데이터 삭제 완료");
		}else if(result2>0 && result3<1) {
			l.warn("채팅, 자리신청 데이터 삭제 완료");
		}else if(result2<1 && result3>0) {
			l.warn("채팅, 신고 데이터 삭제 완료");
		}else {
			l.warn("채팅 데이터 삭제 완료");
		}
		managerDao.resetChatMessage();
		}catch(Exception e) {
			
		}
	}
}
