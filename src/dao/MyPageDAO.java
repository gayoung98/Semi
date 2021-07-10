package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import dto.FreeBoardDTO;
import dto.InquireDTO;
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
	
	public MemberDTO getTeacher(MemberDTO mb) throws Exception{
		String sql = "select * from kh_member where khclass =? and branch =? and position = 'teacher'";
		MemberDTO temp = null; 
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, mb.getKhClass());
			psmt.setString(2, mb.getBranch());
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
	
	public List<FreeBoardDTO> getWrittenFreeBoard(String session) throws Exception{
	String sql = "select * from (select row_number() over(order by seq desc) rnum, seq,branch,writer,title, contents, id,write_date, viewCount, policecount from freeboard where writer =?) where rnum between 1 and 4";
	List<FreeBoardDTO> li =  new ArrayList<FreeBoardDTO>();
	try(Connection conn = this.getConnection();
		PreparedStatement psmt = conn.prepareStatement(sql);){
		psmt.setString(1, session); 
		try(ResultSet rs = psmt.executeQuery()){
			while(rs.next()) {
				li.add(new FreeBoardDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getDate(8),rs.getInt(9),rs.getInt(10))); 
			}
			return li; 
		}
	}
	
	}
	
	public List<InquireDTO> getWrittenInquired(String session) throws Exception{
		String sql = "select * from (select row_number() over(order by seq desc) rnum,seq,id,major_category,sub_category, contents,recomment,reg_date from inquire where id =?) where rnum between 1 and 4";
		List<InquireDTO> li =  new ArrayList<InquireDTO>();
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, session); 
			try(ResultSet rs = psmt.executeQuery()){
				while(rs.next()) {
					li.add(new InquireDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8))); 
				}
				return li; 
			}
		}
		
		}
	public List<MemberDTO> getStudentList(MemberDTO temp) throws Exception{
		String sql = "select * from kh_member where branch =? and khclass =? and position not in ?";
		List<MemberDTO> li =  new ArrayList<MemberDTO>();
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, temp.getBranch());
			psmt.setString(2, temp.getKhClass()); 
			psmt.setString(3, temp.getPosition()); 
			try(ResultSet rs = psmt.executeQuery()){
				while(rs.next()) {
					li.add(new MemberDTO(rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getDate(10))); 
				}
				return li; 
			}
		}
		
		}

}
