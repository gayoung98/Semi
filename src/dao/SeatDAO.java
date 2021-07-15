package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*; 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.SeatDTO;

public class SeatDAO {
	private volatile static SeatDAO instance;
	private SeatDAO() {

	}
	public static SeatDAO getInstance() {
		if(instance == null){
			synchronized(SeatDAO.class) {
				if(instance == null)
					instance = new SeatDAO();
			}
		}
		return instance;
	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	public synchronized int insert(String date, String email, String name, String seat_number) throws Exception{
		String sql = "insert into seat values(seat_SEQ.nextval, ?,?,?,?,sysdate)";
		try(Connection con = getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, date);
			pstat.setString(2, email);
			pstat.setString(3, name);
			pstat.setString(4, seat_number);
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int delete(String email, String seat_number) throws Exception{
		String sql = "delete from seat where email = ? and seat_number = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, email);
			pstat.setString(2, seat_number);
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public boolean isReserved(String email, String date) throws Exception{
		String sql = "select * from seat where email = ? and seat_day = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, email);
			pstat.setString(2, date);
			try(ResultSet rs = pstat.executeQuery()){
				if(rs.next()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	public boolean conflict(String date) throws Exception{
		String sql = "select * from seat where seat_day = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, date);
			try(ResultSet rs = pstat.executeQuery()){
				if(rs.next()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	public List<SeatDTO> reservedList() throws Exception{
		List <SeatDTO> li = new ArrayList<SeatDTO>();
		String sql = "select * from seat";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();){
			while(rs.next()) {
				li.add(new SeatDTO(rs.getInt(1), rs.getString(2),  rs.getString(3),  rs.getString(4),  rs.getString(5),  rs.getDate(6)));
			}
			return li;
		}
	}

	public int rownum(String date) throws Exception{
		String sql = "select * from seat where seat_day = ?";
		int count = 0;
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, date);
			try(ResultSet rs = pstat.executeQuery()){
				while(rs.next()) {
					count++;
				}
				return count;
			}
		}
	}
	public boolean mySeat(String email, String seat_number, String date) throws Exception{
		String sql = "select * from seat where email = ? and seat_number = ? and seat_day = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, email);
			pstat.setString(2, seat_number);
			pstat.setString(3, date);
			try(ResultSet rs = pstat.executeQuery()){
				if(rs.next()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}

	public List<SeatDTO> classList(String date, String khclass, String branch) throws Exception{
		List <SeatDTO> li = new ArrayList<SeatDTO>();
		String sql = "select "
				+ "    seat.seq, seat.seat_day, email, seat.name, seat.seat_number, seat.apply_date "
				+ "from "
				+ "    seat join kh_member "
				+ "using (email)"
				+ "where"
				+ "    seat_day = ? and khclass = ? and branch = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, date);
			pstat.setString(2, khclass);
			pstat.setString(3, branch);
			try(ResultSet rs = pstat.executeQuery()){
				while(rs.next()) {
					li.add(new SeatDTO(rs.getInt(1), rs.getString(2),  rs.getString(3),  rs.getString(4),  rs.getString(5),  rs.getDate(6)));
				}
				return li;
			}
		}
	}

	public int min(String date, String seat_number) throws Exception{
        String sql = "select min(seq) from seat where seat_day = ? and seat_number = ?";
        try(Connection con = this.getConnection();
              PreparedStatement pstat = con.prepareStatement(sql)){
           pstat.setString(1, date);
           pstat.setString(2, seat_number);
           try(ResultSet rs = pstat.executeQuery()){
              if(rs.next())
              {
                   return rs.getInt(1);
              }
           }
         return 0;
           }
        }     
	public int deleteSame(int min, String date, String seat_number) throws Exception{
		String sql = "delete from seat where seq > ? and seat_day = ? and seat_number = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, min);
			pstat.setString(2, date);
			pstat.setString(3, seat_number);
			int result = pstat.executeUpdate();
			return result;
		}
	}
}


