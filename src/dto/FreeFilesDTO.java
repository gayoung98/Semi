package dto;

import java.sql.Date;

public class FreeFilesDTO {
	

	private int seq;
	private String oriname;

	    private String sysname;

	    private Date reg_date;

	    private int parent;
	
	    
	    public FreeFilesDTO() {};
	    public FreeFilesDTO(int seq, String oriname, String sysname, Date reg_date, int parent) {
		
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

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

}
