package dto;

import java.sql.Date;

public class MainDTO {
	private int seq;
	private String writer;
	private String id;
	private String contents;
	private String kh_class;
	private Date write_date;

	public MainDTO() {}
	public MainDTO(int seq, String writer, String id, String contents, String kh_class, Date write_date) {
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.contents = contents;
		this.kh_class = kh_class;
		this.write_date = write_date;
	}
	public MainDTO(String writer, String contents) {
		this.writer = writer;
		this.contents = contents;
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
