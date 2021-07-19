package dto;

import java.sql.Date;

public class NoticeFilesDTO {
	
//	create table noticeFiles(
//			seq number primary key,
//			oriName varchar2(400) not null,
//			sysName varchar2(400) not null,
//			reg_date date default sysdate not null,
//			parent number default 0,
//			foregin key(parent) references noticeboard(seq) on delete cascade
//			);
//
//			create sequence noticeFiles_seq start with 1 increment by 1 nocache nomaxvalue;
	
	    private int seq;

	    private String oriName;

	    private String sysName;

	    private Date reg_date;

	    private int parent;
	    
	    public NoticeFilesDTO() {};
	    
	    public NoticeFilesDTO(int seq, String oriName, String sysName, Date reg_date, int parent) {
			super();
			this.seq = seq;
			this.oriName = oriName;
			this.sysName = sysName;
			this.reg_date = reg_date;
			this.parent = parent;
		}

	    public int getSeq() {
	        return seq;
	    }

	    public void setSeq(int seq) {
	        this.seq = seq;
	    }

	    public String getOriName() {
	        return oriName;
	    }

	    public void setOriName(String oriName) {
	        this.oriName = oriName;
	    }

	    public String getSysName() {
	        return sysName;
	    }

	    public void setSysName(String sysName) {
	        this.sysName = sysName;
	    }

	    public Date getRegDate() {
	        return reg_date;
	    }

	    public void setRegDate(Date reg_date) {
	        this.reg_date = reg_date;
	    }

	    public int getParent() {
	        return parent;
	    }

	    public void setParent(int parent) {
	        this.parent = parent;
	    }

	
	   
	}

