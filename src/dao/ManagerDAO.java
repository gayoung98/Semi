package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.ManagerDTO;
import dto.MemberDTO;
import utils.Util;

public class ManagerDAO {
	private volatile static ManagerDAO instance;
	private ManagerDAO() {

	}
	public static ManagerDAO getInstance(){

		if(instance == null){
			synchronized (ManagerDAO.class) {
				if(instance == null)
					instance = new ManagerDAO();
			}
		}
		return instance;

	}
	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	public boolean login(String id, String pw) throws Exception{
		String sql = "select * from admin where id=? and pw=?";

		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){pstat.setNString(1, id);
				pstat.setNString(2, Util.getSHA512(pw));
				try(ResultSet rs= pstat.executeQuery();){
				return rs.next();

		}

	}
	}
}
