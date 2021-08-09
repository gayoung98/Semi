package dto;

import java.sql.Date;

public class AssDTO {
	
//	create table ass(
//		    seq number primary key,
//		    writer constraints a_writer_fk references kh_member(email) on delete set null,
//		    id varchar2(20),
//		    title varchar2(200),
//		    contents varchar2(4000),
//		    khClass varchar2(10),
//		    branch varchar2(10),
//		    write_date date,
//		    viewCount number
//		);
	
//	create sequence ass_seq start with 1 increment by 1 nocache nomaxvalue;
	
//	create table ass_seq_table(
//		    ass_seq number primary key
//		);

	private int seq;
	private String writer;
	private String id;
	private String name;
	private String title;
	private String contents;
	private String khClass;
	private String branch;
	private Date write_date;
	private int viewCount;

	public AssDTO() {}

	public AssDTO(int seq, String writer, String id, String title, String contents, String khClass, String branch,
			Date write_date, int viewCount) {
		
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.khClass = khClass;
		this.branch = branch;
		this.write_date = write_date;
		this.viewCount = viewCount;
	}
	

	public AssDTO(int seq, String writer, String id, String name, String title, String contents, String khClass,
			String branch, Date write_date, int viewCount) {
		super();
		this.seq = seq;
		this.writer = writer;
		this.id = id;
		this.name = name;
		this.title = title;
		this.contents = contents;
		this.khClass = khClass;
		this.branch = branch;
		this.write_date = write_date;
		this.viewCount = viewCount;
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
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getKhClass() {
		return khClass;
	}

	public void setKhClass(String khClass) {
		this.khClass = khClass;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


}
