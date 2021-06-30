package dto;

import java.sql.Date;

public class inquiredDTO {
   
   private int seq; 
   private String reporter;
   private String major_category;
   private String sub_category;
   private String contents;
   private Date reg_date;
   public inquiredDTO(int seq, String reporter, String major_category, String sub_category, String contents,
         Date reg_date) {
      super();
      this.seq = seq;
      this.reporter = reporter;
      this.major_category = major_category;
      this.sub_category = sub_category;
      this.contents = contents;
      this.reg_date = reg_date;
   }
   public inquiredDTO(String reporter, String major_category, String sub_category, String contents) {
      super();
      this.reporter = reporter;
      this.major_category = major_category;
      this.sub_category = sub_category;
      this.contents = contents;
   }
   public int getSeq() {
      return seq;
   }
   public void setSeq(int seq) {
      this.seq = seq;
   }
   public String getReporter() {
      return reporter;
   }
   public void setReporter(String reporter) {
      this.reporter = reporter;
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
   public Date getReg_date() {
      return reg_date;
   }
   public void setReg_date(Date reg_date) {
      this.reg_date = reg_date;
   }
}