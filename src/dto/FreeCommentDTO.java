package dto;

import java.sql.Date;
import java.sql.Timestamp;

public class FreeCommentDTO {
//	create table freeComment( 
//			seq number primary key,
//			id varchar2(20) not null,
//			writer varchar2(100) not null,
//			comments varchar2(300) not null,
//			write_date timestamp not null,
//			parent number default 0,
//			foregin key(parent) references freeboard(seq) on delete cascade
//			);
//			create sequence freeComment_seq start with 1 increment by 1 nocache nomaxvalue;
//	
	private int seq;
	private String id;
    private String name;
    private String writer;

    private String comments;

	private Timestamp write_date;

    private int parent;
    
    public FreeCommentDTO() {}
    
    public FreeCommentDTO(int seq, String id, String writer, String comments, Timestamp write_date, int parent) {
	this.seq = seq;
	this.id = id;
	this.writer = writer;
	this.comments = comments;
	this.write_date = write_date;
	this.parent = parent;
}
    public FreeCommentDTO(int seq, String id, String name, String writer, String comments, Timestamp write_date, int parent
			) {
		
		this.seq = seq;
		this.id = id;
		this.name = name;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Timestamp getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	
	    
	    
}
