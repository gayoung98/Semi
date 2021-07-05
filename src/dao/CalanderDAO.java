package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.*;

public class CalanderDAO {

	private static CalanderDAO cd;
	public static CalanderDAO getInstance() {
		if(cd ==null) {
			cd= new CalanderDAO();
		}
		return cd;
	}
	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public List<Map<Integer,String[]>> getSchedual(String khclass,String branch,int month) throws Exception{
		String sql = "select extract(DAY from event_date), contents, day_type from Academic_Calendar where extract(MONTH from event_date) =? and khclass = ? and branch = ?";
		List<Map<Integer,String[]>> li = new ArrayList<Map<Integer, String[]>>();
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);){
			 psmt.setInt(1, month);
			 psmt.setString(2, khclass);
			 psmt.setString(3, branch);
			 try(ResultSet rs = psmt.executeQuery();){
				 while(rs.next()) {
					 System.out.println(rs.getInt(1)+rs.getString(2)+rs.getString(3));
					 String[] temp = {rs.getString(2),rs.getString(3)};
					 Map<Integer,String[]> mp = new HashMap<Integer, String[]>();
					 mp.put(rs.getInt(1),temp);
					 li.add(mp);
				 }
			 }
			 return li;
		}
	
	}
	
}
