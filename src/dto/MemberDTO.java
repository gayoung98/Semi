package dto;

import java.sql.Date;

public class MemberDTO {
   private int seq;
   private String email;
   private String pw;
   private String name;
   private String phone;
   private String id;
   private String khClass;
   private String branch;
   private String position;
   private Date signUpDate;
   
   public MemberDTO(int seq, String email, String pw, String name, String phone, String id, String khClass, String branch, String position, Date signUpDate) {
      this.seq = seq;
      this.email = email;
      this.pw = pw;
      this.name = name;
      this.phone = phone;
      this.id = id;
      this.khClass = khClass;
      this.branch = branch;
      this.position = position;
      this.signUpDate = signUpDate;
   }
   
   public MemberDTO(String email, String pw, String name, String phone, String id, String khClass, String branch, String position, Date signUpDate) {
      this.email = email;
      this.pw = pw;
      this.name = name;
      this.phone = phone;
      this.id = id;
      this.khClass = khClass;
      this.branch = branch;
      this.position = position;
      this.signUpDate = signUpDate;
   }
   
   public MemberDTO(String email, String name, String phone, String id, String khClass, String branch,
		String position, Date signUpDate) {
	
	
	this.email = email;
	this.name = name;
	this.phone = phone;
	this.id = id;
	this.khClass = khClass;
	this.branch = branch;
	this.position = position;
	this.signUpDate = signUpDate;
}

public MemberDTO(String id, String khClass, String branch) {
      this.id = id;
      this.khClass = khClass;
      this.branch = branch;
   }

public MemberDTO(String name, String id, String khClass, String branch) {
    this.name = name;
	this.id = id;
    this.khClass = khClass;
    this.branch = branch;
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

   public Date getSignUpDate() {
      return signUpDate;
   }

   public void setSignUpDate(Date signUpDate) {
      this.signUpDate = signUpDate;
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
   

   
   
}

