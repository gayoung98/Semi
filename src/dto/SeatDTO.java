package dto;

import java.sql.Date;

public class SeatDTO {
	private int seq;
	private String seat_day;
	private String email;
	private String name;
	private String seat_number;
	private Date apply_date;
	
	public SeatDTO() {}
	public SeatDTO(int seq, String seat_day, String email, String name, String member_seat, String seat_number, Date apply_date) {
		this.seq = seq;
		this.seat_day = seat_day;
		this.email = email;
		this.name = name;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
