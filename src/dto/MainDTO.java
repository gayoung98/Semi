package dto;

import java.sql.Date;

public class MainDTO {
	private int seq;
	private String writer;
	private String member_number;
	private String contents;
	private String kh_class;
	private Date write_date;

	public MainDTO() {}
	public MainDTO(int seq, String writer, String member_number, String contents, String kh_class, Date write_date) {
		this.seq = seq;
		this.writer = writer;
		this.member_number = member_number;
		this.contents = contents;
		this.kh_class = kh_class;
		this.write_date = write_date;
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
	public String getMember_number() {
		return member_number;
	}
	public void setMember_number(String member_number) {
		this.member_number = member_number;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getKh_class() {
		return kh_class;
	}
	public void setKh_class(String kh_class) {
		this.kh_class = kh_class;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	
}
