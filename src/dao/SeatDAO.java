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
	public int insert(String email, String name, String seat_number) throws Exception{
		String sql = "insert into seat values(seat_SEQ.nextval, 'm',?,?,?,sysdate)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, email);
			pstat.setString(2, name);
			pstat.setString(3, seat_number);
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public int delete(SeatDTO dto) throws Exception{
		String sql = "delete from seat where seat_number = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getSeat_number());
			int result = pstat.executeUpdate();
			return result;
		}
	}
	public boolean isReserved(String email) throws Exception{
		String sql = "select * from seat where email = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setString(1, email);
			try(ResultSet rs = pstat.executeQuery()){
				if(rs.next()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	public List<String> reservedList() throws Exception{
		List <String> li = new ArrayList<String>();
		String sql = "select seat_number from seat";
		try(Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();){
				while(rs.next()) {
					li.add(rs.getString(1));
				}
				return li;
			}
		}
	
	public int rownum() throws Exception{
		String sql = "select * from seat";
		int count = 0;
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				count++;
			}
			return count;
		}
	}
}


