package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.MemberDTO;

public class MyPageDAO {
	
	private static MyPageDAO mpd;
	private MyPageDAO() {}
	
	public static MyPageDAO getInstance() {
		if(mpd == null) return new MyPageDAO(); 
		return mpd;
	}
	
	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public String getID(String session) throws Exception{
		String sql = "select id from kh_member where email =?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, session);
			try(ResultSet rs = psmt.executeQuery();){
				if(rs.next()) {
					return rs.getString(1);
				}
			}
			return null;
		}
	}
	
	public MemberDTO getMember(String session) throws Exception {
		String sql = "select * from kh_member where email =?";
		 MemberDTO temp = null; 
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql)){
			psmt.setString(1, session);
			try(ResultSet rs = psmt.executeQuery();){
				if(rs.next()) {
					temp = new  MemberDTO(rs.getInt(1),
									  rs.getString(2),
									  rs.getString(3),
									  rs.getString(4),
									  rs.getString(5),
									  rs.getString(6),
									  rs.getString(7),
									  rs.getString(8),
									  rs.getString(9),
									  rs.getDate(10));
				}
				return temp;
			}
		}
		
	}
	
	public int update(MemberDTO mb) throws Exception{
		String sql = "update kh_member set pw=?,phone=? where id=?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, mb.getPw());
			psmt.setString(2, mb.getPhone());
			psmt.setString(3, mb.getId());
			return psmt.executeUpdate();
		}
	}
	
	

}
