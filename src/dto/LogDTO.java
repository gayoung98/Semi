package dto;

public class LogDTO {
//	create table log (
//			seq number primary key,
//			log_message varchar2(300) not null
//			);
	
	private int seq;
	private String log_message;
	
	public LogDTO() {
		
	}

	public LogDTO(int seq, String log_message) {
		
		this.seq = seq;
		this.log_message = log_message;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getLog_message() {
		return log_message;
	}

	public void setLog_message(String log_message) {
		this.log_message = log_message;
	}
	
}
