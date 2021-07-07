package dto;

import java.sql.Date;

public class TypingDTO {

	private int seq;
	private String writer;
	private String id;
	private int record;
	private int accurate;
	private Date reg_date;

	public TypingDTO() {
		super();
	}
	
	
	public TypingDTO(int seq, String writer, String id, int record, int accurate, Date reg_date) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.record = record;
		this.accurate = accurate;
		this.reg_date = reg_date;
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


	public int getRecord() {
		return record;
	}


	public void setRecord(int record) {
		this.record = record;
	}


	public int getAccurate() {
		return accurate;
	}


	public void setAccurate(int accurate) {
		this.accurate = accurate;
	}


	public Date getReg_date() {
		return reg_date;
	}


	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	
	
	
}
