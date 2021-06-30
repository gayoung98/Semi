package dto;

import java.sql.Date;

public class NoticeCommentsDTO {
	
	private int seq;
	private String id;

    private String writer;

    private String comments;

    private Date write_date;

    private int parent;
    
public NoticeCommentsDTO() {}
    
    public NoticeCommentsDTO(int seq, String id, String writer, String comments, Date write_date, int parent) {
	this.seq = seq;
	this.id = id;
	this.writer = writer;
	this.comments = comments;
	this.write_date = write_date;
	this.parent = parent;
    }
    
    public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
}
