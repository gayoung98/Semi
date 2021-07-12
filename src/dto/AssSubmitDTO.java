package dto;

import java.sql.Date;

public class AssSubmitDTO {

	private int seq;
	private String writer;
	private String id;
	private String name;
	private String oriName;
	private String sysName;
	private Date reg_date;
	private int parent;
	
	public AssSubmitDTO() {
		super();
	}
	
	public AssSubmitDTO(int seq, String writer, String id, String oriName, String sysName, Date reg_date, int parent) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.oriName = oriName;
		this.sysName = sysName;
		this.reg_date = reg_date;
		this.parent = parent;
	}
	
	public AssSubmitDTO(int seq, String writer, String id, String name, String oriName, String sysName, Date reg_date,
			int parent) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.name = name;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


