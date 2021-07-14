	package dao;

	import java.sql.Connection;


	import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.sql.DataSource;

import dto.FreeFilesDTO;

	public class FreeFilesDAO {
		private static FreeFilesDAO instance;
		FreeBoardDAO bdao = FreeBoardDAO.getInstance();

		public synchronized static FreeFilesDAO getInstance() {
			if (instance == null) {
				instance = new FreeFilesDAO();
			}
			return instance;
		}

		private FreeFilesDAO() {}

		private Connection getConnection() throws Exception { 
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
			return ds.getConnection();
		}

		public String getSysName(int seq) throws Exception {
			String sql="select sysName from freefiles where seq=?";
			try(Connection con =this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql)){
				pstat.setInt(1, seq);
				try(ResultSet rs = pstat.executeQuery()){
					rs.next();
					return rs.getNString("sysname");
				}
			}
		}

		
		public int fileUpload(FreeFilesDTO dto) throws Exception {
			String sql="insert into freefiles values(freefiles_seq.nextval,?,?,sysdate,?)";
			try(Connection con =this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql)){
				pstat.setNString(1, dto.getOriName());
				pstat.setNString(2, dto.getSysName());
				pstat.setInt(3,dto.getParent());
				int result = pstat.executeUpdate();
				return result;


			}
		}
		
		
		
		public List<FreeFilesDTO>selectAll(int parent) throws Exception{
			String sql="select *from freefiles where parent=?";
			try(Connection con =this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql)){
				pstat.setInt(1,parent);					
				try(ResultSet rs =pstat.executeQuery();){
					List<FreeFilesDTO> list = new ArrayList<>();
					while(rs.next()) {
						int seq = rs.getInt("seq");
						String oriName = rs.getNString("oriName");
						String sysName = rs.getNString("sysName");
						Date reg_date =rs.getDate("reg_date");
						int parent1 = rs.getInt("parent");

						FreeFilesDTO dto = new FreeFilesDTO(seq,oriName,sysName,reg_date,parent1);
						list.add(dto);
					}
					return list;
				}
			}	
		}

		public int fileDelete(int seq) throws SQLException, Exception {
			String sql="delete from freeFiles where seq=?";
			try(Connection con =this.getConnection();
					PreparedStatement pstat = con.prepareStatement(sql)){
				pstat.setInt(1, seq);
				int result = pstat.executeUpdate();
				return result;

			}
		}
		
		
		
	}


