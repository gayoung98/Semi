package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.FreeBoardDTO;
import dto.FreePoliceDTO;

public class FreePoliceDAO {
	private static FreePoliceDAO instance;

	public synchronized static FreePoliceDAO getInstance() {
		if (instance == null) {
			instance = new FreePoliceDAO();
		}
		return instance;
	}

	private FreePoliceDAO() {}

	private Connection getConnection() throws Exception { 
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}

	public int report(String id, String contents, String parent) throws Exception{ //게시글 신고하기
		String sql="insert into freepolice values(freepolice_seq.nextval,?,?,?,sysdate)";
		try(Connection con =this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql)){
			pstat.setNString(1, id);
			pstat.setNString(2, contents);
			pstat.setNString(3, parent);
			int result = pstat.executeUpdate();

			return result;
		}

	}

	public int PoliceCountPlus(int seq) throws Exception{ //신고횟수 증가!!
		String sql = "update freeBoard set PoliceCount = PoliceCount+1 where seq = ?";
		try(
				Connection con= this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				){			
			pstat.setInt(1, seq);

			int result = pstat.executeUpdate();

			return result;
		}
	}

	private int getPoliceCount(int parent) throws Exception{ //신고 카운팅 출력
		String sql= "select count(*) from freepolice where parent=?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				
				){
					pstat.setInt(1, parent);
			try(
					ResultSet rs = pstat.executeQuery();
					){

				rs.next();	
				return rs.getInt(1);	
			}
		}	
	}
	
	private int getPageCount() throws Exception{ //페이지 수 카운팅 출력
		String sql= "select count(*) from freeBoard";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs =pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);
		}

	}
//	public FreePoliceDTO boardView(int boardseq) throws Exception{ //게시글 조회수 출력
//		String sql = "select policeCount from freeBoard where seq = ?";
//		try(
//				Connection con = this.getConnection();
//				PreparedStatement pstat = con.prepareStatement(sql);
//				){
//			pstat.setInt(1, boardseq);
//			try(
//					ResultSet rs = pstat.executeQuery();
//					){
//				if(rs.next()) {
//					return new FreePoliceDTO(boardseq, rs.getInt("policeCount"));
//				}
//				return null;
//			}
//		}
//	}

} 