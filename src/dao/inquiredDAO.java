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

import dto.InquireDTO;


public class inquiredDAO {
	
	private static inquiredDAO bd;
	public static inquiredDAO getInstance() {
		if(bd ==null) {
			bd= new inquiredDAO();
		}
		return bd;
	}
	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public int insert(InquireDTO temp) throws Exception{
		String sql = "insert into inquire values(inquire_seq.nextval,?,?,?,?,null,sysdate)";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1, temp.getId());
			psmt.setString(2, temp.getMajor_category());
			psmt.setString(3, temp.getSub_category());
			psmt.setString(4, temp.getContents());
			return psmt.executeUpdate();
		}
	}
	
	public InquireDTO getDTO(int seq) throws Exception{
		String sql = "select * from inquire where seq = ?";
		InquireDTO idto = null; 
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setInt(1,seq);
			try(ResultSet rs = psmt.executeQuery();){
				if(rs.next()) {
					idto = new InquireDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7));
				}
			}
			return idto;	
		}
	}
	
	
	public int delete(int seq) throws Exception{
		String sql = "delete from inquire where seq = ?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setInt(1,seq);
			return psmt.executeUpdate();	
		}
	}
	
	public int update(InquireDTO temp) throws Exception{
		String sql = "update inquire set id=?,major_category=?,sub_category=?, contents=?,reg_date=sysdate where seq = ?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1,temp.getId());
			psmt.setString(2,temp.getMajor_category());
			psmt.setString(3,temp.getSub_category());
			psmt.setString(4,temp.getContents());
			psmt.setInt(5,temp.getSeq());
			return psmt.executeUpdate();	
		}
	}
	
	public List<InquireDTO> getList(String session) throws Exception{
		String sql = "select * from inquire where id =? order by reg_date";
		List<InquireDTO> li = new ArrayList<InquireDTO>(); 
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			psmt.setString(1,session);
			try(ResultSet rs = psmt.executeQuery();){
				while(rs.next()) {
					li.add(new InquireDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7)));
				}
			}
			return li;	
		}
	}

}
