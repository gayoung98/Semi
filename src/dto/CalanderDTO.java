package dto;

import java.sql.Date;

public class CalanderDTO {
	
	private int seq;
	private String branch;
	private String khclass;
	private String contents;
	private String day_type;
	private Date event_date;
	
	public CalanderDTO(int seq, String branch, String khclass, String contents, String day_type, Date event_date) {
		super();
		this.seq = seq;
		this.branch = branch;
		this.khclass = khclass;
		this.contents = contents;
		this.day_type = day_type;
		this.event_date = event_date;
	}
	
	public CalanderDTO(String contents, String day_type, Date event_date) {
		this.contents = contents;
		this.day_type = day_type;
		this.event_date = event_date;
	}
	
	public CalanderDTO(String contents, Date event_date) {
		this.contents = contents;
		this.event_date = event_date;
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getKhclass() {
		return khclass;
	}
	public void setKhclass(String khclass) {
		this.khclass = khclass;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getDay_type() {
		return day_type;
	}
	public void setDay_type(String day_type) {
		this.day_type = day_type;
	}
	public Date getEvent_date() {
		return event_date;
	}
	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	} 
	
	

}
