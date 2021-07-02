package dto;

import java.sql.Date;

public class FreePoliceDTO {
	
	private int seq;
	private String id;

    private String contents;

    private int parent;
    
    private Date reg_date;
    
    public FreePoliceDTO() {}

	public FreePoliceDTO(int seq, String id, String contents, int parent, Date reg_date) {
		super();
		this.seq = seq;
		this.id = id;
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