package dto;

import java.sql.Date;

public class InquireDTO {
   
   private int seq; 
   private String id;
   private String major_category;
   private String sub_category;
   private String contents;
   private String recomment;
   private Date reg_date;
   public InquireDTO(int seq, String id, String major_category, String sub_category, String contents,String recomment,
         Date reg_date) {
      super();
      this.seq = seq;
      this.id = id;
      this.major_category = major_category;
      this.sub_category = sub_category;
      this.contents = contents;
      this.recomment=recomment;
      this.reg_date = reg_date;
   }
   public InquireDTO(String id, String major_category, String sub_category, String contents) {
      super();
      this.id = id;
      this.major_category = major_category;
      this.sub_category = sub_category;
      this.contents = contents;
   }
   
   public InquireDTO(int seq, String id, String major_category, String sub_category, Date reg_date) {
	super();
	this.seq = seq;
	this.id = id;
	this.major_category = major_category;
	this.sub_category = sub_category;
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
public String getMajor_category() {
      return major_category;
   }
   public void setMajor_category(String major_category) {
      this.major_category = major_category;
   }
   public String getSub_category() {
      return sub_category;
   }
   public void setSub_category(String sub_category) {
      this.sub_category = sub_category;
   }
   public String getContents() {
      return contents;
   }
   public void setContents(String contents) {
      this.contents = contents;
   }
   
   public String getRecomment() {
	return recomment;
}
public void setRecomment(String recomment) {
	this.recomment = recomment;
}
public Date getReg_date() {
      return reg_date;
   }
   public void setReg_date(Date reg_date) {
      this.reg_date = reg_date;
   }
}