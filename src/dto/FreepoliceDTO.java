package dto;

import java.sql.Date;

public class FreepoliceDTO {
	
	private int seq;
	private String id;

    private String writer;

    private String contents;

    private int parent;
    
    private Date reg_date;
    
    public FreepoliceDTO() {};
    
	public FreepoliceDTO(int seq, String id, String writer, String contents, int parent, Date reg_date) {
		super();
		this.seq = seq;
		this.id = id;
		this.writer = writer;
		this.contents = contents;
		this.parent = parent;
		this.reg_date = reg_date;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	
}
