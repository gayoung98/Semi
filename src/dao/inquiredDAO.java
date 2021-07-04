package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	

}
