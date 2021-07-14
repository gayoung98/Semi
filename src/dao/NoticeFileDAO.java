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
import dto.NoticeFilesDTO;

public class NoticeFileDAO {
	private static NoticeFileDAO instance;
	NoticeBoardDAO nbdao = NoticeBoardDAO.getInstance();

	public synchronized static NoticeFileDAO getInstance() {
		if (instance == null) {
			instance = new NoticeFileDAO();
		}
		return instance;
	}

	private NoticeFileDAO() {}

	private Connection getConnection() throws Exception { 
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public String getSysName(int seq) throws Exception {
		String sql="select sysName from noticefiles where seq=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery()){
				rs.next();
				return rs.getNString("sysname");
			}
		}
	}
	
	
	public String getSysNameByparent(int seq) throws Exception { //게시글 번호로 실제 파일 sysname 받아오기
		String sql="select sysName from noticefiles where parent=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery()){
				rs.next();
				return rs.getNString("sysname");
			}
		}
	}
	public int fileUpload(NoticeFilesDTO dto) throws Exception {
		String sql="insert into noticefiles values(noticefiles_seq.nextval,?,?,sysdate,?)";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setNString(1, dto.getOriName());
			pstat.setNString(2, dto.getSysName());
			pstat.setInt(3,dto.getParent());
			int result = pstat.executeUpdate();
			return result;
		}
	}

	public List<NoticeFilesDTO>selectAll(int parent) throws Exception{
		String sql="select *from noticefiles where parent=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1,parent);					
			try(ResultSet rs =pstat.executeQuery();){
				List<NoticeFilesDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String oriName = rs.getNString("oriName");
					String sysName = rs.getNString("sysName");
					Date reg_date =rs.getDate("reg_date");
					int parent1 = rs.getInt("parent");

					NoticeFilesDTO dto = new NoticeFilesDTO(seq,oriName,sysName,reg_date,parent1);
					list.add(dto);
				}
				return list;
			}
		}	
	}

	public int fileDelete(int seq) throws SQLException, Exception {
		String sql="delete from noticeFiles where seq=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			return result;

		}
	}
	
	public int fileDeleteByparent(int seq) throws SQLException, Exception {
		String sql="delete from noticeFiles where parent=?";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setInt(1, seq);
			int result = pstat.executeUpdate();
			return result;

		}
	}
}

