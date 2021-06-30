package dto;

import java.sql.Date;

public class NoticeFilesDTO {
	
	    private int seq;

	    private String oriname;

	    private String sysname;

	    private Date reg_date;

	    private int parent;
	    
	    public NoticeFilesDTO() {};
	    
	    public NoticeFilesDTO(int seq, String oriname, String sysname, Date reg_date, int parent) {
			super();
			this.seq = seq;
			this.oriname = oriname;
			this.sysname = sysname;
			this.reg_date = reg_date;
			this.parent = parent;
		}

	    public int getSeq() {
	        return seq;
	    }

	    public void setSeq(int seq) {
	        this.seq = seq;
	    }

	    public String getOriname() {
	        return oriname;
	    }

	    public void setOriname(String oriname) {
	        this.oriname = oriname;
	    }

	    public String getSysname() {
	        return sysname;
	    }

	    public void setSysname(String sysname) {
	        this.sysname = sysname;
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

