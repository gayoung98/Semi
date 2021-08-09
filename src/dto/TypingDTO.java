package dto;

import java.sql.Date;

public class TypingDTO {
	
//	create table typing(
//		    seq number primary key,
//		    writer constraints typ_writer_fk references kh_member(email) on delete cascade,
//		    id varchar2(20),
//		    record number default 0,
//		    accuracy number default 0,
//		    reg_date Date default sysdate
//		);
	
//	create sequence typing_seq start with 1 increment by 1 nocache nomaxvalue;

	private int seq;
	private String writer;
	private String id;
	private int record;
	private int accuracy;
	private String reg_date;
	
	public TypingDTO() {
		super();
	}
	
	public TypingDTO(int seq, String writer, String id, int record, int accuracy, String reg_date) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.record = record;
		this.accuracy = accuracy;
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
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}


	
	
	
}
