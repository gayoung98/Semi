package dto;

import java.sql.Date;

public class AssFilesDTO {
	
//	create table assFiles(
//		    seq number primary key,
//		    oriName varchar(400),
//		    sysName varchar(400),
//		    reg_date Date default sysdate,
//		    parent constraints af_parent_fk references ass(seq) on delete cascade 
//		);
	
//	create sequence assFiles_seq start with 1 increment by 1 nocache nomaxvalue;
	
	private int seq;
	private String oriName;
	private String sysName;
	private Date reg_date;
	private int parent;
	
	public AssFilesDTO() {
		super();
	}

	public AssFilesDTO(int seq, String oriName, String sysName, Date reg_date, int parent) {
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
