package dto;

import java.sql.Date;

public class FreeBoardDTO {
	
	 
	private int seq;

	    private String branch;

	    private String writer;

	    private String title;

	    private String contents;

	    private String id;
	    
	    private String name;

		private Date write_date;

	    private int viewCount;

	    private int policeCount;
	    
	    
	    
	    public FreeBoardDTO() {}

		public FreeBoardDTO(int seq, String branch, String writer, String title, String contents, String id,
				Date write_date, int viewCount, int policeCount) {
			super();
			this.seq = seq;
			this.branch = branch;
			this.writer = writer;
			this.title = title;
			this.contents = contents;
			this.id = id;
			this.write_date = write_date;
			this.viewCount = viewCount;
			this.policeCount = policeCount;
		}

		public FreeBoardDTO(int seq, String branch, String writer, String title, Date write_date) {
			super();
			this.seq = seq;
			this.branch = branch;
			this.writer = writer;
			this.title = title;
			this.write_date = write_date;
		}
		
		 public FreeBoardDTO(int seq, String branch, String writer, String title, String contents, String id,
				String name, Date write_date, int viewCount, int policeCount) {
			super();
			this.seq = seq;
			this.branch = branch;
			this.writer = writer;
			this.title = title;
			this.contents = contents;
			this.id = id;
			this.name = name;
			this.write_date = write_date;
			this.viewCount = viewCount;
			this.policeCount = policeCount;
		}

		public FreeBoardDTO(int seq, int viewCount) {
	    	  this.seq = seq;
	    	  this.viewCount = viewCount;
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
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

		public int getPoliceCount() {
			return policeCount;
		}

		public void setPoliceCount(int policeCount) {
			this.policeCount = policeCount;
		}

		
	
	
}
