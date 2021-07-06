package dto;

import java.sql.Date;

public class SeatDTO {
	private int seq;
	private String seat_day;
	private String member_number;
	private String member_name;
	private String seat_number;
	private Date apply_date;
	
	public SeatDTO() {}
	public SeatDTO(int seq, String seat_day, String member_number, String member_seat, String seat_number, Date apply_date) {
		this.seq = seq;
		this.seat_day = seat_day;
		this.member_number = member_number;
		this.member_name = member_name;
		this.seat_number = seat_number;
		this.apply_date = apply_date;
	}
	public SeatDTO(String seat_number) {
		this.seat_number = seat_number;
	}
	public SeatDTO(String seat_day, String seat_number) {
		this.seat_day = seat_day;
		this.seat_number = seat_number;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSeat_day() {
		return seat_day;
	}
	public void setSeat_day(String seat_day) {
		this.seat_day = seat_day;
	}
	public String getMember_number() {
		return member_number;
	}
	public void setMember_number(String member_number) {
		this.member_number = member_number;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_seat(String member_name) {
		this.member_name = member_name;
	}
	public String getSeat_number() {
		return seat_number;
	}
	public void setSeat_number(String seat_number) {
		this.seat_number = seat_number;
	}
	public Date getApply_date() {
		return apply_date;
	}
	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}
	
}
