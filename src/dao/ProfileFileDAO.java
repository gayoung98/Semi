package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.ProfileFileDTO;

public class ProfileFileDAO {
	private static ProfileFileDAO fd;
	public static ProfileFileDAO  getInstance() {
		if(fd ==null) {
			fd= new ProfileFileDAO ();
		}
		return fd;
	}
	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public int insert(ProfileFileDTO temp) throws Exception{
		String sql = "insert into profile_files values(profile_file_Seq.nextval,?,?,sysdate,?)";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql)){
			psmt.setString(1, temp.getOriName());
			psmt.setString(2, temp.getSysName());
			psmt.setString(3, temp.getParent_key());
			return psmt.executeUpdate();
		}
	}
	
	public ProfileFileDTO getFile(String parentseq) throws Exception{
		String sql = "select * from profile_files where parent=?";
		ProfileFileDTO temp = new ProfileFileDTO();
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);
			){
			psmt.setString(1, parentseq);
			try(ResultSet rs = psmt.executeQuery()){
				if(rs.next()) {
					temp = new ProfileFileDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5));
				}
			}
			return temp;
		}
	}
	
	public String getSysName(String parent) throws Exception{
		String sql = "select sysname from profile_files where parent=?";
		try(Connection conn = this.getConnection();
				PreparedStatement psmt = conn.prepareStatement(sql);
				){
				psmt.setString(1, parent);
				try(ResultSet rs = psmt.executeQuery()){
					if(rs.next()) return rs.getString(1); 
				}
				return "";
		}
	}
	
	public List<ProfileFileDTO> selectBySeq(String i) throws Exception{
		String sql = "select * from profile_files where parent=?";
		List<ProfileFileDTO> list = new ArrayList(); 
		ProfileFileDTO temp = new ProfileFileDTO();
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql);
			){
			psmt.setString(1, i);
			try(ResultSet rs = psmt.executeQuery()){
				while(rs.next()) {
					list.add(new ProfileFileDTO(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5)));
				}
			}
			return list;
		}
	}
	
	public int deletefile(String parent) throws Exception{
		String sql = "delete from profile_files where parent=?";
		try(Connection conn = this.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql)){
			psmt.setString(1, parent);
			return psmt.executeUpdate(); 
		}
	}

}
