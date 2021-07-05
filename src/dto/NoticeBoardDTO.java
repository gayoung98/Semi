package dto;

import java.sql.Date;

public class NoticeBoardDTO {
	

		private int seq;
	 	private String writer;

	    private String title;

	    private String contents;

	    private Date write_date;

	    private String khClass;

	    private String branch;
	    private int viewCount;
	    
	    public NoticeBoardDTO() {};

	  	    public NoticeBoardDTO(int seq, String writer, String title, String contents, Date write_date, String khClass,
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

	

	    public Date getWrite_date() {
			return write_date;
		}

		public void setWrite_date(Date write_date) {
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
