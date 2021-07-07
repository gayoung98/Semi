package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.MainDTO;
import dto.MemberDTO;

public class MainDAO {
	private volatile static MainDAO instance;
	private MainDAO() {
		
	}
	public static MainDAO getInstance() {
		if(instance == null){
			synchronized(MainDAO.class) {
				if(instance == null)
					instance = new MainDAO();
			}
		}
		return instance;
	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int writechat(String writer, String id, String contents, String kh_class)throws Exception{
		String sql = "insert into chatBoard values(chatBoard_SEQ.nextval, ?, ?, ?, ?, sysdate)";
		try(Connection con =  this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, writer);
			pstat.setString(2, id);
			pstat.setString(3, contents);
			pstat.setString(4, kh_class);
			int result = pstat.executeUpdate();
			return result;
		}

	}
	public List<MainDTO> getAllList() throws Exception{
		String sql = "select * from chatBoard";
		List<MainDTO> list = new ArrayList<>();
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();
				){
			while(rs.next()) {
				list.add(new MainDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6)));
			}
			return list;
		}
	}
	public List<MainDTO> likeFacebook(int viewcount,int index) throws Exception{
	      String sql = "select * from(select row_number() over(order by seq desc) rnum, seq, writer, id, contents, class, write_date from chatBoard where contents is not null) where rnum between ? and ?";
	      List<MainDTO> li = new ArrayList();
	      try(Connection conn = this.getConnection();
	         PreparedStatement psmt = conn.prepareStatement(sql)){
	         psmt.setInt(1, 1+viewcount*(index-1));
	         psmt.setInt(2, viewcount*index);
	         try(ResultSet rs = psmt.executeQuery()){
	            while(rs.next()) {
	            	li.add(new MainDTO(rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7)));
	            }
	         }
	         return li;
	      }
	   }
	
	public String getName(String email) throws Exception{
		String sql = "select name from kh_member where email = ?";
		String name = null;
		try(Connection con = this.getConnection();
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, email);
			try(ResultSet rs = psmt.executeQuery();){
				if(rs.next()) {
					name = rs.getString("name");
				}
			}
			return name;
		}
		
	}

}


//	public static void main(String[] args) throws Exception{
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//	      String url = "jdbc:oracle:thin:@database-1.c3js76768snl.ap-northeast-2.rds.amazonaws.com:1521:DATABASE";
//	      Connection conn = DriverManager.getConnection(url,"admin","hm930810");
//	
//	      String sql = "insert into kh_member values(seq.nextval,?,?,?,?,?,?,sysdate)";
//			try(
//				PreparedStatement pstat = conn.prepareStatement(sql);){
//				pstat.setString(1, "가영1");
//				pstat.setString(2, "가영2");
//				pstat.setString(3, "가영3");
//				pstat.setString(4, "가영4");
//				pstat.setString(5, "가영5");
//				pstat.setString(6, "Y");
//				
//				int result = pstat.executeUpdate();
//				System.out.println("커밋완료.");
//			}
//	
//	}

