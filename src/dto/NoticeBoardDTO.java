package dto;

import java.sql.Date;
import java.sql.Timestamp;

public class NoticeBoardDTO {
//	create table noticeBoard(
//			seq number primary key,
//			writer varchar2(100) not null,
//			title varchar2(200) not null,
//			contents varchar2(4000) not null,
//			write_date timestmp not null,
//			khClass varchar2(10) not null,
//			branch varchar2(10) not null,
//			viewCount number not null,
//			foreign key (writer) references kh_member(email) on delete cascade
//			);
//	create sequence noticeBoard_seq start with 1 increment by 1 nocache nomaxvalue;

		private int seq;
	 	private String writer;

	    private String title;

	    private String contents;

	    private Timestamp write_date;

	    private String khClass;

	    private String branch;
	    private int viewCount;
	    
	    public NoticeBoardDTO() {};

	  	    public NoticeBoardDTO(int seq, String writer, String title, String contents, Timestamp write_date, String khClass,
				String branch,int viewCount) {
			
			this.seq = seq;
			this.writer = writer;
			this.title = title;
			this.contents = contents;
			this.write_date = write_date;
			this.khClass = khClass;
			this.branch = branch;
			this.viewCount=viewCount;
		}

	  	  public NoticeBoardDTO(int seq, int viewCount) {
	    	  this.seq = seq;
	    	  this.viewCount = viewCount;
		}


		public NoticeBoardDTO(int seq, String branch, String khClass, String writer, String title,
				String contents, Timestamp write_date, int viewCount) {
			this.seq = seq;
			this.writer = writer;
			this.title = title;
			this.contents = contents;
			this.write_date = write_date;
			this.khClass = khClass;
			this.branch = branch;
			this.viewCount=viewCount;
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

	

	    public Timestamp getWrite_date() {
			return write_date;
		}

		public void setWrite_date(Timestamp write_date) {
			this.write_date = write_date;
		}

		public String getKhClass() {
			return khClass;
		}

		public void setKhClass(String khClass) {
			this.khClass = khClass;
		}

		public void setClass(String khClass) {
	        this.khClass = khClass;
	    }

	    public String getBranch() {
	        return branch;
	    }

	    public void setBranch(String branch) {
	        this.branch = branch;
	    }

		public int getViewCount() {
			return viewCount;
		}

		public void setViewCount(int viewCount) {
			this.viewCount = viewCount;
		}
	    
}
