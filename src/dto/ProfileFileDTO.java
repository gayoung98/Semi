package dto;

import java.sql.Date;

public class ProfileFileDTO {
	
	private int seq;
	private String oriName;
	private String sysName;
	private Date date; 
	private String parent_key;
	
	public ProfileFileDTO() {};
	
	public ProfileFileDTO(int seq, String oriName, String sysName, Date date, String parent_key) {
		super();
		this.seq = seq;
		this.oriName = oriName;
		this.sysName = sysName;
		this.date = date;
		this.parent_key = parent_key;
	}
	public ProfileFileDTO(String oriName, String sysName, String parent_key) {
		this.oriName = oriName;
		this.sysName = sysName;
		this.parent_key = parent_key;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getParent_key() {
		return parent_key;
	}
	public void setParent_key(String parent_key) {
		this.parent_key = parent_key;
	}
}
