package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.AssFilesDTO;

public class AssFilesDAO {

	private volatile static AssFilesDAO instance;

	private AssFilesDAO() {
	}

	public static AssFilesDAO getInstance(){ //싱글톤 패턴

		if(instance == null){
			synchronized (AssFilesDAO.class) {
				if(instance == null)
					instance = new AssFilesDAO();
			}
		}
		return instance;

	}


	private Connection getConnection() throws Exception{
		Context ctx = new InitialContext(); 
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int insert(AssFilesDTO dto) throws Exception{

		String sql = "insert into AssFiles values(assFiles_seq.nextval, ?, ?, sysdate, ?)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setString(1, dto.getOriName());
			pstat.setString(2, dto.getSysName());
			pstat.setInt(3, dto.getParent());
			int result = pstat.executeUpdate();
			return result;

		}
	}
	
	public int delete(int seq) throws Exception{

		String sql = "delete from assFiles where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			return result;

		}
	}
	public int deleteAll(int parent) throws Exception{
		String sql = "delete from assFiles where parent=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){
			
			pstat.setInt(1, parent);
			int result = pstat.executeUpdate();
			return result;

		}
	}
	
	public int update(AssFilesDTO dto) throws Exception{
		
		int result1=0;
		int result2=0;
		
		String sql1 = "delete from assFiles where parent=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql1);
				){
			
			pstat.setInt(1, dto.getParent());
			result1 = pstat.executeUpdate();
			con.commit();

		}
		
		String sql2 = "insert into AssFiles values(assFiles_seq.nextval, ?, ?, sysdate, ?)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql2);
				){

			pstat.setString(1, dto.getOriName());
			pstat.setString(2, dto.getSysName());
			pstat.setInt(3, dto.getParent());
			result2 = pstat.executeUpdate();
			return result1*result2;

		}
		
	}
	
	public String getSysName(int seq) throws Exception {
		String sql="select sysName from assFiles where seq=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery()){
				if(rs.next()) {
				return rs.getNString("sysName");
				}else {
					return null;
				}
			}
		}
	}

	public AssFilesDTO select(int parent) throws Exception {

		String sql = "select * from assFiles where parent=?";
		try(
				Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){

			pstat.setInt(1, parent);
			ResultSet rs = pstat.executeQuery();
			AssFilesDTO dto = new AssFilesDTO();
			if(rs.next()) {
				
				int seq = rs.getInt("seq");
				String oriName = rs.getString("oriName");
				String sysName = rs.getString("sysName");
				Date reg_date = rs.getDate("reg_date");
				dto =new AssFilesDTO(seq, oriName, sysName, reg_date, parent);
				
			}
			return dto;
		} 
	
	}
	
}
