package dto;

import java.sql.Date;

public class AssDTO {

	private int seq;
	private String writer;
	private String id;
	private String title;
	private String contents;
	private Date write_date;
	
	public AssDTO() {
		super();
	}

	public AssDTO(int seq, String writer, String id, String title, String contents, Date write_date) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.title = title;
		this.contents = contents;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

		
	
}
