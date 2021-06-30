package dto;

import java.sql.Date;

public class MemberDTO {
		
	private int seq;
	private String name;
	private String phone;
	private String email;
	private String pw;
	private String id;
	private String khClass;
	private String branch;
	private String position;
	private Date Sign_UP_DATE;
	
	public MemberDTO() {}
	
	public MemberDTO(int seq, String name, String phone, String email, String pw, String id, String khClass,
			String branch, String position, Date Sign_UP_DATE) {
		super();
		this.seq = seq;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.pw = pw;
		this.id = id;
		this.khClass = khClass;
		this.branch = branch;
		this.position = position;
		this.Sign_UP_DATE = Sign_UP_DATE;
	}

	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getSign_UP_DATE() {
		return Sign_UP_DATE;
	}
	public void setSign_UP_DATE(Date Sign_UP_DATE) {
		this.Sign_UP_DATE = Sign_UP_DATE;
	}
	

	
}
